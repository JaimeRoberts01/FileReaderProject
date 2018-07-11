import java.awt.*;


import javax.swing.*;
import javax.swing.border.*;


@SuppressWarnings("serial")
public class ReportFrame extends JFrame {


	private JTextArea displayFile;
	//private JScrollPane scrollPane;


	public ReportFrame () {		

		setDefaultCloseOperation (DISPOSE_ON_CLOSE);
		setTitle ("File Contents");
		setLocation (1500, 330);
		setSize (700, 330);
		setVisible (true);
		//setResizable (false); // Disable owing to scrollbar being a dick.
		frameComponents ();
	}


	public void frameComponents () {

		displayFile = new JTextArea ();
		displayFile.setLineWrap (true);
		displayFile.setWrapStyleWord (true);
		displayFile.setFont (new Font ("Courier", Font.PLAIN, 14));
		displayFile.setBorder (new EmptyBorder (10,10,10,10));
		displayFile.setEditable (false);
		add (displayFile);

		//scrollPane = new JScrollPane (displayFile); // WHY DO YOU NOT WORK!!!
		//scrollPane.setVerticalScrollBarPolicy (JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		//scrollPane.setPreferredSize (new Dimension (200, 200));
		//add (scrollPane);
	}


//	public void reportFormatter (ArrayList<String> fileLine) {
	public void reportFormatter (String l) {
		
		displayFile.append (l + "\n");	
	}	
}