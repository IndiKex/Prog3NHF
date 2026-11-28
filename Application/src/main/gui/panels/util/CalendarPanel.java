package main.gui.panels.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import main.gui.Window;
import main.gui.config.lang.Language;
import main.gui.config.theme.Theme;

public class CalendarPanel extends JPanel implements Refreshable {
	private class MonthTableModel extends AbstractTableModel {
		private List<LocalDate> dates;
		private List<String> columnNames;
		
		public MonthTableModel(List<LocalDate> dates) {
			this.dates = dates;
			Language lang = Window.getConfig().language;
			columnNames = List.of(
				lang.get(DayOfWeek.MONDAY),
				lang.get(DayOfWeek.TUESDAY),
				lang.get(DayOfWeek.WEDNESDAY),
				lang.get(DayOfWeek.THURSDAY),
				lang.get(DayOfWeek.FRIDAY),
				lang.get(DayOfWeek.SATURDAY),
				lang.get(DayOfWeek.SUNDAY)
			);
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
		
		@Override
		public Class getColumnClass(int columnIndex) {
			return Integer.class;
		}
	}
	
	private class DayTableCellRenderer extends JLabel implements TableCellRenderer {

		private TableCellRenderer renderer;

		public DayTableCellRenderer(TableCellRenderer defRenderer) {
			this.renderer = defRenderer;
		}
   	 
	   	public Component getTableCellRendererComponent(JTable table,
	   		Object value, boolean isSelected, boolean hasFocus,
	   		int row, int column) {
			Component component = renderer.getTableCellRendererComponent(
			table, value, isSelected, hasFocus, row, column);
			// Kikeressük az éppen megjelenítendő hallgatót a külső,
			// StudentFrame osztály data tagváltozójából,
			// megállapítjuk, hogy bukásra áll-e vagy sem,
			// és ez alapján átállítjuk a komponens háttérszínét:
			// component.setBackground(...)
			
			
			Theme theme = Window.getConfig().theme;
			Font font = new Font("Arial", Font.PLAIN, 15);
			
			component.setBackground(theme.baseColor);
			component.setForeground(theme.calendarDateColor);
			JLabel componentLabel = (JLabel)component;
			componentLabel.setHorizontalAlignment(JLabel.CENTER);
			componentLabel.setFont(font);
			componentLabel.setBorder(null);
			//componentLabel.setOpaque(false);
			
			if ((row == 0 && (Integer)value > 7) || (row >= 4 && (Integer)value < 7)) {
				component.setForeground(theme.calendarGreyedColor);
			}
			
			if (Integer.parseInt(yearLabel.getText()) == LocalDate.now().getYear() && LocalDate.now().getMonth().name().equals(monthLabel.getText()) && (Integer)value == LocalDate.now().getDayOfMonth()) {
				component.setForeground(theme.calendarNowColor);
			}
			
			return component;
	   	}
	 }
	
	public static interface CalendarDateListener {
		public void onCalendarDateClicked(LocalDate date);
	}
	
	private JPanel lowerPanel;
	private LocalDate currentDate;
	private TextField yearLabel;
	private VisibleJLabel monthLabel;
	
	private CalendarDateListener calendarDateListener;
	
	public CalendarPanel(LocalDate currentDate, CalendarDateListener calendarDateListener) {
		this.currentDate = currentDate;
		this.calendarDateListener = calendarDateListener;
		
		Theme theme = Window.getConfig().theme;
		
		this.setBackground(theme.baseColor);
		
		Border border = BorderFactory.createLineBorder(theme.secondaryColor, 3);
		this.setBorder(border);
		
		
		initPanels();
		initTable();
		
		updateLabels();
	}
	
	public CalendarPanel(CalendarDateListener calendarDateListener) {
		this(LocalDate.now(), calendarDateListener);
	}
	
	private void initTable() {
		Theme theme = Window.getConfig().theme;
		
		JPanel tableHolder = new InvisibleJPanel();
		tableHolder.setLayout(new BorderLayout());
		
		
		List<LocalDate> dates = calculateMonthDates();
		MonthTableModel model = new MonthTableModel(dates);
		JTable table = new JTable(model);
		table.setOpaque(false);
		table.setDefaultRenderer(Integer.class, new DayTableCellRenderer(table.getDefaultRenderer(Integer.class)));
		table.setShowGrid(false);
		table.setIntercellSpacing(new Dimension(0, 0));
		
		JTableHeader header = table.getTableHeader();
		header.setBackground(theme.baseColor);
		header.setForeground(theme.textColor);
		header.setFont(new Font("Arial", Font.BOLD, 13));
		header.setBorder(null);
		
		int cellSize = 40;
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(cellSize);
		for (int i = 0; i < model.getColumnCount(); i++) {
			TableColumn column = table.getColumnModel().getColumn(i);
			column.setPreferredWidth(cellSize);
			column.setMaxWidth(cellSize);
			column.setMinWidth(cellSize);
		}
		
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
		
		lowerPanel.revalidate();
		lowerPanel.repaint();
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
		
		Dimension topPanelSize = new Dimension(1000, 60);
		Dimension buttonSize = new Dimension(topPanelSize.height, topPanelSize.height);
		
		this.setLayout(new BorderLayout());
		
		JPanel topPanel = new InvisibleJPanel();
		topPanel.setLayout(new BorderLayout());
		topPanel.setMaximumSize(topPanelSize);
		
		JButton leftButton = new TextButton();
		leftButton.setText("<");
		leftButton.setBorder(null);
		leftButton.setMaximumSize(buttonSize);
		leftButton.setPreferredSize(buttonSize);
		leftButton.addActionListener(e -> {
			currentDate = currentDate.minusMonths(1);
			updateLabels();
			initTable();
		});
		
		JButton rightButton = new TextButton();
		rightButton.setText(">");
		rightButton.setBorder(null);
		rightButton.setMaximumSize(buttonSize);
		rightButton.setPreferredSize(buttonSize);
		rightButton.addActionListener(e -> {
			currentDate = currentDate.plusMonths(1);
			updateLabels();
			initTable();
		});
		
		JPanel yearMonthHolder = new InvisibleJPanel();
		yearMonthHolder.setLayout(new BorderLayout());
		
		yearLabel = new TextField(4);
		yearLabel.setBorder(null);
		yearLabel.setHorizontalAlignment(JTextField.CENTER);
		yearLabel.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				adjustYearLabel();
			}
		});
		yearLabel.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}

			public void keyPressed(KeyEvent e) {}

			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					adjustYearLabel();
					KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
				}
			}
			
		});
		
		monthLabel = new VisibleJLabel("Nov");
		monthLabel.setHorizontalAlignment(JTextField.CENTER);
		
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
	
	private void adjustYearLabel() {
		String text = yearLabel.getText();
		int sigma = currentDate.getYear();
		try {
			sigma = Integer.parseInt(text);
		} catch (Exception e) {}
		
		currentDate = currentDate.minusYears(currentDate.getYear() - sigma);
		updateLabels();
		initTable();
	}
	
	private void updateLabels() {
		Language lang = Window.getConfig().language;
		yearLabel.setText(Integer.toString(currentDate.getYear()));
		monthLabel.setText(lang.get(currentDate.getMonth()));
	}

	@Override
	public void refreshColors() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void refreshLanguage() {
		// TODO Auto-generated method stub
		
	}
}
