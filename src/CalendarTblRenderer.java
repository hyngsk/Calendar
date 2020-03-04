import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CalendarTblRenderer extends DefaultTableCellRenderer {
    //    @Override
//    public Component getTableCellRendererComponent(JTable table, Object value, boolean   isSelected, boolean hasFocus, int row, int column)
//    {
//        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//        if (! table.isRowSelected(row)) //현재 선택된 행의 색상은 변경하지 않고 선택 해제된 경우에만 배경색상을 변경한다
//        {
//            if(table.getValueAt(row, 1).toString().indexOf("3")!=-1) { // 특정한 값을 가진 셀을 찾아서 그 셀만 배경색상을 변경한다
//                c.setBackground(Color.lightGray);
//            }else{
//                c.setBackground(Color.white);
//            }
//        }
//        return c;
//    }
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
            if (Integer.parseInt(value.toString()) == CalendarPanel.realDay && CalendarPanel.currentMonth == CalendarPanel.realMonth
                    && CalendarPanel.currentYear == CalendarPanel.realYear) { // Today
                setBackground(Color.GRAY);
            }
            // set days with events to yellow
            //System.out.println( value+" "+currentMonth +" "+ currentYear);

            if (CalendarPanel.findEvent(value, CalendarPanel.currentMonth + 1, CalendarPanel.currentYear)) {//일자 , 달, 년 탐색
                setBackground(Color.YELLOW);
            }
        }


        // allows user to select date to add event to
        if (selected && value != null) {
            setBackground(Color.LIGHT_GRAY);
            EventPanel.events.setText(null);
            EventPanel.events.setText("");
            selectedMonth = currentMonth + 1;
            selectedYear = currentYear;
            selectedDay = Integer.parseInt(value.toString());
            displayEvents(selectedDay, selectedMonth, selectedYear);
            isCellSelected = true;
        }

        setBorder(null);
        setForeground(Color.BLACK);

        return this;
    }
}
