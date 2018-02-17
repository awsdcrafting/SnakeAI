package ai;
import snake.spielfeld.Spielfeld;
import utils.RandomUtils;
/**
 * Created by scisneromam on 17.02.2018.
 */
public class RandomAI implements AI
{

	Spielfeld spielfeld;
	private int failSaves = 777;

	public RandomAI(Spielfeld spielfeld)
	{
		this.spielfeld = spielfeld;
	}

	@Override
	public void zug()
	{
		Spielfeld.direction dir = Spielfeld.direction.SOUTH;
		boolean accepted = false;
		while (!accepted)
		{
			switch (RandomUtils.randomInt(4))
			{
			case 0:
				System.out.println("Going " + Spielfeld.direction.NORTH);
				dir = Spielfeld.direction.NORTH;
				break;
			case 1:
				System.out.println("Going " + Spielfeld.direction.EAST);
				dir = Spielfeld.direction.EAST;
				break;
			case 2:
				System.out.println("Going " + Spielfeld.direction.SOUTH);
				dir = Spielfeld.direction.SOUTH;
				break;
			case 3:
				System.out.println("Going " + Spielfeld.direction.WEST);
				dir = Spielfeld.direction.WEST;
				break;
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
			if (spielfeld.getState(headX, headY) != Spielfeld.state.APPLE && spielfeld.getState(headX, headY) != Spielfeld.state.EMPTY)
			{
				if (failSaves > 0)
				{
					System.out.println("Would crash into " + spielfeld.getState(headX, headY) + " using 1 of " + failSaves-- + " failsaves to avoid death retrying now");
				} else
				{
					System.out.println("Will crash into " + spielfeld.getState(headX, headY) + " no failsave left to avoid death :(");
					accepted = true;
				}
			}else{
				accepted = true;
			}

		}
		spielfeld.setMoveDirection(dir);

	}

}
