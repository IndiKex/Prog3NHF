package main.gui.panels;

import java.awt.CardLayout;
import java.util.List;

import javax.swing.JPanel;

public class SidePanelContainer<E extends Enum<E>> extends InvisibleJPanel {
	
	public SidePanelContainer(List<JPanel> panels, List<E> panelEnumIDs) {
		this.setLayout(new CardLayout());
		
		for (int i = 0; i < panelEnumIDs.size(); i++) {
			E enumID = panelEnumIDs.get(i);
			JPanel panel = panels.get(i);
			this.add(panel, enumID.name());
		}
	}
	
	public void switchPanel(E enumID) {
		CardLayout cl = (CardLayout)this.getLayout();
		cl.show(this, enumID.name());
	}
}
