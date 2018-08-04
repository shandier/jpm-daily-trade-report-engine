package com.jpmc.coding.assignment.services;

import java.util.List;

import com.jpmc.coding.assignment.model.TradeDetails;
import com.jpmc.coding.assignment.model.TradeInstruction;

public interface InstructionManager {

    /**
     * Method to make modification in the instructions as per trade rules.
     * 
     * @param tradeInstructions:
     *            list of TradeInstructions received.
     * @return List<TradeDetails> List of TradeDetails, as per trade
     *         rules.
     */
    List<TradeDetails> settleInstructions(List<TradeInstruction> tradeInstructions);
}
