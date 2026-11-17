package main.comm.asnwer;

import java.time.LocalDateTime;
import java.util.List;

public class TicketListAnswer implements Answer {
	public static class TicketData {
		public String ticketID;
		public LocalDateTime departureTime;
		public LocalDateTime arrivalTime;
		public int price;
		
		public TicketData(String ticketID, LocalDateTime departureTime, LocalDateTime arrivalTime, int price) {
			this.ticketID = ticketID;
			this.departureTime = departureTime;
			this.arrivalTime = arrivalTime;
			this.price = price;
		}
	}
	
	public boolean recieved;
	public boolean successful;
	public List<TicketData> data;
	
	public TicketListAnswer(boolean recieved, boolean successful, List<TicketData> data) {
		this.recieved = recieved;
		this.successful = successful;
		this.data = data;
	}
}
