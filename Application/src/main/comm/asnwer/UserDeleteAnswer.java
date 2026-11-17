package main.comm.asnwer;

public class UserDeleteAnswer implements Answer {
	public boolean recieved;
	public boolean successful;
	
	public UserDeleteAnswer(boolean recieved, boolean successful) {
		this.recieved = recieved;
		this.successful = successful;
	}
}
