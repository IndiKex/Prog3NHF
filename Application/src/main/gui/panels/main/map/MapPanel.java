package main.gui.panels.main.map;

import java.awt.BorderLayout;

import main.gui.panels.util.InvisibleJPanel;
import main.gui.panels.util.Refreshable;

public class MapPanel extends InvisibleJPanel implements Refreshable {
	private MapCanvas mapCanvas;
	
	public MapPanel() {
		this.setLayout(new BorderLayout());
		
		mapCanvas = new MapCanvas();
		this.add(mapCanvas, BorderLayout.CENTER);
	}

	public void refreshColors() {
		
	}

	public void refreshLanguage() {
		
	}
}
