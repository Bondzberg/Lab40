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

public class OuterSpace extends Canvas implements KeyListener, Runnable
{
	private Ship ship;
	private Alien alienOne;
	private Alien alienTwo;
	private long timer;
	private int timing;
	private boolean playerShots;

	/* uncomment once you are ready for this part
	 *
   private AlienHorde horde;
   */
	private Bullets shots;


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

		setVisible(true);
		ship = new Ship(350,350,50,50,1);
		alienOne = new Alien(200,200);
		alienTwo = new Alien(200,200);
		timer = System.currentTimeMillis();
		timing = 0;
		shots = new Bullets();
		playerShots=true;
	}


   public void update(Graphics window)
   {
	   paint(window);
   }

	public void paint( Graphics window )
	{
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
		alienOne.draw(graphToBack);
		alienTwo.draw(graphToBack);
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
			shots.add(new Ammo(ship.getX(), ship.getY() + 25));
			playerShots=false;
		}
		else if(keys[4]==false)
			playerShots=true;

		for(Ammo ammo:shots.getList())
		{
			ammo.move("UP");
			ammo.draw(graphToBack);
		}
		timing = (int)(System.currentTimeMillis()-timer);
		//add code to move Ship, Alien, etc.
		if(timing>=1000)
		{
			timing = timing-1000;
			timer = System.currentTimeMillis();
			alienOne.move("DOWN");
			alienTwo.move("DOWN");

		}
		int x = (int)(Math.sin(timing)*15);
		alienOne.setX(getX()-x);
		alienTwo.setX(getX()-x);


		//add in collision detection to see if Bullets hit the Aliens and if Bullets hit the Ship


		twoDGraph.drawImage(back, null, 0, 0);
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
}

