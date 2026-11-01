package main.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import main.gui.panels.sidebar.SideBarPanel;

public class Window implements GUI {
	SideBarPanel sideBarPanel;
	
	JFrame mainFrame;
	
	public void start(Runnable onExitFunction) {
		mainFrame = new JFrame("MAVEnd");
		mainFrame.setSize(new Dimension(1280, 720));
		
		addFunctionToClose(onExitFunction);
		
		mainFrame.setVisible(true);
		
		initializePanels();
	}
	
	private void addFunctionToClose(Runnable onExitFunction) {
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
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
}
