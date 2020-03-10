import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;



class CalendarTableRenderer extends DefaultTableCellRenderer {
	public static boolean isCellSelected;
	public static int selectedMonth, selectedYear, selectedDay;


	public Component getTableCellRendererComponent(JTable table, Object value, boolean selected,
	                                               boolean focused,
	                                               int row, int column) {
		super.getTableCellRendererComponent(table, value, selected, focused, row, column);

		if (column == 0) { // Week-end
			setBackground(Color.pink);
		} else if (column == 6) {
			setBackground(Color.orange);
		} else { // Week
			setBackground(Color.WHITE);
		}
		if (value != null) {
			// Today is gray
			if (Integer.parseInt(value.toString()) == CalendarPanel.realDay
					&& CalendarPanel.currentMonth == CalendarPanel.realMonth
					&& CalendarPanel.currentYear == CalendarPanel.realYear) { // Today
				setBackground(Color.GRAY);
			}
			// set days with events to yellow
			if (CalendarPanel.EventDate.contains(String.format("%02d",value))) {
				setBackground(Color.YELLOW);
			}
		}
		// allows user to select date to add event to
		if (selected && value != null) {
			setBackground(Color.LIGHT_GRAY);
			//events.setText(null);
			//events.setText("");
			selectedMonth = CalendarPanel.currentMonth + 1;
			selectedYear = CalendarPanel.currentYear;
			selectedDay = Integer.parseInt(value.toString());
			EventPanel.displayEvent(selectedDay, selectedMonth, selectedYear);
			isCellSelected = true;

			System.out.println("render... "+String.format("%d%02d%02d", selectedYear, selectedMonth, selectedDay));

		}
		setBorder(null);
		setForeground(Color.BLACK);



		return this;
	}
}
