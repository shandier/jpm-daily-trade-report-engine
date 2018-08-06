package com.jpmc.coding.assignment.util;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.jpmc.coding.assignment.model.TradeDetails;
import com.jpmc.coding.assignment.model.TradeInstruction;
import com.jpmc.coding.assignment.model.TradeType;

/**
 * Utility class specifically to provide instruction based utility methods
 *
 */
public class InstructionUtil {
    public static final String OUTGOING = "Outgoing";
    public static final String INCOMING = "Incoming";

    /**
     * filter instructions based on trade type and date
     */
    public static List<TradeDetails> filterInstructionsbySettlementDateAndTradeType(List<TradeDetails> tradeDetails,
	    LocalDate settlemetDate, TradeType tradeType) {
	if (tradeDetails == null || tradeDetails.isEmpty()) {
	    return tradeDetails;
	} else {
	    return tradeDetails.stream().filter(tradeDetail -> (tradeDetail.getSettlementDate().equals(settlemetDate)
		    && tradeDetail.getTradeType().equals(tradeType))).collect(Collectors.toList());
	}
    }

    public static double calculateInstructionUSD(TradeInstruction instruction) {

	return (instruction != null)
		? instruction.getAgreedFX() * instruction.getPricePerUnit() * instruction.getTradeUnits()
		: 0.0;
    }
}
