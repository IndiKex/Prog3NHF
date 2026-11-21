package main.comm.request;

import main.backend.continuos.RequestHandler;
import main.comm.asnwer.Answer;

public class UserAuthRequest implements Request {
	public String username;
	public String password;
	
	public UserAuthRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public Answer accept(RequestHandler handler) {
		return handler.handleRequest(this);
	}
}
