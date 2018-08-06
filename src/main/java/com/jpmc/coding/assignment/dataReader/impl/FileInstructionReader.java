package com.jpmc.coding.assignment.dataReader.impl;

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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.jpmc.coding.assignment.dataReader.TradeInstructionReader;
import com.jpmc.coding.assignment.model.TradeInstruction;
import com.jpmc.coding.assignment.model.TradeType;

/**
 * FileInstructionReader is a strategy implementation of TradeInstructionReader
 * which read data from a CSV file source and parse it into trade instructions.
 */
public class FileInstructionReader implements TradeInstructionReader {
    private final String FilePath;
    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("dd MMM yyyy");

    public FileInstructionReader(String filePath) {
	FilePath = filePath;
    }

    @Override
    public List<TradeInstruction> getTradeInstruction() throws IOException {
	List<TradeInstruction> instructionslist = new ArrayList<>();
	try (Stream<String> stream = Files.lines(Paths.get(FilePath))) {
	    // filter blank lines
	    List<String> validLines = stream.skip(1).filter(line -> !line.equals("")).collect(Collectors.toList());
	    int linenumber = 1;
	    // parse instructions
	    for (String line : validLines) {
		fetchInstuctions(instructionslist, line, ++linenumber);
	    }
	} catch (IOException e) {
	    throw new IOException("ERROR: Unable to read souce file at " + FilePath);
	}
	return instructionslist;
    }

    /**
     * validate and read data from line and add valid instruction into the list
     */
    private void fetchInstuctions(List<TradeInstruction> instructionslist, String line, int linenumber) {
	String[] insrunctionData = line.split(",");
	if (ValidateRecord(insrunctionData, linenumber)) {
	    try {
		TradeInstruction instruction = parseInstructions(insrunctionData);
		instructionslist.add(instruction);
	    } catch (IllegalArgumentException | DateTimeParseException e) {
		System.out.println(
			String.format("INFO: Unable to parse data, Skipping invalid record at line number %d, %s",
				linenumber, e.getMessage()));
	    }
	}
    }

    /**
     * Parse data into Instruction and build and instruction object
     */
    private TradeInstruction parseInstructions(String[] instructionData)
	    throws IllegalArgumentException, DateTimeParseException {
	return new TradeInstruction.InstructionBuilder().setTraderEntity(instructionData[0])
		.setTradeType(instructionData[1].equalsIgnoreCase("b") ? TradeType.BUY : TradeType.SELL)
		.setAgreedFX(Float.valueOf(instructionData[2])).setCurrency(Currency.getInstance(instructionData[3]))
		.setInstructionDate(LocalDate.parse(instructionData[4], DTF))
		.setSettlementDate(LocalDate.parse(instructionData[5], DTF))
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
    private boolean ValidateRecord(String[] instructionData, int linenumber) {
	if (instructionData.length < 8) {
	    System.out.println(String.format("INFO: Missing column at line %d, Skipping the record", linenumber));
	    return false;
	}
	if (Arrays.asList(instructionData).contains("")) {
	    System.out.println(String.format("INFO: Blank column found at line %d, Skipping the record", linenumber));
	    return false;
	}
	if (!(instructionData[1].equalsIgnoreCase("b") || instructionData[1].equalsIgnoreCase("s"))) {
	    System.out.println(
		    String.format("INFO: Trade type value should be B or S, Skipping record at line %d" + linenumber));
	    return false;
	}
	return true;
    }
}
