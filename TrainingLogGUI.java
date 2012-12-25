/******************************************************
 ***** Author: 		        William Tarrant      ******
 ***** Project Title: 		TrainingLogGUI       ******
 ***** File Name:         TrainingLogGUI.java  ******
 ***** Date of Last Edit:	27/11/2011           ****** 
 ******************************************************
 */

/*The is the driver class that will create the GUI for the user to interact with,
 * It is also responsible for the loading, file interaction and saving of the file
 *to storage.
 *The file is loaded on startup and saved on close to removing the need for user
 *to search for the relevant file.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class TrainingLogGUI extends JFrame implements ActionListener, WindowListener
{
   //Global variables
   private JMenu fileMenu;
   private Toolkit toolkit;
   private JLabel lblimage;
   private JLabel lblTitle;
   private JLabel lblName;
   private JLabel lblAge;
   private JLabel lblGender;
   private JLabel lblHeartRate;
   private JLabel lblCalories;
   private JLabel lblDistance;
   private JLabel lblTimeTraining;
   private JTextField txtNameField; //user uneditable
   private JTextField txtAgeField; //user uneditable
   private JTextField txtGenderField; //user uneditable
   private JTextField txtHeartRate;
   private JTextField txtCalories;
   private JTextField txtDistance;
   private JTextField txtTimeTraining;
   private JButton btnAdd;
   private JButton btnStatistics;

   private BorderLayout layout;

   private int currentUserIndex = -1; //who is the current active user

   private LinkedList <User> users; //this will be assigned a array from stored file
   //ArrayList<User> users = new ArrayList<User>(); //for test purposes


	//Main method
   public static void main(String[] args) {
        TrainingLogGUI frame = new TrainingLogGUI();
        frame.setVisible(true);
    }

	//Constructor
    public TrainingLogGUI() {
    	
    	// Attempt to load the user list
    	loadUserList();

        Container cPane;

        //set the frame properties
        setTitle     ("Training Log");
        setSize      (350,300);
        setResizable (false);
        addWindowListener(this);

        //set the location of the window at the center of the screen
		toolkit = this.getToolkit();
		Dimension size = toolkit.getScreenSize();
		setLocation((size.width - getWidth())/2, (size.height - getHeight())/2);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

		cPane = getContentPane( );
        GridLayout gridLayout = new GridLayout(9, 2, 5, 5); //create a gridlayout

        //create a panel that will hold all the components
        JPanel myPanel = new JPanel(gridLayout);
        //this panel will have a consistent border around it
        myPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        //Create the menu system
        JMenuBar menuBar = new JMenuBar();
        createFileMenu();
        setJMenuBar(menuBar);
        menuBar.add(fileMenu);

        createLabelsAndButtons();

        //add them to the panel
        myPanel.add(lblimage);
        myPanel.add(lblTitle);
		myPanel.add(lblName);
        myPanel.add(txtNameField);
        myPanel.add(lblAge);
        myPanel.add(txtAgeField);
        myPanel.add(lblGender);
        myPanel.add(txtGenderField);
        myPanel.add(lblHeartRate);
        myPanel.add(txtHeartRate);
        myPanel.add(lblCalories);
        myPanel.add(txtCalories);
        myPanel.add(lblDistance);
        myPanel.add(txtDistance);
        myPanel.add(lblTimeTraining);
        myPanel.add(txtTimeTraining);
        myPanel.add(btnAdd);
        myPanel.add(btnStatistics);

        // finally add the panel to the container
        cPane.add(myPanel);

    } // end constructor

    public void loadUserList() 
    {
    	/* This method called at start up will load the user file and assign its
    	 *contents to the users linked list.  If the file does not exist then the 
    	 *linked list will be instatiated as a new LinkList (this will later be saved to file
    	 *that will be created on program exit)
    	 */
    	try{
      	  ObjectInputStream is;

      	  FileInputStream fIS = new FileInputStream ("users.dat");

      	  is = new ObjectInputStream(fIS);
    	  users = (LinkedList<User>) is.readObject();
      	  is.close();

      	}
      	catch(Exception e){
      		//The file does not exist
      		//System.out.println(e.getMessage());
      	}
		
		//create a new linked list if one does not exist
      	if(users == null){
      		users = new LinkedList<User>();
      	}
    } //loadFile

    public void createLabelsAndButtons()
    {
    	/*This method is responsible for the creation of all the
    	 *buttons and labels
    	 */

    	//create the labels
    	lblimage = new JLabel(new ImageIcon( getClass().getResource( "title.png" )));
    	lblTitle = new JLabel(new ImageIcon( getClass().getResource( "bike.png" )));
        lblName = new JLabel("Name:");
        lblAge = new JLabel("Age:");
        lblGender = new JLabel("Gender:");
        lblHeartRate = new JLabel("Avg Heartrate (bpm)");
        lblCalories = new JLabel("Calories Burned (cal)");
        lblDistance = new JLabel("Distance Travelled (km)");
        lblTimeTraining = new JLabel("Time Training (min)");
        
        //create the fields
        txtNameField = new JTextField(30);
        txtAgeField = new JTextField(30);
        txtGenderField = new JTextField(30);
        txtHeartRate = new JTextField(30);
        txtCalories = new JTextField(30);
        txtDistance = new JTextField(30);
        txtTimeTraining = new JTextField(30);

        //create the buttons
        btnAdd = new JButton("Add");
        btnAdd.addActionListener(this);
        btnStatistics = new JButton("Statistics");
        btnStatistics.addActionListener(this);

        //disable all textFields and buttons
        txtNameField.setEditable(false);
        txtAgeField.setEditable(false);
        txtGenderField.setEditable(false);
        txtHeartRate.setEditable(false);
        txtCalories.setEditable(false);
       	txtDistance.setEditable(false);
       	txtTimeTraining.setEditable(false);
        btnAdd.setEnabled(false);
        btnStatistics.setEnabled(false);
    }


    public void actionPerformed(ActionEvent event) {
    	/* This method is responsible for the handling of
    	 * any user click events.
    	 */
        String  actionName;
        actionName = event.getActionCommand();

        if (actionName.equals("Exit")) {
           //Save to file now
			try{
      	 		saveToFile();
      	 		JOptionPane.showMessageDialog(null, "Data saved successfully", "Save Successful",
      	 			JOptionPane.INFORMATION_MESSAGE);
      	 	} // try
      	 		catch (IOException f){
      	 		JOptionPane.showMessageDialog(null, "Not able to save the file", "File Save Error",
      	 		JOptionPane.ERROR_MESSAGE);
      	 	}// catch
           System.exit(0);
        }
        else if (actionName.equals("New user"))
        {
           //do new user procedure
           createNewUser();
        }
        else if (actionName.equals("Load user"))
        {
        	//do load user procedure
        	loadUser();
        }
        else if (actionName.equals("Remove user"))
        {
        	//do Remove user procedure
        	removeUser();
        }

        else if (event.getSource() == btnAdd)
        {
        	//do add to user procedure
         	addToUser();
        }
        else if (event.getSource() == btnStatistics)
        {
        	//do statistics procedure
        	if (users.get(currentUserIndex).getHeartRates().size() >= 1)
        		showStats();
        	else
        		JOptionPane.showMessageDialog(null, "You need at least one record before you can use this feature", "Warning",
        			JOptionPane.ERROR_MESSAGE);
        }

    } // end actionPerformend


    public void createFileMenu( ) {
        JMenuItem    item;
        fileMenu = new JMenu("File");
		item = new JMenuItem("New user");
        item.addActionListener( this );
        fileMenu.add( item );
        item = new JMenuItem("Load user");
        item.addActionListener( this );
        fileMenu.add( item );
        item = new JMenuItem("Remove user");
        item.addActionListener( this );
        fileMenu.add( item );
        fileMenu.addSeparator();
        item = new JMenuItem("Exit");
        item.addActionListener( this );
        fileMenu.add( item );
    } //createFileMenu

    public void createNewUser()
    {
    	//To avoid problems disable all these
    	txtNameField.setText("");
        txtAgeField.setText("");
        txtGenderField.setText("");
        txtHeartRate.setText("");
    	txtCalories.setText("");
    	txtDistance.setText("");
    	txtTimeTraining.setText("");
    	txtHeartRate.setEditable(false);
        txtCalories.setEditable(false);
       	txtDistance.setEditable(false);
       	txtTimeTraining.setEditable(false);
        btnAdd.setEnabled(false);
        btnStatistics.setEnabled(false);
		//create a new user and get their details
    	User user = new User();
    	user.setName(inputBox("Please enter your name"));
    	user.setAge(inputBox("Please enter your age"));
    	user.setGender(inputBox("Please enter your gender"));
    	users.add(user);
    } //createNewUser

    public void loadUser()
    {
    	/* Present the user with a list of users, the method will set
    	 * the index of the current user to a valid integer within
    	 * range of the LinkedList and allow editing of the user and
    	 * display of statistics
    	 */

    	 //disable / clear - buttons / textfields
    	txtHeartRate.setText("");
    	txtCalories.setText("");
    	txtDistance.setText("");
    	txtTimeTraining.setText("");
    	txtNameField.setText("");
        txtAgeField.setText("");
        txtGenderField.setText("");
        txtHeartRate.setEditable(false);
        txtCalories.setEditable(false);
       	txtDistance.setEditable(false);
       	txtTimeTraining.setEditable(false);
        btnAdd.setEnabled(false);
        btnStatistics.setEnabled(false);

    	if (users.size() > 0) //the list contains at least one user
        {

        	JTextArea textArea = new JTextArea();
        	Font myFont = new Font("monospaced", Font.PLAIN, 12);
        	textArea.setFont(myFont);
        	textArea.setText("Please select your index from the list\nthat you wish to edit\n\nIndex    User\n--------------------------------------\n");

            for (int i = 0; i < users.size(); i++)
        	{
        		textArea.append(String.format("%-9d%s\n", i+1, users.get(i).getName()));
        	}

        	currentUserIndex = validUser(textArea) - 1;
        	if (currentUserIndex >= 0) // check to see that user didnt cancel
        	{
        		// if not then carry on loading the user
        		txtNameField.setText(users.get(currentUserIndex).getName());
        		txtAgeField.setText("" + users.get(currentUserIndex).getAge());
        		txtGenderField.setText("" + users.get(currentUserIndex).getGender());
        		// enable fields so that user may type information into them
        		txtHeartRate.setEditable(true);
        		txtCalories.setEditable(true);
       			txtDistance.setEditable(true);
       			txtTimeTraining.setEditable(true);
        		btnAdd.setEnabled(true);
        		btnStatistics.setEnabled(true);

        	}

        }
        else
        {
        	// There are no users in the file
        	JOptionPane.showMessageDialog(null, "The file has no users", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } //loadUser

    public void removeUser()
    {
    	// same as above, except remove
    	// Disable all buttons, clear all text fields and
    	// remove user form list
    	txtHeartRate.setText("");
    	txtCalories.setText("");
    	txtDistance.setText("");
    	txtTimeTraining.setText("");
    	txtNameField.setText("");
        txtAgeField.setText("");
        txtGenderField.setText("");
        txtHeartRate.setEditable(false);
        txtCalories.setEditable(false);
       	txtDistance.setEditable(false);
       	txtTimeTraining.setEditable(false);
        btnAdd.setEnabled(false);
        btnStatistics.setEnabled(false);

    	if (users.size() > 0) //the list has at least one user
        {

        	JTextArea textArea = new JTextArea();
        	Font myFont = new Font("monospaced", Font.PLAIN, 12);
        	textArea.setFont(myFont);
        	textArea.setText("Please select the index from the list\nthat you wish to remove\n\nIndex    User\n--------------------------------------\n");

            for (int i = 0; i < users.size(); i++)
        	{
        		textArea.append(String.format("%-9d%s\n", i+1, users.get(i).getName()));
        	}

        	currentUserIndex = validUser(textArea) - 1;
        	txtNameField.setText("");
        	txtAgeField.setText("");
        	txtGenderField.setText("");
        	if (currentUserIndex >= 0) //user canceled request
        		users.remove(currentUserIndex);
        }
        else //the system has no users
        {
        	JOptionPane.showMessageDialog(null, "The file has no users", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

	public void addToUser()
	{
		// add the information to the relavent user and clear the textfields for
		// another entry
		users.get(currentUserIndex).addToHeartrate(txtHeartRate.getText());
		users.get(currentUserIndex).addToCalories(txtCalories.getText());
		users.get(currentUserIndex).addToDistanceTravelled(txtDistance.getText());
		users.get(currentUserIndex).addToTimeTraining(txtTimeTraining.getText());
		txtHeartRate.setText("");
		txtCalories.setText("");
		txtDistance.setText("");
		txtTimeTraining.setText("");


	} // addToUser



	public void showStats()
	{
		// Create and display a graph of the users training progress in a new window
		Statistics stats = new Statistics(users.get(currentUserIndex).getName(), users.get(currentUserIndex).getHeartRates(),
				users.get(currentUserIndex).getCalories(), users.get(currentUserIndex).getDistanceTraveled(),
				users.get(currentUserIndex).getTimeTraining());


	} // showStats

	public String inputBox(String dialog)
	{
		// creates a shorthand way for coders benefit to get input from user
		String input = JOptionPane.showInputDialog(dialog);
		while( input == null )
			input = JOptionPane.showInputDialog(dialog);
		return input;
	} // inputBox


	public void saveToFile() throws IOException
	{
		// save the information in the users LinkedList to file if a file does not exist then one will
		// be created
		try{
      	  	ObjectOutputStream os;
      		os = new ObjectOutputStream(new FileOutputStream ("users.dat"));
      		os.writeObject(users);
      		os.close();
      	}
      	catch(Exception f){
      		JOptionPane.showMessageDialog(null, "Problem saving to file", "File save error",
      		JOptionPane.ERROR_MESSAGE);
      	}
	}


	public int validUser(JTextArea dialog)
	{
		// perfrom checks to ensure that the value entered was indeed an integer and that
		// it is within the LinkLists Range

		boolean validInt = false, testInvalidRange = true;
		String testThis = JOptionPane.showInputDialog(dialog);

		if ( testThis == null )
		{
			return 0; // user canceled request
		}

		int i, validInteger = 0;

		while ( testThis.length() == 0 ) // field cannot be left blank
		{
			JOptionPane.showMessageDialog(null, "You must enter a valid integer", "Error", JOptionPane.ERROR_MESSAGE);
			testThis = JOptionPane.showInputDialog(dialog);
		} // while

		while(validInt == false || testThis == null)
		{
			for ( i = 0; i < testThis.length(); i++)
			{
				if ( testThis.charAt(i) < '0' || testThis.charAt(i) > '9' ||
					testThis.charAt(0) == '0' )
						break;
			}

			if (i != testThis.length()) // Its not an integer
			{
				JOptionPane.showMessageDialog(null, "That value is out of range\nPlease enter a valid integer!!!\n\n",
						"Error", JOptionPane.ERROR_MESSAGE);
				testThis = JOptionPane.showInputDialog(dialog);
				
				if (testThis == null)
					return 0; // the user cancelled
			}
			else
			{
				// The value entered is a valid integer
				// now test that it is with in range of the arrayList
				validInteger = Integer.parseInt(testThis);
				while(testInvalidRange == true)
				{
					if (validInteger >= 1 && validInteger <= (users.size()))
					{
						// its within range
						validInt = true;
						testInvalidRange = false;
					}
					else
					{
						// its out of range
						testInvalidRange = false;
						JOptionPane.showMessageDialog(null, "That value is out of range\nPlease enter a valid integer!!!\n\n",
						"Error", JOptionPane.ERROR_MESSAGE);
						testThis = JOptionPane.showInputDialog(dialog);
					}
				}
				testInvalidRange = true; //reset the test
			}
		} // while loop
		return validInteger;
	} // validInteger

	public void windowClosing(WindowEvent e){
		// Save to file now
		try{
      	 	saveToFile();
      	 	JOptionPane.showMessageDialog(null, "Data saved successfully");
      	 } // try
      	 catch (IOException f){
      	 	JOptionPane.showMessageDialog(null, "Not able to save the file");
      	 	f.printStackTrace();
      	 }// catch
	} // end windowClosing

	public void windowActivated(WindowEvent e){ }
	public void windowClosed(WindowEvent e){ }
	public void windowDeactivated(WindowEvent e){ }
	public void windowIconified(WindowEvent e){ }
	public void windowDeiconified(WindowEvent e){ }
	public void windowOpened(WindowEvent e){ }


} // end class
