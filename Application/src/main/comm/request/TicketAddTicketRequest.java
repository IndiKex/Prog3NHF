package main.comm.request;

import main.backend.continuos.RequestHandler;
import main.comm.asnwer.Answer;
import main.comm.products.TicketData;

public class TicketAddTicketRequest implements Request {
	public String username;
	public TicketData ticketData;
	
	public TicketAddTicketRequest(String username, TicketData ticketData) {
		this.username = username;
		this.ticketData = ticketData;
	}
	
	public Answer accept(RequestHandler handler) {
		return handler.handleRequest(this);
	}
}
