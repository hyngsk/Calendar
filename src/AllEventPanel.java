import javax.swing.*;

public class AllEventPanel extends JPanel {
	public JTextArea AllEvents;

	public AllEventPanel(){
		super(null);
		setBounds(10, 350, 640, 160);
		setBorder(BorderFactory.createTitledBorder("All Events"));
		AllEvents = new JTextArea("");
		AllEvents.setEditable(false);
		AllEvents.setLineWrap(true);
		AllEvents.setBounds(10, 20, 620, 130);
		add(AllEvents);

	}


}
