package snake.spielfeld;
import utils.RandomUtils;

/**
 * Created by scisneromam on 15.02.2018.
 */
public class Spielfeld
{

	private state[][] field;
	private int width;
	private int height;

	private int headX;
	private int headY;
	private int tailX;
	private int tailY;

	private direction moveDirection;

	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}

	public state getState(int x, int y)
	{
		return field[x][y];
	}

	public void setMoveDirection(direction moveDirection)
	{
		switch (field[headX][headY])
		{
		case HEADSOUTH:
			if (moveDirection != direction.NORTH)
			{
				this.moveDirection = moveDirection;
			}
			break;
		case HEADNORTH:
			if (moveDirection != direction.SOUTH)
			{
				this.moveDirection = moveDirection;
			}
			break;
		case HEADWEST:
			if (moveDirection != direction.EAST)
			{
				this.moveDirection = moveDirection;
			}
			break;
		case HEADEAST:
			if (moveDirection != direction.WEST)
			{
				this.moveDirection = moveDirection;
			}
			break;
		}
	}

	public direction getMoveDirection()
	{
		return moveDirection;
	}

	public int getHeadX()
	{
		return headX;
	}
	public int getHeadY()
	{
		return headY;
	}
	public Spielfeld(int width, int height)
	{
		this.width = width + 2;
		this.height = height + 2;
		setUp(this.width, this.height);
	}

	public void setUp(int width, int height)
	{
		field = new state[width][height];
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				if (x == 0 || y == 0 || y == height - 1 || x == width - 1)
				{
					field[x][y] = state.WALL;
				} else
				{
					field[x][y] = state.EMPTY;
				}
			}
		}

		int x = RandomUtils.randomInt(width / 4, width - width / 4);
		int y = RandomUtils.randomInt(height / 4, height - height / 4);
		field[x][y] = state.HEADEAST;
		for (int i = 1; i < 3; i++)
		{
			field[x - i][y] = state.BODYHORIZONTAL;
		}
		headX = x;
		headY = y;
		tailX = x - 3;
		tailY = y;
		field[tailX][tailY] = state.TAILEAST;
		moveDirection = direction.EAST;
		placeApple();

		/*field[2][2] = state.BODYNORTHEAST;
		field[2][4] = state.BODYNORTHWEST;
		field[2][6] = state.BODYSOUTHEAST;
		field[2][8] = state.BODYSOUTHWEST;*/

	}

	public void placeApple()
	{
		int width = field.length;
		int height = field[0].length;
		int x = RandomUtils.randomInt(1, width);
		int y = RandomUtils.randomInt(1, height);
		int tries = 1;
		while (field[x][y] != state.EMPTY)
		{
			x = RandomUtils.randomInt(1, width);
			y = RandomUtils.randomInt(1, height);
			tries++;
		}
		field[x][y] = state.APPLE;
		System.out.println("Placed the apple after " + tries + " tries");
	}

	public boolean willDie(direction moveDirection)
	{
		int moveX = headX;
		int moveY = headY;

		switch (moveDirection)
		{
		case EAST:
			moveX++;
			break;
		case WEST:
			moveX--;
			break;
		case NORTH:
			moveY--;
			break;
		case SOUTH:
			moveY++;
			break;
		}
		state s = field[moveX][moveY];
		return !(s == state.APPLE || s == state.EMPTY || s == state.TAILEAST || s == state.TAILNORTH || s == state.TAILSOUTH || s == state.TAILWEST);
	}

	public void updateSnake(boolean apple)
	{
		state headDir = getState(headX, headY);
		//recode needed
		//todo
		if (apple)
		{
			switch (headDir)
			{
			case HEADNORTH:
				switch (moveDirection)
				{
				case EAST:
					field[headX][headY] = state.BODYNORTHEAST;
					field[++headX][headY] = state.HEADEAST;
					break;
				case WEST:
					field[headX][headY] = state.BODYNORTHWEST;
					field[--headX][headY] = state.HEADWEST;
					break;
				case NORTH:
					field[headX][headY] = state.BODYVERTICAL;
					field[headX][--headY] = state.HEADNORTH;
					break;
				}
				break;
			case HEADEAST:
				switch (moveDirection)
				{
				case EAST:
					field[headX][headY] = state.BODYHORIZONTAL;
					field[++headX][headY] = state.HEADEAST;
					break;
				case NORTH:
					field[headX][headY] = state.BODYSOUTHWEST;
					field[headX][--headY] = state.HEADNORTH;
					break;
				case SOUTH:
					field[headX][headY] = state.BODYNORTHWEST;
					field[headX][++headY] = state.HEADSOUTH;
					break;
				}
				break;
			case HEADSOUTH:
				switch (moveDirection)
				{
				case EAST:
					field[headX][headY] = state.BODYSOUTHEAST;
					field[++headX][headY] = state.HEADEAST;
					break;
				case WEST:
					field[headX][headY] = state.BODYSOUTHWEST;
					field[--headX][headY] = state.HEADWEST;
					break;
				case SOUTH:
					field[headX][headY] = state.BODYVERTICAL;
					field[headX][++headY] = state.HEADSOUTH;
					break;
				}
				break;
			case HEADWEST:
				switch (moveDirection)
				{
				case WEST:
					field[headX][headY] = state.BODYHORIZONTAL;
					field[--headX][headY] = state.HEADWEST;
					break;
				case NORTH:
					field[headX][headY] = state.BODYSOUTHEAST;
					field[headX][--headY] = state.HEADNORTH;
					break;
				case SOUTH:
					field[headX][headY] = state.BODYNORTHEAST;
					field[headX][++headY] = state.HEADSOUTH;
					break;
				}
				break;
			}
		} else
		{
			state prevTailState = field[tailX][tailY];
			field[tailX][tailY] = state.EMPTY;
			switch (prevTailState)
			{
			case TAILSOUTH:
				switch (field[tailX][++tailY])
				{
				case BODYVERTICAL:
					field[tailX][tailY] = state.TAILSOUTH;
					break;
				case BODYSOUTHEAST:
					field[tailX][tailY] = state.TAILEAST;
					break;
				case BODYSOUTHWEST:
					field[tailX][tailY] = state.TAILWEST;
					break;
				}
				break;
			case TAILNORTH:
				switch (field[tailX][--tailY])
				{
				case BODYVERTICAL:
					field[tailX][tailY] = state.TAILNORTH;
					break;
				case BODYNORTHEAST:
					field[tailX][tailY] = state.TAILEAST;
					break;
				case BODYNORTHWEST:
					field[tailX][tailY] = state.TAILWEST;
					break;
				}
				break;
			case TAILWEST:
				switch (field[--tailX][tailY])
				{
				case BODYHORIZONTAL:
					field[tailX][tailY] = state.TAILWEST;
					break;
				case BODYNORTHEAST:
					field[tailX][tailY] = state.TAILSOUTH;
					break;
				case BODYSOUTHEAST:
					field[tailX][tailY] = state.TAILNORTH;
					break;
				}
				break;
			case TAILEAST:
				switch (field[++tailX][tailY])
				{
				case BODYHORIZONTAL:
					field[tailX][tailY] = state.TAILEAST;
					break;
				case BODYNORTHWEST:
					field[tailX][tailY] = state.TAILSOUTH;
					break;
				case BODYSOUTHWEST:
					field[tailX][tailY] = state.TAILNORTH;
					break;
				}
				break;
			}
			switch (headDir)
			{
			case HEADNORTH:
				switch (moveDirection)
				{
				case EAST:

					field[headX][headY] = state.BODYNORTHEAST;
					field[++headX][headY] = state.HEADEAST;
					break;
				case WEST:
					field[headX][headY] = state.BODYNORTHWEST;
					field[--headX][headY] = state.HEADWEST;
					break;
				case NORTH:
					field[headX][headY] = state.BODYVERTICAL;
					field[headX][--headY] = state.HEADNORTH;
					break;
				}
				break;
			case HEADEAST:
				switch (moveDirection)
				{
				case EAST:
					field[headX][headY] = state.BODYHORIZONTAL;
					field[++headX][headY] = state.HEADEAST;
					break;
				case NORTH:
					field[headX][headY] = state.BODYSOUTHWEST;
					field[headX][--headY] = state.HEADNORTH;
					break;
				case SOUTH:
					field[headX][headY] = state.BODYNORTHWEST;
					field[headX][++headY] = state.HEADSOUTH;
					break;
				}
				break;
			case HEADSOUTH:
				switch (moveDirection)
				{
				case EAST:
					field[headX][headY] = state.BODYSOUTHEAST;
					field[++headX][headY] = state.HEADEAST;
					break;
				case WEST:
					field[headX][headY] = state.BODYSOUTHWEST;
					field[--headX][headY] = state.HEADWEST;
					break;
				case SOUTH:
					field[headX][headY] = state.BODYVERTICAL;
					field[headX][++headY] = state.HEADSOUTH;
					break;
				}
				break;
			case HEADWEST:
				switch (moveDirection)
				{
				case WEST:
					field[headX][headY] = state.BODYHORIZONTAL;
					field[--headX][headY] = state.HEADWEST;
					break;
				case NORTH:
					field[headX][headY] = state.BODYSOUTHEAST;
					field[headX][--headY] = state.HEADNORTH;
					break;
				case SOUTH:
					field[headX][headY] = state.BODYNORTHEAST;
					field[headX][++headY] = state.HEADSOUTH;
					break;
				}
				break;
			}
		}
	}

	public enum state
	{
		EMPTY,
		//nix
		BODYHORIZONTAL,
		//links rechts
		BODYVERTICAL,
		//oben unten
		BODYNORTHEAST,
		//oben rechts
		BODYNORTHWEST,
		//oben links
		BODYSOUTHEAST,
		//unten rechts
		BODYSOUTHWEST,
		//unten links
		//HEAD: richtung in die die schlange guckt
		HEADNORTH,
		HEADEAST,
		HEADSOUTH,
		HEADWEST,
		//TAIL: richtung in die der körper ist
		TAILNORTH,
		TAILEAST,
		TAILSOUTH,
		TAILWEST,
		APPLE,
		WALL
	}

	public enum direction
	{
		NORTH,
		SOUTH,
		EAST,
		WEST
	}

}


