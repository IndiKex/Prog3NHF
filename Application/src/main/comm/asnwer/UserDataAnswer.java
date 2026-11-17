package main.comm.asnwer;

public class UserDataAnswer implements Answer {
	public boolean recieved;
	public boolean successful;
	
	public String username;
	public String dateOfBirth;
	public String IDNumber;
	public String address;
	
	public UserDataAnswer(boolean recieved, boolean successful, String username, String dateOfBirth, String IDNumber, String address) {
		this.username = username;
		this.dateOfBirth = dateOfBirth;
		this.IDNumber = IDNumber;
		this.address = address;
	}
}
