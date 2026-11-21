package main.comm.request;

import main.backend.continuos.RequestHandler;
import main.comm.asnwer.Answer;
import main.comm.products.FareData;

public class TicketAddFareRequest implements Request {
	public String username;
	public FareData fareData;
	
	public TicketAddFareRequest(String username, FareData fareData) {
		this.username = username;
		this.fareData = fareData;
	}
	
	public Answer accept(RequestHandler handler) {
		return handler.handleRequest(this);
	}
}
