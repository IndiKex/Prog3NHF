package main.gui.panels.sidebar;

import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.io.File;
import java.util.List;

import main.gui.Window;
import main.gui.config.theme.Theme;
import main.gui.enums.MainPanels;
import main.gui.panels.VisibleJPanel;

public class SideBarPanel extends VisibleJPanel {
	Window window;
	ButtonPanel buttonPanel;
	
	MainPanels chosenPanel = MainPanels.MAP;
	
	private Dimension panelSize = new Dimension(60,720);
	
	public SideBarPanel(Window window) {
		this.window = window;
		
		//this.setBackground(Window.getConfig().theme.baseColor);
		this.setOpaque(false);
		
		this.setLayout(new GridBagLayout());
		initializeButtons();
	}
	
	private void initializeButtons() {
		buttonPanel = new ButtonPanel(window, new Dimension(60, 200));
		List<File> iconPaths = List.of(
			new File("assets/graphics/sidebar/map_button_icon.png"),
			new File("assets/graphics/sidebar/ticket_button_icon.png"),
			new File("assets/graphics/sidebar/menu_button_icon.png")
		);
		List<Runnable> onClickFunctions = List.of(
			() -> {setChosenPanel(MainPanels.MAP); window.updateMainPanels();},
			() -> {setChosenPanel(MainPanels.TICKET); window.updateMainPanels();},
			() -> {setChosenPanel(MainPanels.MENU); window.updateMainPanels();}
		);
		buttonPanel.addButtons(iconPaths, onClickFunctions);
		this.add(buttonPanel);
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
