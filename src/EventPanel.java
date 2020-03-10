import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import java.util.stream.IntStream;

public class EventPanel extends JPanel implements ActionListener {

	public JButton btnAddEvent, btnDelEvent;
	public JTextField txtData;
	public static JTextArea EventOnDay;
	public static String newLine = "\n";

	public EventPanel() {
		super(null);
		setBounds(330, 10, 320, 335);
		setBorder(BorderFactory.createTitledBorder("Events"));

		btnAddEvent = new JButton("+");
		btnDelEvent = new JButton("-");
		txtData = new JTextField(20);
		EventOnDay = new JTextArea();

		btnAddEvent.addActionListener(this);
		btnDelEvent.addActionListener(this);

		btnAddEvent.setBounds(220, 43, 50, 25);
		btnDelEvent.setBounds(263, 43, 50, 25);
		txtData.setBounds(10, 40, 200, 30);

		EventOnDay.setBounds(10, 80, 300, 245);
		EventOnDay.setEditable(false);
		EventOnDay.setLineWrap(true);

		add(txtData);
		add(btnAddEvent);
		add(btnDelEvent);
		add(EventOnDay);
	}

	public static void displayEvent(int yyyy, int mm, int dd) {
		DataTransfer dto = new DataTransfer();
		DataAccess dao = new DataAccess();

		dto.setEventYear(yyyy);
		dto.setEventMonth(mm);
		dto.setEventDay(dd);
		Vector data = dao.getScheduleOnDate(dto);

		IntStream.range(0, data.size()).forEach(i -> {
			dto.setEventName(data.get(i).toString());
			EventOnDay.append(dto.getEventName() + newLine);
			
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnAddEvent) {
			CalendarPanel cp = new CalendarPanel();
			DataTransfer dto = new DataTransfer();
			DataAccess dao = new DataAccess();

			if (!CalendarTableRenderer.isCellSelected) {
				JOptionPane.showMessageDialog(this, "ERROR: No date selected");
			}

			// checks if anything is in the text box
			else if (txtData.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "ERROR: No event entered to be added");
			} else {
				String getValue = txtData.getText();
				dto.setEventName(getValue);
				dto.setEventDay(CalendarTableRenderer.selectedDay);
				dto.setEventMonth(CalendarTableRenderer.selectedMonth);
				dto.setEventYear(CalendarTableRenderer.selectedYear);

				dao.insertSchedule(dto);
				txtData.setText("");
			}

			cp.refreshCalendar(CalendarPanel.currentMonth, CalendarPanel.currentYear);
			//this.refreshTxtArea(CalendarTableRenderer.selectedYear, CalendarTableRenderer.selectedMonth, CalendarTableRenderer.selectedDay);
		}

		if (e.getSource() == btnDelEvent) {

			DataTransfer dto = new DataTransfer();
			DataAccess dao = new DataAccess();
			CalendarPanel cp = new CalendarPanel();


			boolean hasEvent = false;
			if (!CalendarTableRenderer.isCellSelected) {
				JOptionPane.showMessageDialog(this, "ERROR: No date selected");
			} else {
				hasEvent = false;
				dto.setEventDay(CalendarTableRenderer.selectedDay);
				dto.setEventMonth(CalendarTableRenderer.selectedMonth);
				dto.setEventYear(CalendarTableRenderer.selectedYear);


				hasEvent = dao.deleteSchedule(dto.getTimeset());
			}
			// checks if any events exist on this day
			if (!hasEvent) {
				JOptionPane.showMessageDialog(this, "ERROR: No event on this date to be deleted");
			}

			cp.refreshCalendar(CalendarPanel.currentMonth, CalendarPanel.currentYear);
			//this.refreshTxtArea(CalendarTableRenderer.selectedYear, CalendarTableRenderer.selectedMonth, CalendarTableRenderer.selectedDay);

		}
	}
}
