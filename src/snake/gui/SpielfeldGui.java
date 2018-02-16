package snake.gui;
import snake.spielfeld.Spielfeld;

import javax.swing.*;
import java.awt.*;
/**
 * Created by scisneromam on 15.02.2018.
 */
public class SpielfeldGui extends JPanel
{

	Spielfeld spielfeld;

	public void setSpielfeld(Spielfeld spielfeld)
	{
		this.spielfeld = spielfeld;
	}

	@Override
	public void paintComponent(Graphics g)
	{
		System.out.println("Repainting sfg");
		long startTime = System.currentTimeMillis();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.ORANGE);
		g2d.fillRect(0, 0, 770, 770);
		g2d.setColor(Color.GRAY);
		g2d.fillRect(10, 10, 750, 750);

		if (spielfeld != null)
		{

			for (int x = 1; x < spielfeld.getWidth() - 1; x++)
			{
				for (int y = 1; y < spielfeld.getHeight() - 1; y++)
				{
					paintField(x, y, g2d);
				}
			}
		}
		System.out.println("SFG:"+ (System.currentTimeMillis() - startTime));
	}

	private void paintField(int x, int y, Graphics2D g2d)
	{
		int xPos = x * 10;
		int yPos = y * 10;
		g2d.setColor(Color.BLACK);
		g2d.fillRect(xPos, yPos, 10, 10);
		g2d.setColor(Color.WHITE);
		switch (spielfeld.getState(x, y))
		{
		case APPLE:
			g2d.setColor(Color.RED);
			g2d.fillRect(xPos + 1, yPos + 1, 8, 8);
			break;
		case WALL:
			g2d.setColor(Color.ORANGE);
			g2d.fillRect(xPos, yPos, 10, 10);
			break;
		case EMPTY: //equals default
			g2d.setColor(Color.GRAY);
			g2d.fillRect(xPos, yPos, 10, 10);
			break;

		case HEADEAST:
			g2d.setColor(Color.BLUE);
			g2d.fillRect(xPos, yPos + 1, 9, 8);
			break;
		case HEADWEST:
			g2d.setColor(Color.BLUE);
			g2d.fillRect(xPos+1, yPos + 1, 9, 8);
			break;
		case HEADNORTH:
			g2d.setColor(Color.BLUE);
			g2d.fillRect(xPos + 1, yPos + 1, 8, 9);
			break;
		case HEADSOUTH:
			g2d.setColor(Color.BLUE);
			g2d.fillRect(xPos + 1, yPos, 8, 9);
			break;

		case BODYVERTICAL:
			g2d.fillRect(xPos + 1, yPos, 8, 10);
			break;
		case BODYHORIZONTAL:
			g2d.fillRect(xPos, yPos + 1, 10, 8);
			break;
		case BODYNORTHWEST:
			g2d.fillRect(xPos + 1, yPos + 1, 8, 9);
			g2d.fillRect(xPos, yPos + 1, 9, 8);
			break;
		case BODYNORTHEAST:
			g2d.fillRect(xPos + 1, yPos + 1, 8, 9);
			g2d.fillRect(xPos + 1, yPos + 1, 9, 8);
			break;
		case BODYSOUTHEAST:
			g2d.fillRect(xPos + 1, yPos, 8, 9);
			g2d.fillRect(xPos + 1, yPos + 1, 9, 8);
			break;
		case BODYSOUTHWEST:
			g2d.fillRect(xPos + 1, yPos, 8, 9);
			g2d.fillRect(xPos, yPos + 1, 9, 8);
			break;

		case TAILEAST:
			g2d.fillRect(xPos + 1, yPos + 1, 9, 8);
			break;
		case TAILWEST:
			g2d.fillRect(xPos, yPos + 1, 9, 8);
			break;
		case TAILNORTH:
			g2d.fillRect(xPos + 1, yPos, 8, 9);
			break;
		case TAILSOUTH:
			g2d.fillRect(xPos + 1, yPos + 1, 8, 9);
			break;

		default:
			g2d.setColor(Color.BLACK);
			g2d.drawRect(xPos, yPos, 10, 10);
			break;
		}
	}

}
