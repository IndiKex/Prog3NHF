package main.comm.request;

public class UserDataRequest implements Request {
	public String username;
	
	public UserDataRequest(String username) {
		this.username = username;
	}
}
