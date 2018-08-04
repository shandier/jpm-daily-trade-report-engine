package com.jpmc.coding.assignment.services.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jpmc.coding.assignment.model.TradeInstruction;
import com.jpmc.coding.assignment.model.TradeType;
import com.jpmc.coding.assignment.services.TradeInstructionReader;

/**
 * @FileInstructionReader is a strategy implementation
 *                        of @TradeInstructionReader which read data from a CSV
 *                        file source and parse it into trade instruction to
 *                        work on.
 * @FileInstructionReader constructor takes string source file as input to read
 *                        data from file. In future extension , it can be
 *                        extended to read other file formats also.
 *
 */
public class FileInstructionReader implements TradeInstructionReader {
    private final String FilePath;

    public FileInstructionReader(String filePath) {
	super();
	FilePath = filePath;
    }

    @Override
    public List<TradeInstruction> getTradeInstruction() {
	List<TradeInstruction> instructionslist = new ArrayList<>();
	try (Stream<String> stream = Files.lines(Paths.get(FilePath))) {
	    AtomicInteger linenumber = new AtomicInteger(1);
	    // filter blank lines
	    List<String> validLines = stream.skip(1).filter(line -> !line.equals("")).collect(Collectors.toList());
	    // read instructions line by line to fetch instructions
	    validLines.forEach(line -> {
		linenumber.getAndIncrement();
		fetchInstuction(instructionslist, line, linenumber);
	    });
	} catch (IOException e1) {
	    System.out.println("Unable to read souce file at" + FilePath);
	}
	return instructionslist;

    }

    /**
     * validate and read data from line and add valid instruction into the list
     * 
     * @param instructionslist
     *            reference to list of instructions
     * @param line
     *            line from file to parse
     * @param linenumber
     *            line number to track
     */
    private void fetchInstuction(List<TradeInstruction> instructionslist, String line, AtomicInteger linenumber) {
	String[] insrunctionData = line.split(",");
	if (ValidateRecord(insrunctionData, linenumber)) {
	    try {
		TradeInstruction instruction = parseInstructions(insrunctionData);
		instructionslist.add(instruction);
	    } catch (IllegalArgumentException | DateTimeParseException e) {
		System.out.println("Unable to parse, Skipping invalid record at line number " + linenumber);
	    }
	}
    }

    /**
     * Parse data into Instruction and build and instruction object
     * 
     * @param instructionData
     *            array of instruction fields values
     * @return @TradeInstruction
     * @throws IllegalArgumentException
     * @throws DateTimeParseException
     */
    private TradeInstruction parseInstructions(String[] instructionData)
	    throws IllegalArgumentException, DateTimeParseException {
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd MMM yyyy");
	return new TradeInstruction.InstructionBuilder().setTraderEntity(instructionData[0])
		.setTradeType(instructionData[1].equalsIgnoreCase("b") ? TradeType.BUY : TradeType.SELL)
		.setAgreedFX(Float.valueOf(instructionData[2])).setCurrency(Currency.getInstance(instructionData[3]))
		.setInstructionDate(LocalDate.parse(instructionData[4], dtf))
		.setSettlementDate(LocalDate.parse(instructionData[5], dtf))
		.setTradeUnits(Integer.valueOf(instructionData[6])).setPricePerUnit(Double.valueOf(instructionData[7]))
		.buildInstructions();
    }

    /**
     * validate instruction fields values
     * 
     * @param instructionData
     * @param linenumber
     * @return
     */
    private boolean ValidateRecord(String[] instructionData, AtomicInteger linenumber) {
	if (instructionData.length < 8 || Arrays.asList(instructionData).contains("")) {
	    System.out.println("Skipping invalid record at line number " + linenumber);
	    return false;
	}
	if (!(instructionData[1].equalsIgnoreCase("b") || instructionData[1].equalsIgnoreCase("s"))) {
	    System.out
		    .println("Trade type value should be B or S, Skipping invalid record at line number " + linenumber);
	    return false;
	}
	return true;
    }
}
