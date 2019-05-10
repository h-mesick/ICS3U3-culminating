/*
Max Li
Mrs. Krasteva
1-17-2019
Final Project, Jeopardy ISP

NAME                        TYPE        PURPOSE
c                           Console     Output window of the program
value                       int[][]     Used as a placeholder for the values of each question
choice                      String      Used to get the input in the main menu to determine program flow
user1                       String      The name of the first user
user2                       String      The name of the second user
level                       String      The level of difficulty the users have chosen
finalJeopardyQuestion       String      The question used for final Jeopardy
finalJeopardyAnswer         String      The answer used for final Jeopardy
score1                      int         Score of the first user
score2                      int         Score of the second user
xindex                      int         Category index
yindex                      int         Question index
multiplier                  int         Multiplies the amount depending on the level
user1Exists                 boolean     Stores if the first user previously existed
user2Exists                 boolean     Stores if the second user previously existed
isLastQuestion              boolean     Stores if the current question is the last question
turn                        boolean     Stores the current turn in the game, true = user1, false = user2
userEntered                 boolean     Used to check if the users entered to answer the prompt
tmempEntered                boolean     Temporary variable for userEntered
visibility                  boolean[][] Stores the questions that have been used
dailyDouble                 boolean[][] Stores the locations of the daily double
questions                   String[][]  Stores the questions used
answers                     String[][]  Stores the answers used for the questions
categories                  String[]    Stored the 5 categories used for the game
fastest                     char        char for the fastest user to respond
*/

//imports
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;
import hsa.*;

public class Jeopardy
{

    //global variables
    Console c;
    final int[] [] value = {{100, 200, 300, 400, 500}, {100, 200, 300, 400, 500}, {100, 200, 300, 400, 500}, {100, 200, 300, 400, 500}, {100, 200, 300, 400, 500}};
    String choice, user1, user2, level, finalJeopardyQuestion, finalJeopardyAnswer;
    int score1, score2, xindex, yindex, multiplier;
    boolean user1Exists, user2Exists, isLastQuestion, turn = true, userEntered, tempEntered;
    boolean[] [] visibility, dailyDouble;
    String[] [] questions, answers;
    String[] categories;
    private char fastest;

    //constructor
    public Jeopardy ()
    {
	c = new Console (25, 80, 14, "Jeopardy!");
    }


    //pause program pauses the program execution until the user presses a key
    //this helps as the user can read the text appearing on the screen
    private void pauseProgram ()
    {
	c.println ();
	c.print ("", 28);
	c.println ("Press any key to continue");
	c.getChar (); //keyboard input
    }


    //title is used for setting the background color and clearing the text
    private void title ()
    {
	c.clear ();
	//c.setColor (lightblue);
	//c.fillRect (0, 0, 640, 500); //fills the whole window
	//c.setTextBackgroundColor (lightblue); //allows for the space behind the text to be the same color as the background
	c.print ("", 36);
	c.println ("Jeopardy!");
	c.println ();
    }


    /*
    method used to center the text passed into the parameter
    Takes in a String variable, the output that is to be centered
    */
    private void printCentre (String output)
    {
	c.print ("", 80 / 2 - output.length () / 2);
	c.println (output);
    }


    /*
    String return method that strips capitals and whitespace
    NAME    TYPE    PURPOSE
    input   String  Used as a temporary placeholder for the resulting string
    */
    private String cleanInput (String input)
    {
	input = input.toLowerCase (); //converts everything to lower case
	String temp = "";
	for (int i = 0 ; i < input.length () ; i++)
	{
	    if (input.charAt (i) != ' ') //strips the spaces
		temp += input.charAt (i);
	}
	return temp;
    }


    /*
    Splashscreen that serves as a short introduction to the program
    NAME    TYPE            PURPOSE
    jl      JeopardyLogo    Thread that outputs an animation to the screen
    */
    public void splashScreen ()
    {
	JeopardyLogo jl = new JeopardyLogo (c); //create the animation thread
	jl.start (); //starts the thread
	try
	{
	    jl.join ();
	    Thread.sleep (1000); //delays the thread before moving to the next method
	}
	catch (InterruptedException e)
	{
	}
    }


