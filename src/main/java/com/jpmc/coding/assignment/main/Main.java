package com.jpmc.coding.assignment.main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import com.jpmc.coding.assignment.dataReader.readerImpl.FileInstructionReader;
import com.jpmc.coding.assignment.model.TradeInstruction;
import com.jpmc.coding.assignment.model.TradeType;
import com.jpmc.coding.assignment.services.ReportManager;
import com.jpmc.coding.assignment.services.servicesImpl.ReportManagerImpl;

public class Main {
    public static void main(String[] args) {
	ReportManager reportingService = new ReportManagerImpl();
	// file path to read instruction
	String source = "";
	// trade type for report generation
	TradeType tradeType = TradeType.BUY;
	// reporting date
	LocalDate date = LocalDate.now();
	if (args.length >= 1) {
	    source = args[0];
	}
	if (args.length >= 2) {
	    tradeType = args[1].equalsIgnoreCase("b") ? TradeType.BUY
		    : args[1].equalsIgnoreCase("s") ? TradeType.SELL : TradeType.BUY;
	}
	if (args.length >= 3) {
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
	    try {
		date = LocalDate.parse(args[2], dtf);
	    } catch (DateTimeParseException e) {
		date = LocalDate.now();
	    }
	}
	// Read instructions from file
	List<TradeInstruction> tradeInstructions = new FileInstructionReader(source).getTradeInstruction();
	// Generate trade type report for given date
	reportingService.generateTradeReport(tradeInstructions, tradeType, date);
	// Generate trade type dashboard for given date
	reportingService.generateDashboard(tradeInstructions, tradeType, date);
    }

}
