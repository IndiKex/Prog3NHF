package main.backend.continuos.dbm;

import java.io.File;
import java.util.List;

public class FareDatabase extends AbstractDatabase implements Database {
	public FareDatabase(File dataFile) {
		super(List.of("username_plus_id", "area", "validity_date", "validity_length"), 0, dataFile);
	}
}
