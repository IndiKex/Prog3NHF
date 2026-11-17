package main.dbm.user;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.dbm.Data;
import main.dbm.Data.DataTypes;
import main.dbm.Database;
import main.dbm.EmptyDatabase;

public class UserDataDatabase extends EmptyDatabase implements Database {
	public UserDataDatabase() {
		super(
				new File("data/userData.csv"),
				new File("data/cache/userdata_cache.dat"),
				List.of("userID", "username", "date_of_birth", "id_number", "address"),
				List.of(DataTypes.INT, DataTypes.STRING, DataTypes.STRING, DataTypes.STRING, DataTypes.STRING)
		);
	}
}
