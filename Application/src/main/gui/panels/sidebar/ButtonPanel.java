package main.gui.panels.sidebar;

import java.awt.Dimension;
import java.io.File;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import main.gui.Window;

public class ButtonPanel extends JPanel {
	Dimension preferredSize;
	
	
	public ButtonPanel(Window window, Dimension preferredSize) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.preferredSize = preferredSize;
		
		this.setOpaque(false);
	}
	
	public void addButtons(List<File> paths, List<Runnable> onClickFunctions) {
		int buttonSize = (int)(preferredSize.width * 0.8);
		
		int padding = 0;
		if (paths.size() > 1)
			padding = (preferredSize.height - buttonSize * paths.size()) / (paths.size() - 1);
		
		for (int i = 0; i < paths.size(); i++) {
			SideBarButton button = new SideBarButton(paths.get(i), onClickFunctions.get(i), buttonSize);
			this.add(button);
			
			if (i < paths.size() - 1) {
				this.add(Box.createRigidArea(new Dimension(0, padding))); // distance between buttons
			}
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return preferredSize;
	}
	
}