    /*
    mainMenu serves as a method that controls the program flow
    This is where the String choice's input is taken
    If it is 1, 2, 3, 4, or 5 the program can continue
    otherwise it gives an error message and retakes the input
    The while loop is for taking in input until the user enters correct input
    There is an if statement inside the while loop for checking if the input
    is vald input, if it is the loop exits, otherwise the loop continues to run
    */
    public void mainMenu ()
    {
	title ();
	//options
	c.println ();
	c.print ("", 32);
	c.println ("1. Instructions");
	c.print ("", 32);
	c.println ("2. Play Game");
	c.print ("", 32);
	c.println ("3. Highscores");
	c.print ("", 32);
	c.println ("4. Clear Highscores");
	c.print ("", 32);
	c.println ("5. Exit");
	c.println ();
	c.println ();
	c.print ("", 32);
	c.print ("Enter a menu choice: ");
	//infinite loop that keeps taking input until the user enters a valud choice
	while (true)
	{
	    choice = c.readLine ();
	    if (choice.equals ("1") || choice.equals ("2") || choice.equals ("3") || choice.equals ("4") || choice.equals ("5")) //checks for validity
	    {
		break; //if valid the loop breaks
	    }
	    else
	    {
		new Message ("Please enter a valid choice"); //message for when it is not valid
		//sets the cursor so it is ready to take input again
		c.setCursor (11, 54);
		c.println ();
		c.setCursor (11, 54);
	    }
	}
    }


    /*
    instructions is a method that just tells the user how the program works and what is being done
    */
    public void instructions ()
    {
	title ();
	//the instructions that the user should read if they want to understand the program
	c.println ();
	printCentre ("This is a two player version of the game Jeopardy!");
	printCentre ("Jeopardy is a trivia game where the players answer prompts to gain money");
	printCentre ("The player with the most money at the end is the winner of the game");
	printCentre ("The player that responds the quickest (5 seconds) is the one that gets to answer");
	c.println ();
	printCentre ("Key for player 1: 's'");
	printCentre ("Key for player 2: 'k'");
	c.println ();
	printCentre ("If no one chooses to answer, the answer will be revealed");
	printCentre ("If they get the answer correct, they will gain the money, otherwise lose");
	printCentre ("There will be two different levels with 5 random categories");
	printCentre ("Daily Doubles will allow you to wager money for a chance to gain double");
	printCentre ("If you get the question right you gain double of your wager");
	printCentre ("If you get it wrong you lose the amount you wagered");
	printCentre ("Final Jeopardy happens when all the questions have been picked");
	printCentre ("A random question will be selected from another category");
	printCentre ("You will be allowed to wager $0 to max($1000,user's current amount) dollars");
	pauseProgram ();
    }


    /*
    highScore is a method that displays the top ten users with the highest scores
    highscores.txt is a separate file storing the names of all the users
    The scores of these users are scored in separate text files, with one line with their score
    uses a bubble sort acting on two arrays
    NAME        TYPE            PURPOSE
    names       String[]        Stores the names of the users
    scores      int[]           Stores the scores of the users
    lines       int             Counts the number of users in the file
    in          String          Temporary input variable
    br          BufferedReader  Reads the contents of a file
    */
    public void highScore ()
    {
	title ();
	//variable declaration
	String[] names = new String [10];
	int[] scores = new int [10];
	int lines = 0;
	try
	{
	    //reads the number of lines, (the number of pre-existing users)
	    BufferedReader br = new BufferedReader (new FileReader ("highscores.txt"));
	    String in;
	    while ((in = br.readLine ()) != null)
	    {
		lines++;
	    }
	    names = new String [lines];
	    scores = new int [lines];
	    //reads the name and score of an existing user into arrays
	    br = new BufferedReader (new FileReader ("highscores.txt"));
	    for (int i = 0 ; i < lines ; i++)
	    {
		names [i] = br.readLine ();
		BufferedReader buf = new BufferedReader (new FileReader (names [i] + ".txt"));
		scores [i] = Integer.parseInt (buf.readLine ());
	    }
	    //bubble sorts the array of scores based on larger amount appears first
	    for (int i = 0 ; i < lines ; i++)
	    {
		for (int j = 0 ; j < lines ; j++)
		{
		    if (scores [j] < scores [i])
		    {
			int temp = scores [i];
			scores [i] = scores [j];
			scores [j] = temp;
			String nameTemp = names [i];
			names [i] = names [j];
			names [j] = nameTemp;
		    }
		}
	    }
	    br.close ();
	}
	catch (IOException e)
	{
	}
	c.print ("", 25);
	c.print ("Name", 30);
	c.println ("Score");
	//displays the top 10 users, if there is less than 10 then displays that amount
	for (int i = 0 ; i < Math.min (10, lines) ; i++)
	{
	    c.print ("", 25);
	    c.print (names [i], 30);
	    c.println (scores [i]);
	}
	pauseProgram ();
    }


