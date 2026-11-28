package main.gui.panels.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.gui.panels.main.tickets.sidepanels.product_panels.ProductDataPanel;

public class ProductListPanel extends VisibleJPanel implements Refreshable {
	private List<ProductDataPanel> dataPanels = new ArrayList<>();
	private JPanel dataHolderPanel;
	private JScrollPane scrollPane;
	
	protected VisibleJLabel emptyListText;
	private JPanel emptyListTextPanel;
	
	public ProductListPanel() {
		initPanels();
	}
	
	protected void clearPanel() {
		dataHolderPanel.removeAll();
	}
	
	protected void addToDataHolderPanel(JPanel panel) {
		dataHolderPanel.add(panel);
	}
	
	protected void addProductDataPanel(ProductDataPanel dataPanel) {
		dataHolderPanel.add(dataPanel);
		dataPanels.add(dataPanel);
	}
	
	protected void addProductDataPanel(JPanel holderPanel, ProductDataPanel dataPanel) {
		holderPanel.add(dataPanel);
		dataPanels.add(dataPanel);
	}
	
	protected void addEmptyListText() {
		dataHolderPanel.add(emptyListTextPanel);
	}
	
	private void initPanels() {
		dataHolderPanel = new InvisibleJPanel();
		dataHolderPanel.setLayout(new BoxLayout(dataHolderPanel, BoxLayout.Y_AXIS));
		
		scrollPane = new VisibleJScrollPane(dataHolderPanel);
		scrollPane.setPreferredSize(this.getSize());
		
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		
		
		emptyListTextPanel = new InvisibleJPanel();
		emptyListTextPanel.setLayout(new BoxLayout(emptyListTextPanel, BoxLayout.Y_AXIS));
		emptyListTextPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		emptyListText = new VisibleJLabel("");
		emptyListText.setAlignmentX(Component.CENTER_ALIGNMENT);
		emptyListTextPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		emptyListTextPanel.add(emptyListText);
	}
	
	public void refreshColors() {
		super.refreshColors();
		
		for (ProductDataPanel panel : dataPanels) {
			panel.refreshColors();
		}
		emptyListText.refreshColors();
	}
}
