package com.jpmc.coding.assignment.service.impl;

import java.time.LocalDate;
import java.util.List;

import com.jpmc.coding.assignment.model.TradeDetails;
import com.jpmc.coding.assignment.model.TradeInstruction;
import com.jpmc.coding.assignment.model.TradeType;
import com.jpmc.coding.assignment.service.InstructionManager;
import com.jpmc.coding.assignment.service.ReportManager;
import com.jpmc.coding.assignment.util.InstructionUtil;

public class ReportManagerImpl implements ReportManager {

    private final InstructionManager instructionManager;

    public ReportManagerImpl(InstructionManager instructionManager) {
	this.instructionManager = instructionManager;
    }

    @Override
    public void generateTradeReport(List<TradeInstruction> instructions, TradeType tradeType, LocalDate settlemetDate) {
	List<TradeDetails> tradeDetails = getFilteredTradeDetails(instructions, tradeType, settlemetDate);
	// calculate total trade USD
	double totalTrade = tradeDetails.stream().map(tradeDetail -> tradeDetail.getTradeUSD()).reduce(0.0,
		Double::sum);
	String reportFor = TradeType.BUY.equals(tradeType) ? InstructionUtil.OUTGOING : InstructionUtil.INCOMING;
	// report text to display
	System.out.println(String.format("Total %s on %s is %f ", reportFor, settlemetDate.toString(), totalTrade));
    }

    @Override
    public void generateDashboard(List<TradeInstruction> instructions, TradeType tradeType, LocalDate settlemetDate) {
	List<TradeDetails> tradeDetails = getFilteredTradeDetails(instructions, tradeType, settlemetDate);
	String reportFor = TradeType.BUY.equals(tradeType) ? InstructionUtil.OUTGOING : InstructionUtil.INCOMING;
	// sort in descending order
	tradeDetails.sort((trade1, trade2) -> trade2.getTradeUSD().compareTo(trade1.getTradeUSD()));
	System.out.println("Top entites for " + reportFor + "\n");
	tradeDetails.forEach((tradeDetail) -> {
	    System.out.println(String.format("Entity %s with total %s is %f ", tradeDetail.getEntity(), reportFor,
		    tradeDetail.getTradeUSD()));
	});
    }

    /**
     * Apply trading rules on received instructions and return list of TradeDetails
     * to settle the trade.
     */
    private List<TradeDetails> getFilteredTradeDetails(List<TradeInstruction> instructions, TradeType tradeType,
	    LocalDate settlemetDate) {
	if (instructions == null || instructions.isEmpty()) {
	    throw new IllegalArgumentException("No trade instructions found to generate report");
	}
	List<TradeDetails> tradeDetails = instructionManager.processInstructions(instructions);
	return InstructionUtil.filterInstructionsbySettlementDateAndTradeType(tradeDetails, settlemetDate, tradeType);
    }
}
