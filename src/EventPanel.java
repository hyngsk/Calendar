import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;

public class EventPanel extends JPanel implements MouseListener, ActionListener {

    static String newLine = "\n";
    static String eventValue;
    static JTextArea events;
    static JTextField txtdata;
    static JButton btnAddEvent, btnDelEvent;
    static ArrayList<DataTransfer> list = new ArrayList<DataTransfer>();


    public EventPanel() {
        super(null);
        System.out.println("Run EventPanel Constructor");
        setBounds(330, 10, 320, 335);
        setBorder(BorderFactory.createTitledBorder("Events"));

        //Add button
        btnAddEvent = new JButton("+");
        btnAddEvent.setBounds(220, 43, 50, 25);
        add(btnAddEvent);

        //Delete button
        btnDelEvent = new JButton("-");
        btnDelEvent.setBounds(263, 43, 50, 25);
        add(btnDelEvent);

        //Enter event
        txtdata = new JTextField(20);
        txtdata.setBounds(10, 40, 200, 30);
        add(txtdata);

        //Show event
        events = new JTextArea();
        events.setBounds(10, 80, 300, 245);
        add(events);


        // make text areas non-editable
        events.setEditable(false);
        events.setLineWrap(true);
        btnAddEvent.setEnabled(true);
        btnDelEvent.setEnabled(true);

        btnAddEvent.addActionListener(this::actionPerformed);
        btnDelEvent.addActionListener(this::actionPerformed);


    }

    //need to Access DB
    public static void createEvent() {
        DataTransfer singleEvent = new DataTransfer();
        // checks if a date is selected
        if (!CalendarPanel.isCellSelected) {
            JOptionPane.showMessageDialog(Main.pane, "ERROR: No date selected");
        }
        // checks if anything is in the text box
        else if (txtdata.getText().equals("")) {
            JOptionPane.showMessageDialog(Main.pane, "ERROR: No event entered to be added");

        } else {
            //need to Access DB
            DataAccess dao = new DataAccess();

            String getValue = txtdata.getText();
            eventValue = getValue;
            singleEvent.setEventName(eventValue);
            singleEvent.setTimeset(singleEvent.intToSt(CalendarPanel.selectedYear,
                    CalendarPanel.selectedMonth,
                    CalendarPanel.selectedDay));
            System.out.println(singleEvent.toString());

            //dao.insertSchedule(singleEvent);


            txtdata.setText("");

        }

    }

    //need to Access DB
    public static void deleteEvent(int day, int month, int year) {
        // checks if a date is selected
        if (!CalendarPanel.isCellSelected) {
            JOptionPane.showMessageDialog(Main.pane, "ERROR: No date selected");
        } else {
            boolean hasEvent = false;

//            need to Access DB
//            디비에 있으면 hasEvent 를 true로

//            for (int i = 0; i < list.size(); i++) {
//                if (list.get(i).getEventDay() == day && list.get(i).getEventMonth() == month && list.get(i).getEventYear() == year) {
//
//                    list.remove(i);
//                    hasEvent = true;
//                    break;
//                }
//            }


            // checks if any events exist on this day
            if (!hasEvent) {
                JOptionPane.showMessageDialog(Main.pane, "ERROR: No event on this date to be deleted");
            }
        }

    }

    public static void mainEventDisplay() {//need to access DB
        DataTransfer dto = new DataTransfer();

        int[] dayArr = new int[list.size()];
        int[] monthArr = new int[list.size()];
        int[] yearArr = new int[list.size()];

        for (int i = 0; i < list.size(); i++) {
            dayArr[i] = list.get(i).getEventDay();
            monthArr[i] = list.get(i).getEventDay();
            yearArr[i] = list.get(i).getEventDay();
        }
        Collections.sort(list, new SortEvent());
        //allEvents.append("Your up coming events: " + newLine);

//        for (int i = 0; i < list.size(); i++) {
//            allEvents.append(("- " + list.get(i).getEventName() + "[" + list.get(i).getEventMonth() + "/" + list.get(i).getEventDay() + "/" + list.get(i).getEventYear() + "]" + newLine));
//        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnAddEvent) {
            createEvent();
            //Refresh calendar panel
            CalendarPanel.refreshCalendar(CalendarPanel.currentMonth, CalendarPanel.currentYear);

            // resets all events display
            AllEventPanel.AllEventRefresh();

            mainEventDisplay();
        }

        if (e.getSource() == btnDelEvent) {
            deleteEvent(CalendarPanel.selectedDay, CalendarPanel.selectedMonth, CalendarPanel.selectedYear);
            //Refresh calendar panel
            CalendarPanel.refreshCalendar(CalendarPanel.currentMonth, CalendarPanel.currentYear);
            // resets all events display
            //allEvents.setText(null);
            //allEvents.setText("");
            mainEventDisplay();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
