/******************************************************
 ***** Author: 		        William Tarrant      ******
 ***** Project Title: 		TrainingLogGUI       ******
 ***** File Name:           Statistics.java      ******
 ***** Date of Last Edit:	27/11/2011           ****** 
 ******************************************************
 */

/* inspiration for creating a graph in java taken from code posted on a public forum
 * which can be viewed @ http://www.coderanch.com/t/344345/GUI/java/Simple-Graph
 *
 * This program/class will give a graphical representation of the training data supplied by the user
 * It will express in a graph format, the users training progress with respect to their
 * training time (total), average heart rate and calories burned durning the sessions.
 *
 * It will also give text averages for each of the items.
 *
 * This class will create a Container and draw an axis and then plot the values from the array on
 * to it.  The x intervals will be determined based on the number of elements contained in the
 * array while Y coordinates will be expressed as a percentage of the length of the Y axis.
 *
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Statistics extends JComponent //JComponent for paintComponent(Graphics g), getHeight() and getWidth()
{

	// Variables
	private ArrayList<Integer> heartRates = new ArrayList<Integer>();
	private ArrayList<Integer> calories = new ArrayList<Integer>();
	private ArrayList<Integer> distanceTravelled = new ArrayList<Integer>();
	private ArrayList<Integer> timeTraining = new ArrayList<Integer>();

	private Font fontBig = new Font("Arial", Font.BOLD, 16);
	private Font fontMedium = new Font("Arial", Font.BOLD, 12);
	private Font fontSmall = new Font("Arial", Font.PLAIN, 12);


	private final int FRAME_HEIGHT = 500, FRAME_WIDTH = 600, PADDING_X = 140, PADDING_Y_TOP = 50, PADDING_Y_BTM = 150;
	private int y_axisLength, x_axisLength, maxHr, avgHr, minHr, maxCal, avgCal, minCal, maxDist, avgDist, minDist, maxTime, avgTime, minTime;
	private Toolkit toolkit;

	//Constructor
	public Statistics( 	String userName,
						ArrayList<Integer> heartRates,
						ArrayList<Integer> calories,
						ArrayList<Integer> distanceTravelled,
						ArrayList<Integer> timeTraining )
	{
		this.heartRates = heartRates;
		this.calories = calories;
		this.distanceTravelled = distanceTravelled;
		this.timeTraining = timeTraining;

		calcAvgMaxMin();

		JFrame frame = new JFrame("Statistics for " + userName);
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		Container cPane = frame.getContentPane();
		cPane.add(this);
		frame.setResizable(false);
		//frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//set the location of the window at the center of the screen
		toolkit = frame.getToolkit();
		Dimension size = toolkit.getScreenSize();
		frame.setLocation((size.width - frame.getWidth())/2, (size.height - frame.getHeight())/2);
		frame.setVisible(true);

	} // constructor

	// Perform the drawing procedure
	public void paintComponent(Graphics g)
	{
		Graphics2D graphics = (Graphics2D)g;

		//draw the XY axis
		//set a heavier line thickness for the axes
		graphics.setStroke(new BasicStroke(2.0f));
		//y Axis
		graphics.drawLine(0 + PADDING_X, 0 + PADDING_Y_TOP , 0 + PADDING_X, getHeight() - PADDING_Y_BTM);
		//x Axis
		graphics.drawLine(0 + PADDING_X, getHeight() - PADDING_Y_BTM, getWidth() - PADDING_X, getHeight() - PADDING_Y_BTM);

		// determine my x and y axis length axis
        y_axisLength = (getHeight() - PADDING_Y_TOP - PADDING_Y_BTM);
		x_axisLength = getWidth() - (PADDING_X * 2);

		// set labels and legends
		drawLabelsAndLedgends(graphics);

		// draw user stats
		drawStats(graphics);

		// plot the graph
		plotGraph(graphics);

	} //Paint component


	public int getMaxDataY(ArrayList<Integer> array)
	{
		//determine the maximum y value
		int dataMaxY = 0;
		for (int z = 0; z < array.size(); z++)
		{
			if (array.get(z) > dataMaxY)
				dataMaxY = array.get(z);

		}

		return dataMaxY;
	} // getMaxDataY

	public int getXintervals()
	{
		//divide the x axis into intervals based on the number of items in the array
		//all arrays will be the same size so its not important which one is used here
		if (heartRates.size() > 1)
			return (int)x_axisLength / (heartRates.size() - 1);
		else
			return 1;

	} // getXintervals

	public void plotGraph(Graphics2D graphics)
	{
		//lets draw a graph

		int heartRateY1 = 0, heartRateY2 = 0;
		int caloriesY1  = 0,  caloriesY2 = 0;
		int distanceTravelledY1 = 0, distanceTravelledY2 = 0;
		int timeTrainingY1 = 0, timeTrainingY2 = 0;

		for (int i = 0; i < heartRates.size(); i++) // all arrays will be same length so it doesnt matter which one we use to get the length
		{

			heartRateY1 = getHeight() - ( (int)((heartRates.get(i) / (float)getMaxDataY(heartRates)) * y_axisLength) + PADDING_Y_BTM); // the current value
			caloriesY1 =  getHeight() - ( (int)((calories.get(i) / (float)getMaxDataY(calories)) * y_axisLength) + PADDING_Y_BTM); // the current value
			distanceTravelledY1 = getHeight() - ( (int)((distanceTravelled.get(i) / (float)getMaxDataY(distanceTravelled)) * y_axisLength) + PADDING_Y_BTM); // the current value
			timeTrainingY1 = getHeight() - ( (int)((timeTraining.get(i) / (float)getMaxDataY(timeTraining)) * y_axisLength) + PADDING_Y_BTM); // the current value

			if (i != heartRates.size() -1)  // prevent out of bounds error
			{


					if ( heartRates.size() > 1)  // check if more than one element in array then continue with the following
					{


						heartRateY2 = getHeight() - ( (int)((heartRates.get(i + 1) / (float)getMaxDataY(heartRates)) * y_axisLength) + PADDING_Y_BTM); // the next value
						caloriesY2  = getHeight() - ( (int)((calories.get(i + 1) / (float)getMaxDataY(calories)) * y_axisLength) + PADDING_Y_BTM); // the next value
						distanceTravelledY2  = getHeight() - ( (int)((distanceTravelled.get(i + 1) / (float)getMaxDataY(distanceTravelled)) * y_axisLength) + PADDING_Y_BTM); // the next value
						timeTrainingY2 = getHeight() - ( (int)((timeTraining.get(i + 1) / (float)getMaxDataY(timeTraining)) * y_axisLength) + PADDING_Y_BTM); // the next value

						graphics.setPaint(Color.BLUE);
						graphics.drawLine( PADDING_X + (getXintervals() * (i) ), heartRateY1, PADDING_X + (getXintervals() * (i + 1)), heartRateY2 );

						graphics.setPaint(Color.RED);
						graphics.drawLine( PADDING_X + (getXintervals() * (i) ), caloriesY1, PADDING_X + (getXintervals() * (i + 1)), caloriesY2 );

						graphics.setPaint(Color.GREEN);
						graphics.drawLine( PADDING_X + (getXintervals() * (i) ), distanceTravelledY1, PADDING_X + (getXintervals() * (i + 1)), distanceTravelledY2 );

						graphics.setPaint(Color.CYAN);
						graphics.drawLine( PADDING_X + (getXintervals() * (i) ), timeTrainingY1, PADDING_X + (getXintervals() * (i + 1)), timeTrainingY2 );
					}


			}
			graphics.setPaint(Color.BLUE);
			graphics.fillOval( (PADDING_X + (getXintervals() * (i) )) - 4, heartRateY1 - 4, 8, 8); // x1, y1, w, h
			graphics.setPaint(Color.RED);
			graphics.fillOval( (PADDING_X + (getXintervals() * (i) )) - 4, caloriesY1 - 4, 8, 8); // x1, y1, w, h
			graphics.setPaint(Color.GREEN);
			graphics.fillOval( (PADDING_X + (getXintervals() * (i) )) - 4, distanceTravelledY1 - 4, 8, 8); // x1, y1, w, h
			graphics.setPaint(Color.CYAN);
			graphics.fillOval( (PADDING_X + (getXintervals() * (i) )) - 4, timeTrainingY1 - 4, 8, 8); // x1, y1, w, h

		} // for loop - draw test data
	} // plot graph

	public void drawLabelsAndLedgends(Graphics2D graphics)
	{
		graphics.setFont(fontBig);
		graphics.drawString("% of Max", 50, 170);


		graphics.setFont(fontSmall);
		graphics.drawString("Heart Rate", 10, 260);
		graphics.setPaint(Color.BLUE);
		graphics.fillOval(70, 250, 8, 8);

		graphics.setPaint(Color.BLACK);
		graphics.drawString("Calories", 10, 280);
		graphics.setPaint(Color.RED);
		graphics.fillOval(70, 270, 8, 8);

		graphics.setPaint(Color.BLACK);
		graphics.drawString("Distance", 10, 300);
		graphics.setPaint(Color.GREEN);
		graphics.fillOval(70, 290, 8, 8);

		graphics.setPaint(Color.BLACK);
		graphics.drawString("Time", 10, 320);
		graphics.setPaint(Color.CYAN);
		graphics.fillOval(70, 310, 8, 8);


		graphics.setFont(fontBig);
		graphics.setPaint(Color.BLACK);
		graphics.drawString("Progress", 250, 340);

		//reset the brush stroke
		graphics.setStroke(new BasicStroke(1.0f));
	}


	public void drawStats(Graphics2D graphics)
	{
		graphics.drawLine(0, 355, 600, 355);
		graphics.setFont(fontMedium);
		graphics.setPaint(Color.BLACK);

		graphics.drawString("HeartRate (bpm)", 10, 390);
		graphics.drawString("Calories (cal)", 10, 410);
		graphics.drawString("Distance Travelled (km)", 10, 430);
		graphics.drawString("Time Training (min)", 10, 450);

		graphics.drawString("Min", 200, 370);
		graphics.drawString("Avg", 300, 370);
		graphics.drawString("Max", 400, 370);

		graphics.setFont(fontSmall);

		graphics.drawString("" + minHr, 200, 390);
		graphics.drawString("" + avgHr, 300, 390);
		graphics.drawString("" + maxHr, 400, 390);

		graphics.drawString("" + minCal, 200, 410);
		graphics.drawString("" + avgCal, 300, 410);
		graphics.drawString("" + maxCal, 400, 410);

		graphics.drawString("" + minDist, 200, 430);
		graphics.drawString("" + avgDist, 300, 430);
		graphics.drawString("" + maxDist, 400, 430);

		graphics.drawString("" + minTime, 200, 450);
		graphics.drawString("" + avgTime, 300, 450);
		graphics.drawString("" + maxTime, 400, 450);
	}

	public void calcAvgMaxMin()
	{
		//for all array lists this method will calculate the maximum,
		//minimum and average of the values contained within.
 		int totalHr = 0, totalCal = 0, totalDist = 0, totalTime = 0;

		for (int i = 0; i < heartRates.size(); i++)
		{
			totalHr = totalHr + heartRates.get(i);
			totalCal = totalCal + calories.get(i);
			totalDist = totalDist + distanceTravelled.get(i);
			totalTime = totalTime + timeTraining.get(i);

			if (i == 0)
			{
				maxHr = heartRates.get(0);
				minHr = heartRates.get(0);

				maxCal = calories.get(0);
				minCal = calories.get(0);

				maxDist = distanceTravelled.get(0);
				minDist = distanceTravelled.get(0);

				maxTime = timeTraining.get(0);
				minTime = timeTraining.get(0);
			}
			else
			{
				if (heartRates.get(i) > maxHr)
					maxHr = heartRates.get(i);
				if (heartRates.get(i) < minHr)
					minHr = heartRates.get(i);

				if (calories.get(i) > maxCal)
					maxCal = calories.get(i);
				if (calories.get(i) < minCal)
					minCal = calories.get(i);

				if (distanceTravelled.get(i) > maxDist)
					maxDist = distanceTravelled.get(i);
				if (distanceTravelled.get(i) < minDist)
					minDist = distanceTravelled.get(i);

				if (timeTraining.get(i) > maxTime)
					maxTime = timeTraining.get(i);
				if (timeTraining.get(i) < minTime)
					minTime = timeTraining.get(i);
			}
		}

		avgHr = totalHr / heartRates.size();
		avgCal = totalCal / calories.size();
		avgDist = totalDist /distanceTravelled.size();
		avgTime = totalTime / timeTraining.size();
	} //calcAvgMaxMin


} // Statistics