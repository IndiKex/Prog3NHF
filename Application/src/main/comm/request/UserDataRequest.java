package main.comm.request;

import main.backend.continuos.RequestHandler;
import main.comm.asnwer.Answer;

public class UserDataRequest implements Request {
	public String username;
	
	public UserDataRequest(String username) {
		this.username = username;
	}
	
	public Answer accept(RequestHandler handler) {
		return handler.handleRequest(this);
	}
}
