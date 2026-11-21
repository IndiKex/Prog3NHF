package main.gui.panels;

import java.time.LocalDate;


public class DateChoiceButton extends TextButton {
	private LocalDate date = LocalDate.now();
	
	public DateChoiceButton() {
		this.setText(date.toString());
		this.addActionListener(e -> {
			CalendarPopup popup = new CalendarPopup(d -> {date = d; setText(date.toString());});
			popup.showPopupBelow(this);
		});
	}
	
	public LocalDate getDate() {
		return date;
	}
}
