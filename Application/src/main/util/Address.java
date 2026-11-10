package main.util;

public class Address {
	public int postcode;
	public String town;
	public String street;
	public int houseNum;
	
	public Address(int postcode, String town, String street, int houseNum) {
		this.postcode = postcode;
		this.town = town;
		this.street = street;
		this.houseNum = houseNum;
	}
}
