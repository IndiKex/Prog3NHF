package main.backend.continuos.dbm;

import java.util.List;

public interface Database {
	public class KeyExistsException extends Exception {}
	public class KeyNotExistsException extends Exception {}
	
	public void init();
	public void close();
	public void addRecord(List<String> record) throws KeyExistsException;
	public List<String> getRecord(String key) throws KeyNotExistsException;
	public void changeRecord(String key, List<String> newRecord) throws KeyNotExistsException;
	public void deleteRecord(String key) throws KeyNotExistsException;
	public int getFieldID(String fieldName);
}
