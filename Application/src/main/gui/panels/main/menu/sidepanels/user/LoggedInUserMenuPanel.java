package main.gui.panels.main.menu.sidepanels.user;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.comm.RequestType;
import main.comm.asnwer.UserDataAnswer;
import main.comm.asnwer.UserDeleteAnswer;
import main.comm.request.UserDataRequest;
import main.comm.request.UserDeleteRequest;
import main.gui.RequestSender;
import main.gui.Window;
import main.gui.config.lang.Language;
import main.gui.config.theme.Theme;
import main.gui.panels.TextButton;
import main.gui.panels.VisibleJPanel;

public class LoggedInUserMenuPanel extends VisibleJPanel {
	private JPanel containPanel;
	private JPanel labelPanel;
	private JPanel infoPanel;
	private JPanel buttonPanel;
	
	private JLabel iconLabel;
	
	private JPanel newPasswordPanel;
	private JButton deleteProfileButton;
	private JButton logoutButton;
	
	private Runnable onLogoutFunction;
	
	public LoggedInUserMenuPanel(Runnable onLogoutFunction) {
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(120, 50, 20, 0)); // top, left, bottom, right
		
		this.onLogoutFunction = onLogoutFunction;
		
		initializePanels();
	}
	
	public void initializePanels() {
		containPanel = new JPanel();
		containPanel.setOpaque(false);
		containPanel.setLayout(new BorderLayout());
		
		labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		labelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		labelPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
		labelPanel.setOpaque(false);
		
		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		infoPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
		infoPanel.setOpaque(false);
		
		addLabels();
		
		addIcon();
		
		addButtons();

		containPanel.add(iconLabel, BorderLayout.NORTH);
		containPanel.add(labelPanel, BorderLayout.WEST);
		containPanel.add(infoPanel, BorderLayout.CENTER);
		containPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		this.add(containPanel);
	}
	
	private void addLabels() {
		List<String> infoNames = List.of("Username", "Born", "IDNum", "Address");
		
		Theme theme = Window.getConfig().theme;
		Font font = new Font("Arial", Font.PLAIN, 20);
		
		UserDataAnswer answer = (UserDataAnswer)RequestSender.sendRequest(new UserDataRequest(RequestSender.username), RequestType.USERDATA);
		List<String> infoDatas = List.of(answer.username, answer.dateOfBirth, answer.IDNumber, answer.address);
		
		Language lang = Window.getConfig().language;
		for (int i = 0; i < infoNames.size(); i++) {
			String labelData = lang.get("menuSideUserAboutLabel" + infoNames.get(i));
			String infoData = infoDatas.get(i);
			
			JLabel labelObj = new JLabel(labelData + "     ");
			labelObj.setForeground(theme.textColor);
			labelObj.setFont(font);
			labelPanel.add(labelObj);
			
			JLabel infoObj = new JLabel(infoData);
			infoObj.setForeground(theme.secondaryColor);
			infoObj.setFont(font);
			infoPanel.add(infoObj);
			
			labelPanel.add(Box.createRigidArea(new Dimension(labelPanel.getWidth(), 20)));
			infoPanel.add(Box.createRigidArea(new Dimension(infoPanel.getWidth(), 20)));
		}
	}
	
	private void addIcon() {
		Theme theme = Window.getConfig().theme;
		
		File iconFile = new File("assets/graphics/menu/user_icon.png");
		ImageIcon loadedIcon = new ImageIcon(iconFile.getPath());
		ImageIcon resizedIcon = Theme.resizeIcon(loadedIcon, 200, 200);
		ImageIcon recoloredIcon = Theme.recolorIcon(resizedIcon, theme.secondaryColor);
		
		iconLabel = new JLabel(recoloredIcon);
		iconLabel.setOpaque(false);
		
		
	}
	
	private void addButtons() {
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setOpaque(false);
		
		Theme theme = Window.getConfig().theme;
		Language lang = Window.getConfig().language;
		
		Dimension buttonSize = new Dimension(200, 40);
		
		newPasswordPanel = new NewPasswordPanel(buttonSize);
		
		initDeleteButton(buttonSize);
		
		initLogoutButton(buttonSize);
		
		buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		buttonPanel.add(newPasswordPanel);
		buttonPanel.add(Box.createRigidArea(new Dimension(buttonPanel.getWidth(), 20)));
		buttonPanel.add(deleteProfileButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(buttonPanel.getWidth(), 20)));
		buttonPanel.add(logoutButton);
	}
	
	public void initDeleteButton(Dimension buttonSize) {
		Language lang = Window.getConfig().language;
		
		deleteProfileButton = new TextButton();
		String deleteProfileButtonLabel = lang.get("menuSideUserButtonDelete");
		deleteProfileButton.setText(deleteProfileButtonLabel);
		deleteProfileButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		deleteProfileButton.setMaximumSize(buttonSize);
		deleteProfileButton.setMinimumSize(buttonSize);
		deleteProfileButton.setPreferredSize(buttonSize);
		
		deleteProfileButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserDeleteAnswer answer = sendDeleteRequest();
				if (!answer.successful) {
					return;
				}
				
				RequestSender.username = "";
				onLogoutFunction.run();
			}
		});
	}
	
	public void initLogoutButton(Dimension buttonSize) {
		Language lang = Window.getConfig().language;
		
		logoutButton = new TextButton();
		String logoutButtonLabel = lang.get("menuSideUserButtonLogout");
		logoutButton.setText(logoutButtonLabel);
		logoutButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		logoutButton.setMaximumSize(buttonSize);
		logoutButton.setMinimumSize(buttonSize);
		logoutButton.setPreferredSize(buttonSize);
		
		logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RequestSender.username = "";
				onLogoutFunction.run();
			}
		});
	}
	
	private UserDeleteAnswer sendDeleteRequest() {
		return (UserDeleteAnswer) RequestSender.sendRequest(new UserDeleteRequest(RequestSender.username), RequestType.USERDELETE);
	}
	
}
