package main.dbm.user;

import java.io.File;
import java.util.List;

import main.dbm.Data.DataTypes;
import main.dbm.Database;
import main.dbm.EmptyDatabase;

public class UserIDDatabase extends EmptyDatabase implements Database {
	public UserIDDatabase() {
		super(
				new File("data/userID.csv"),
				new File("data/cache/userid_cache.dat"),
				List.of("username", "userID", "valid"),
				List.of(DataTypes.STRING, DataTypes.INT, DataTypes.INT)
		);
	}
}
