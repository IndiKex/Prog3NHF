package main.comm.asnwer;

public class TicketAddTicketAnswer implements Answer {
	public boolean recieved;
	public boolean successful;
	
	public TicketAddTicketAnswer(boolean recieved, boolean successful) {
		this.recieved = recieved;
		this.successful = successful;
	}
}
