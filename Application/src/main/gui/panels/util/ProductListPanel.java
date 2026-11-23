package main.gui.panels.util;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.gui.panels.main.tickets.sidepanels.product_panels.ProductDataPanel;

public class ProductListPanel extends VisibleJPanel implements Refreshable {
	private List<ProductDataPanel> dataPanels = new ArrayList<>();
	private JPanel dataHolderPanel;
	private JScrollPane scrollPane;
	
	public ProductListPanel() {
		initPanels();
	}
	
	public void clearPanel() {
		dataHolderPanel.removeAll();
	}
	
	public void addToDataHolderPanel(JPanel panel) {
		dataHolderPanel.add(panel);
	}
	
	public void addProductDataPanel(ProductDataPanel dataPanel) {
		dataHolderPanel.add(dataPanel);
		dataPanels.add(dataPanel);
	}
	
	public void addProductDataPanel(JPanel holderPanel, ProductDataPanel dataPanel) {
		holderPanel.add(dataPanel);
		dataPanels.add(dataPanel);
	}
	
	private void initPanels() {
		dataHolderPanel = new InvisibleJPanel();
		dataHolderPanel.setLayout(new BoxLayout(dataHolderPanel, BoxLayout.Y_AXIS));
		
		scrollPane = new VisibleJScrollPane(dataHolderPanel);
		scrollPane.setPreferredSize(this.getSize());
		
		this.setLayout(new BorderLayout());
		this.add(scrollPane, BorderLayout.CENTER);
		
	}
	
	public void refreshColors() {
		super.refreshColors();
		
		for (ProductDataPanel panel : dataPanels) {
			panel.refreshColors();
		}
	}
}
