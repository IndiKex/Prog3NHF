package main.backend.maps.data;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import main.backend.continuos.dbm.GeoJsonReader;

public class BorderData {
	private List<Point> points;
	private Rectangle bounds;
	
	public static BorderData load(File file) {
		BorderData borderData = new BorderData();
		
		GeoJsonReader reader = new GeoJsonReader(file);
		borderData.setPoints(reader.readPoints());
		
		return borderData;
	}
	
	public List<Point> optimizePoints(Rectangle boundingBox) {
		List<Point> res = new ArrayList<>();
		
		if (bounds == null) {
			calculateBounds();
		}
		
		if (!intersects(boundingBox, bounds)) {
			return res;
		}
		
		int distanceThreshold = boundingBox.width / 100;
		int distanceThresholdOutside = boundingBox.width / 20;
		boolean lastWasIn = false;
		
		for (int i = 0; i < points.size(); i++) {
			Point point = points.get(i);
			
			// If empty
			if (res.isEmpty()) {
				res.add(point);
				if (contains(boundingBox, point))
					lastWasIn = true;
				else
					lastWasIn = false;
				continue;
			}
			
			Point prevResPoint = res.get(res.size() - 1);
			
			// Cases
			if (contains(boundingBox, point) && lastWasIn) {
				lastWasIn = true;
				if (prevResPoint.distance(point) >= distanceThreshold) {
					res.add(point);
				}
				
			} else if (contains(boundingBox, point) && !lastWasIn) {
				lastWasIn = true;
				
				res.add(points.get(i-1));
				res.add(point);
				
			} else if (!contains(boundingBox, point) && lastWasIn) {
				lastWasIn = false;
				res.add(point);
				
			} else if (!contains(boundingBox, point) && !lastWasIn) {
				lastWasIn = false;
				
				if (prevResPoint.distance(point) >= distanceThresholdOutside) {
					res.add(point);
				}
			}
		}
		return res;
	}
	
	private BorderData() {
		
	}

	public List<Point> getPoints() {
		return points;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
		calculateBounds();
	}
	
	public void calculateBounds() {
		Comparator<Point> xComparator = new Comparator<Point>() {
			public int compare(Point o1, Point o2) {
				return o1.x - o2.x;
			}
		};
		
		Comparator<Point> yComparator = new Comparator<Point>() {
			public int compare(Point o1, Point o2) {
				return o1.y - o2.y;
			}
		};
		
		int minX = Collections.min(points, xComparator).x;
		int maxX = Collections.max(points, xComparator).x;
		
		int minY = Collections.min(points, yComparator).y;
		int maxY = Collections.max(points, yComparator).y;
		
		bounds = new Rectangle(minX, maxY, maxX - minX, maxY - minY);
	}
	
	private boolean contains(Rectangle rect, Point point) {
		return rect.x <= point.x && rect.x + rect.width >= point.x && rect.y >= point.y && rect.y - rect.height <= point.y;
	}
	
	private boolean intersects(Rectangle rect1, Rectangle rect2) {
		return intersectsX(rect1, rect2) && intersectsY(rect1, rect2);
	}
	
	private boolean intersectsX(Rectangle rect1, Rectangle rect2) {
	    return rect1.x < rect2.x + rect2.width && rect1.x + rect1.width > rect2.x;
	}

	private boolean intersectsY(Rectangle rect1, Rectangle rect2) {
	    return rect1.y > rect2.y - rect2.height && rect1.y - rect1.height < rect2.y;
	}
}
