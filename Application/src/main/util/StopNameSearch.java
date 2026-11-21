package main.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StopNameSearch {
	/*
	 * Returns: a score from 0-100
	 */
	private static int countMatchingLetters(String base, String compare) {
		Map<Character, Integer> letterFrequency = new HashMap<>();
		for (char baseChar : base.toCharArray()) {
			Integer freq = letterFrequency.get(baseChar);
			if (freq == null) {
				letterFrequency.put(baseChar, 0);
				freq = 0;
			}
			letterFrequency.put(baseChar, freq + 1);
		}
		
		int matching = 0;
		for (char compareChar : compare.toCharArray()) {
			Integer freq = letterFrequency.get(compareChar);
			if (freq != null && freq > 0) {
				letterFrequency.put(compareChar, freq - 1);
				matching += 1;
			}
		}
		
		return matching;
	}
	
	public static List<String> search(String currentName, List<String> stopNames) {
		List<String> copy = new ArrayList<>();
		copy.addAll(stopNames);
		
		String currentNameLower = currentName.toLowerCase();
		
		Map<String, Integer> resMap = new HashMap<>();
		for (String stopName : stopNames) {
			String stopNameLower = stopName.toLowerCase();
			
			int nMatching1 = countMatchingLetters(stopNameLower, currentNameLower);
			int nMatching2 = countMatchingLetters(currentNameLower, stopNameLower);
			
			double p1 = (double) nMatching1 / stopNameLower.length();
			double p2 = (double) nMatching2 / currentNameLower.length();
			
			int correctedWeight = (int)(Math.sqrt(p1 * p2) * 100);
			
			resMap.put(stopName, correctedWeight);
		}
		
		copy.sort((a, b) -> resMap.get(b) - resMap.get(a));
		
		return copy;
	}
}
