package main.gui.panels.main.tickets.sidepanels.ticket_list;

import java.awt.BorderLayout;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.gui.Window;
import main.gui.config.theme.Theme;
import main.gui.panels.InvisibleJPanel;
import main.gui.panels.Refreshable;
import main.gui.panels.TextButton;
import main.gui.panels.VisibleJLabel;

public class TicketDataPanel extends JPanel implements Refreshable {
	private List<Refreshable> refreshables = new ArrayList<>();
	
	public TicketDataPanel(String trainID, LocalDateTime departureTime, LocalDateTime arrivalTime, int price) {
		
		VisibleJLabel idLabel = new VisibleJLabel(trainID);
		VisibleJLabel departureLabel = new VisibleJLabel(departureTime.toString());
		VisibleJLabel arrivalLabel = new VisibleJLabel(arrivalTime.toString());
		VisibleJLabel priceLabel = new VisibleJLabel(Integer.toString(price) + " Ft");
		
		refreshables.add(idLabel);
		refreshables.add(departureLabel);
		refreshables.add(arrivalLabel);
		refreshables.add(priceLabel);
	
		JPanel timePanel = new InvisibleJPanel();
		timePanel.add(departureLabel);
		timePanel.add(arrivalLabel);
		
		JPanel dataPanel = new InvisibleJPanel();
		dataPanel.setLayout(new BorderLayout());
		
		dataPanel.add(idLabel, BorderLayout.WEST);
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
