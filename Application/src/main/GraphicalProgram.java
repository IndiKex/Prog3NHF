package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;

import main.graphics.panels.map.MapCanvas;
import main.graphics.panels.map.MapPanel;
import main.graphics.panels.sidebar.SideBarPanel;
import main.graphics.window.Window;

public class GraphicalProgram {
	private Program program;
	private Window window;
	
	public GraphicalProgram(Program program) {
		this.program = program;
	}
	
	private void initializeWindow() {
		window = new Window();
		window.create("MÁVgo", 1280, 720);
		
		window.getFrame().addWindowListener(
			new WindowAdapter() {
				public void windowClosing(WindowEvent windowEvent) {
					close();
				}
			}
		);
		
		window.setBackgroundColor(35, 0, 148);
		
		window.show();
		window.turnOnFullscreen();
	}
	
	public void startGraphicalApplication() {
		initializeWindow();
		
		window.getFrame().getContentPane().setLayout(new BoxLayout(window.getFrame().getContentPane(), BoxLayout.X_AXIS));
		
		SideBarPanel sideBarPanel = new SideBarPanel(window);
		
		window.add(sideBarPanel);
		
		MapPanel mapPanel = new MapPanel();
		
		window.add(mapPanel);
		
		window.update();
	}
	
	public void close() {
		
		program.close();
	}
}
