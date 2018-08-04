package com.jpmc.coding.assignment.services;

import java.time.LocalDate;
import java.util.List;

import com.jpmc.coding.assignment.model.TradeInstruction;
import com.jpmc.coding.assignment.model.TradeType;

public interface ReportManager {

    /**
     * Generate report for total number of outgoing and incoming instructions on
     * particular date.
     * 
     * @param instructions
     *            : trade instructions
     * @param tradeType:
     *            buy or sell
     * @param settlemetDate:
     *            date for which data is required
     * @return
     */
    void generateTradeReport(List<TradeInstruction> instructions, TradeType tradeType, LocalDate settlemetDate);

    /**
     * Method to identify top ranked entities for incoming and outgoing instructions
     * 
     * @param instructions:
     *            instructions for trade
     * @param tradeType:
     *            buy or sell
     * @param settlemetDate:
     *            date for which data is required
     */
    void generateDashboard(List<TradeInstruction> instructions, TradeType tradeType, LocalDate settlemetDate);
}
