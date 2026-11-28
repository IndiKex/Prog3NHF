package main.io.csvparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Static class for parsing CSV documents.
 */
public class CSVParser {
	/**
	 * Parses a CSV document from start to finish. The user can decide what to do with the parsed lines with the container parameter.
	 * @param sourceFile
	 * @param tableContainer this object determines what to do with the parsed lines
	 */
	public static void parse(File sourceFile, Container tableContainer) {
		try {
			FileReader fr = new FileReader(sourceFile);
			BufferedReader br = new BufferedReader(fr);
			
			String fieldLine = br.readLine();
			parseFieldLine(fieldLine, tableContainer);
			
			String nextLine = br.readLine();
			int i = 0;
			int rowLength = fieldLine.split(",").length;
			while (nextLine != null) {
				parseDataLine(nextLine, i, rowLength, tableContainer);
				nextLine = br.readLine();
				i++;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Parses a single line.
	 * @param sourceFile
	 * @param lineID the index of the line to be parsed, the 0th line is the field definition line
	 * @return a list of strings of the parsed data
	 */
	public static List<String> parseLine(File sourceFile, int lineID) {
		try {
			ArrayList<String> dataRow = new ArrayList<>();
			
			BufferedReader br = new BufferedReader(new FileReader(sourceFile));
			for (int i = 0; i < lineID; i++) {
				br.readLine();
			}
			
			String dataLine = br.readLine();
			
			int lastCommaIdx = -1;
			boolean isInQuotation = false;
			for (int i = 0; i < dataLine.length(); i++) {
				if (dataLine.charAt(i) == ',' && !isInQuotation) {
					String word = dataLine.substring(lastCommaIdx + 1, i);
					word = word.replace("\"", "");
					dataRow.add(word);
					lastCommaIdx = i;
				}
				
				if (dataLine.charAt(i) == '\"') {
					isInQuotation = !isInQuotation;
				}
			}
			String lastWord = dataLine.substring(lastCommaIdx + 1, dataLine.length());
			lastWord = lastWord.replace("\"", "");
			dataRow.add(lastWord);
			
			br.close();
			
			return dataRow;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}
	
	/**
	 * Parses the field names of the csv document
	 * @param sourceFile
	 * @return list of the field names
	 */
	public static List<String> getFieldNames(File sourceFile) {
		List<String> res = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(sourceFile));
			String fieldLine = br.readLine();
			parseFieldLine(fieldLine, new Container() {
				public void addRow(List<String> data) {
					
				}
				public void addFields(List<String> fields) {
					res.clear();
					res.addAll(fields);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/**
	 * Parses the field definition line and adds it to the container to deal with them as it likes.
	 * @param fieldLine
	 * @param tableContainer object that determines what to do with the field names
	 */
	private static void parseFieldLine(String fieldLine, Container tableContainer) {
		String[] lineSplit = fieldLine.split(",");
		ArrayList<String> lineSplitList = new ArrayList<>();
		lineSplitList.ensureCapacity(lineSplit.length);
		for (String fieldName : lineSplit)
			lineSplitList.add(fieldName);
		tableContainer.addFields(lineSplitList);
	}
	
	/**
	 * Parses a data line of the csv document and gives it to the container
	 * @param dataLine
	 * @param lineIdx index of the line to be read, 0th is the field definition line
	 * @param rowLength ammount of data in the line
	 * @param tableContainer object that determines what to do with the datas in the line
	 */
	private static void parseDataLine(String dataLine, int lineIdx, int rowLength, Container tableContainer) {
		ArrayList<String> dataRow = new ArrayList<>();
		dataRow.ensureCapacity(rowLength);
		dataRow.add("" + lineIdx);
		
		int lastCommaIdx = -1;
		boolean isInQuotation = false;
		for (int i = 0; i < dataLine.length(); i++) {
			if (dataLine.charAt(i) == ',' && !isInQuotation) {
				String word = dataLine.substring(lastCommaIdx + 1, i);
				word = word.replace("\"", "");
				dataRow.add(word);
				lastCommaIdx = i;
			}
			
			if (dataLine.charAt(i) == '\"') {
				isInQuotation = !isInQuotation;
			}
		}
		String lastWord = dataLine.substring(lastCommaIdx + 1, dataLine.length());
		lastWord = lastWord.replace("\"", "");
		dataRow.add(lastWord);
		
		tableContainer.addRow(dataRow);
	}
}
