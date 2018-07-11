import java.util.*;


public class Processing {

	private ArrayList<String> frame, trajectory;
	private ArrayList<Double> x, y, x_raw, y_raw, dx, dy, deflection, nanometers, picoNewtons;
	private String [][] data;
	private Object [][] newData;
	private int rows;
	private double frameAverage, trajectoryAverage; //, frameStandardDeviation, trajectoryStandardDeviation;


	public Processing () {	

	}


	/*Getters and Setters*/

	public ArrayList<String> getFrame() {return frame;}
	public void setFrame(ArrayList<String> frame) {this.frame = frame;}
	public ArrayList<String> getTrajectory() {return trajectory;}
	public void setTrajectory(ArrayList<String> trajectory) {this.trajectory = trajectory;}
	public ArrayList<Double> getDx() {return dx;}
	public void setDx(ArrayList<Double> dx) {this.dx = dx;}
	public ArrayList<Double> getDy() {return dy;}
	public void setDy(ArrayList<Double> dy) {this.dy = dy;}
	public ArrayList<Double> getDeflection() {return deflection;}
	public void setDeflection(ArrayList<Double> deflection) {this.deflection = deflection;}
	public ArrayList<Double> getNanometers() {return nanometers;}
	public void setNanometers(ArrayList<Double> nanometers) {this.nanometers = nanometers;}
	public Object[][] getNewData() {return newData;}
	public void setNewData(Object[][] newData) {this.newData = newData;}


	public Processing (ArrayList<String> fileLine) {

		rows = fileLine.size();
		int columns = 8;

		System.out.println("We have found size: " + rows);

		data = new String [rows][columns];

		frame = new ArrayList<String>();
		trajectory = new ArrayList<String>();
		x = new ArrayList<Double>();
		y = new ArrayList<Double>();
		x_raw = new ArrayList<Double>();
		y_raw = new ArrayList<Double>();
		dx = new ArrayList<Double>();
		dy = new ArrayList<Double>();



		for (int i = 0; i < rows; i++) {
			data [i] = fileLine.get(i).split(",");
			frame.add (data [i][0]);
			trajectory.add (data [i][1]);
			x.add (Double.parseDouble(data [i][2]));
			y.add (Double.parseDouble(data [i][3]));
			x_raw.add (Double.parseDouble(data [i][4]));
			y_raw.add (Double.parseDouble(data [i][5]));
			dx.add (Double.parseDouble(data [i][6]));
			dy.add (Double.parseDouble(data [i][7]));
		}

	}

	//		for (String s : trajectory) {
	//		System.out.println("tragic " + s);
	//		}

	//System.out.println("frame: " + frame + "\n" + "trajectory: " + trajectory + "\n" + "dx: " + dx + "\n" + "dy: " + dy);



	public ArrayList<Double> pillarDeflection () { // This method should calculate deflection.

		deflection = new ArrayList <Double>();

		for (int i =0; i< rows; i++) {

			double d = Math.sqrt ((Math.pow (dx.get(i), 2)) + (Math.pow (dy.get(i), 2)));
			deflection.add(d);
		}
		//System.out.println(deflection);
		return deflection;
	}


	public ArrayList<Double> nanoMeters () { // This method should calculate nanometers

		nanometers = new ArrayList <Double>();

		for (int i=0; i<rows;i++) {

			double nm = deflection.get(i) * 73;
			nanometers.add(nm);
		}
		//System.out.println(nanometers);
		return nanometers;
	}


	public ArrayList<Double> forces () {

		picoNewtons = new ArrayList <Double>();

		double constant = (double) 3/64;
		double E = 2.0;
		double pi = Math.PI;
		double diameter = 0.5;
		double length = 1.3;

		for (int i=0; i<rows; i++) {

			double picoMeters = nanometers.get(i)*1000;
			double picoForces = (constant * pi *E * (Math.pow(diameter, 4)/Math.pow(length, 3))*picoMeters);
			picoNewtons.add(picoForces);
			System.out.println(String.format("%.10f", picoNewtons.get(i)));
		}
		return picoNewtons;
	}


