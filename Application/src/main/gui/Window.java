package main.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import main.gui.config.Config;
import main.gui.enums.MainPanels;
import main.gui.panels.sidebar.SideBarPanel;

public class Window implements GUI {
	MainPanels currentMainPanel = MainPanels.MAP;
	SideBarPanel sideBarPanel;
	
	
	private static Config config = new Config();
	
	private JFrame mainFrame;
	
	public static Config getConfig() {
		return config;
	}
	
	public void initialize(Runnable onExitFunction) {
		mainFrame = new JFrame("MAVEnd");
		mainFrame.setSize(new Dimension(1280, 720));
		
		addFunctionToClose(onExitFunction);
		
		initializePanels();
	}
	
	public void start() {
		mainFrame.setVisible(true);
	}
	
	public void close() {
		
	}
	
	
	private void addFunctionToClose(Runnable onExitFunction) {
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				close();
				onExitFunction.run();
			}
		});
	}
	
	private void initializePanels() {
		initializeSideBarPanel();
	}
	
	private void initializeSideBarPanel() {
		sideBarPanel = new SideBarPanel(this);
		mainFrame.add(sideBarPanel, BorderLayout.WEST);
		
	}
	
	public void updateMainPanels() {
		if (currentMainPanel.equals(sideBarPanel.getChosenPanel())) {
			return;
		}
		
		System.out.println("Main panel changed to " + sideBarPanel.getChosenPanel());
	}
}
