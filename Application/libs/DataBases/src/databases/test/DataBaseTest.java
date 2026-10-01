package databases.test;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import databases.main.interfaces.Table;
import databases.main.tables.NaiveTable;

public class DataBaseTest {

	public static void main(String[] args) {
		Table t = new NaiveTable("Stops");
		
		// TEST 1
		Test.addTest(new Test("1") {
			public boolean run() {
				String[] expectedFields = {"DefaultKeyID", "stop_id", "stop_code", "stop_name", "stop_desc", "stop_lat", "stop_lon", "zone_id", "stop_url", "location_type", "parent_station", "stop_timezone", "wheelchair_boarding"};
				
				t.loadCSV(new File("testFiles/csv/stops.txt"));
				List<String> fields = t.getFields();
				
				if (fields.size() != t.getFieldSize()) return false;
				else if (fields.size() != expectedFields.length) return false;
				else
					for (int i = 0; i < expectedFields.length; i++)
						if (!fields.get(i).equals(expectedFields[i]))
							return false;
				return true;
			}
		});
		
		// TEST 2
		String[] expectedZeroRowData = {"0", "70", "", "Búcsúszentlászló", "", "46.793056", "16.932778", "", "", "0", "", "", "2"};
		List<String> fields = t.getFields();
		
		Test.addTest(new Test("2") {
			public boolean run() {
				
				HashMap<String, String> row = t.searchRowByKey("0");
				
				for (int i = 0; i < t.getFieldSize(); i++)
					if (!row.get(fields.get(i)).equals(expectedZeroRowData[i]))
						return false;
				return true;
			}
		});
		
		// TEST 3
		String[] expected1193RowData = {"1193", "152470", "", "Szeged, Rókus vasútállomás", "", "46.266107", "20.125686", "", "", "0", "", "", "2"};
		
		Test.addTest(new Test("3") {
			public boolean run() {
				
				HashMap<String, String> row = t.searchRowByKey("1193");
				
				for (int i = 0; i < t.getFieldSize(); i++)
					if (!row.get(fields.get(i)).equals(expected1193RowData[i]))
						return false;
				return true;
			}
		});
		
		// TEST 4
		Test.addTest(new Test("4") {
			public boolean run() {
				for (int i = 0; i < t.getFieldSize(); i++) {
					String data = t.searchFieldByKey(fields.get(i), "0");
					if (!data.equals(expectedZeroRowData[i]))
						return false;
				}
				return true;
			}
		});
	}
	
}