    /*
    method used to clear the file containing all the highscores
    */
    public void clearHighScores ()
    {
	title ();
	try
	{
	    //creates the file again
	    PrintWriter pw = new PrintWriter (new FileWriter ("highscores.txt"));
	    //outputs blank
	    pw.print ("");
	    pw.close ();
	    //confirmation message
	    printCentre ("Highscores have been cleared");
	    pauseProgram ();
	}
	catch (IOException e)
	{
	}
    }


    /*
    method that takes in the names of the two users and the level of difficulty that they want to play on
    Also checks for if the users had previously existed
    NAME        TYPE              PURPOSE
    valid       String            Stores characters of an valid username
    good        boolean           Becomes true if all the characters are valid
    found       boolean           Checks if there had previously been a username with the same name
    used        boolean           Checks if the user would like to continue with this username if the username had been found
    br          BufferedReader    Reader used for reading files
    */
    public void userInput ()
    {
	title ();
	//variable declaration
	user1Exists = false;
	user2Exists = false;
	String valid = "qwertyuiopasdfghjklzxcvbnm";
	valid += valid.toUpperCase ();
	valid += "1234567890";
	try
	{
	    //reads all the preexisting users
	    BufferedReader br = new BufferedReader (new FileReader ("highscores.txt"));
	    int lines = 0;
	    String in;
	    while ((in = br.readLine ()) != null)
		lines++;
	    br = new BufferedReader (new FileReader ("highscores.txt"));
	    String[] usedNames = new String [lines];
	    for (int i = 0 ; i < lines ; i++)
	    {
		usedNames [i] = br.readLine ();
	    }
	    br.close ();
	    //prompt
	    printCentre ("Please enter the username the first user would like to use: ");
	    while (true)
	    {
		boolean good = true, found = false, used = false;
		user1 = c.readLine ();
		//first error check, to see if there are any illegal characters
		for (int i = 0 ; i < user1.length () ; i++)
		{
		    if (valid.indexOf (user1.charAt (i)) < 0)
		    {
			good = false;
			break;
		    }
		}
		if (good)
		{
		    //second error check, to see if there are any pre-existing users with the same name
		    for (int i = 0 ; i < lines ; i++)
		    {
			if (user1.equals (usedNames [i]))
			{
			    int msg = JOptionPane.showConfirmDialog (null, "This username already exists. Would you like to continue using this user?", "User Found", JOptionPane.ERROR_MESSAGE, JOptionPane.YES_NO_OPTION);
			    found = true;
			    //checks if they want to use the pre-existing user
			    used = msg == JOptionPane.YES_OPTION;
			    if (used)
				user1Exists = true;
			    break;
			}
		    }
		    //breaks if the username is valid
		    if ((found && used) || !found)
		    {
			break;
		    }
		    else
		    {
			//resets the prompt
			c.setCursor (4, 1);
			c.println ();
			c.setCursor (4, 1);
		    }
		}
		else
		{
		    //resets the prompt
		    new Message ("Please enter a valid username with alphanumeric characters");
		    c.setCursor (4, 1);
		    c.println ();
		    c.setCursor (4, 1);
		}
	    }
	    //prompt
	    printCentre ("Please enter the username the second user would like to use: ");
	    while (true)
	    {
		boolean good = true, found = false, used = false;
		user2 = c.readLine ();
		//first error check, to see if they are using the same name as the first user
		if (user2.equals (user1))
		{
		    new Message ("Please enter a different username from the first user");
		    c.setCursor (6, 1);
		    c.println ();
		    c.setCursor (6, 1);
		    continue;
		}
		//second error check, to see if there are any illegal characters
		for (int i = 0 ; i < user2.length () ; i++)
		{
		    if (valid.indexOf (user2.charAt (i)) < 0)
		    {
			good = false;
			break;
		    }
		}
		if (good)
		{
		    //second error check, to see if there are any pre-existing users with the same name
		    for (int i = 0 ; i < lines ; i++)
		    {
			if (user2.equals (usedNames [i]))
			{
			    int msg = JOptionPane.showConfirmDialog (null, "This username already exists. Would you like to continue using this user?", "User Found", JOptionPane.ERROR_MESSAGE, JOptionPane.YES_NO_OPTION);
			    found = true;
			    //checks if they want to use the pre-existing user
			    used = msg == JOptionPane.YES_OPTION;
			    if (used)
				user2Exists = true;
			    break;
			}
		    }
		    //breaks if the username is valid
		    if ((found && used) || !found)
		    {
			break;
		    }
		    else
		    {
			//resets
			c.setCursor (6, 1);
			c.println ();
			c.setCursor (6, 1);
		    }
		}
		else
		{
		    //resets
		    new Message ("Please enter a valid username with alphanumeric characters");
		    c.setCursor (6, 1);
		    c.println ();
		    c.setCursor (6, 1);
		}
	    }
	    //input for the level of difficulty the users would like to play on
	    printCentre ("Enter the level of difficulty that you would like to participate in (1 or 2):");
	    while (true)
	    {
		level = c.readLine ();
		//can only be 1 or 2
		if (level.equals ("1") || level.equals ("2"))
		    break;
		else
		{
		    //resets
		    new Message ("Please enter a valid level, 1 or 2");
		    c.setCursor (8, 1);
		    c.println ();
		    c.setCursor (8, 1);
		}
	    }
	}
	catch (IOException e)
	{
	}
    }


