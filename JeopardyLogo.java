/*
Max Li
Ms Krasteva
2018-1-16
This class animates the intro scene
*/

//imports
import java.awt.*;
import hsa.Console;
import java.lang.*;

public class JeopardyLogo extends Thread
{
    private Console c;
    int delay = 5;

    public void animation ()
    {
	//color declaration
	Color blue = new Color (0, 76, 153);
	for (int i = 0 ; i < 500 ; i++)
	{
	    //erase
	    c.setColor (Color.white);
	    c.fillRect (i - 386, 195, 411, 211);
	    //draw
	    c.setColor (Color.black);
	    c.fillRect (i - 385, 195, 410, 210);
	    c.setColor (blue);
	    c.fillRect (i - 380, 200, 400, 200);
	    c.setColor (Color.white);
	    c.setFont (new Font ("Courier", 0, 70));
	    c.drawString ("JEOPARDY!", i - 380, 300);
	    try
	    {
		sleep (delay);
	    }
	    catch (Exception e)
	    {
	    }
	}
    }


    public JeopardyLogo (Console con)
    {
	c = con;
    }


    public void run ()
    {
	animation ();
    }
}
