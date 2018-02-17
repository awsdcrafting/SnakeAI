package snake.engine;
import ai.impl.AI;
import snake.gui.Gui;
import snake.spielfeld.Spielfeld;
/**
 * Created by scisneromam on 16.02.2018.
 */
public class GameEngine
{
	private Spielfeld spielfeld;
	private Gui gui;
	private AI ai;

	private boolean snakeAlive = true;
	private boolean running = true;
	private boolean paused;
	private long loopTime = 50;

	private boolean log = true;

	private int turn;
	private int score;
	private int turnSinceLastApple;

	public GameEngine(long loopTime)
	{
		this.loopTime = loopTime;
	}

	public GameEngine()
	{
		this.loopTime = 50;
	}

	public int getTurn()
	{
		return turn;
	}
	public int getScore()
	{
		return score;
	}

	public void setLog(boolean log)
	{
		this.log = log;
	}

	public void setSpielfeld(Spielfeld spielfeld)
	{
		this.spielfeld = spielfeld;
	}
	public void setGui(Gui gui)
	{
		this.gui = gui;
	}
	public void setAi(AI ai)
	{
		this.ai = ai;
	}

	public void run()
	{
		turn = 1;
		score = 0;
		turnSinceLastApple = 0;
		snakeAlive = true;
		while (running && snakeAlive)
		{
			long time = System.currentTimeMillis();
			if (ai != null)
			{
				if (log)
				{
					System.out.println("Ai turn: " + turn++);
				}
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
			turnSinceLastApple++;
			if (fieldState == Spielfeld.state.APPLE)
			{
				spielfeld.placeApple();
				score++;
				turnSinceLastApple = 0;
			}
			if (turnSinceLastApple > (500 + 50 * score))
			{
				snakeAlive = false;
			}
			if (gui != null)
			{
				gui.repaint();
			}
			try
			{
				//idling
				long waitTime = loopTime - (System.currentTimeMillis() - time);
				if (waitTime > 0)
				{
					Thread.sleep(waitTime);
				} else
				{
					if (log)
					{
						System.out.println("We are " + (-waitTime) + "ms behind!");
					}
				}
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}

		}
		if (!snakeAlive)
		{
			/*for (int x = 0; x < 77; x++)
			{
				for (int y = 0; y < 77; y++)
				{
					System.out.println(spielfeld.getState(x, y) + "@x:" + x + " y:" + y);
				}
			}*/
			if (log)
			{
				System.out.println("Snake survived " + turn + " turns and achieved a score of " + score + ".");
			}
		}
	}

}
