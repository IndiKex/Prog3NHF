package main.comm.asnwer;

public class UserChangePasswordAnswer implements Answer {
	public boolean recieved;
	public boolean successful;
	
	public UserChangePasswordAnswer(boolean recieved, boolean successful) {
		this.recieved = recieved;
		this.successful = successful;
	}
}
