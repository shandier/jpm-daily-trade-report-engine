package com.jpmc.coding.assignment.model;

import java.time.LocalDate;

public final class TradeDetails {
    private final String entity;
    private final TradeType tradeType;
    private final LocalDate settlementDate;
    private final Double tradeUSD;

    public TradeDetails(String entity, TradeType tradeType, LocalDate settlementDate, double tradeUSD) {
	super();
	this.entity = entity;
	this.tradeType = tradeType;
	this.settlementDate = settlementDate;
	this.tradeUSD = tradeUSD;
    }

    public String getEntity() {
	return entity;
    }

    public TradeType getTradeType() {
	return tradeType;
    }

    public LocalDate getSettlementDate() {
	return settlementDate;
    }

    public Double getTradeUSD() {
	return tradeUSD;
    }

    @Override
    public String toString() {
	return "TradeDetails [entity=" + entity + ", tradeType=" + tradeType + ", settlementDate=" + settlementDate
		+ ", tradeUSD=" + tradeUSD + "]";
    }

}
