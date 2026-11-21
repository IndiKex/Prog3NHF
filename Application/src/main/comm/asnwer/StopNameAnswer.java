package main.comm.asnwer;

import java.util.List;

public class StopNameAnswer implements Answer {
	public boolean recieved;
	public boolean successful;
	public List<String> stopNames;
	
	public StopNameAnswer(boolean recieved, boolean successful, List<String> stopNames) {
		this.recieved = recieved;
		this.successful = successful;
		this.stopNames = stopNames;
	}
}
