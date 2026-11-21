package main.comm.asnwer;

public class TicketAddFareAnswer implements Answer {
	public boolean recieved;
	public boolean successful;
	
	public TicketAddFareAnswer(boolean recieved, boolean successful) {
		this.recieved = recieved;
		this.successful = successful;
	}
}
