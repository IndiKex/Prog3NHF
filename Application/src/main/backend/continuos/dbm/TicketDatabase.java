package main.backend.continuos.dbm;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class TicketDatabase extends AbstractDatabase implements Serializable {
	private List<String> fieldNames = List.of("username_plus_id", "trainID", "departure_time", "arrival_time");
	private int keyField = 0;
	
	public TicketDatabase(File dataFile) {
		super(List.of("username_plus_id", "trainID", "departure_stop_name", "arrival_stop_name", "departure_time", "arrival_time"), 0, dataFile);
	}
	
}
