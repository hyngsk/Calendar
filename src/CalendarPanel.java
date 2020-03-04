import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

public class CalendarPanel extends JPanel implements ActionListener {

	public JLabel lblMonth, lblYear;
	public JButton btnPrev, btnNext;
	public JComboBox cmbYear;
	public JTable tblCalendar;
	public DefaultTableModel Tmodel;
	public JScrollPane TableScroll;

	String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; // All headers
	static int realDay, realMonth, realYear;
	static int currentMonth, currentYear;
	public static boolean isCellSelected;
	public static int selectedMonth, selectedYear, selectedDay;

	public CalendarPanel() {
		super(null);

		setBorder(BorderFactory.createTitledBorder("Calendar"));
		setBounds(10, 10, 320, 335);

		lblMonth = new JLabel("January");
		lblYear = new JLabel("Change year:");
		cmbYear = new JComboBox();
		btnPrev = new JButton("Prev");
		btnNext = new JButton("Next");
		Tmodel = new DefaultTableModel() {
			public boolean isCellEditable(int rowIndex, int mColIndex) {
				return false;
			}
		};
		tblCalendar = new JTable(Tmodel);
		TableScroll = new JScrollPane(tblCalendar);

		add(lblMonth);
		add(lblYear);
		add(cmbYear);
		add(btnPrev);
		add(btnNext);
		add(TableScroll);

		lblMonth.setBounds(160 - lblMonth.getPreferredSize().width / 2, 25, 120, 25);
		lblYear.setBounds(10, 305, 200, 20);
		cmbYear.setBounds(225, 305, 90, 20);
		btnPrev.setBounds(10, 20, 60, 28);
		btnNext.setBounds(250, 20, 60, 28);
		TableScroll.setBounds(10, 50, 300, 250);

		btnPrev.addActionListener(this::actionPerformed);
		btnNext.addActionListener(this::actionPerformed);
		cmbYear.addActionListener(this::actionPerformed);

		initCalendar();
	}

	public void initCalendar() {
		// Get real month/year
		GregorianCalendar cal = new GregorianCalendar(); // Create calendar
		realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); // Get day
		realMonth = cal.get(GregorianCalendar.MONTH); // Get month
		realYear = cal.get(GregorianCalendar.YEAR); // Get year
		currentMonth = realMonth; // Match month and year
		currentYear = realYear;

		for (int i = 0; i < 7; i++) {
			Tmodel.addColumn(headers[i]);
		}
		tblCalendar.getParent().setBackground(tblCalendar.getBackground()); // Set background
		// No resize/reorder
		tblCalendar.getTableHeader().setResizingAllowed(false);
		tblCalendar.getTableHeader().setReorderingAllowed(false);

		// Single cell selection
		tblCalendar.setColumnSelectionAllowed(true);
		tblCalendar.setRowSelectionAllowed(true);
		tblCalendar.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Set row/column count
		tblCalendar.setRowHeight(38);
		Tmodel.setColumnCount(7);
		Tmodel.setRowCount(6);
		for (int i = realYear - 100; i <= realYear + 100; i++) {
			cmbYear.addItem(String.valueOf(i));
		}

		refreshCalendar(realMonth, realYear);

	}

	public void refreshCalendar(int month, int year) {

		String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		int nod, som; // Number Of Days, Start Of Month


		btnPrev.setEnabled(true);
		btnNext.setEnabled(true);
		if (month == 0 && year <= realYear - 10) {
			btnPrev.setEnabled(false);
		} // Too early
		if (month == 11 && year >= realYear + 100) {
			btnNext.setEnabled(false);
		} // Too late
		lblMonth.setText(months[month]); // Refresh the month label (at the top)
		lblMonth.setBounds(160 - lblMonth.getPreferredSize().width / 2, 25, 180, 25); // Re-align label with calendar
		cmbYear.setSelectedItem(String.valueOf(year)); // Select the correct year in the combo box

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				Tmodel.setValueAt(null, i, j);
			}
		}

		GregorianCalendar cal = new GregorianCalendar(year, month, 1);
		nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
		som = cal.get(GregorianCalendar.DAY_OF_WEEK);

		for (int i = 1; i <= nod; i++) {
			@SuppressWarnings("deprecation")
			int row = new Integer((i + som - 2) / 7);
			int column = (i + som - 2) % 7;
			Tmodel.setValueAt(i, row, column);
		}

		tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), new tblCalendarRenderer());
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnNext) {
			if (currentMonth == 11) { // Forward one year
				currentMonth = 0;
				currentYear += 1;
			} else { // Forward one month
				currentMonth += 1;
			}
			//Refresh calendar panel
			refreshCalendar(currentMonth, currentYear);
		}
		//Previous button
		if (e.getSource() == btnPrev) {
			if (currentMonth == 0) { // Back one year
				currentMonth = 11;
				currentYear -= 1;
			} else { // Back one month
				currentMonth -= 1;
			}
			//Refresh calendar panel
			refreshCalendar(currentMonth, currentYear);
		}
		//Change year
		if (e.getSource() == cmbYear) {
			if (cmbYear.getSelectedItem() != null) {
				String b = cmbYear.getSelectedItem().toString();
				currentYear = Integer.parseInt(b);
				//Refresh calendar panel
				refreshCalendar(currentMonth, currentYear);
			}
		}
	}

	static class tblCalendarRenderer extends DefaultTableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean selected,
		                                               boolean focused,
		                                               int row, int column) {
			super.getTableCellRendererComponent(table, value, selected, focused, row, column);

			if (column == 0 || column == 6) { // Week-end
				setBackground(Color.ORANGE);
			} else { // Week
				setBackground(Color.WHITE);
			}
			if (value != null) {
				// set actual day to gray
				if (Integer.parseInt(value.toString()) == realDay && currentMonth == realMonth && currentYear == realYear) { // Today
					setBackground(Color.GRAY);
				}
//				// set days with events to yellow
//				if (findEvent(value, currentMonth + 1, currentYear)) {
//					setBackground(Color.YELLOW);
//				}
			}
			// allows user to select date to add event to
			if (selected && value != null) {
				setBackground(Color.LIGHT_GRAY);
				//events.setText(null);
				//events.setText("");
				selectedMonth = currentMonth + 1;
				selectedYear = currentYear;
				selectedDay = Integer.parseInt(value.toString());
				//displayEvents(selectedDay, selectedMonth, selectedYear);
				isCellSelected = true;

				System.out.println(String.format("%d%02d%02d", selectedYear, selectedMonth, selectedDay));
			}

			setBorder(null);
			setForeground(Color.BLACK);

			return this;
		}
	}


}
