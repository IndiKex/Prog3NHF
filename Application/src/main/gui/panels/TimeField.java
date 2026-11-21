package main.gui.panels;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalTime;

public class TimeField extends TextField {
	private LocalTime time = LocalTime.now();
	
	public TimeField() {
		super(5);
		
		this.setText(LocalTime.now().toString().substring(0, 5));
		
		this.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				// sigma
			}
			
			public void focusLost(FocusEvent e) {
				String text = getText();
				String[] textSplit = text.split(":");
				
				int hour = LocalTime.now().getHour();
				int minute = LocalTime.now().getMinute();
				
				if (textSplit.length == 1) {
					try {
						hour = Integer.parseInt(textSplit[0]);
					} catch (Exception err) {}
				} else if (textSplit.length >= 2) {
					try {
						hour = Integer.parseInt(textSplit[0]);
						minute = Integer.parseInt(textSplit[1]);
					} catch (Exception err) {}
				}
				
				time = LocalTime.of(hour, minute, 0, 0);
				setText(time.toString().substring(0, 5));
			}
		});
	}
	
	public LocalTime getTime() {
		return time;
	}
}
