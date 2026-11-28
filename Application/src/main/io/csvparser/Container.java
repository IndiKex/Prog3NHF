package main.io.csvparser;

import java.util.List;

/**
 * A class that decides what to do with the parsed lines of a CSV document.
 */
public interface Container {
	/**
	 * Deals with the given data of a line
	 * @param data list of string data in a line
	 */
	public void addRow(List<String> data);
	
	/**
	 * Deals with the given field names
	 * @param fields names of each field in the csv document
	 */
	public void addFields(List<String> fields);
}
