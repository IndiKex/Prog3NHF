package main.comm.products;

import java.time.LocalDate;
import java.util.List;

public class FareData implements ProductData {
	private String area;
	private LocalDate validityDate;
	private String validityLength;
	private int price;
	
	public FareData(String area, LocalDate validityDate, String validityLength, int price) {
		this.area = area;
		this.validityDate = validityDate;
		this.validityLength = validityLength;
		this.price = price;
	}
	
	public List<String> getData() {
		return List.of(area, validityDate.toString(), validityLength, Integer.toString(price));
	}
}
