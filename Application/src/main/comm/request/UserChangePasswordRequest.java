package main.comm.request;

import main.backend.continuos.RequestHandler;
import main.comm.asnwer.Answer;

public class UserChangePasswordRequest implements Request {
	public String username;
	public String password;
	
	public UserChangePasswordRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public Answer accept(RequestHandler handler) {
		return handler.handleRequest(this);
	}
}
