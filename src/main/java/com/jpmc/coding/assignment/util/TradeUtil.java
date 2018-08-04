package com.jpmc.coding.assignment.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Currency;

/**
 * Application utility class
 * 
 */

public class TradeUtil {
    /**
     * Check if day of week for provided currency is a working day or not
     * 
     * @param currency
     * @param dayOfWeek
     * @return True if day is not a working day, otherwise false.
     */
    public static boolean isWeekend(Currency currency, DayOfWeek dayOfWeek) {
	if (Currency.getInstance("AED").equals(currency) || Currency.getInstance("SAR").equals(currency)) {
	    if (DayOfWeek.FRIDAY.equals(dayOfWeek) || DayOfWeek.SATURDAY.equals(dayOfWeek)) {
		return true;
	    }
	} else if (DayOfWeek.SATURDAY.equals(dayOfWeek) || DayOfWeek.SUNDAY.equals(dayOfWeek)) {
	    return true;
	}
	return false;
    }

    /**
     * Check and return next working day for provided currency, if provided date is
     * not a working day.
     * 
     * @param currency
     * @param localDate
     * @return next working date if provided date is not working day, same date
     *         otherwise.
     */
    public static LocalDate getWorkingSettlementDate(Currency currency, LocalDate localDate) {
	if (currency == null || localDate == null) {
	    System.out.println("Invalid arguments");
	    return LocalDate.now();
	}
	try {
	    if (isWeekend(currency, localDate.getDayOfWeek())) {
		localDate = getWorkingSettlementDate(currency, localDate.plusDays(1));
	    }
	} catch (IllegalArgumentException e) {
	    System.out.println("Invalid arguments");
	    return LocalDate.now();
	}
	return localDate;
    }
}
