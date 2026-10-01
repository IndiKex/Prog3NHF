package main;

import java.io.File;

import databases.main.databases.NaiveDataBase;
import databases.main.interfaces.DataBase;
import databases.main.interfaces.Table;
import databases.main.tables.NaiveTable;

public class DataBaseOrganizer {
	public static DataBase HungaryBase = new NaiveDataBase();
	public static DataBase MAVBase = new NaiveDataBase();
	public static DataBase AccountBase = new NaiveDataBase();
	
	public static void readData() {
		long start = System.nanoTime();
		
		// MAVBase
		File MAVAssetDir = new File("assets/MAVData/gtfsMavMenetrend");
		String[] MAVAssetFiles = MAVAssetDir.list();
		for (String s : MAVAssetFiles) {
			File MAVAssetFile = new File(MAVAssetDir, s);
			String tableName = s.replace(".txt", "");
			Table t = new NaiveTable(tableName);
			t.loadCSV(MAVAssetFile);
			MAVBase.addTable(t);
		}
		
		long end = System.nanoTime();
		double elapsedTime = (double)(end - start) / 1_000_000_000.0;
		System.out.println("Loading data time: " + elapsedTime);
	}
}
