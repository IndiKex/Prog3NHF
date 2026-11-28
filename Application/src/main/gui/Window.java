package main.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.backend.Backend;
import main.gui.config.Config;
import main.gui.enums.MainPanels;
import main.gui.panels.main.MainPanelContainer;
import main.gui.panels.main.menu.MenuSideMenus;
import main.gui.panels.sidebar.SideBarPanel;
import main.gui.panels.util.InvisibleJPanel;

public class Window implements GUI {
	private static Window _instance;
	
	private MainPanels currentMainPanelID = MainPanels.MAP;
	private SideBarPanel sideBarPanel;
	private JPanel mainPanelContainerHolder;
	private MainPanelContainer mainPanelContainer;
	
	private static Config config;
	
	private JFrame mainFrame;
	
	public static Config getConfig() {
		return config;
	}
	
	public void refreshColors() {
		mainFrame.setBackground(config.theme.gradientSecondColor);
		mainFrame.getContentPane().setBackground(config.theme.gradientSecondColor);
		sideBarPanel.refreshColors();
		mainPanelContainer.refreshColors();
	}
	
	public void refreshLanguage() {
		mainPanelContainerHolder.removeAll();
		this.initializeMainPanels();
		mainPanelContainer.setMenuPanelEnumValue(MenuSideMenus.SETTINGS);
		mainPanelContainerHolder.revalidate();
		mainPanelContainerHolder.repaint();
	}
	
	public static Window get_instance() {
		if (_instance == null)
			_instance = new Window();
		return _instance;
	}
	
	public void initialize(Backend backend, Runnable onExitFunction) {
		try {
			if (Config.configFile.exists()) {
				ObjectInputStream in = new ObjectInputStream(new FileInputStream(Config.configFile));
				config = (Config) in.readObject();
				in.close();
			} else {
				config = new Config();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		RequestSender.backend = backend;
		mainFrame = new JFrame("MAVEnd");
		mainFrame.setSize(new Dimension(1280, 720));
		mainFrame.setResizable(false);
		
		
		addFunctionToClose(onExitFunction);
		
		initializePanels();
		
		refreshColors();
	}
	
	public void start() {
		mainFrame.setVisible(true);
	}
	
	public void close() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(Config.configFile));
			out.writeObject(config);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
		
		mainPanelContainerHolder = new InvisibleJPanel();
		mainPanelContainerHolder.setLayout(new BorderLayout());
		initializeMainPanels();
		mainFrame.add(mainPanelContainerHolder, BorderLayout.CENTER);
	}
	
	private void initializeSideBarPanel() {
		sideBarPanel = new SideBarPanel(this);
		sideBarPanel.setChosenPanel(currentMainPanelID);
		mainFrame.add(sideBarPanel, BorderLayout.WEST);
		
	}
	
	private void initializeMainPanels() {
		mainPanelContainer = new MainPanelContainer();
		mainPanelContainer.switchPanel(currentMainPanelID);
		mainPanelContainerHolder.add(mainPanelContainer);
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