	public Object [][] newDataArray () {

		int columns = 11;
		newData = new Object [rows][columns];

		for (int i = 0; i < rows; i++) {

			newData [i][0] = frame.get(i);
			newData [i][1] = trajectory.get(i);
			newData [i][2] = x.get(i);
			newData [i][3] = y.get(i);
			newData [i][4] = x_raw.get(i);
			newData [i][5] = y_raw.get(i);
			newData [i][6] = dx.get(i);
			newData [i][7] = dy.get(i);
			newData [i][8] = deflection.get(i);
			newData [i][9] = nanometers.get(i);	
			newData [i][10] = picoNewtons.get(i); 
			System.out.println("Here is newData: " + Arrays.toString(newData[i]));
		}

		return newData;
	}

	
	public void byFrame () { //This will need checks for non-existent frames

		ArrayList <Double> byFrame = new ArrayList<Double>();

		for (int i = 0; i<rows; i++) {

			String frameID = (String) newData[i][0];

			if (frameID.equals("1")) {
				double something = (double) newData [i][10];
				byFrame.add(something);
				//System.out.println("Here is the row for Frame: " + Arrays.toString(newData[i]));
				System.out.println("Here is picoNewtons for Frame: " + (newData[i][10]));
			}
		}

		System.out.println("Here is byframe: " + byFrame);
		for (double d : byFrame) {
			frameAverage += d;
		}

		frameAverage = frameAverage/byFrame.size();
		System.out.println("Here is byframe average: " + frameAverage);
	}


	public void byTrajectory () { //This will need checks for non-existent trajectories

		ArrayList <Double> byTrajectory = new ArrayList<Double>();

		for (int i = 0; i<rows; i++) {

			String pillarID = (String) newData[i][1];

			if (pillarID.equals("2")) {
				double something = (double) newData[i][10];
				byTrajectory.add(something);
				//System.out.println("Here is the row for Trajectory: " + Arrays.toString(newData[i]));
				System.out.println("Here is picoNewtons for Trajectory: " + (newData[i][10]));
			}
		}

		System.out.println("Here is byTrajectory: " + byTrajectory);
		for (double d: byTrajectory){
			trajectoryAverage += d; 	
		}

		trajectoryAverage = trajectoryAverage/byTrajectory.size();
		System.out.println("Here is byTrajecotry average: " + trajectoryAverage);
	}


	public String outputString () { // For the reader.

		StringBuilder SB = new StringBuilder();
		for (int i = 0; i<rows; i++) {
			double x = dx.get(i);
			double y = dy.get(i);
			String dxVal = String.format("%.9f", x);
			String dyVal = String.format("%.9f", y);
			SB.append (String.format ("%3s", frame.get(i)) + "\t\t" + String.format("%2.2s", trajectory.get(i)) + "\t" + String.format("%12.23s", dxVal) + "\t" 
					+ String.format("%12.13s", dyVal) + "\t" + String.format(" %1.11s",deflection.get(i)) + "\t" + String.format(" %1.11s",  nanometers.get(i)) 
					+ "\t" + String.format(" %1.11s",  picoNewtons.get(i))+ "\n");
		}

		String output = SB.toString();
		return output;		
	}


	public String outputFile () { // For a CSV file.
		
		String header = ("frame" + "," + "trajectory" + "," + "x" + "," + "y" + "," + "x-raw"  + "," + "y-raw" + "," 
		+ "dx" + "," + "dy" + "," + "deflection" + "," + "nanometers"+ "," + "picoNewtons" + ",");
		String body = "";
		
		for (int i =0; i<rows;i++) {
			body += Arrays.toString(newData[i]) +"\n";
		}
		
		StringBuilder SB = new StringBuilder();
		SB.append(header + "\n");
		SB.append(body);
		String outputFile = SB.toString();
		outputFile = outputFile.replace("[", "");
		outputFile = outputFile.replace("]", "");
		
		return outputFile;
	}
}