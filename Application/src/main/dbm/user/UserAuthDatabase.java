package main.dbm.user;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.dbm.Data;
import main.dbm.Data.DataTypes;
import main.dbm.Database;
import main.dbm.EmptyDatabase;

public class UserAuthDatabase extends EmptyDatabase implements Database {
	
	public UserAuthDatabase() {
		super(
				new File("data/userAuth.csv"),
				new File("data/cache/userauth_cache.dat"),
				List.of("userID", "password"),
				List.of(DataTypes.INT, DataTypes.STRING)
		);
	}
	
}
