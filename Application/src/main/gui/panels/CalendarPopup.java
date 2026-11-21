package main.gui.panels;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import main.gui.panels.CalendarPanel.CalendarDateListener;

public class CalendarPopup extends JPanel {
	private JPopupMenu popup;
	private CalendarPanel calendarPanel;
	
	public CalendarPopup(CalendarDateListener calendarDateListener) {
		calendarPanel = new CalendarPanel(data -> {
			calendarDateListener.onCalendarDateClicked(data);
			popup.setVisible(false);
			popup = null;
		});
	}
	
	public void showPopupBelow(Component invoker) {
		if (popup == null) {
			popup = new JPopupMenu();
			popup.setOpaque(false);
			//popup.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			popup.add(calendarPanel);
		}
		popup.show(invoker, 0, invoker.getHeight());
	}
}
