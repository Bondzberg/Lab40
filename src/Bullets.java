import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;

public class Bullets
{
	private List<Ammo> ammo;

	public Bullets()
	{
		ammo = new ArrayList<>();
	}

	public void add(Ammo al)
	{
		ammo.add(al);
	}

	//post - draw each Ammo
	public void drawEmAll( Graphics window )
	{
		for(Ammo armadilo:ammo)
		{
			armadilo.draw(window);
		}
	}

	public void moveEmAll()
	{
		for(Ammo armadilo:ammo)
		{
			armadilo.move("UP");
		}
	}

	public void cleanEmUp()
	{
		for(int i = ammo.size()-1;i>=0;i--)
		{
			if(ammo.get(i).getX()<0||ammo.get(i).getY()<0||ammo.get(i).getX()>800||ammo.get(i).getY()>600) {
				ammo.remove(ammo.get(i));
				
			}

		}
	}

	public List<Ammo> getList()
	{
		return ammo;
	}

	public String toString()
	{
		return "";
	}
}
