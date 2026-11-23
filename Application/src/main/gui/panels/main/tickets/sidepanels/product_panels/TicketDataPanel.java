package main.gui.panels.main.tickets.sidepanels.product_panels;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.gui.Window;
import main.gui.config.theme.Theme;
import main.gui.panels.util.InvisibleJPanel;
import main.gui.panels.util.Refreshable;
import main.gui.panels.util.VisibleJLabel;

public class TicketDataPanel extends ProductDataPanel implements Refreshable {
	private Dimension size = new Dimension(400, 200);
	private List<Refreshable> refreshables = new ArrayList<>();
	
	private int shadowSize = 20;
	private int cornerRad = 20;
	
	private JPanel dataPanel;
	private JPanel stopNamePanel;
	private JPanel timePanel;
	int firstPad = 10;
	int secondPad = 50;
	int thirdPad = 20;
	
	public TicketDataPanel(String trainID, String departureStopName, String arrivalStopName, LocalDateTime departureTime, LocalDateTime arrivalTime, int price) {
		this.setMaximumSize(size);
		this.setMinimumSize(size);
		this.setPreferredSize(size);
		this.setBorder(new EmptyBorder(firstPad, shadowSize + 10, shadowSize + 10, 10)); // top left bottom right
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		VisibleJLabel idLabel = new VisibleJLabel(trainID);
		VisibleJLabel departureStopLabel = new VisibleJLabel(departureStopName);
		VisibleJLabel arrivalStopLabel = new VisibleJLabel(arrivalStopName);
		VisibleJLabel departureDateLabel = new VisibleJLabel(departureTime.toLocalDate().toString());
		VisibleJLabel arrivalDateLabel = new VisibleJLabel(arrivalTime.toLocalDate().toString());
		VisibleJLabel departureTimeLabel = new VisibleJLabel(departureTime.toLocalTime().toString());
		VisibleJLabel arrivalTimeLabel = new VisibleJLabel(arrivalTime.toLocalTime().toString());
		VisibleJLabel priceLabel = new VisibleJLabel(Integer.toString(price) + " Ft");
		if (price < 0) {
			priceLabel.setVisible(false);
		}
		
		refreshables.add(idLabel);
		refreshables.add(departureStopLabel);
		refreshables.add(arrivalStopLabel);
		refreshables.add(departureDateLabel);
		refreshables.add(arrivalDateLabel);
		refreshables.add(departureTimeLabel);
		refreshables.add(arrivalTimeLabel);
		refreshables.add(priceLabel);
		
		dataPanel = new InvisibleJPanel();
		dataPanel.setLayout(new BorderLayout());
		dataPanel.add(idLabel, BorderLayout.WEST);
		dataPanel.add(priceLabel, BorderLayout.EAST);
		
		stopNamePanel = new InvisibleJPanel();
		stopNamePanel.setLayout(new BorderLayout());
		stopNamePanel.add(departureStopLabel, BorderLayout.WEST);
		stopNamePanel.add(arrivalStopLabel, BorderLayout.EAST);
		
		timePanel = new InvisibleJPanel();
		timePanel.setLayout(new BorderLayout());
		JPanel departureTimePanel = new InvisibleJPanel();
		departureTimePanel.setLayout(new BoxLayout(departureTimePanel, BoxLayout.Y_AXIS));
		JPanel arrivalTimePanel = new InvisibleJPanel();
		arrivalTimePanel.setLayout(new BoxLayout(arrivalTimePanel, BoxLayout.Y_AXIS));
		
		departureDateLabel.setHorizontalAlignment(JLabel.CENTER);
		departureDateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		departureTimeLabel.setHorizontalAlignment(JLabel.CENTER);
		departureTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		arrivalDateLabel.setHorizontalAlignment(JLabel.CENTER);
		arrivalDateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		arrivalTimeLabel.setHorizontalAlignment(JLabel.CENTER);
		arrivalTimeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		departureTimePanel.add(departureDateLabel);
		departureTimePanel.add(departureTimeLabel);
		arrivalTimePanel.add(arrivalDateLabel);
		arrivalTimePanel.add(arrivalTimeLabel);
		
		timePanel.add(departureTimePanel, BorderLayout.WEST);
		timePanel.add(arrivalTimePanel, BorderLayout.EAST);
		
		this.add(dataPanel);
		this.add(Box.createRigidArea(new Dimension(0, secondPad)));
		this.add(stopNamePanel);
		this.add(Box.createRigidArea(new Dimension(0, thirdPad)));
		this.add(timePanel);
		
		Theme theme = Window.getConfig().theme;
		this.setBackground(theme.baseColor);
		this.setOpaque(false);
	}
	
	public void refreshColors() {
		Theme theme = Window.getConfig().theme;
		this.setBackground(theme.baseColor);
		
		for (Refreshable refreshable : refreshables) {
			refreshable.refreshColors();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		Theme theme = Window.getConfig().theme;
		
		// Establish basic values
		Color borderColor = theme.secondaryColor;
		Color cardColor = Theme.blend(theme.baseColor, Color.WHITE, 0.05f);
		int width = getWidth() - (shadowSize);
        int height = getHeight() - (shadowSize);
		
		// Draw shadow
		for (int i = 0; i < shadowSize; i++) {
			int alpha = (int) (35 * (1 - (float) i / shadowSize));
			g2d.setColor(new Color(0, 0, 0, alpha));
			g2d.fillRoundRect(shadowSize - i, shadowSize - i + shadowSize/2 + 2, width - shadowSize + (i*2), height - (int)(shadowSize*1.5) + (i*2), cornerRad + i, cornerRad + i);
		}
		
		// Draw card
		g2d.setColor(cardColor);
		g2d.fillRoundRect(shadowSize, 0, width, height, cornerRad, cornerRad);
		
		// Draw border
		g2d.setColor(borderColor);
		g2d.setStroke(new BasicStroke(2));
		g2d.drawRoundRect(shadowSize, 0, width, height, cornerRad, cornerRad);
		
		// Draw line
        int circleRad = 5;
        int circleCenterHeight = firstPad + secondPad + thirdPad/2 + dataPanel.getHeight() + stopNamePanel.getHeight();
        int horizontalOffset = 30;
        
		g2d.setColor(theme.secondaryColor);
		g2d.fillOval(shadowSize + 10 + horizontalOffset - circleRad, circleCenterHeight - circleRad, circleRad*2, circleRad*2);
		g2d.fillOval(getWidth() - 10 - horizontalOffset - circleRad, circleCenterHeight - circleRad, circleRad*2, circleRad*2);
		g2d.setStroke(new BasicStroke(3));
		g2d.drawLine(shadowSize + 10 + horizontalOffset, circleCenterHeight, getWidth() - 10 - horizontalOffset, circleCenterHeight);
		
	}
	
}
