import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.GregorianCalendar;


public class CalendarPanel extends JPanel implements MouseListener, ActionListener {
    static JLabel lblMonth, lblYear;
    static JButton btnPrev, btnNext;
    static JComboBox cmbYear;
    static JTable tblCalendar;
    static DefaultTableModel mtblCalendar;
    static int realYear, realMonth, realDay, currentYear, currentMonth, currentDay;
    static int selectedDay, selectedMonth, selectedYear;
    static Boolean isCellSelected = false;
    // Refresh Variables
    static String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September",
            "October", "November", "December"};
    static int nod, som; // Number Of Days, Start Of Month

    JScrollPane stblCalendar;


    public CalendarPanel() {

        super(null);
        System.out.println("Run CalendarPanel Constructor");
        // Create controls
        lblMonth = new JLabel("January");
        lblYear = new JLabel("Change year:");
        cmbYear = new JComboBox();
        btnPrev = new JButton("Prev");
        btnNext = new JButton("Next");
//        mtblCalendar = new DefaultTableModel() {
//            @Override
//            public boolean isCellEditable(int rowIndex, int mColIndex) {
//                return false;
//            }
//        };
        String defaultCol[] = getColumn();
        tblCalendar = new JTable();
        stblCalendar = new JScrollPane(tblCalendar);
        DefaultTableModel model = new DefaultTableModel(null, defaultCol) {
            @Override
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };
        CalendarTblRenderer Renderer = new CalendarTblRenderer();
        // Set border
        setBorder(BorderFactory.createTitledBorder("Calendar"));
        //Add in Calendar panel
        add(lblMonth);//Month label
        add(lblYear);//Year label
        add(cmbYear);//Year comboBox
        add(btnPrev);//Previous month button
        add(btnNext);//Next month button
        add(stblCalendar);//Scroll panel in combo
        // Set bounds
        setBounds(10, 10, 320, 335);
        //Set layout
        //lblMonth.setBounds(160 - lblMonth.getPreferredSize().width / 2, 25, 120, 25);
        lblMonth.setBounds(160 - lblMonth.getPreferredSize().width / 2, 25, 180, 25); // Re-align label with calendar
        lblYear.setBounds(10, 305, 200, 20);
        cmbYear.setBounds(225, 305, 90, 20);
        btnPrev.setBounds(10, 25, 60, 28);
        btnNext.setBounds(250, 25, 60, 28);
        stblCalendar.setBounds(10, 50, 300, 250);
        // Register action listeners
        btnPrev.addActionListener(this::actionPerformed);
        btnNext.addActionListener(this::actionPerformed);
        cmbYear.addActionListener(this::actionPerformed);
        // Get real month/year
        GregorianCalendar cal = new GregorianCalendar(); // Create calendar
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); // Get day
        realMonth = cal.get(GregorianCalendar.MONTH); // Get month
        realYear = cal.get(GregorianCalendar.YEAR); // Get year
        currentMonth = realMonth; // Match month and year
        currentYear = realYear;
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
        model.setColumnCount(7);
        model.setRowCount(6);
        // Populate table
        for (int i = realYear - 100; i <= realYear + 100; i++) {
            cmbYear.addItem(String.valueOf(i));
        }
        // Refresh calendar
        getTabelData(realMonth, realYear);
        tblCalendar.setDefaultRenderer(tblCalendar, Renderer);

    }

    //포기. 나중에 need to access DB 랜더링과 맞물려 실행됨
    public static boolean findEvent(Object value, int month, int year) {
        //DataAccess dao = new DataAccess();
        //DataTransfer dto = new DataTransfer();


        int day = Integer.parseInt(value.toString());
        //System.out.println(dto.intToSt(year,month,day));
        //hasEvent = dao.isAtScheduleOnMonth(dto.intToSt(year, month, day));
        //boolean hasEvent = dao.isScheduleListAt(dto.intToSthp(year, month, day));

        return false;
    }

    //need to access DB
    public static void displayEvents(int day, int month, int year) {
        for (int i = 0; i < EventPanel.list.size(); i++) {
            if (EventPanel.list.get(i).getEventDay() == day &&
                    EventPanel.list.get(i).getEventMonth() == month &&
                    EventPanel.list.get(i).getEventYear() == year) {

                EventPanel.events.append((EventPanel.list.get(i).getEventName() + EventPanel.newLine));

            }
        }
        System.out.println(EventPanel.list);

    }

//    public void refreshCalendar(int month, int year) {
//        DefaultTableModel model = new DefaultTableModel();
//        // Allow/disallow buttons
//        // Add headers
//         // All headers
//        for (int i = 0; i < 7; i++) {
//            tblCalendar.add(headers[i]);
//        }
//        btnPrev.setEnabled(true);
//        btnNext.setEnabled(true);
//
//        // Get first day of month and number of days
//        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
//        nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
//        som = cal.get(GregorianCalendar.DAY_OF_WEEK);
//
//        if (month == 0 && year <= realYear - 10) {
//            btnPrev.setEnabled(false);
//        } // Too early
//        if (month == 11 && year >= realYear + 100) {
//            btnNext.setEnabled(false);
//        } // Too late
//        cmbYear.setSelectedItem(String.valueOf(year)); // Select the correct year in the combo box
//        lblMonth.setText(months[month]); // Refresh the month label (at the top)
//        // Clear table
//        for (int i = 0; i < 6; i++) {
//            for (int j = 0; j < 7; j++) {
//                model.setValueAt(null, i, j);
//            }
//        }
//        // Draw calendar
//        for (int i = 1; i <= nod; i++) {
//            @SuppressWarnings("deprecation")
//            int row = new Integer((i + som - 2) / 7);
//            int column = (i + som - 2) % 7;
//            model.setValueAt(i, row, column);
//        }
//
//        // Apply renderer
//        tblCalendarRenderer re = new tblCalendarRenderer();
//        tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0),re);
//    }

    public void getTabelData(int month, int year) {

        btnPrev.setEnabled(true);
        btnNext.setEnabled(true);

        // Get first day of month and number of days
        GregorianCalendar cal = new GregorianCalendar(year, month, 1);
        nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
        som = cal.get(GregorianCalendar.DAY_OF_WEEK);

        if (month == 0 && year <= realYear - 10) {
            btnPrev.setEnabled(false);
        } // Too early
        if (month == 11 && year >= realYear + 100) {
            btnNext.setEnabled(false);
        } // Too late
        cmbYear.setSelectedItem(String.valueOf(year)); // Select the correct year in the combo box
        lblMonth.setText(months[month]); // Refresh the month label (at the top)
        // Clear table
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                model.setValueAt(null, i, j);
            }
        }
        // Draw calendar
        for (int i = 1; i <= nod; i++) {
            @SuppressWarnings("deprecation")
            int row = new Integer((i + som - 2) / 7);
            int column = (i + som - 2) % 7;
            model.setValueAt(i, row, column);
        }

        // Apply renderer
        tblCalendarRenderer re = new tblCalendarRenderer();
        tblCalendar.setDefaultRenderer(tblCalendar.getColumnClass(0), re);
    }

    public String[] getColumn() {
        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        return headers;
    }//getColumn

    @Override
    public void actionPerformed(ActionEvent e) {
        //Next button
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