    /*
    This method intializes the questions and answers used during the gameplay
    It randomizes the categories using the 7 categories provided
    NAME                    TYPE            PURPOSE
    finalJeopardyCategory   String          The category of the final Jeopardy question
    totalCategories         String[]        Stores all the possible categories that can be chosen
    indexes                 int[]           Stores the indexes of the categories that will be used in the gameplay
    size                    int             The number of categories that have already been chosen
    br                      BufferedReader  Reader used for reading in the files
    */
    public void initializeTable ()
    {
	//variable declaration
	questions = new String [5] [5];
	answers = new String [5] [5];
	categories = new String [5];
	visibility = new boolean [5] [5];
	dailyDouble = new boolean [5] [5];
	String finalJeopardyCategory;
	String[] totalCategories = new String [13];
	int[] indexes = new int [5];
	BufferedReader br;
	try
	{
	    //reads in the tota categoris
	    br = new BufferedReader (new FileReader ("categories.cat"));
	    for (int i = 0 ; i < 7 ; i++)
		totalCategories [i] = br.readLine ();

	    int size = 0;
	    while (size < 5)
	    {
		//generates the categories that are going to be used
		int temp = (int) Math.floor (Math.random () * 6);
		boolean found = false;
		for (int i = 0 ; i < size ; i++)
		{
		    if (indexes [i] == temp)
		    {
			found = true;
			break;
		    }
		}
		//checks for if the category hasn't already been chosem
		if (!found)
		{
		    indexes [size] = temp; //change later to become random
		    size++;
		}
	    }

	    //sets the categories
	    for (int i = 0 ; i < 5 ; i++)
		categories [i] = totalCategories [indexes [i]];

	    //reads the questions and answers that will be used
	    for (int i = 0 ; i < 5 ; i++)
	    {
		br = new BufferedReader (new FileReader (categories [i] + level + ".cat"));
		for (int j = 0 ; j < 5 ; j++)
		    questions [i] [j] = br.readLine ();
		for (int j = 0 ; j < 5 ; j++)
		    answers [i] [j] = br.readLine ();
	    }


	    //daily double
	    //generates the first daily double
	    dailyDouble [(int) Math.floor (Math.random () * 4)] [(int) Math.floor (Math.random () * 4)] = true;
	    if (level.equals ("2")) //if the difficulty is level 2, there needs to be another daily double generated
	    {
		while (true)
		{
		    int temp1 = (int) Math.floor (Math.random () * 4), temp2 = (int) Math.floor (Math.random () * 4);
		    //if this hasn't already been chosen
		    if (!dailyDouble [temp1] [temp2])
		    {
			dailyDouble [temp1] [temp2] = true;
			break;
		    }
		}
	    }

	    //final jeopardy
	    while (true)
	    {
		boolean found = false;
		//generates the cateogry for the final jeopardy
		int rand = (int) Math.floor (Math.random () * 6);
		finalJeopardyCategory = totalCategories [rand];
		//checks for if this category hasn't already been chosen
		for (int i = 0 ; i < 5 ; i++)
		{
		    if (categories [i].equals (finalJeopardyCategory))
		    {
			found = true;
			break;
		    }
		}
		if (!found)
		    break;
	    }

	    //sets the question and answer that will be used
	    br = new BufferedReader (new FileReader (finalJeopardyCategory + "f.cat"));
	    finalJeopardyQuestion = br.readLine ();
	    finalJeopardyAnswer = br.readLine ();
	}
	catch (IOException e)
	{
	}
    }


