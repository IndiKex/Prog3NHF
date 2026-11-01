package main.gui.panels.sidebar;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import main.gui.Window;
import main.gui.enums.MainPanels;

public class SideBarPanel extends JPanel {
	Window window;
	
	MainPanels chosenPanel = MainPanels.MAP;
	
	private Dimension panelSize = new Dimension(60,720);
	
	public SideBarPanel(Window window) {
		this.window = window;
		
		this.setBackground(Color.BLUE);
	}
	
	public MainPanels getChosenPanel() {
		return chosenPanel;
	}
	
	public void setChosenPanel(MainPanels panel) {
		chosenPanel = panel;
	}
	
	@Override
	public Dimension getPreferredSize() {
		return panelSize;
	}
	
	@Override
	public Dimension getMinimumSize() {
		return panelSize;
	}
	
	@Override
	public Dimension getMaximumSize() {
		return panelSize;
	}
	
}
