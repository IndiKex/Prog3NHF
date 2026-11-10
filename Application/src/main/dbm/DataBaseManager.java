package main.dbm;

import java.util.Map;

import main.dbm.user.UserDataDatabase;
import main.dbm.user.UserIDDatabase;

public class DataBaseManager {
	private static DataBaseManager _instance;
	
	public static DataBaseManager get_instance() {
		if (_instance == null) {
			_instance = new DataBaseManager();
		}
		return _instance;
	}
	
	private Map<String, Database> databases = Map.of(
			"UserData", new UserDataDatabase(),
			"UserID", new UserIDDatabase()
	);
	
	public Database getDatabase(String databaseName) {
		return databases.get(databaseName);
	}
	
	public void init() {
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