    /*
    Displays the current state of the Jeopardy table
    Hides the values of the cells if it has already been chosen
    Allows the current user to choose a question to answer
    Also displays the current scores of the users
    NAME        TYPE         PURPOSE
    input       String       Temporary variable for the input
    blue        Color        Color of the Jeopardy table
    */
    public void display ()
    {
	title ();
	String input;
	//sets the multiplier for the value
	multiplier = level.equals ("1") ? 1:
	2;
	//draws the table
	Color blue = new Color (0, 76, 153);
	c.setColor (blue);
	c.fillRect (70, 50, 500, 360);
	c.setColor (Color.black);
	for (int i = 70 ; i <= 570 ; i += 100)
	    c.fillRect (i, 50, 2, 360);
	for (int i = 50 ; i <= 410 ; i += 60)
	    c.fillRect (70, i, 502, 2);
	c.setColor (Color.white);
	c.setFont (new Font ("Courier", 0, 16));
	for (int i = 0 ; i < 5 ; i++)
	    c.drawString (categories [i], i * 100 + 80, 85);
	for (int i = 0 ; i < 5 ; i++)
	{
	    for (int j = 0 ; j < 5 ; j++)
	    {
		if (!visibility [i] [j])
		    c.drawString ("$" + value [i] [j] * multiplier, i * 100 + 90, 60 * j + 140);
	    }
	}

	//draws the current scores of the users
	c.setColor (Color.black);
	c.drawString (user1 + "'s Score: " + score1, 5, 490);
	c.drawString (user2 + "'s Score: " + score2, 450, 490);
	c.setCursor (22, 20);
	c.println ("It is now " + (turn ? user1:
	user2) + "'s turn to choose:");
	//input for the question that the current user wants to choose
	while (true)
	{
	    c.setCursor (23, 20);
	    c.println ();
	    c.println ();
	    c.setCursor (23, 20);
	    c.print ("Enter the category num (1-5): ");
	    while (true)
	    {
		input = c.readLine ();
		try
		{
		    xindex = Integer.parseInt (input);
		    //checks for out of bounds
		    if (xindex >= 1 && xindex <= 5)
			break;
		    else
		    {
			new Message ("Enter an integer from 1-5");
			c.setCursor (23, 50);
			c.println ();
			c.setCursor (23, 50);
		    }
		}
		catch (NumberFormatException e)
		{
		    new Message ("Enter an integer from 1-5");
		    c.setCursor (23, 50);
		    c.println ();
		    c.setCursor (23, 50);
		}

	    }
	    c.setCursor (24, 20);
	    c.print ("Enter the row num (1-5): ");
	    while (true)
	    {
		input = c.readLine ();
		try
		{
		    yindex = Integer.parseInt (input);
		    //checks for out of bounds
		    if (yindex >= 1 && yindex <= 5)
			break;
		    else
		    {
			new Message ("Enter an integer from 1-5");
			c.setCursor (24, 45);
			c.println ();
			c.setCursor (24, 45);
		    }
		}
		catch (NumberFormatException e)
		{
		    new Message ("Enter an integer from 1-5");
		    c.setCursor (24, 45);
		    c.println ();
		    c.setCursor (24, 45);
		}
	    }
	    xindex--;
	    yindex--;
	    //checks for if it hasn't already been chosen
	    if (!visibility [xindex] [yindex])
		break;
	    else
	    {
		new Message ("This has already been picked");
	    }
	}
    }


    /*
    Boolean return method that returns true if all the questions have been selected
    Loops throught all the cells of the visibility array and if it finds a cell
    that is false, that means it hasn't been picked and return false.
    Otherwise, if it manages to get through all the cells, it returns true
    */
    private boolean lastQuestion ()
    {
	for (int i = 0 ; i < 5 ; i++)
	    for (int j = 0 ; j < 5 ; j++)
		if (!visibility [i] [j])
		    return false;
	return true;
    }


