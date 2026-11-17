package main.comm.request;

public class UserDeleteRequest implements Request {
	public String username;
	
	public UserDeleteRequest(String username) {
		this.username = username;
	}
}
