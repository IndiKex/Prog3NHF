package main.backend.continuos.dbm;

import java.awt.Point;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class GeoJsonReader {
	private File sourceFile;
	
	public GeoJsonReader(File sourceFile) {
		this.sourceFile = sourceFile;
	}
	
	public List<Point> readPoints() {
		List<Point> res = new ArrayList<>();
		
		try {
			FileReader basicReader = new FileReader(sourceFile);
			JsonReader reader = Json.createReader(basicReader);
			JsonObject obj = reader.readObject();
			
			JsonObject feature = (JsonObject) obj.getJsonArray("features").get(0);
			JsonArray pointsArray = feature.getJsonObject("geometry").getJsonArray("coordinates").getJsonArray(0);
			for (int i = 0; i < pointsArray.size(); i++) {
				JsonArray pointArray = pointsArray.getJsonArray(i);
				int longitude = (int)(pointArray.getJsonNumber(0).doubleValue() * 10000);
				int latitude = (int)(pointArray.getJsonNumber(1).doubleValue() * 10000);
				res.add(new Point(longitude, latitude));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
}
