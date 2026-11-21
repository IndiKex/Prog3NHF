package main.comm.asnwer;

import java.util.List;

import main.comm.products.TicketData;

public class TicketListAnswer implements Answer {
	public boolean recieved;
	public boolean successful;
	public List<TicketData> data;
	
	public TicketListAnswer(boolean recieved, boolean successful, List<TicketData> data) {
		this.recieved = recieved;
		this.successful = successful;
		this.data = data;
	}
}
