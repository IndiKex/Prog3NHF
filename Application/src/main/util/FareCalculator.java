package main.util;

import java.time.LocalDate;

public class FareCalculator {
	public static LocalDate calculate(LocalDate validityStart, String validityLength) {
		int plusDays = 0;
		switch (validityLength) {
		case "2 hét":
			plusDays = 14;
			break;
		case "1 hónap":
			plusDays = 30;
			break;
		case "1 év":
			plusDays = 365;
			break;
		}
		
		return validityStart.plusDays(plusDays);
	}
	
	public static int priceCalculator(String validityLength) {
		switch (validityLength) {
		case "2 hét": return 7500;
		case "1 hónap": return 9850;
		case "1 év": return 75000;
		default: return -1;
		}
	}
}
