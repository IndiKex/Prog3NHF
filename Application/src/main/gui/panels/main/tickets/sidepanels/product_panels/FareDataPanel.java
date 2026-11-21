package main.gui.panels.main.tickets.sidepanels.product_panels;

import java.awt.BorderLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import main.gui.Window;
import main.gui.config.theme.Theme;
import main.gui.panels.InvisibleJPanel;
import main.gui.panels.Refreshable;
import main.gui.panels.VisibleJLabel;

public class FareDataPanel extends JPanel implements ProductDataPanel {
private List<Refreshable> refreshables = new ArrayList<>();
	
	public FareDataPanel(String area, LocalDate validityStart, String validityLength, int price) {
		
		VisibleJLabel areaLabel = new VisibleJLabel(area);
		VisibleJLabel validityStartLabel = new VisibleJLabel(validityStart.toString());
		VisibleJLabel validityLengthLabel = new VisibleJLabel(validityLength);
		VisibleJLabel priceLabel = new VisibleJLabel(Integer.toString(price) + " Ft");
		if (price < 0) {
			priceLabel.setVisible(false);
		}
		
		refreshables.add(areaLabel);
		refreshables.add(validityStartLabel);
		refreshables.add(validityLengthLabel);
		refreshables.add(priceLabel);
	
		JPanel timePanel = new InvisibleJPanel();
		timePanel.add(validityStartLabel);
		timePanel.add(validityLengthLabel);
		
		JPanel dataPanel = new InvisibleJPanel();
		dataPanel.setLayout(new BorderLayout());
		
		dataPanel.add(areaLabel, BorderLayout.WEST);
		dataPanel.add(timePanel, BorderLayout.SOUTH);
		dataPanel.add(priceLabel, BorderLayout.EAST);
		
		this.add(dataPanel);
		
		Theme theme = Window.getConfig().theme;
		this.setBackground(theme.baseColor);
	}
	
	public void refreshColors() {
		Theme theme = Window.getConfig().theme;
		this.setBackground(theme.baseColor);
		
		for (Refreshable refreshable : refreshables) {
			refreshable.refreshColors();
		}
	}
	
}
