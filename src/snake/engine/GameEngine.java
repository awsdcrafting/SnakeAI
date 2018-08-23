package snake.engine;
import ai.impl.AI;
import ai.impl.SnakeFitnessFunction;
import main.Settings;
import org.neuroph.contrib.neat.gen.operations.FitnessFunction;
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
	private long loopTime = 50;

	private int turn;
	private int score;
	private int turnSinceLastApple;

	public GameEngine(long loopTime)
	{
		this.loopTime = loopTime;
	}

	public GameEngine()
	{
	}

	public int getTurn()
	{
		return turn;
	}
	public int getScore()
	{
		return score;
	}

	public void setSpielfeld(Spielfeld spielfeld)
	{
		this.spielfeld = spielfeld;
	}
	public void setGui(Gui gui)
	{
		this.gui = gui;
	}

	public AI getAi()
	{
		return ai;
	}
	public void setAi(AI ai)
	{
		this.ai = ai;
	}

	public long getLoopTime()
	{
		return loopTime;
	}
	public void setLoopTime(long loopTime)
	{
		this.loopTime = loopTime;
	}

	public void pause()
	{
		this.running = false;
	}

	public void resume()
	{
		this.running = true;
		run();
	}

	public void stop()
	{
		this.running = false;
		this.snakeAlive = false;
	}

	public void startRun()
	{
		turn = 1;
		score = 0;
		turnSinceLastApple = 0;
		snakeAlive = true;
		run();
	}

	public void run()
	{
		while (running && snakeAlive)
		{
			long time = System.currentTimeMillis();
			if (ai != null)
			{
				if (Settings.debugOutput)
				{
					System.out.println("Ai turn: " + turn);
				}
				turn++;
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
				break;
			case WEST:
				fieldState = spielfeld.getState(--headX, headY);
				break;
			case NORTH:
				fieldState = spielfeld.getState(headX, --headY);
				break;
			case SOUTH:
				fieldState = spielfeld.getState(headX, ++headY);
				break;
			}
			if (willSurvive)
			{
				spielfeld.updateSnake(fieldState == Spielfeld.state.APPLE);
			} else
			{
				snakeAlive = false;
			}
			turnSinceLastApple++;
			if (fieldState == Spielfeld.state.APPLE)
			{
				spielfeld.placeApple();
				score++;
				turnSinceLastApple = 0;
			}
			if (turnSinceLastApple > (5625 + 562.5 * score))
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
					if (Settings.debugOutput)
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
			System.out.println("Snake survived " + turn + " turns and achieved a score of " + score + ".");
			System.out.println("This equals a fitness of: " + SnakeFitnessFunction.getFitness(turn, score) + ".");

		}
	}

}
