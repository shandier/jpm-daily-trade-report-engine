package com.jpmc.coding.assignment.service;

import java.util.List;

import com.jpmc.coding.assignment.model.TradeDetails;
import com.jpmc.coding.assignment.model.TradeInstruction;

public interface InstructionManager {

    /**
     * Method to make modification in the instructions as per trade rules.
     */
    List<TradeDetails> processInstructions(List<TradeInstruction> tradeInstructions);
}