    /*
    This method displays the question that the user has picked
    If this question is a daily double, it allows the user to enter a wager
    This wager will be doubled if they get the answer correct
    Otherwise they will lose the amount of the wager
    If the question is a regular Jeopardy question then there will be a 5 second reponse period
    The user that responds the fastest is the one that gets to answer
    If no one chooses to answer, the answer is revealed
    NAME            TYPE        PURPOSE
    current         String      Stores the user that is currently answering
    input           String      Temporary variable for the input, to be error checked
    wager           int         The amount of money the user is willing to wager
    maxWager        int         The maximum amount of money that the user can wager
    validResponse   boolean     True if the respose is valid
    timer           Timer       Timer for the response
    task            TimerTask   What is to be done when the timer ends
    */
    public void question ()
    {
	title ();
	String current = "", input;
	int wager, maxWager;
	boolean validResponse = false;
	//marks the question as being chosen
	visibility [xindex] [yindex] = true;

	if (lastQuestion ())
	    isLastQuestion = true;

	//checks if it is a dailyDouble
	if (dailyDouble [xindex] [yindex])
	{
	    //takes the max wager
	    if (turn)
		maxWager = Math.max (score1, value [xindex] [yindex] * multiplier);
	    else
		maxWager = Math.max (score2, value [xindex] [yindex] * multiplier);
	    printCentre ("Congratrulations! You got a daily double!");
	    c.println ();
	    c.println ("Please enter the amount you would like to wager");
	    c.println ("You can enter an amount between $5 and $" + maxWager + ": ");

	    //takes in the user's wager
	    while (true)
	    {
		try
		{
		    input = c.readLine ();
		    wager = Integer.parseInt (input);
		    if (wager >= 5 && wager <= maxWager)
			break;
		    else
		    {
			new Message ("Please enter a amount within the constraints");
			c.setCursor (7, 1);
			c.println ();
			c.setCursor (7, 1);
		    }
		}
		catch (NumberFormatException e)
		{
		    new Message ("Please enter a amount within the constraints");
		    c.setCursor (7, 1);
		    c.println ();
		    c.setCursor (7, 1);
		}
	    }

	    //prompt for the answer of the user
	    c.println (questions [xindex] [yindex]);
	    c.println ();
	    c.println ("Please enter what you think the answer is");
	    c.println ("(Spaces and capitals will be automatically adjusted, but spelling matters)");

	    //cleans the input
	    input = c.readLine ();
	    input = cleanInput (input);

	    if (input.equals (answers [xindex] [yindex])) //if correct
	    {
		printCentre ("You got the answer correct! You have gained $" + wager * 2);
		if (turn)
		    score1 += wager * 2;
		else
		    score2 += wager * 2;
	    }
	    else //wrong
	    {
		printCentre ("You got the answer incorrect! You have loss $" + wager);
		if (turn)
		    score1 -= wager;
		else
		    score2 -= wager;
	    }
	}
	else //if regular question
	{

	    tempEntered = true; //temporary variable checks for if there was input
	    userEntered = true; //variable that is changed based on whether there was input or not

	    c.println (questions [xindex] [yindex]);
	    c.println ();

	    //timer from https://stackoverflow.com/questions/5853989/time-limit-for-an-input
	    Timer timer = new Timer ();
	    TimerTask task = new TimerTask ()
	    {
		public void run ()
		{
		    if (tempEntered)
		    {
			userEntered = false;
			printCentre ("No user has entered, please press a key to continue");
		    }
		}
	    }

	    ;

	    //schedules for 5 seconds
	    timer.schedule (task, 5 * 1000);


	    printCentre ("User that responds fastest within 5 seconds gets to answer: ");
	    printCentre (user1 + " should press 's', " + user2 + " should press 'k'");
	    c.println ();
	    fastest = c.getChar ();

	    //if the response if before 5 seconds is over
	    tempEntered = false;
	    timer.cancel ();

	    if (userEntered)
	    {
		if (fastest == 's')
		{
		    current = "1";
		    validResponse = true;
		}
		else if (fastest == 'k')
		{
		    current = "2";
		    validResponse = true;
		}
	    }

	    //checks if the response was valid
	    if (validResponse)
	    {
		printCentre ("The fastest response was from " + current);
		c.println ();
		//answer of the user
		c.println ("Please enter what you think the answer is");
		c.println ("(Spaces and capitals will be automatically adjusted, but spelling matters)");
		input = c.readLine ();
		input = cleanInput (input);
		if (input.equals (answers [xindex] [yindex])) // if the user gets it correct
		{
		    printCentre ("You got the answer correct! You have gained $" + value [xindex] [yindex] * multiplier);
		    if (current.equals ("1"))
		    {
			score1 += value [xindex] [yindex] * multiplier;
			turn = true;
		    }
		    else
		    {
			score2 += value [xindex] [yindex] * multiplier;
			turn = false;
		    }
		}
		else //if the user gets it incorrect
		{
		    printCentre ("You got the answer incorrect! You have loss $" + value [xindex] [yindex] * multiplier);
		    if (current.equals ("1"))
		    {
			score1 -= value [xindex] [yindex] * multiplier;
		    }
		    else
		    {
			score2 -= value [xindex] [yindex] * multiplier;
		    }
		}
	    }
	    else //if response is errored
	    {
		printCentre ("No one chose to respond or the responses were errored");
		printCentre ("If you don't know which keys to press to respond, please review the instructions");
		printCentre ("The correct answer was " + answers [xindex] [yindex]);
	    }
	}

	//intended to make sure nothing accidental is pressed
	c.println ();
	c.println ("Enter a string and press enter to continue");
	c.readLine ();
    }


