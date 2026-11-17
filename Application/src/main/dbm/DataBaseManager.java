package main.dbm;

import java.io.File;
import java.util.Map;

import main.dbm.user.UserAuthDatabase;
import main.dbm.user.UserDataDatabase;
import main.dbm.user.UserIDDatabase;

public class DataBaseManager {
	private static DataBaseManager _instance;
	
	private File dataFolder = new File("data");
	private File cacheFolder = new File("data/cache");
	
	public static DataBaseManager get_instance() {
		if (_instance == null) {
			_instance = new DataBaseManager();
		}
		return _instance;
	}
	
	private Map<String, Database> databases = Map.of(
			"UserData", new UserDataDatabase(),
			"UserID", new UserIDDatabase(),
			"UserAuth", new UserAuthDatabase()
	);
	
	public Database getDatabase(String databaseName) {
		return databases.get(databaseName);
	}
	
	public void init() {
		if (!dataFolder.exists()) {
			dataFolder.mkdir();
		}
		if (!cacheFolder.exists()) {
			cacheFolder.mkdir();
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
