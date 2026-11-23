package main.gui.panels.util;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

import main.gui.Window;
import main.gui.config.theme.Theme;

public class VisibleJScrollPane extends JScrollPane implements Refreshable {
	public VisibleJScrollPane(JPanel panel) {
		super(panel);
		
		
		this.setOpaque(false);
		this.getViewport().setOpaque(false);
		this.setBorder(null);
		
		this.getVerticalScrollBar().setUnitIncrement(16);
		
		refreshColors();
	}

	@Override
	public void refreshColors() {
		Theme theme = Window.getConfig().theme;
		this.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
            protected void configureScrollBarColors() {
                this.thumbColor = theme.secondaryColor;
                this.trackColor = theme.baseColor;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton(); // Hides the top scroll arrow
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton(); // Hides the bottom scroll arrow
            }

            private JButton createZeroButton() {
                JButton btn = new JButton();
                btn.setPreferredSize(new Dimension(0, 0));
                return btn;
            }
		});
	}
}
