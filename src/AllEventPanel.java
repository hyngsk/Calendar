import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class AllEventPanel extends JPanel {

    static JTable allEvents;
    static DefaultTableModel mallEvents;

    public AllEventPanel() {
        super(null);
        System.out.println("Run AllEventPanel Constructor");
        DataAccess dao = new DataAccess();

        System.out.println("v=" + dao.getScheduleList()); // 확인

        allEvents = new JTable(new DefaultTableModel(dao.getScheduleList(), getColumn()) {
            @Override
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        });
        System.out.println(dao.getScheduleList().subList(0, 0));

        // mallEvents = new DefaultTableModel(dao.getScheduleList(), getColumn()) {
//            @Override
//            public boolean isCellEditable(int rowIndex, int mColIndex) {
//                return false;
//            }
//        };
        setBounds(10, 350, 640, 160);
        setBorder(BorderFactory.createTitledBorder("All Events"));
        allEvents.setBounds(10, 20, 620, 130);
        add(allEvents);


    }

    public static Vector getColumn() {
        Vector col = new Vector();
        col.add("날짜");
        col.add("이벤트");
        return col;
    }//getColumn

    //Jtable 내용 갱신 메서드
    public static void AllEventRefresh() {

        DataAccess dao = new DataAccess();
        DefaultTableModel model = new DefaultTableModel(dao.getScheduleList(), getColumn());
        allEvents.setModel(model);

    }
}
