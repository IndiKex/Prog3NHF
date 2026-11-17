package main.comm.request;

import java.time.LocalDateTime;

public class TicketListRequest implements Request {
	public String startStopName;
	public String destStopName;
	public LocalDateTime startTime;
	
	public TicketListRequest(String startStopName, String destStopName, LocalDateTime startTime) {
		this.startStopName = startStopName;
		this.destStopName = destStopName;
		this.startTime = startTime;
	}
}
