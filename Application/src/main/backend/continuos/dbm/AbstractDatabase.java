package main.backend.continuos.dbm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AbstractDatabase implements Database, Serializable {
	private List<List<String>> data = new ArrayList<>();
	private Map<String, Integer> cache = new HashMap<>();
	protected List<String> fieldNames;
	protected int keyField;
	
	protected File dataFile;
	
	public AbstractDatabase(List<String> fieldNames, int keyField, File dataFile) {
		this.fieldNames = fieldNames;
		this.keyField = keyField;
		this.dataFile = dataFile;
	}
	
	public void init() {
		try {
			if (!dataFile.exists()) {
				dataFile.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			if (!dataFile.exists()) {
				init();
			}
			
			ObjectOutputStream w = new ObjectOutputStream(new FileOutputStream(dataFile));
			w.writeObject(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addRecord(List<String> record) throws KeyExistsException {
		String key = record.get(keyField);
		Integer id = cache.get(key);
		if (id != null) {
			throw new KeyExistsException();
		}
		
		data.add(record);
		cache.put(key, data.size() - 1);
	}
	
	public List<String> getRecord(String key) throws KeyNotExistsException {
		Integer id = cache.get(key);
		if (id == null) {
			throw new KeyNotExistsException();
		}
		
		return data.get(id);
	}
	
	public void changeRecord(String key, List<String> newRecord) throws KeyNotExistsException {
		Integer id = cache.get(key);
		if (id == null) {
			throw new KeyNotExistsException();
		}
		
		data.set(id, newRecord);
	}

	public void deleteRecord(String key) throws KeyNotExistsException {
		Integer id = cache.get(key);
		if (id == null) {
			throw new KeyNotExistsException();
		}
		
		cache.put(key, null);
	}
	
	public int getFieldID(String fieldName) {
		return fieldNames.indexOf(fieldName);
	}
}
