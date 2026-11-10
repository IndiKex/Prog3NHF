package main.dbm;

import java.util.List;

public interface Database {
	public void init();
	public void close();
	public int getNRecords();
	
	public boolean writeRecord(Data key, List<Data> record);
	public List<Data> readRecord(Data key);
}
