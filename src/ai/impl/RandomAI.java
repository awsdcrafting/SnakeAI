package ai.impl;
import snake.spielfeld.Spielfeld;
import utils.RandomUtils;
/**
 * Created by scisneromam on 17.02.2018.
 */
public class RandomAI extends AI
{
	private int failSaves = 777;
	private int maxFailSaves = 777;
	private boolean infiniteFailSaves;

	public void reset()
	{
		failSaves = maxFailSaves;
	}

	public void save()
	{

	}

	public RandomAI(Spielfeld spielfeld, int maxFailSaves)
	{
		super(spielfeld);
		this.maxFailSaves = maxFailSaves;
		failSaves = maxFailSaves;
		this.name = "Random'AI'";
	}

	@Override
	public void zug()
	{
		Spielfeld.direction dir = Spielfeld.direction.SOUTH;
		boolean accepted = false;
		while (!accepted)
		{
			if (infiniteFailSaves)
			{
				failSaves = maxFailSaves;
			}
			switch (RandomUtils.randomInt(4))
			{
			case 0:
				spielfeld.getGameEngine().log("Going " + Spielfeld.direction.NORTH);
				dir = Spielfeld.direction.NORTH;
				break;
			case 1:
				spielfeld.getGameEngine().log("Going " + Spielfeld.direction.EAST);
				dir = Spielfeld.direction.EAST;
				break;
			case 2:
				spielfeld.getGameEngine().log("Going " + Spielfeld.direction.SOUTH);
				dir = Spielfeld.direction.SOUTH;
				break;
			case 3:
				spielfeld.getGameEngine().log("Going " + Spielfeld.direction.WEST);
				dir = Spielfeld.direction.WEST;
				break;
			}
			if (!spielfeld.validMoveDirection(dir))
			{
				spielfeld.getGameEngine().log(dir + " is not a valid direction retrying!");
				continue;
			}
			//failsave
			int headX = spielfeld.getHeadX();
			int headY = spielfeld.getHeadY();
			switch (dir)
			{
			case SOUTH:
				headY++;
				break;
			case NORTH:
				headY--;
				break;
			case WEST:
				headX--;
				break;
			case EAST:
				headX++;
				break;
			}
			if (spielfeld.willDie(dir))
			{
				if (failSaves > 0)
				{
					spielfeld.getGameEngine().log("Would crash into " + spielfeld.getState(headX, headY) + " using 1 of " + failSaves-- + " failsaves to avoid death retrying now");
				} else
				{
					spielfeld.getGameEngine().log("Will crash into " + spielfeld.getState(headX, headY) + " no failsave left to avoid death :(");
					accepted = true;
				}
			} else
			{
				accepted = true;
			}

		}
		spielfeld.setMoveDirection(dir);

	}

}
