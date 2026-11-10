package main.io.csvparser;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import main.dbm.Data;


public class CSVWriter {
	public static boolean write(File sourceFile, List<String> fieldNames, List<List<Data>> recordList) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(sourceFile));
			
			String fieldLine = "";
			for (int i = 0; i < fieldNames.size(); i++) {
				if (i < fieldNames.size() - 1)
					fieldLine += fieldNames.get(i) + ",";
				else
					fieldLine += fieldNames.get(i);
			}
			
			bw.write(fieldLine);
			bw.newLine();
			
			for (int i = 0; i < recordList.size(); i++) {
				String recordLine = recordToLine(recordList.get(i));
				bw.write(recordLine);
				bw.newLine();
			}
			
			bw.close();
			
			return true;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean append(File sourceFile, List<Data> record) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(sourceFile, true));
			
			String recordLine = recordToLine(record);
			bw.write(recordLine);
			bw.newLine();
			
			bw.close();
			
			return true;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private static String recordToLine(List<Data> record) {
		String res = "";
		for (int i = 0; i < record.size(); i++) {
			String dataString = dataToString(record.get(i));
			if (i < record.size() - 1)
				res += dataString + ",";
			else
				res += dataString;
		}
		return res;
	}
	
	private static String dataToString(Data data) {
		if (data.getDataType().equals(Data.DataTypes.FLOAT) || data.getDataType().equals(Data.DataTypes.INT))
			return data.toString();
		return "\"" + data.toString() + "\"";
	}
	
}
