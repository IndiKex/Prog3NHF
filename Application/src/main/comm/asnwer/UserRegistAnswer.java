package main.comm.asnwer;

public class UserRegistAnswer implements Answer {
	public boolean successful;
	public boolean recieved;
	public String username;
	
	public UserRegistAnswer(boolean recieved, boolean successful, String username) {
		this.successful = successful;
		this.recieved = recieved;
		this.username = username;
	}
}
