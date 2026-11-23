package main.gui.panels.util;

import java.awt.Component;
import java.time.LocalDate;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import main.gui.panels.util.CalendarPanel.CalendarDateListener;

public class CalendarPopup extends JPanel {
	private JPopupMenu popup;
	private CalendarPanel calendarPanel;
	
	public CalendarPopup(LocalDate date, CalendarDateListener calendarDateListener) {
		calendarPanel = new CalendarPanel(date, data -> {
			calendarDateListener.onCalendarDateClicked(data);
			popup.setVisible(false);
			popup = null;
		});
	}
	
	public CalendarPopup(CalendarDateListener calendarDateListener) {
		this(LocalDate.now(), calendarDateListener);
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
