package com.jpmc.coding.assignment.test;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.jpmc.coding.assignment.model.TradeDetails;
import com.jpmc.coding.assignment.model.TradeInstruction;
import com.jpmc.coding.assignment.model.TradeType;
import com.jpmc.coding.assignment.services.InstructionManager;
import com.jpmc.coding.assignment.services.servicesImpl.InstructionManagerImpl;
import com.jpmc.coding.assignment.util.InstructionUtil;
import com.jpmc.coding.assignment.util.TradeUtil;

public class DailyTradingTest {
    private InstructionManager instructionManager = new InstructionManagerImpl();
    private List<TradeInstruction> tradingInstrunctions = new ArrayList<TradeInstruction>();

    @Before
    public void buildTestData() {

	TradeInstruction inst1 = buildTradeInstruction("foo", "b", "0.50", "SGD", "2018-08-01", "2018-08-04", "200",
		"100.25");

	TradeInstruction inst2 = buildTradeInstruction("bar", "s", "0.22", "AED", "2018-02-02", "2018-08-03", "450",
		"150.5");

	TradeInstruction inst3 = buildTradeInstruction("foo", "s", "0.30", "INR", "2018-08-01", "2018-08-04", "100",
		"130.25");

	TradeInstruction inst4 = buildTradeInstruction("bar", "b", "0.42", "INR", "2016-01-05", "2018-01-07", "400",
		"135.5");

	TradeInstruction inst5 = buildTradeInstruction("foo", "b", "0.20", "SGD", "2016-01-01", "2018-08-04", "140",
		"112.25");
	tradingInstrunctions.add(inst1);
	tradingInstrunctions.add(inst2);
	tradingInstrunctions.add(inst3);
	tradingInstrunctions.add(inst4);
	tradingInstrunctions.add(inst5);

    }

    @Test
    public void testsettleInstructionsWithNullAndEmptyList() {
	assertEquals(true, instructionManager.settleInstructions(null).isEmpty());
	assertEquals(true, instructionManager.settleInstructions(new ArrayList<TradeInstruction>()).isEmpty());
    }

    @Test
    public void testGetWorkingSettlementDate() {
	// Currency with weekend as Friday and Saturday
	Currency currency = Currency.getInstance("AED");
	// Currency with weekend as Saturday and Sunday
	Currency regularWeekendCurrency = Currency.getInstance("SGD");
	LocalDate weekDayFriday = LocalDate.parse("2018-08-03");
	LocalDate weekDaySunday = LocalDate.parse("2018-08-05");

	LocalDate nextWorkingDate = TradeUtil.getWorkingSettlementDate(currency, weekDayFriday);
	assertEquals(nextWorkingDate, weekDayFriday.plusDays(2));

	LocalDate nextWorkingDate2 = TradeUtil.getWorkingSettlementDate(regularWeekendCurrency, weekDayFriday);
	assertEquals(nextWorkingDate2, weekDayFriday);

	LocalDate nextWorkingDate3 = TradeUtil.getWorkingSettlementDate(regularWeekendCurrency, weekDaySunday);
	assertEquals(nextWorkingDate3, weekDaySunday.plusDays(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeGetWorkingSettlementDate() {
	LocalDate nextWorkingDate = TradeUtil.getWorkingSettlementDate(null, null);
	assertEquals(nextWorkingDate, LocalDate.now());

	LocalDate nextWorkingDate1 = TradeUtil.getWorkingSettlementDate(Currency.getInstance("fgd"),
		LocalDate.parse("2018-08-05"));
	assertEquals(nextWorkingDate1, LocalDate.now());
    }

    @Test
    public void testFilterInstructionsbySettlementDateAndTradeType() {
	List<TradeDetails> tradeDetails = instructionManager.settleInstructions(tradingInstrunctions);
	List<TradeDetails> fillteredTradesForBuy = InstructionUtil.filterInstructionsbySettlementDateAndTradeType(
		tradeDetails, LocalDate.parse("2018-08-06"), TradeType.BUY);
	List<TradeDetails> fillteredtradesForSell = InstructionUtil.filterInstructionsbySettlementDateAndTradeType(
		tradeDetails, LocalDate.parse("2018-08-06"), TradeType.SELL);
	assertEquals(fillteredTradesForBuy.size(), 2);
	assertEquals(fillteredtradesForSell.size(), 1);
    }

    @Test
    public void testGenerateDailyTradeReport() {
	List<TradeDetails> tradeDetails = instructionManager.settleInstructions(tradingInstrunctions);
	List<TradeDetails> fillteredTradesForBuy = InstructionUtil.filterInstructionsbySettlementDateAndTradeType(
		tradeDetails, LocalDate.parse("2018-08-06"), TradeType.BUY);
	List<TradeDetails> fillteredtradesForSell = InstructionUtil.filterInstructionsbySettlementDateAndTradeType(
		tradeDetails, LocalDate.parse("2018-08-06"), TradeType.SELL);
	assertEquals(fillteredTradesForBuy.size(), 2);
	assertEquals(fillteredtradesForSell.size(), 1);
    }

    private TradeInstruction buildTradeInstruction(String... instructionData) {
	return new TradeInstruction.InstructionBuilder().setTraderEntity(instructionData[0])
		.setTradeType(instructionData[1].equalsIgnoreCase("b") ? TradeType.BUY : TradeType.SELL)
		.setAgreedFX(Float.valueOf(instructionData[2])).setCurrency(Currency.getInstance(instructionData[3]))
		.setInstructionDate(LocalDate.parse(instructionData[4]))
		.setSettlementDate(LocalDate.parse(instructionData[5]))
		.setTradeUnits(Integer.valueOf(instructionData[6])).setPricePerUnit(Double.valueOf(instructionData[7]))
		.buildInstructions();
    }
}
