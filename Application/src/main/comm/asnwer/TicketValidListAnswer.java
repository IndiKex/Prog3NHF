package main.comm.asnwer;

import java.util.List;

import main.comm.products.FareData;
import main.comm.products.TicketData;

public class TicketValidListAnswer implements Answer {
	public boolean recieved;
	public boolean successful;
	public List<TicketData> ticketDatas;
	public List<FareData> fareDatas;
	
	public TicketValidListAnswer(boolean recieved, boolean successful, List<TicketData> ticketDatas, List<FareData> fareDatas) {
		this.recieved = recieved;
		this.successful = successful;
		this.ticketDatas = ticketDatas;
		this.fareDatas = fareDatas;
	}
}
