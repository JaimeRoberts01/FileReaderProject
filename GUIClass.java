import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;


@SuppressWarnings("serial")
public class GUIClass extends JFrame implements ActionListener {

	private JPanel Panel1, Panel2, Panel3;
	private JLabel Label1, Label2, Label3;
	private JButton Button1, Button2, Button3;
	
	private ReportFrame ReportFrame;
	private ReportFrame2 ReportFrame2;
	private Processing Process;

	private ArrayList <String> fileLine;
	
	public int getArrayListSize() { int size = fileLine.size();return size;}
	public ArrayList<String> getFileLine() {return fileLine;}
	public void setFileLine(ArrayList<String> fileLine) {this.fileLine = fileLine;}

	
	public GUIClass ()  {

		setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		setTitle ("Jemma's Test GUI");
		setSize (700, 300);
		setLocation (1500,0);
		setResizable (false);
		setLayout (new GridLayout (3,1));
		GUIComponents ();
		fileReader ();

	}


	public void GUIComponents () { // Basic GUI setup.

		Panel1 = new JPanel ();
		Panel1.setBackground (Color.lightGray);
		add (Panel1);

		Panel2 = new JPanel ();
		Panel2.setBackground (Color.lightGray);
		add (Panel2);

		Panel3 = new JPanel ();
		Panel3.setBackground (Color.lightGray);
		add (Panel3);


		Label1 = new JLabel ("Label1");
		Panel1.add (Label1);
		Label1.setEnabled (true);

		Label2 = new JLabel ("Label2");
		Panel2.add (Label2);
		Label2.setEnabled (true);

		Label3 = new JLabel ("Label3");
		Panel3.add (Label3);
		Label3.setEnabled (true);


		Button1 = new JButton ("Button1");
		Button1.addActionListener (this);
		Panel1.add (Button1);
		Button1.setEnabled (true);

		Button2 = new JButton ("Button2");
		Button2.addActionListener (this);
		Panel2.add (Button2);
		Button2.setEnabled (true);

		Button3 = new JButton ("Button3");
		Button3.addActionListener (this);
		Panel3.add (Button3);
		Button3.setEnabled (true);

	}


	public void fileReader () { // Get my file - eventually the file size will be too much for this at 9000 lines. 


		FileReader reader = null;
		BufferedReader bufferedReader = null;
		String file = null;
		String line = null;
		ReportFrame = new ReportFrame ();
		
		try {

			//file = "Book1C.csv";
			file = "Book1C.csv";
			fileLine = new ArrayList<String>();
			
			reader = new FileReader (file);
			bufferedReader = new BufferedReader(reader);
			
			while ((line = bufferedReader.readLine()) !=null) {
				fileLine.add (line);
			}
			
			for (String s : fileLine) {
				ReportFrame.reportFormatter(s);
				//System.out.println (s);
			}
			
			Process = new Processing (fileLine);	
		
			reader.close();	
			bufferedReader.close();
		}
	
		catch (IOException IOE) {

			JOptionPane.showMessageDialog (null, "FILE NOT FOUND", "ERROR", JOptionPane.ERROR_MESSAGE);
			IOE.printStackTrace();	
		}

		catch (InputMismatchException IME) {

			JOptionPane.showMessageDialog (null, "INVALID FILE", "ERROR", JOptionPane.ERROR_MESSAGE);
			IME.printStackTrace();	
		}	
	}


			public void fileWriter () {
	
				FileWriter writer = null;	
				String file = null;
	
				try {
	
					try {
	
						file = "fileOut.csv";
						writer = new FileWriter (file);	
						//writer.write(Process.header());
						//writer.write(Process.outputFile());
						writer.write(Process.outputFile());
					}
	
					finally {
	
						writer.close();	
					}
				}
	
				catch (IOException IOE) {
	
					JOptionPane.showMessageDialog (null, "FILE NOT FOUND", "ERROR", JOptionPane.ERROR_MESSAGE);
					IOE.printStackTrace();	
				}
			}


	public void displayOutput () { // This is for the Output Display - testing for correct output.

		ReportFrame2 = new ReportFrame2 (Process);	
		ReportFrame2.reportFormatter();
	}


	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == Button1) { // I want this button to calculate the deflection and nanometer values.
			System.out.println("We definitely hit button 1");
			Process.pillarDeflection();
			Process.nanoMeters();
			Process.forces();
			Process.newDataArray();
			System.out.println("And if you're seeing this, we did something!");
		}

		if (e.getSource() == Button2) {
			System.out.println("We definitely hit button 2");	// I want this button to bring up the display page - testing to see if it prints out properly.
			displayOutput();
			Process.byFrame ();
			Process.byTrajectory ();
			System.out.println("And if you're seeing this, we did something!");
		}

		if (e.getSource() == Button3) {
			System.out.println("We definitely hit button 3");	// Not sure what I'm doing with this one
			fileWriter ();
			System.out.println("And if you're seeing this, we did something!");	
		}
	}
}