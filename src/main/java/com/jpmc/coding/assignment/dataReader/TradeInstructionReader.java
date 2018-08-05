package com.jpmc.coding.assignment.dataReader;

import java.util.List;

import com.jpmc.coding.assignment.model.TradeInstruction;

public interface TradeInstructionReader {
    /**
     * Read trade instructions from the source
     * 
     * @return list of trade instruction
     */
    List<TradeInstruction> getTradeInstruction();
}
