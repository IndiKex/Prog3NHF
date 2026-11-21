package main.comm.request;

import java.time.LocalDateTime;

import main.backend.continuos.RequestHandler;
import main.comm.asnwer.Answer;

public class TicketListRequest implements Request {
	public String startStopName;
	public String destStopName;
	public LocalDateTime startTime;
	public int peopleNum;
	
	public TicketListRequest(String startStopName, String destStopName, LocalDateTime startTime, int peopleNum) {
		this.startStopName = startStopName;
		this.destStopName = destStopName;
		this.startTime = startTime;
		this.peopleNum = peopleNum;
	}
	
	public Answer accept(RequestHandler handler) {
		return handler.handleRequest(this);
	}
}
