package main.comm.products;

import java.time.LocalDateTime;
import java.util.List;

public class TicketData implements ProductData {
	private String ticketID;
	private LocalDateTime departureTime;
	private LocalDateTime arrivalTime;
	private int price;
	
	public TicketData(String ticketID, LocalDateTime departureTime, LocalDateTime arrivalTime, int price) {
		this.ticketID = ticketID;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.price = price;
	}
	
	public List<String> getData() {
		return List.of(ticketID, departureTime.toString(), arrivalTime.toString(), Integer.toString(price));
	}
}
