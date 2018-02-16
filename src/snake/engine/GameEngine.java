package snake.engine;
import snake.gui.Gui;
import snake.spielfeld.Spielfeld;

import javax.swing.*;
/**
 * Created by scisneromam on 16.02.2018.
 */
public class GameEngine
{
	Spielfeld spielfeld;
	Gui gui;

	public void setSpielfeld(Spielfeld spielfeld)
	{
		this.spielfeld = spielfeld;
	}
	public void setGui(Gui gui)
	{
		this.gui = gui;
	}
	private boolean snakeAlive = true;
	private boolean running = true;
	private boolean paused;
	private long loopTime = 150;

	public void run()
	{
		while (running && snakeAlive)
		{
			long time = System.currentTimeMillis();
			//doing things
			int headX = spielfeld.getHeadX();
			int headY = spielfeld.getHeadY();
			System.out.println(headX + " " + headY + " before");
			Spielfeld.state fieldState;
			switch (spielfeld.getMoveDirection())
			{
			case EAST:
				fieldState = spielfeld.getState(++headX, headY);
				if (fieldState == Spielfeld.state.APPLE || fieldState == Spielfeld.state.EMPTY)
				{
					spielfeld.updateSnake(fieldState == Spielfeld.state.APPLE);
				} else
				{
					snakeAlive = false;
				}
				break;
			case WEST:
				fieldState = spielfeld.getState(--headX, headY);
				if (fieldState == Spielfeld.state.APPLE || fieldState == Spielfeld.state.EMPTY)
				{
					spielfeld.updateSnake(fieldState == Spielfeld.state.APPLE);
				} else
				{
					snakeAlive = false;
				}
				break;
			case NORTH:
				fieldState = spielfeld.getState(headX, --headY);
				if (fieldState == Spielfeld.state.APPLE || fieldState == Spielfeld.state.EMPTY)
				{
					spielfeld.updateSnake(fieldState == Spielfeld.state.APPLE);
				} else
				{
					snakeAlive = false;
				}
				break;
			case SOUTH:
				fieldState = spielfeld.getState(headX, ++headY);
				if (fieldState == Spielfeld.state.APPLE || fieldState == Spielfeld.state.EMPTY)
				{
					spielfeld.updateSnake(fieldState == Spielfeld.state.APPLE);
				} else
				{
					snakeAlive = false;
				}
				break;
			}
			System.out.println(headX + " " + headY + " after");
			try
			{
				//idling
				System.out.println("GE:" + (System.currentTimeMillis() - time));
				long waitTime = loopTime - (System.currentTimeMillis() - time);
				if (waitTime > 0)
				{
					Thread.sleep(waitTime);
				} else
				{
					System.out.println("We are " + (-waitTime) + "ms behind!");
				}
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			System.out.println("Repainting gui");
			gui.repaint();

		}
		if (!snakeAlive)
		{
			System.out.println("Snake died!");
			/*for (int x = 0; x < 77; x++)
			{
				for (int y = 0; y < 77; y++)
				{
					System.out.println(spielfeld.getState(x, y) + "@x:" + x + " y:" + y);
				}
			}*/
		}
	}

}
