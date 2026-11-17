package main.comm.asnwer;

public class UserAuthAnswer implements Answer {
	public boolean recieved;
	public boolean successful;
	public String username;
	
	public UserAuthAnswer(boolean recieved, boolean successful, String username) {
		this.recieved = recieved;
		this.successful = successful;
		this.username = username;
	}
}
