package com.jpmc.coding.assignment.service;

import java.time.LocalDate;
import java.util.List;

import com.jpmc.coding.assignment.model.TradeInstruction;
import com.jpmc.coding.assignment.model.TradeType;

public interface ReportManager {

    /**
     * Generate report for total number of outgoing and incoming instructions on
     * particular date.
     */
    void generateTradeReport(List<TradeInstruction> instructions, TradeType tradeType, LocalDate settlemetDate);

    /**
     * Method to identify top ranked entities for incoming and outgoing instructions
     */
    void generateDashboard(List<TradeInstruction> instructions, TradeType tradeType, LocalDate settlemetDate);
}
