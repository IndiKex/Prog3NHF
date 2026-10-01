package main.graphics.panels.map;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import main.graphics.panels.map.stop.Stop;

// latitude szélességi - y
// longitude hosszúsági - x

public class StopCanvas extends JPanel {
	List<Stop> stops = new ArrayList<>();
	float zeroLongitudeX;
	float zeroLatitudeY;
	
	float scale, translateX, translateY;
	
	Stop lastHoveredStop;
	Stop lastSelectedStop;
	
	public StopCanvas() {
		setLayout(null);
		setOpaque(false);
		
		calculateZeroLonLat();
		
		readDataFiles();
	}
	
	public void onMouseMoved(MouseEvent e) {
		Stop hoveredStop = findStopAt(e.getPoint());
		if (lastHoveredStop != null && hoveredStop != lastHoveredStop) {
			lastHoveredStop.setHovered(false);
			lastHoveredStop = null;
			repaint();
		}
		
		if (hoveredStop != null) {
			hoveredStop.setHovered(true);
			lastHoveredStop = hoveredStop;
			repaint();
		}
		
	}
	
	public void onMouseClicked(MouseEvent e) {
		if (lastHoveredStop != null && lastSelectedStop != lastHoveredStop) {
			System.out.println("Chose " + lastHoveredStop.name + " stop");
			lastHoveredStop.setSelected(true);
			if (lastSelectedStop != null)
				lastSelectedStop.setSelected(false);
			lastSelectedStop = lastHoveredStop;
			repaint();
		} else if (lastHoveredStop != null) {
			lastSelectedStop.setSelected(false);
			lastSelectedStop = null;
			repaint();
		} else if (lastSelectedStop != null) {
			lastSelectedStop.setSelected(false);
			lastSelectedStop = null;
			repaint();
		}
	}
	
	private float distance(Point p1, Point p2) {
		double distSquared = (p1.getX() - p2.getX()) * (p1.getX() - p2.getX()) + (p1.getY() - p2.getY()) * (p1.getY() - p2.getY());
		return (float)Math.sqrt(distSquared);
	}
	
	private Stop findStopAt(Point pos) {
		for (Stop stop : stops) {
			if (distance(pos, stop.getScreenPos()) <= stop.SIZE)
				return stop;
		}
		return null;
	}
	
 	private void readDataFiles() {
		try {
			FileReader fr = new FileReader(new File("assets/MAVData/gtfsMavMenetrend/stops.txt"));
			
			List<String> allLines = fr.readAllLines();
			
			int latID = 4;
			int lonID = 5;
			
			for (int i = 1; i < allLines.size(); i++) {
				String line = allLines.get(i);
				String[] lineData = line.split(",");
				
				
				if (lineData.length == 12) {
					float latY = Float.parseFloat(lineData[latID]);
					float lonX = Float.parseFloat(lineData[lonID]);
					addPoint(lonX, latY, lineData[2]);
				} else if (lineData.length == 13) {
					float latY = Float.parseFloat(lineData[latID+1]);
					float lonX = Float.parseFloat(lineData[lonID+1]);
					addPoint(lonX, latY, lineData[2]);
				} else
					System.out.println("Anyád szopjon le kétszer bazdmeg! >:(");
			}
			
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void addPoint(float longitudeX, float latitudeY, String name) {
		stops.add(new Stop(calculatePixel(longitudeX, latitudeY), name));
	}
	
	private Point calculatePixel(float longitudeX, float latitudeY) {
		float posX = (longitudeX - zeroLongitudeX) * (918 - 434) / (21.440167f - 18.446360f);
		float posY = -1 * (latitudeY - zeroLatitudeY) * (684 - 49) / (48.585217f - 45.737430f);
		return new Point((int)posX, (int)posY);
	}
	
	private void calculateZeroLonLat() {
		zeroLongitudeX = 18.446360f - (21.440167f - 18.446360f) / (918 - 434) * 434;
		zeroLatitudeY = 48.585217f + (48.585217f - 45.737430f) / (684 - 49) * 49;
	}
	
	void updateTransform(float scale, float translateX, float translateY) {
		this.scale = scale;
		this.translateX = translateX;
		this.translateY = translateY;
		
		for (Stop stop : stops) {
			stop.updateTransform(scale, translateX, translateY);
		}
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (Stop stop : stops) {
			stop.draw(g);
		}
	}
	
}
