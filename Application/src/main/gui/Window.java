package main.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import main.gui.config.Config;
import main.gui.enums.MainPanels;
import main.gui.panels.main.MainPanelContainer;
import main.gui.panels.sidebar.SideBarPanel;

public class Window implements GUI {
	private MainPanels currentMainPanelID = MainPanels.MENU;
	private SideBarPanel sideBarPanel;
	private MainPanelContainer mainPanelContainer;
	
	private static Config config = new Config();
	
	private JFrame mainFrame;
	
	public static Config getConfig() {
		return config;
	}
	
	public void initialize(Runnable onExitFunction) {
		mainFrame = new JFrame("MAVEnd");
		mainFrame.setSize(new Dimension(1280, 720));
		mainFrame.setBackground(config.theme.baseColor);
		mainFrame.getContentPane().setBackground(config.theme.baseColor);
		
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
		initializeMainPanels();
	}
	
	private void initializeSideBarPanel() {
		sideBarPanel = new SideBarPanel(this);
		sideBarPanel.setChosenPanel(currentMainPanelID);
		mainFrame.add(sideBarPanel, BorderLayout.WEST);
		
	}
	
	private void initializeMainPanels() {
		mainPanelContainer = new MainPanelContainer();
		mainPanelContainer.switchPanel(currentMainPanelID);
		mainFrame.add(mainPanelContainer);
	}
	
	public void updateMainPanels() {
		if (currentMainPanelID.equals(sideBarPanel.getChosenPanel())) {
			return;
		}
		
		currentMainPanelID = sideBarPanel.getChosenPanel();
		mainPanelContainer.switchPanel(currentMainPanelID);
		System.out.println("Main panel changed to " + sideBarPanel.getChosenPanel());
	}
}
