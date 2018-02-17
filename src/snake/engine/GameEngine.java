package snake.engine;
import ai.AI;
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
	AI ai;

	public void setSpielfeld(Spielfeld spielfeld)
	{
		this.spielfeld = spielfeld;
	}
	public void setGui(Gui gui)
	{
		this.gui = gui;
	}
	public void setAi(AI ai){
		this.ai = ai;
	}
	private boolean snakeAlive = true;
	private boolean running = true;
	private boolean paused;
	private long loopTime = 50;

	public void run()
	{
		int zug = 1;
		while (running && snakeAlive)
		{
			long time = System.currentTimeMillis();
			if(ai!=null){
				System.out.println("AI zug: " + zug++);
				ai.zug();
			}
			//doing things
			int headX = spielfeld.getHeadX();
			int headY = spielfeld.getHeadY();
			Spielfeld.state fieldState = Spielfeld.state.EMPTY;
			boolean willSurvive = !spielfeld.willDie(spielfeld.getMoveDirection());
			switch (spielfeld.getMoveDirection())
			{
			case EAST:
				fieldState = spielfeld.getState(++headX, headY);
				if (willSurvive)
				{
					spielfeld.updateSnake(fieldState == Spielfeld.state.APPLE);
				} else
				{
					snakeAlive = false;
				}
				break;
			case WEST:
				fieldState = spielfeld.getState(--headX, headY);
				if (willSurvive)
				{
					spielfeld.updateSnake(fieldState == Spielfeld.state.APPLE);
				} else
				{
					snakeAlive = false;
				}
				break;
			case NORTH:
				fieldState = spielfeld.getState(headX, --headY);
				if (willSurvive)
				{
					spielfeld.updateSnake(fieldState == Spielfeld.state.APPLE);
				} else
				{
					snakeAlive = false;
				}
				break;
			case SOUTH:
				fieldState = spielfeld.getState(headX, ++headY);
				if (willSurvive)
				{
					spielfeld.updateSnake(fieldState == Spielfeld.state.APPLE);
				} else
				{
					snakeAlive = false;
				}
				break;
			}
			if(fieldState == Spielfeld.state.APPLE){
				spielfeld.placeApple();
			}
			gui.repaint();
			try
			{
				//idling
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
