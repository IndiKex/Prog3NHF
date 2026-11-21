package main.comm.request;

import java.util.Date;

import main.backend.continuos.RequestHandler;
import main.comm.asnwer.Answer;
import main.util.Address;


public class UserRegistRequest implements Request {
	public String username;
	public String password;
	public String dateOfBirth;
	public String IDNumber;
	public String address;
	
	public UserRegistRequest(String username, String password, String dateOfBirth, String IDNumber, String address) {
		this.username = username;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.IDNumber = IDNumber;
		this.address = address;
	}
	
	public Answer accept(RequestHandler handler) {
		return handler.handleRequest(this);
	}
}
