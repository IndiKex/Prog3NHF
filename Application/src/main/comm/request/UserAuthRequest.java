package main.comm.request;

public class UserAuthRequest implements Request {
	public String username;
	public String password;
	
	public UserAuthRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
