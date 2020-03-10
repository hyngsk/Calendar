import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EventPanel extends JPanel implements ActionListener {

	public JButton btnAddEvent, btnDelEvent;
	public JTextField txtData;
	public JTextArea EventOnDay;


	public EventPanel(){
		super(null);
		setBounds(330, 10, 320, 335);
		setBorder(BorderFactory.createTitledBorder("Events"));

		btnAddEvent = new JButton("+");
		btnDelEvent = new JButton("-");
		txtData = new JTextField(20);
		EventOnDay = new JTextArea();

		btnAddEvent.addActionListener(this::actionPerformed);
		btnDelEvent.addActionListener(this::actionPerformed);

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


	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnAddEvent) {
			DataTransfer dto = new DataTransfer();
//			createEvent();
//			refreshCalendar(currentMonth, currentYear);
//			// resets all events display
//			mainEventDisplay();
		}
		if (e.getSource() == btnDelEvent) {
//			deleteEvent(selectedDay, selectedMonth, selectedYear);
//			refreshCalendar(currentMonth, currentYear);
//			// resets all events display
//			allEvents.setText(null);
//			allEvents.setText("");
//			mainEventDisplay();
		}
	}
}
