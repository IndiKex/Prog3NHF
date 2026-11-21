package main.gui.panels;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import main.gui.Window;
import main.gui.config.theme.Theme;

public class CalendarPanel extends JPanel {
	private class MonthTableModel extends AbstractTableModel {
		private List<LocalDate> dates;
		private List<String> columnNames;
		
		public MonthTableModel(List<LocalDate> dates) {
			this.dates = dates;
			columnNames = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
		}
		
		public LocalDate getDateAt(int rowIndex, int colIndex) {
			return dates.get(rowIndex * 7 + colIndex);
		}

		@Override
		public int getRowCount() {
			return dates.size() / 7;
		}

		@Override
		public int getColumnCount() {
			return 7;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return dates.get(rowIndex * 7 + columnIndex).getDayOfMonth();
		}

		@Override
        public String getColumnName(int col) {
            return columnNames.get(col);
        }
	}
	
	public static interface CalendarDateListener {
		public void onCalendarDateClicked(LocalDate date);
	}
	
	private JPanel lowerPanel;
	private LocalDate currentDate = LocalDate.now();
	private VisibleJLabel yearLabel;
	private VisibleJLabel monthLabel;
	
	private CalendarDateListener calendarDateListener;
	
	public CalendarPanel(CalendarDateListener calendarDateListener) {
		this.calendarDateListener = calendarDateListener;
		
		Theme theme = Window.getConfig().theme;
		
		this.setBackground(theme.baseColor);
		
		initPanels();
		initTable();
	}
	
	private void initTable() {
		JPanel tableHolder = new InvisibleJPanel();
		tableHolder.setLayout(new BorderLayout());
		
		
		List<LocalDate> dates = calculateMonthDates();
		MonthTableModel model = new MonthTableModel(dates);
		JTable table = new JTable(model);
		table.setOpaque(false);
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				int col = table.columnAtPoint(e.getPoint());
				
				if (row != -1 && col != -1) {
					int modelRow = table.convertRowIndexToModel(row);
					int modelCol = table.convertColumnIndexToModel(col);
					LocalDate date = model.getDateAt(modelRow, modelCol);
					calendarDateListener.onCalendarDateClicked(date);
				}
			}
		});
		
		tableHolder.add(table.getTableHeader(), BorderLayout.NORTH);
		tableHolder.add(table, BorderLayout.CENTER);
		
		lowerPanel.removeAll();
		lowerPanel.add(tableHolder, BorderLayout.CENTER);
	}
	
	private List<LocalDate> calculateMonthDates() {
		ArrayList<LocalDate> dates = new ArrayList<>();
		
		LocalDate nowDate = currentDate;
		int nowMonthNDays = nowDate.lengthOfMonth();
		
		LocalDate startDate = nowDate.minusDays(nowDate.getDayOfMonth() - 1);
		DayOfWeek startDateDayOfWeek = startDate.getDayOfWeek();
		int startOrdinal = startDateDayOfWeek.ordinal();
		LocalDate prevDate = startDate.minusDays(1);
		for (int i = startOrdinal; i > 0; i--) {
			dates.add(prevDate.minusDays(i - 1));
		}
		
		for (int i = 0; i < nowMonthNDays; i++) {
			dates.add(startDate.plusDays(i));
		}
		
		LocalDate endDate = startDate.plusDays(nowMonthNDays - 1);
		LocalDate nextDate = endDate.plusDays(1);
		int nDaysLeft = ((dates.size() / 7) + 1) * 7 - dates.size();
		for (int i = 0; i < nDaysLeft; i++) {
			dates.add(nextDate.plusDays(i));
		}
		
		return dates;
	}
	
	private void initPanels() {
		Theme theme = Window.getConfig().theme;
		
		this.setLayout(new BorderLayout());
		
		JPanel topPanel = new InvisibleJPanel();
		topPanel.setLayout(new BorderLayout());
		
		JButton leftButton = new JButton();
		leftButton.setText("<");
		leftButton.addActionListener(e -> {
			currentDate = currentDate.minusMonths(1);
			updateLabels();
			initTable();
		});
		
		JButton rightButton = new JButton();
		rightButton.setText(">");
		rightButton.addActionListener(e -> {
			currentDate = currentDate.plusMonths(1);
			updateLabels();
			initTable();
		});
		
		JPanel yearMonthHolder = new InvisibleJPanel();
		yearMonthHolder.setLayout(new BorderLayout());
		
		yearLabel = new VisibleJLabel("2026");
		monthLabel = new VisibleJLabel("Nov");
		
		yearMonthHolder.add(yearLabel, BorderLayout.NORTH);
		yearMonthHolder.add(monthLabel, BorderLayout.CENTER);
		
		topPanel.add(leftButton, BorderLayout.WEST);
		topPanel.add(rightButton, BorderLayout.EAST);
		topPanel.add(yearMonthHolder, BorderLayout.CENTER);
		
		lowerPanel = new InvisibleJPanel();
		lowerPanel.setLayout(new BorderLayout());
		
		this.add(topPanel, BorderLayout.NORTH);
		this.add(lowerPanel, BorderLayout.CENTER);
	}
	
	private void updateLabels() {
		yearLabel.setText(Integer.toString(currentDate.getYear()));
		monthLabel.setText(currentDate.getMonth().name());
	}
}
