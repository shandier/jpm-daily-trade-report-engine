package com.jpmc.coding.assignment.services.servicesImpl;

import java.time.LocalDate;
import java.util.List;

import com.jpmc.coding.assignment.model.TradeDetails;
import com.jpmc.coding.assignment.model.TradeInstruction;
import com.jpmc.coding.assignment.model.TradeType;
import com.jpmc.coding.assignment.services.InstructionManager;
import com.jpmc.coding.assignment.services.ReportManager;
import com.jpmc.coding.assignment.util.InstructionUtil;

public class ReportManagerImpl implements ReportManager {

    private final InstructionManager instructionManager = new InstructionManagerImpl();

    @Override
    public void generateTradeReport(List<TradeInstruction> instructions, TradeType tradeType, LocalDate settlemetDate) {
	double totalTrade = 0;
	if (instructions == null || instructions.isEmpty()) {
	    System.out.println("No trade instructions found to generate report");
	} else {
	    List<TradeDetails> tradeDetails = getFilteredTradeDetails(instructions, tradeType, settlemetDate);
	    // calculate total trade USD
	    totalTrade = tradeDetails.stream().map(tradeDetail -> tradeDetail.getTradeUSD()).reduce(0.0, Double::sum);
	    String reportFor = TradeType.BUY.equals(tradeType) ? InstructionUtil.OUTGOING : InstructionUtil.INCOMING;
	    // report text to display
	    String reportText = String.format("Total %s on %s is %f ", reportFor, settlemetDate.toString(), totalTrade);
	    System.out.println(reportText + "\n");
	}
    }

    @Override
    public void generateDashboard(List<TradeInstruction> instructions, TradeType tradeType, LocalDate settlemetDate) {
	if (instructions == null || instructions.isEmpty()) {
	    System.out.println("No trade Instructions found to generate report");
	} else {
	    List<TradeDetails> tradeDetails = getFilteredTradeDetails(instructions, tradeType, settlemetDate);
	    String reportFor = TradeType.BUY.equals(tradeType) ? InstructionUtil.OUTGOING : InstructionUtil.INCOMING;
	    // sort in descending order
	    tradeDetails.sort((trade1, trade2) -> trade2.getTradeUSD().compareTo(trade1.getTradeUSD()));
	    System.out.println("Top entites for " + reportFor + "\n");
	    tradeDetails.forEach((tradeDetail) -> {
		String reportText = String.format("Entity %s with total %s is %f ", tradeDetail.getEntity(), reportFor,
			tradeDetail.getTradeUSD());
		System.out.println(reportText);
	    });
	}
    }

    /**
     * Apply trading rules on received instructions and return list of TradeDetails
     * to settle the trade.
     * 
     * @param instructions:
     *            received instructions
     * @param tradeType
     * @param settlemetDate
     * @return list of TradeDetails.
     */
    private List<TradeDetails> getFilteredTradeDetails(List<TradeInstruction> instructions, TradeType tradeType,
	    LocalDate settlemetDate) {
	List<TradeDetails> tradeDetails = instructionManager.settleInstructions(instructions);
	tradeDetails = InstructionUtil.filterInstructionsbySettlementDateAndTradeType(tradeDetails, settlemetDate,
		tradeType);
	return tradeDetails;
    }

}
