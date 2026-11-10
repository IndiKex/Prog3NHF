package main.comm.asnwer;

public class UserRegistAnswer implements Answer {
	public boolean successful;
	public boolean recieved;
	
	public UserRegistAnswer(boolean successful, boolean recieved) {
		this.successful = successful;
		this.recieved = recieved;
	}
}
