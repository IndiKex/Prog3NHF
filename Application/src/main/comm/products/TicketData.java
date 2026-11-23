package main.comm.products;

import java.time.LocalDateTime;
import java.util.List;

public class TicketData implements ProductData {
	private String ticketID;
	private String departureStopName;
	private String arrivalStopName;
	private LocalDateTime departureTime;
	private LocalDateTime arrivalTime;
	private int price;
	
	public TicketData(String ticketID, String departureStopName, String arrivalStopName, LocalDateTime departureTime, LocalDateTime arrivalTime, int price) {
		this.ticketID = ticketID;
		this.departureStopName = departureStopName;
		this.arrivalStopName = arrivalStopName;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.price = price;
	}
	
	public List<String> getData() {
		return List.of(ticketID, departureStopName, arrivalStopName, departureTime.toString(), arrivalTime.toString(), Integer.toString(price));
	}
}