    /*
    finalJeopardy is the method that runs when isLastQuestion is true
    This method takes in the wagers of both the users
    It also takes in the answers of both the users
    NAME        TYPE        PURPOSE
    input       String      temporary variable for input, to be errorchecked
    answer1     String      error checked answer of the first user, stripped of spaces and capitals
    answer2     String      error checked answer of the second user, stripped of spaces and capitals
    max1        int         maximum wager amount of the first user
    max2        int         maximum wager amount of the second user
    wager1      int         the wager of the first user
    wager2      int         the wager of the second user
    */
    public void finalJeopardy ()
    {
	title ();
	String input, answer1, answer2;
	int max1, max2, wager1, wager2;
	//gets the maximum amount of the wgaers
	max1 = Math.max (1000, score1);
	max2 = Math.max (1000, score2);
	printCentre ("It is now time for final Jeopardy!");
	printCentre ("Make sure you hide your response and wagers from the other person!");
	c.println ();
	//prints the question
	printCentre (finalJeopardyQuestion);
	c.println ();
	//gets wager of first user
	c.println (user1 + ", enter an amount between $0 and $" + max1 + " to wager");
	while (true)
	{
	    try
	    {
		input = c.readLine ();
		wager1 = Integer.parseInt (input);
		if (wager1 >= 0 && wager1 <= max1)
		    break;
		else
		{
		    new Message ("Enter a wager within the constraints");
		    c.setCursor (9, 1);
		    c.println ();
		    c.setCursor (9, 1);
		}
	    }
	    catch (NumberFormatException e)
	    {
		new Message ("Enter a valid number");
		c.setCursor (9, 1);
		c.println ();
		c.setCursor (9, 1);
	    }
	}

	//rests
	c.setCursor (8, 1);
	c.println ();
	c.println ();
	c.setCursor (8, 1);

	//gets wager of sercond user
	c.println (user2 + ", enter an amount between $0 and $" + max2 + " to wager");
	while (true)
	{
	    try
	    {
		input = c.readLine ();
		wager2 = Integer.parseInt (input);
		if (wager2 >= 0 && wager2 <= max2)
		    break;
		else
		{
		    new Message ("Enter a wager within the constraints");
		    c.setCursor (9, 1);
		    c.println ();
		    c.setCursor (9, 1);
		}
	    }
	    catch (NumberFormatException e)
	    {
		new Message ("Enter a valid number");
		c.setCursor (9, 1);
		c.println ();
		c.setCursor (9, 1);
	    }
	}

	//rests
	c.setCursor (8, 1);
	c.println ();
	c.println ();
	c.println ();
	c.setCursor (8, 1);

	//gest the answer of the first user
	c.println (user1 + " enter what you think the correct answer is (remember, spelling counts): ");
	answer1 = c.readLine ();
	answer1 = cleanInput (answer1);

	//rests
	c.setCursor (8, 1);
	c.println ();
	c.println ();
	c.println ();
	c.setCursor (8, 1);

	//gets the answer of the second user
	c.println (user2 + " enter what you think the correct answer is (remember, spelling counts): ");
	answer2 = c.readLine ();
	answer2 = cleanInput (answer2);

	//resets
	c.setCursor (8, 1);
	c.println ();
	c.println ();
	c.println ();
	c.setCursor (8, 1);

	//shows the answer
	printCentre ("The correct answer was: " + finalJeopardyAnswer);
	c.println ();
	if (answer1.equals (finalJeopardyAnswer)) // if the first user got it correct
	{
	    printCentre ("User " + user1 + " got the answer correct! You have gained " + wager1);
	    score1 += wager1;
	}
	else //if the first user got it wrong
	{
	    printCentre ("User " + user1 + " got the answer incorrect! You have loss " + wager1);
	    score1 -= wager1;
	}

	if (answer2.equals (finalJeopardyAnswer)) // if the second user got it correct
	{
	    printCentre ("User " + user2 + " got the answer correct! You have gained " + wager2);
	    score2 += wager2;
	}
	else // if the second user got it wrong
	{
	    printCentre ("User " + user2 + " got the answer incorrect! You have loss " + wager2);
	    score2 -= wager2;
	}
	pauseProgram ();
    }


