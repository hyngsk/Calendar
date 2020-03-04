import javax.swing.*;
import java.awt.*;


public class Main extends JFrame {


	public Container pane;

	public Main() {
		super("Calendar");
		System.out.println("Run Main Constructor");

		//CalendarPanel CalendarPanel = new CalendarPanel();
		//EventPanel EventPanel = ;
		//AllEventPanel AllEventPanel = ;

		//set main frame
		setSize(670, 550);
		pane = getContentPane();
		pane.setLayout(null);

		//add panels
		add(new CalendarPanel());
		add(new EventPanel());
		add(new AllEventPanel());

		// Make frame visible
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {

		new Main();

	}

}
