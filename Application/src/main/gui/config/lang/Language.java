package main.gui.config.lang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Language {
	public Map<String, String> data;
	
	public Language(File languageFile) {
		try {
			load(languageFile);
		} catch (IOException e) {
			data = new HashMap<>();
		}
	}
	
	public void load(File languageFile) throws IOException {
		data = new HashMap<>();
		
		BufferedReader br = new BufferedReader(new FileReader(languageFile));
		
		String line;
		while ((line = br.readLine()) != null) {
			if (contains(line, ':')) {
				String[] lineData = parseDataLine(line);
				data.put(lineData[0], lineData[1]);
			}
		}
	}
	
	public String[] parseDataLine(String line) {
		String[] res = new String[2];
		
		boolean isInQuotation = false;
		int lastQuotationMarkIdx = -1;
		boolean didFirstData = false;
		
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			
			if (!isInQuotation && c == '"') {
				isInQuotation = true;
				lastQuotationMarkIdx = i;
			}
			
			if (didFirstData && !isInQuotation && c == '"') {
				lastQuotationMarkIdx = i;
				break;
			}
			
			if (isInQuotation && c == '"' && line.length() > i + 1 && line.charAt(i+1) == ':') {
				isInQuotation = false;
				res[0] = line.substring(lastQuotationMarkIdx + 1, i);
				didFirstData = true;
			}
		}
		
		if (line.length() > lastQuotationMarkIdx + 1) {
			res[1] = line.substring(lastQuotationMarkIdx + 1, line.length() - 1);
		} else {
			res[1] = "";
		}
		
		return res;
	}
	
	private boolean contains(String s, char c) {
		for (char cs : s.toCharArray()) {
			if (cs == c) {
				return true;
			}
		}
		return false;
	}
	
	public String get(String dataID) {
		return data.get(dataID);
	}
	
	// TODO
}
