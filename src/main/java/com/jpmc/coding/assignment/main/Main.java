package com.jpmc.coding.assignment.main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.jpmc.coding.assignment.dataReader.impl.FileInstructionReader;
import com.jpmc.coding.assignment.model.TradeInstruction;
import com.jpmc.coding.assignment.model.TradeType;
import com.jpmc.coding.assignment.service.ReportManager;
import com.jpmc.coding.assignment.service.impl.InstructionManagerImpl;
import com.jpmc.coding.assignment.service.impl.ReportManagerImpl;

public class Main {
    public static void main(String[] args) {
	// file path to read instruction
	String source;
	// trade type for report generation
	TradeType tradeType;
	// reporting date
	LocalDate date;
	try {
	    if (args.length < 1 || args.length > 3) {
		throw new IllegalArgumentException(
			"Invalid arguments provided. Valid arguments are Filepath, TradeType, reportingDate");
	    }
	    source = args[0];
	    if (args[1].equalsIgnoreCase("b")) {
		tradeType = TradeType.BUY;
	    } else if (args[1].equalsIgnoreCase("s")) {
		tradeType = TradeType.SELL;
	    } else {
		throw new IllegalArgumentException(
			"Invalid Value for TradeType. TradeType values should be 'b' or'B' for Buy and  's' or 'S' for Sell");
	    }
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
	    date = LocalDate.parse(args[2], dtf);
	    // Read instructions from file
	    List<TradeInstruction> tradeInstructions = new FileInstructionReader(source).getTradeInstruction();
	    ReportManager reportingService = new ReportManagerImpl(new InstructionManagerImpl());
	    // Generate trade type report for given date and trade type
	    reportingService.generateTradeReport(tradeInstructions, tradeType, date);
	    // Generate trade type dashboard for given date
	    reportingService.generateDashboard(tradeInstructions, tradeType, date);
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
    }
}