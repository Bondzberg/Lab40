import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javafx.event.EventHandler;

public class OuterSpace extends Canvas implements KeyListener, Runnable
{

	private Ship ship;
	private Alien alienOne;
	private Alien alienTwo;
	private boolean playerShots;

    private AlienHorde horde;
	private Bullets shots;
	private int simple;

	private String input;

	private boolean[] keys;
	private BufferedImage back;

	public OuterSpace()
	{
		setBackground(Color.black);

		keys = new boolean[5];

		//instantiate other instance variables
		//Ship, Alien

		this.addKeyListener(this);
		new Thread(this).start();
		horde = new AlienHorde(0);
		setVisible(true);
		ship = new Ship(350,350,50,50,1);
		alienOne = new Alien(200,100);
		alienTwo = new Alien(300,100);
		horde.add(alienOne);
		horde.add(alienTwo);
        horde.add(new Alien(300,0));
        horde.add(new Alien(400,0));
        horde.add(new Alien(500,0));
        horde.add(new Alien(600,0));
        horde.add(new Alien(700,0));
        horde.add(new Alien(0,100));
        horde.add(new Alien(400,0));
        horde.add(new Alien(500,0));
        horde.add(new Alien(700,0));
        horde.add(new Alien(600,0));
		shots = new Bullets();
		playerShots=true;
		simple = 120;
		input =JOptionPane.showInputDialog("Enter Movement please");
	}


   public void update(Graphics window)
   {
	   paint(window);
   }

	public void paint( Graphics window )
	{
		simple++;
		//set up the double buffering to make the game animation nice and smooth
		Graphics2D twoDGraph = (Graphics2D)window;



		//take a snap shop of the current screen and save it as an image
		//that is the exact same width and height as the current screen
		if(back==null)
		   back = (BufferedImage)(createImage(getWidth(),getHeight()));

		//create a graphics reference to the back ground image
		//we will draw all changes on the background image
		Graphics graphToBack = back.createGraphics();

		graphToBack.setColor(Color.BLUE);
		graphToBack.drawString("StarFighter ", 25, 50 );
		graphToBack.setColor(Color.BLACK);
		graphToBack.fillRect(0,0,800,600);
		ship.draw(graphToBack);
		if(keys[0] == true)
		{
			ship.move("LEFT");
		}
		if(keys[1] == true)
			ship.move("RIGHT");
		if(keys[2] == true)
			ship.move("UP");
		if(keys[3] == true)
			ship.move("DOWN");
		if(keys[4]==true&&playerShots) {
			shots.add(new Ammo(ship.getX()+5, ship.getY()-5));
			playerShots=false;
		}
		else if(keys[4]==false)
			playerShots=true;

		shots.moveEmAll();
		shots.drawEmAll(graphToBack);
		shots.cleanEmUp();

		//add code to move Ship, Alien, etc.
		if(simple%580==0)
		{
			horde.moveEmAll();
		}
		int x = (int)((Math.sin(((2*Math.PI)/240)*(simple%240)))*2);
		for(Alien alan:horde.getAliens())
		{
			alan.setX(alan.getX()+x);
		}
		horde.removeDeadOnes(shots.getList());
		horde.drawEmAll(graphToBack);

		//add in collision detection to see if Bullets hit the Aliens and if Bullets hit the Ship


		twoDGraph.drawImage(back, null, 0, 0);
		if(input!=null)
		{
			interpret(input);
			input=null;
			input =JOptionPane.showInputDialog("Enter Movement please");
		}
	}

	public void interpret(String s)
	{
		s = s.toLowerCase();
		String[] strings = s.split(" ");
		try{
			if(strings[0].equals("fire"))
			{
				shots.add(new Ammo(ship.getX()+5, ship.getY()-5));
			}
			else if(strings[0].equals("move")){
				if(strings[1].equals("up"))
				{
					int speed = ship.getSpeed();
					ship.setSpeed(Integer.valueOf(strings[2]));
					ship.move("UP");
					ship.setSpeed(speed);
				}
				if(strings[1].equals("down"))
				{
					int speed = ship.getSpeed();
					ship.setSpeed(Integer.valueOf(strings[2]));
					ship.move("DOWN");
					ship.setSpeed(speed);
				}
				if(strings[1].equals("left"))
				{
					int speed = ship.getSpeed();
					ship.setSpeed(Integer.valueOf(strings[2]));
					ship.move("LEFT");
					ship.setSpeed(speed);
				}
				if(strings[1].equals("right"))
				{
					int speed = ship.getSpeed();
					ship.setSpeed(Integer.valueOf(strings[2]));
					ship.move("RIGHT");
					ship.setSpeed(speed);
				}
				else{

				}
			}
			else{

			}
		}catch(Exception e)
		{

		}

	}

	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			keys[0] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			keys[1] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			keys[2] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			keys[3] = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			keys[4] = true;
		}
		repaint();
	}

	public void keyReleased(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			keys[0] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			keys[1] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP)
		{
			keys[2] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			keys[3] = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			keys[4] = false;
		}
		repaint();
	}

	public void keyTyped(KeyEvent e)
	{
      //no code needed here
	}



	public void run()
   {
   	try
   	{
   		while(true)
   		{
   		   Thread.currentThread().sleep(5);
            repaint();
         }
      }catch(Exception e)
      {
      }
  	}

  	public Boolean isIntersecting(Moveable x,Moveable y)
	{
		int x1 = x.getX();
		int x2 = x.getX()+x.getWidth();
		int y1 = x.getY();
		int y2 = x.getY()+x.getHeight();
		if(x1<=y.getX()&&y.getX()<=x2)
			return true;
		else if(y1<=y.getY()&&y.getY()<=y2)
			return true;
		else if(y1<=y.getY()+y.getHeight()&&y.getY()+y.getHeight()<=y2)
			return true;
		else if(x1<=y.getX()+y.getWidth()&&y.getX()+y.getWidth()<=x2)
			return true;
		return false;
	}
}

