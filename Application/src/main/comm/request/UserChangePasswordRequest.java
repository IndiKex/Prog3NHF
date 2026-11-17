package main.comm.request;

public class UserChangePasswordRequest implements Request {
	public String username;
	public String password;
	
	public UserChangePasswordRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
