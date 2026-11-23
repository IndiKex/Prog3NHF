package main.gui.panels.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.ComboPopup;

import main.gui.Window;
import main.gui.config.theme.Theme;

public class VisibleJComboBox<E> extends JComboBox<E> implements Refreshable {
	private Color background;
	private Color foreground;
	private Color arrowBackground;
	private Color arrowColor;
	private Color scrollThumb;
	private Color scrollTrack;
	
	public VisibleJComboBox(E[] objList) {
		super(objList);

		Font font = new Font("Arial", Font.PLAIN, 20);

		this.setOpaque(false);
		this.setFont(font);
		
		refreshColors();
	}

	@Override
	public void refreshColors() {
		Theme theme = Window.getConfig().theme;

		background = theme.baseColor;
		foreground = theme.textColor;
		arrowBackground = theme.baseColor;
		arrowColor = theme.secondaryColor;
		scrollThumb = theme.secondaryColor;
		scrollTrack = theme.baseColor;
		
		
        this.setBackground(background);
        this.setForeground(foreground);
        
        Border border = BorderFactory.createLineBorder(theme.secondaryColor, 3);
		this.setBorder(border);

        // 2. Customize Dropdown List & Hover/Selection colors
        this.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBackground(background);
                setForeground(foreground);
                return this;
            }
        });

        this.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                BasicArrowButton button = new BasicArrowButton(
                    BasicArrowButton.SOUTH,
                    arrowBackground,
                    arrowColor,
                    arrowColor,
                    arrowBackground
                );
                button.setBorder(BorderFactory.createEmptyBorder());
                return button;
            }
            
            @Override
            protected ComboPopup createPopup() {
                return new BasicComboPopup(comboBox) {
                	@Override
                	protected void configurePopup() {
                        setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
                        setBorderPainted( true );
                        Border border = BorderFactory.createLineBorder(theme.secondaryColor, 3);
                        setBorder(border);
                        setOpaque( false );
                        add( scroller );
                        setDoubleBuffered( true );
                        setFocusable( false );
                    }
                	
                    @Override
                    protected JScrollPane createScroller() {
                        JScrollPane scroller = super.createScroller();
                        scroller.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
                            @Override
                            protected void configureScrollBarColors() {
                                this.thumbColor = scrollThumb;
                                this.trackColor = scrollTrack;
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
                        return scroller;
                    }
                };
            }
        });
	}
}
