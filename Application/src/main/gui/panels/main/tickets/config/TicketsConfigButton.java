package main.gui.panels.main.tickets.config;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import main.gui.Window;
import main.gui.config.theme.Theme;


public class TicketsConfigButton extends JButton {
	private boolean chosen = false;
	private ImageIcon baseIcon;
	private ImageIcon chosenIcon;
	
	public TicketsConfigButton(String text, File iconFile, Runnable onClickFunction, Dimension buttonSize) {
		this.setMaximumSize(buttonSize);
		this.setPreferredSize(buttonSize);
		this.setMinimumSize(buttonSize);
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.setHorizontalAlignment(SwingConstants.LEFT);
		
		//this.setBackground(Window.getConfig().theme.secondaryColor);
		
		Dimension iconSize = new Dimension((int)(buttonSize.getHeight() * 0.8), (int)(buttonSize.getHeight() * 0.8));
		
		Theme theme = Window.getConfig().theme;
		ImageIcon baseIconDefault = Theme.recolorIcon(new ImageIcon(iconFile.getPath()), theme.buttonColor);
		ImageIcon hoverIconDefault = Theme.recolorIcon(new ImageIcon(iconFile.getPath()), theme.buttonHoverColor);
		ImageIcon clickIconDefault = Theme.recolorIcon(new ImageIcon(iconFile.getPath()), theme.buttonClickColor);
		
		ImageIcon baseResized = Theme.resizeIcon(baseIconDefault, (int)(iconSize.getWidth() * 0.8), (int)(iconSize.getHeight() * 0.8));
		ImageIcon chosenResized = Theme.resizeIcon(baseIconDefault, (int)iconSize.getWidth(), (int)iconSize.getHeight());
		ImageIcon hoverResized = Theme.resizeIcon(hoverIconDefault, (int)iconSize.getWidth(), (int)iconSize.getHeight());
		ImageIcon clickResized = Theme.resizeIcon(clickIconDefault, (int)iconSize.getWidth(), (int)iconSize.getHeight());
		
		baseIcon = Theme.drawOnFixCanvas(baseResized, (int)(iconSize.getWidth() * 0.1), (int)(iconSize.getHeight() * 0.1), (int)iconSize.getWidth(), (int)iconSize.getHeight());
		chosenIcon = chosenResized;
		
		//this.setIcon(drawOnFixCanvas(baseIcon, (int)(buttonSize * 0.1), (int)(buttonSize * 0.1), buttonSize, buttonSize));
		this.setIcon(baseIcon);
		this.setRolloverIcon(hoverResized);
		this.setPressedIcon(clickResized);
		
		this.setBorderPainted(false);
		this.setContentAreaFilled(false);
		this.setFocusPainted(false);
		
		this.setText(text);
		this.setFont(new Font("Arial", Font.PLAIN, 20));
		this.setForeground(Color.WHITE);
		
		this.setIconTextGap((int)(baseIcon.getIconWidth() * 0.25));
		
		addClickFunction(onClickFunction);
		addHoverListener();
	}
	
	private void addClickFunction(Runnable onClickFunction) {
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				onClickFunction.run();
			}
		});
	}
	
	private void addHoverListener() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				setIconTextGap((int)(chosenIcon.getIconWidth() * 0.25));
			}
			
			@Override
            public void mouseExited(MouseEvent e) {
				if (!chosen) {
					setIconTextGap((int)(baseIcon.getIconWidth() * 0.1));
				}
            }
		});
	}
	
	public void setChosen(boolean chosen) {
		this.chosen = chosen;
	}
	
	public void updateChosen() {
		if (chosen) {
			this.setIcon(chosenIcon);
			this.setIconTextGap((int)(chosenIcon.getIconWidth() * 0.25));
		} else {
			this.setIcon(baseIcon);
			this.setIconTextGap((int)(baseIcon.getIconWidth() * 0.1));
		}
	}
	
	
	
	
}
