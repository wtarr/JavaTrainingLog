/******************************************************
 ***** Author: 		        William Tarrant      ******
 ***** Project Title: 		TrainingLogGUI       ******
 ***** File Name:           User.java            ******
 ***** Date of Last Edit:	27/11/2011           ****** 
 ******************************************************
 */

//This contain all the relavant information relating to a single user
import java.util.ArrayList;
import javax.swing.*;
import java.io.*;

public class User implements Serializable {

	//Attributes
	private String name;
	private int age;
	private char gender;

	private ArrayList<Integer> heartRates;
	private ArrayList<Integer> calories;
	private ArrayList<Integer> distanceTravelled;
	private ArrayList<Integer> timeTraining;

	
	// Constructor
	public User()
	{
		this.name = null;
		this.age = 0;
		this.gender = '0';
		
		this.heartRates = new ArrayList<Integer>();
		this.calories = new ArrayList<Integer>();
		this.distanceTravelled = new ArrayList<Integer>();
		this.timeTraining = new ArrayList<Integer>();
		
	}
	
	
	//Mutator methods
	
	public void setName(String name)
	{
		this.name = name;
	}

	public void setAge(String age)
	{
		//validate integer and store it
		this.age = validateInteger( age, "age" );
	}

	public void setGender( String gender)
	{
		//validate that it is m or f only
		this.gender = validateGender( gender );

	}

	public void addToHeartrate(	String hr )
	{
		//validate here convert to int and store it
		heartRates.add(validateInteger( hr, "heartrate" ) );
	}

	public void addToCalories( String cal )
	{
		//validate here convert to int and store it
		calories.add( validateInteger( cal, "calories") );
	}

	public void addToDistanceTravelled( String dist )
	{
		//validate here convert to int and store it
		distanceTravelled.add( validateInteger( dist, "distance travelled") );
	}

	public void addToTimeTraining( String time )
	{
		//validate here convert to int and store it
		timeTraining.add( validateInteger( time, "time training") );
	}

	//Accessor methods
	public String getName()
	{
		return name;
	}

	public int getAge()
	{
		return age;
	}

	public char getGender()
	{
		return gender;
	}

	public ArrayList<Integer> getHeartRates()
	{
		return heartRates;
	}

	public ArrayList<Integer> getCalories()
	{
		return calories;
	}

	public ArrayList<Integer> getDistanceTraveled()
	{
		return distanceTravelled;
	}

	public ArrayList<Integer> getTimeTraining()
	{
		return timeTraining;
	}

	//Validation methods
	public int validateInteger(String test, String message)
	{
		boolean valid = false;
		String testThis = test;
		String temp = null;
		int i;

		while(valid == false || testThis.length() == 0)
		{
			for ( i = 0; i < testThis.length(); i++)
			{
				
				if ( testThis.charAt(i) < '0' || testThis.charAt(i) > '9' ||
					testThis.charAt(0) == '0' )
						break;
			}

			if (i != testThis.length() || testThis.length() == 0)
			{
				
				//That was not a valid integer
				temp = null;
				temp = JOptionPane.showInputDialog("Please enter a valid integer for " + message);
				while (temp == null || temp.length() == 0)
				{
					temp = JOptionPane.showInputDialog("You must enter a valid integer for " + message);
				}

				testThis = temp;
			}
			else
			{
				valid = true;
			}

		} //while loop

		return Integer.parseInt(testThis);

	} // validateInteger

	public char validateGender(String test)
	{
		String testThis = test;

		while (testThis == null || (!testThis.toLowerCase().equals("m") &&
			 !testThis.toLowerCase().equals("f") ) )
		{
			testThis = JOptionPane.showInputDialog("Please enter either m of f for gender");
		}
		
		
		return testThis.toLowerCase().charAt(0);
		
	} //validate gender


}