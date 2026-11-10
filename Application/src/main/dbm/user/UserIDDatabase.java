package main.dbm.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.dbm.Data;
import main.dbm.Data.DataTypes;
import main.dbm.Database;
import main.io.csvparser.CSVParser;
import main.io.csvparser.CSVWriter;

public class UserIDDatabase implements Database {
	private File userDataFile = new File("data/userID.csv");
	private File userDataCacheFile = new File("data/cache/userid_cache.dat");
	
	private List<String> fieldNames = List.of("username", "userID");
	private List<DataTypes> fieldTypes = List.of(DataTypes.STRING, DataTypes.STRING);
	private Map<Data, Integer> cache = new HashMap<>();
	
	public void init() {
		try {
			if (!userDataFile.exists()) {
				userDataFile.createNewFile();
				CSVWriter.write(userDataFile, fieldNames, new ArrayList<>());
			}
			if (!userDataCacheFile.exists()) {
				userDataCacheFile.createNewFile();
			} else {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(userDataCacheFile));
				cache = (Map<Data, Integer>) in.readObject();
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(userDataCacheFile));
			out.writeObject(cache);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getNRecords() {
		return cache.keySet().size();
	}
	
	public boolean writeRecord(Data key, List<Data> record) {
		boolean success =  CSVWriter.append(userDataFile, record);
		if (!success) {
			return false;
		}
		
		int lineID = cache.values().size() + 1;
		cache.put(key, lineID);
		return success;
	}
	
	public List<Data> readRecord(Data key) {
		Integer recordID = cache.get(key);
		if (recordID == null) {
			return null;
		}
		List<String> stringRecord = CSVParser.parseLine(userDataFile, recordID);
		ArrayList<Data> record = new ArrayList<>();
		record.ensureCapacity(stringRecord.size());
		
		for (int i = 0; i < stringRecord.size(); i++) {
			record.add(new Data(fieldTypes.get(i), stringRecord.get(i)));
		}
		
		return record;
	}
}
