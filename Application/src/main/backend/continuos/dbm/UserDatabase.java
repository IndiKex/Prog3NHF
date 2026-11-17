package main.backend.continuos.dbm;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class UserDatabase extends AbstractDatabase implements Serializable {
	private List<String> fieldNames = List.of("username", "password", "dateOfBirth", "IDNumber", "address");
	private int keyField = 0;
	
	public UserDatabase(File dataFile) {
		super(List.of("username", "password", "dateOfBirth", "IDNumber", "address"), 0, dataFile);
	}
	
}
