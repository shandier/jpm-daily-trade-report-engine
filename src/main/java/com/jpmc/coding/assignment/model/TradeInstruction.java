package com.jpmc.coding.assignment.model;

import java.time.LocalDate;
import java.util.Currency;

public final class TradeInstruction {
    private final String traderEntity;
    private final float agreedFX;
    private final Currency currency;
    private final LocalDate instructionDate;
    private final LocalDate settlementDate;
    private final int tradeUnits;
    private final double pricePerUnit;
    private final TradeType tradeType;

    private TradeInstruction(InstructionBuilder instructionBuilder) {
	this.traderEntity = instructionBuilder.traderEntity;
	this.agreedFX = instructionBuilder.agreedFX;
	this.currency = instructionBuilder.currency;
	this.instructionDate = instructionBuilder.instructionDate;
	this.settlementDate = instructionBuilder.settlementDate;
	this.tradeUnits = instructionBuilder.tradeUnits;
	this.pricePerUnit = instructionBuilder.pricePerUnit;
	this.tradeType = instructionBuilder.tradeType;

    }

    public String getTraderEntity() {
	return traderEntity;
    }

    public float getAgreedFX() {
	return agreedFX;
    }

    public Currency getCurrency() {
	return currency;
    }

    public LocalDate getInstructionDate() {
	return instructionDate;
    }

    public LocalDate getSettlementDate() {
	return settlementDate;
    }

    public int getTradeUnits() {
	return tradeUnits;
    }

    public double getPricePerUnit() {
	return pricePerUnit;
    }

    public TradeType getTradeType() {
	return tradeType;
    }

    @Override
    public String toString() {
	return "TradeInstruction [traderEntity=" + traderEntity + ", agreedFX=" + agreedFX + ", currency=" + currency
		+ ", instructionDate=" + instructionDate + ", settlementDate=" + settlementDate + ", tradeUnits="
		+ tradeUnits + ", pricePerUnit=" + pricePerUnit + ", tradeType=" + tradeType + "]";
    }

    public static class InstructionBuilder {
	private String traderEntity;
	private float agreedFX;
	private Currency currency;
	private LocalDate instructionDate;
	private LocalDate settlementDate;
	private int tradeUnits;
	private double pricePerUnit;
	private TradeType tradeType;

	public InstructionBuilder() {
	}

	public InstructionBuilder setTraderEntity(String traderEntity) {
	    this.traderEntity = traderEntity;
	    return this;
	}

	public InstructionBuilder setAgreedFX(float agreedFX) {
	    this.agreedFX = agreedFX;
	    return this;
	}

	public InstructionBuilder setCurrency(Currency currency) {
	    this.currency = currency;
	    return this;
	}

	public InstructionBuilder setInstructionDate(LocalDate instructionDate) {
	    this.instructionDate = instructionDate;
	    return this;
	}

	public InstructionBuilder setSettlementDate(LocalDate settlementDate) {
	    this.settlementDate = settlementDate;
	    return this;
	}

	public InstructionBuilder setTradeUnits(int tradeUnits) {
	    this.tradeUnits = tradeUnits;
	    return this;
	}

	public InstructionBuilder setPricePerUnit(double pricePerUnit) {
	    this.pricePerUnit = pricePerUnit;
	    return this;
	}

	public InstructionBuilder setTradeType(TradeType tradeType) {
	    this.tradeType = tradeType;
	    return this;
	}

	public TradeInstruction buildInstructions() {
	    return new TradeInstruction(this);
	}
    }
}
