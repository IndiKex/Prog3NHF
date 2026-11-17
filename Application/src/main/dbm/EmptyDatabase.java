package main.dbm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.dbm.Data.DataTypes;
import main.io.csvparser.CSVParser;
import main.io.csvparser.CSVWriter;

public class EmptyDatabase implements Database {
	private File dataFile;
	private File cacheFile;
	
	private List<String> fieldNames;
	private List<DataTypes> fieldTypes;
	private Map<Data, Integer> cache = new HashMap<>();
	
	public EmptyDatabase(File dataFile, File cacheFile, List<String> fieldNames, List<DataTypes> fieldTypes) {
		this.dataFile = dataFile;
		this.cacheFile = cacheFile;
		this.fieldNames = fieldNames;
		this.fieldTypes = fieldTypes;
	}
	
	public void init() {
		try {
			if (!dataFile.exists()) {
				dataFile.createNewFile();
				CSVWriter.write(dataFile, fieldNames, new ArrayList<>());
			}
			if (!cacheFile.exists()) {
				cacheFile.createNewFile();
			} else {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(cacheFile));
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
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(cacheFile));
			out.writeObject(cache);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public int getNRecords() {
		if (cache.values().isEmpty())
			return 0;
		else
			return Collections.max(cache.values());
	}
	
	public boolean writeRecord(Data key, List<Data> record) {
		boolean success =  CSVWriter.append(dataFile, record);
		if (!success) {
			return false;
		}
		
		int lineID = getNRecords() + 1;
		
		System.out.println(cache);
		cache.put(key, lineID);
		System.out.println(cache);
		return success;
	}
	
	public List<Data> readRecord(Data key) {
		Integer recordID = cache.get(key);
		if (recordID == null) {
			return null;
		}
		
		List<String> stringRecord = CSVParser.parseLine(dataFile, recordID);
		ArrayList<Data> record = new ArrayList<>();
		record.ensureCapacity(stringRecord.size());
		
		for (int i = 0; i < stringRecord.size(); i++) {
			record.add(new Data(fieldTypes.get(i), stringRecord.get(i)));
		}
		
		return record;
	}
}
