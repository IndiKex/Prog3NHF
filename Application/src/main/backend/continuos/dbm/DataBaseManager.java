package main.backend.continuos.dbm;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

public class DataBaseManager {
	private static DataBaseManager _instance;
	
	private File dataFolder = new File("data");
	
	public static DataBaseManager get_instance() {
		if (_instance == null) {
			_instance = new DataBaseManager();
		}
		return _instance;
	}
	
	private Map<String, Database> databases = new HashMap<>();
	
	public DataBaseManager() {
		initUserDatabase();
		initTicketDatabase();
		initFareDatabase();
	}
	
	private void initUserDatabase() {
		File dataFile = new File("data/user.dat");
		Database db;
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(dataFile));
			db = (UserDatabase) in.readObject();
		} catch (IOException e) {
			db = new UserDatabase(dataFile);
		} catch (ClassNotFoundException cryAboutIt) {
			db = null;
		}
		databases.put("user",  db);
	}
	
	private void initTicketDatabase() {
		File dataFile = new File("data/ticket.dat");
		Database db;
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(dataFile));
			db = (TicketDatabase) in.readObject();
		} catch (IOException e) {
			db = new TicketDatabase(dataFile);
		} catch (ClassNotFoundException cryAboutIt) {
			db = null;
		}
		databases.put("ticket",  db);
	}
	
	private void initFareDatabase() {
		File dataFile = new File("data/fare.dat");
		Database db;
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(dataFile));
			db = (FareDatabase) in.readObject();
		} catch (IOException e) {
			db = new FareDatabase(dataFile);
		} catch (ClassNotFoundException cryAboutIt) {
			db = null;
		}
		databases.put("fare",  db);
	}
	
	public Database getDatabase(String databaseName) {
		return databases.get(databaseName);
	}
	
	public void init() {
		if (!dataFolder.exists()) {
			dataFolder.mkdir();
		}
		
		for (Database db : databases.values()) {
			db.init();
		}
	}
	
	public void close() {
		for (Database db : databases.values()) {
			db.close();
		}
	}
}
