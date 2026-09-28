package main.graphics.panels.map;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class MapPanel extends JPanel {
	public MapPanel() {
		setLayout(new BorderLayout());
		
		MapCanvas mapCanvas = new MapCanvas();
		add(mapCanvas);
	}
}
