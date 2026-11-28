package main.util;

import java.time.LocalDate;

/**
 * Calculator class for calculating fare price and the end of validity for a fare
 */
public class FareCalculator {
	/**
	 * Enum for validity length of a fare.
	 */
	public static enum FareValidityLength {
		TWOWEEKS,
		ONEMONTH,
		ONEYEAR
	}
	
	/**
	 * Calculates the end of validity for a fare
	 * 
	 * @param validityStart the time of the start of validity
	 * @param validityLength the length of validity in strings
	 * @return start of validity plus the length of validity = end of validity
	 */
	public static LocalDate calculate(LocalDate validityStart, FareValidityLength validityLength) {
		int plusDays = 0;
		switch (validityLength) {
		case FareValidityLength.TWOWEEKS:
			plusDays = 14;
			break;
		case FareValidityLength.ONEMONTH:
			plusDays = 30;
			break;
		case FareValidityLength.ONEYEAR:
			plusDays = 365;
			break;
		}
		
		return validityStart.plusDays(plusDays);
	}
	
	/**
	 * Calculates the fare price based on length of validity
	 * 
	 * @param validityLength length of validity format
	 * @return price of the fare
	 */
	public static int priceCalculator(FareValidityLength validityLength) {
		switch (validityLength) {
		case FareValidityLength.TWOWEEKS: return 7500;
		case FareValidityLength.ONEMONTH: return 9850;
		case FareValidityLength.ONEYEAR: return 75000;
		default: return -1;
		}
	}
}
