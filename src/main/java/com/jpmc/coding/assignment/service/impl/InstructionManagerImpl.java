package com.jpmc.coding.assignment.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.jpmc.coding.assignment.model.TradeDetails;
import com.jpmc.coding.assignment.model.TradeInstruction;
import com.jpmc.coding.assignment.service.InstructionManager;
import com.jpmc.coding.assignment.util.InstructionUtil;
import com.jpmc.coding.assignment.util.TradeUtil;

public class InstructionManagerImpl implements InstructionManager {

    @Override
    public List<TradeDetails> processInstructions(List<TradeInstruction> tradeInstructions) {
	List<TradeDetails> tradeDetailsList = new ArrayList<TradeDetails>();
	if (tradeInstructions == null || tradeInstructions.isEmpty()) {
	    throw new IllegalArgumentException("ERROR: No trade instructions found for processing");
	}
	tradeInstructions.forEach(instruction -> {
	    if (instruction != null) {
		tradeDetailsList.add(getTradeDetailsFromIstruction(instruction));
	    }
	});
	return tradeDetailsList;
    }

    /**
     * Apply trading settlement rule on received trade instructions
     */
    private TradeDetails getTradeDetailsFromIstruction(TradeInstruction instruction) {
	LocalDate updatedDated = TradeUtil.getWorkingSettlementDate(instruction.getCurrency(),
		instruction.getSettlementDate());
	double tradeUSD = InstructionUtil.calculateInstructionUSD(instruction);
	return new TradeDetails(instruction.getTraderEntity(), instruction.getTradeType(), updatedDated, tradeUSD);
    }
}