    /*
    Method that displays the winner of the game and their score
    Also writes to the highscores and user files
    NAME              TYPE              PURPOSE
    BufferedReader    BufferedReader    Reads the contents of files
    PrintWriter       PrintWriter       Writes the contents to files
    in                String            Temporary input variable
    lines             int               The number of lines in a file
    names             String[]          The names of the users that were already in the file
    */
    public void winner ()
    {
	title ();
	if (score1 > score2) //if the first user won
	{
	    printCentre ("The winner is: " + user1 + " with $" + score1);
	}
	else if (score2 > score1) //if the second user won
	{
	    printCentre ("The winner is: " + user2 + " with $" + score2);
	}
	else //if there was a tie
	{
	    printCentre ("You have tied! Both had " + score1);
	}

	BufferedReader br;
	PrintWriter pw;
	try
	{
	    if (user1Exists) //if the user the first user picked had previously existed
	    {
		br = new BufferedReader (new FileReader (user1 + ".txt"));
		//takes the maximum of the scores
		int temp = Math.max (Integer.parseInt (br.readLine ()), score1);
		pw = new PrintWriter (new FileWriter (user1 + ".txt"));
		pw.println (temp);
		pw.close ();
	    }
	    else //if it is a new user
	    {
		//adds them to the user list
		br = new BufferedReader (new FileReader ("highscores.txt"));
		int lines = 0;
		String in;
		while ((in = br.readLine ()) != null)
		    lines++;
		String[] names = new String [lines];
		br = new BufferedReader (new FileReader ("highscores.txt"));
		for (int i = 0 ; i < lines ; i++)
		    names [i] = br.readLine ();
		pw = new PrintWriter (new FileWriter ("highscores.txt"));
		for (int i = 0 ; i < lines ; i++)
		    pw.println (names [i]);
		pw.println (user1);
		pw.close ();
		//creates a new user file
		pw = new PrintWriter (new FileWriter (user1 + ".txt"));
		pw.println (score1);
		br.close ();
		pw.close ();
	    }
	    if (user2Exists) //if the user the second user picked had previously existed
	    {
		br = new BufferedReader (new FileReader (user2 + ".txt"));
		//takes the maximum of their scores
		int temp = Math.max (Integer.parseInt (br.readLine ()), score2);
		pw = new PrintWriter (new FileWriter (user2 + ".txt"));
		pw.println (temp);
		pw.close ();
	    }
	    else //if it is a new user
	    {
		//adds them to the user list
		br = new BufferedReader (new FileReader ("highscores.txt"));
		int lines = 0;
		String in;
		while ((in = br.readLine ()) != null)
		    lines++;
		String[] names = new String [lines];
		br = new BufferedReader (new FileReader ("highscores.txt"));
		for (int i = 0 ; i < lines ; i++)
		    names [i] = br.readLine ();
		pw = new PrintWriter (new FileWriter ("highscores.txt"));
		for (int i = 0 ; i < lines ; i++)
		    pw.println (names [i]);
		pw.println (user2);
		pw.close ();
		//creates a new user file
		pw = new PrintWriter (new FileWriter (user2 + ".txt"));
		pw.println (score2);
		pw.close ();
	    }
	}
	catch (IOException e)
	{
	}
	pauseProgram ();
    }


    /*
    Method that serves as a exit window before the program closes
    */
    public void goodbye ()
    {
	title ();
	c.println ();
	c.println ();
	printCentre ("This program was made by Max Li");
	pauseProgram ();
	c.close ();
    }


    /*
    Main method where the program flow is controlled
    The program first starts at the splashScreen where it proceeds to go to the menu
    All the methods are encased in an infinite loop, except goodbye
    If the user chooses to exit, the while loop is broken and the goodbye method is run at the end
    */
    public static void main (String[] args) throws IOException
    {
	Jeopardy j = new Jeopardy ();
	j.splashScreen ();
	while (true)
	{
	    j.mainMenu ();
	    if (j.choice.equals ("1"))
		j.instructions ();
	    else if (j.choice.equals ("3"))
		j.highScore ();
	    else if (j.choice.equals ("4"))
		j.clearHighScores ();
	    else if (j.choice.equals ("2"))
	    {
		j.userInput ();
		j.initializeTable ();
		do
		{
		    j.display ();
		    j.question ();
		}
		while (!j.isLastQuestion);
		j.finalJeopardy ();
		j.winner ();
	    }
	    else
		break;
	}
	j.goodbye ();
    }
}


