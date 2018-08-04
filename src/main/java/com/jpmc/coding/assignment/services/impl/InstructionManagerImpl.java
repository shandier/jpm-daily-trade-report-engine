package com.jpmc.coding.assignment.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.jpmc.coding.assignment.model.TradeDetails;
import com.jpmc.coding.assignment.model.TradeInstruction;
import com.jpmc.coding.assignment.services.InstructionManager;
import com.jpmc.coding.assignment.util.InstructionUtil;
import com.jpmc.coding.assignment.util.TradeUtil;

public class InstructionManagerImpl implements InstructionManager {

    @Override
    public List<TradeDetails> settleInstructions(List<TradeInstruction> tradeInstructions) {
	List<TradeDetails> tradeDetailsList = new ArrayList<TradeDetails>();
	if (tradeInstructions == null || tradeInstructions.isEmpty()) {
	    System.out.println("No TradeInstructions found to settlement");
	} else {
	    tradeInstructions.forEach(instruction -> {
		if (instruction != null) {
		    tradeDetailsList.add(getTradeDetailsFromIstruction(instruction));
		}
	    });
	}
	return tradeDetailsList;
    }

    /**
     * apply trading settlement rule on received trade instructions
     * 
     * @param instruction:
     *            received trade instruction for settlement
     * @return TradeDetails object with required data for trade settlement
     */
    private TradeDetails getTradeDetailsFromIstruction(TradeInstruction instruction) {
	LocalDate updatedDated = TradeUtil.getWorkingSettlementDate(instruction.getCurrency(),
		instruction.getSettlementDate());
	double tradeUSD = InstructionUtil.calculateInstructionUSD(instruction);
	return new TradeDetails(instruction.getTraderEntity(), instruction.getTradeType(), updatedDated, tradeUSD);
    }
}
