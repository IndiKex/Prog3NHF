package main.gui.panels.main.tickets.sidepanels.product_panels;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import main.gui.Window;
import main.gui.config.lang.Language;
import main.gui.config.theme.Theme;
import main.gui.panels.util.InvisibleJPanel;
import main.gui.panels.util.Refreshable;
import main.gui.panels.util.VisibleJLabel;
import main.util.FareCalculator;

public class FareDataPanel extends ProductDataPanel implements Refreshable {
	private Dimension size = new Dimension(400, 200);
	private List<Refreshable> refreshables = new ArrayList<>();

	private int shadowSize = 20;
	private int cornerRad = 20;
	
	public FareDataPanel(String area, LocalDate validityStart, String validityLength, int price) {
		this.setMaximumSize(size);
		this.setMinimumSize(size);
		this.setPreferredSize(size);
		this.setBorder(new EmptyBorder(10, shadowSize + 10, shadowSize + 10, 10)); // top left bottom right
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		Language lang = Window.getConfig().language;
		
		VisibleJLabel areaLabel = new VisibleJLabel(area);
		VisibleJLabel areaLabelLabel = new VisibleJLabel(lang.get("ticketsSideFareDataCountyFareLabel"));
		//VisibleJLabel validityStartLabel = new VisibleJLabel(validityStart.toString());
		//VisibleJLabel validityLengthLabel = new VisibleJLabel(validityLength);
		VisibleJLabel validityEndLabelLabel = new VisibleJLabel(lang.get("ticketsSideFareDataValidityEndLabel"));
		VisibleJLabel validityEndLabel = new VisibleJLabel(FareCalculator.calculate(validityStart, validityLength).toString());
		validityEndLabel.setFont(new Font("Arial", Font.BOLD, 25));
		VisibleJLabel priceLabel = new VisibleJLabel(Integer.toString(price) + " Ft");
		if (price < 0) {
			priceLabel.setVisible(false);
		}
		
		refreshables.add(areaLabelLabel);
		refreshables.add(areaLabel);
		refreshables.add(validityEndLabelLabel);
		refreshables.add(validityEndLabel);
		//refreshables.add(validityStartLabel);
		//refreshables.add(validityLengthLabel);
		refreshables.add(priceLabel);
		
		JPanel dataPanel = new InvisibleJPanel();
		dataPanel.setLayout(new BoxLayout(dataPanel, BoxLayout.Y_AXIS));
		dataPanel.add(areaLabel);
		dataPanel.add(Box.createRigidArea(new Dimension(0, 7)));
		dataPanel.add(areaLabelLabel);
		dataPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		dataPanel.add(validityEndLabelLabel);
		dataPanel.add(Box.createRigidArea(new Dimension(0, 7)));
		dataPanel.add(validityEndLabel);
		
		this.add(dataPanel, BorderLayout.WEST);
		this.add(priceLabel, BorderLayout.EAST);
		
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
	}
}
