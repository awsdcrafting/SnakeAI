package snake.spielfeld;
import snake.engine.GameEngine;
import utils.RandomUtils;

import java.awt.*;
import java.text.BreakIterator;

/**
 * Created by scisneromam on 15.02.2018.
 */
public class Spielfeld
{

	private GameEngine gameEngine;

	public GameEngine getGameEngine()
	{
		return gameEngine;
	}
	public void setGameEngine(GameEngine gameEngine)
	{
		this.gameEngine = gameEngine;
	}

	private state[][] field;
	private Node[][] grid;
	private int width;
	private int height;
	private boolean firstTurn = true;

	private long seed = 0;
	private long goal = 0;
	private long population = 0;
	private long number = 0;
	private String mode = "";
	private double lastFitness = 0;

	public long getSeed()
	{
		return seed;
	}
	public void setSeed(long seed)
	{
		this.seed = seed;
	}
	public long getGoal()
	{
		return goal;
	}
	public void setGoal(long generation)
	{
		this.goal = generation;
	}
	public long getPopulation()
	{
		return population;
	}
	public void setPopulation(long population)
	{
		this.population = population;
	}
	public long getNumber()
	{
		return number;
	}
	public void setNumber(long number)
	{
		this.number = number;
	}
	public String getMode()
	{
		return mode;
	}
	public void setMode(String mode)
	{
		this.mode = mode;
	}
	public double getLastFitness()
	{
		return lastFitness;
	}
	public void setLastFitness(double lastFitness)
	{
		this.lastFitness = lastFitness;
	}

	private boolean log = true;

	private int headX;
	private int headY;
	private int tailX;
	private int tailY;

	private Color snakeBodyColor = Color.WHITE;
	private Color snakeHeadColor = Color.BLUE;

	private int appleX;
	private int appleY;

	private int emptyPlaces;

	private direction moveDirection;

	public void setLog(boolean log)
	{
		this.log = log;
	}

	public int getWidth()
	{
		return width;
	}
	public int getHeight()
	{
		return height;
	}

	public Color getSnakeBodyColor()
	{
		return snakeBodyColor;
	}
	public void setSnakeBodyColor(Color snakeBodyColor)
	{
		this.snakeBodyColor = snakeBodyColor;
	}
	public Color getSnakeHeadColor()
	{
		return snakeHeadColor;
	}
	public void setSnakeHeadColor(Color snakeHeadColor)
	{
		this.snakeHeadColor = snakeHeadColor;
	}
	public state getState(int x, int y)
	{
		return field[x][y];
	}

	public Node getNode(int x, int y)
	{
		return grid[x][y];
	}

	public Node getNodeIn(direction moveDirection, int step)
	{

		int[] xy = getXYIn(moveDirection, step);
		return grid[xy[0]][xy[1]];

	}

	public state getStateIn(direction moveDirection, int step)
	{
		int[] xy = getXYIn(moveDirection, step);
		return field[xy[0]][xy[1]];

	}

	public int[] getXYIn(direction moveDirection, int step)
	{
		return getXYInFrom(moveDirection, step, headX, headY);
	}

	public int[] getXYInFrom(direction moveDirection, int step, int startX, int startY)
	{
		switch (moveDirection)
		{
		case NORTH:
			return new int[]{startX, startY - step};
		case WEST:
			return new int[]{startX - step, startY};
		case SOUTH:
			return new int[]{startX, startY + step};
		case EAST:
			return new int[]{startX + step, startY};
		}
		return new int[]{startX, startY};
	}

	public void resetGrid()
	{
		for (int x = 0; x < 77; x++)
		{
			for (int y = 0; y < 77; y++)
			{
				grid[x][y].g = 0;
				grid[x][y].h = 0;
				grid[x][y].f = 0;
				grid[x][y].previous = null;
				if (isDeadly(field[x][y]))
				{
					grid[x][y].passable = false;
				} else
				{
					grid[x][y].passable = true;
				}
			}
		}
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

	public boolean validMoveDirection(direction moveDirection)
	{
		switch (field[headX][headY])
		{
		case HEADSOUTH:
			if (moveDirection != direction.NORTH)
			{
				return true;
			}
			break;
		case HEADNORTH:
			if (moveDirection != direction.SOUTH)
			{
				return true;
			}
			break;
		case HEADWEST:
			if (moveDirection != direction.EAST)
			{
				return true;
			}
			break;
		case HEADEAST:
			if (moveDirection != direction.WEST)
			{
				return true;
			}
			break;
		}
		return false;
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
	public int getTailX()
	{
		return tailX;
	}
	public int getTailY()
	{
		return tailY;
	}
	public int getAppleX()
	{
		return appleX;
	}
	public int getAppleY()
	{
		return appleY;
	}

	public Spielfeld(int width, int height)
	{
		this(width, height, 0);
	}

	public Spielfeld(int width, int height, long seed)
	{
		this.seed = seed;
		this.width = width + 2;
		this.height = height + 2;
		if (seed != 0)
		{
			RandomUtils.setSeed(seed);
		}
		setUp(this.width, this.height);
	}

	public void setUp(int width, int height)
	{
		if (seed != 0)
		{
			RandomUtils.setSeed(seed);
		}
		field = new state[width][height];
		grid = new Node[width][height];
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				if (x == 0 || y == 0 || y == height - 1 || x == width - 1)
				{
					field[x][y] = state.WALL;
					grid[x][y] = new Node(x, y, false, false);
				} else
				{
					field[x][y] = state.EMPTY;
					grid[x][y] = new Node(x, y, true, false);
				}
			}
		}
		connectNodes();
		int x = 35;
		int y = 35;
		field[x][y] = state.HEADEAST;
		grid[x][y].passable = false;
		for (int i = 1; i <= 3; i++)
		{
			field[x - i][y] = state.BODYHORIZONTAL;
			grid[x - i][y].passable = false;
		}
		headX = x;
		headY = y;
		tailX = x - 4;
		tailY = y;
		field[tailX][tailY] = state.TAILEAST;
		moveDirection = direction.EAST;
		emptyPlaces = 75 * 75 - 5;
		freeRows = 75;
		freeSpacesInRow = new int[77];
		freeSpacesInRow[0] = 0;
		for (int i = 1; i < 35; i++)
		{
			freeSpacesInRow[i] = 75;
		}
		freeSpacesInRow[35] = 70;
		for (int i = 36; i < 76; i++)
		{
			freeSpacesInRow[i] = 75;
		}
		freeSpacesInRow[76] = 0;

		firstTurn = true;
		placeApple();

		/*field[2][2] = state.BODYNORTHEAST;
		field[2][4] = state.BODYNORTHWEST;
		field[2][6] = state.BODYSOUTHEAST;
		field[2][8] = state.BODYSOUTHWEST;*/

	}

	private void connectNodes()
	{
		for (int x = 0; x < 77; x++)
		{
			for (int y = 0; y < 77; y++)
			{
				boolean north = true;
				boolean east = true;
				boolean south = true;
				boolean west = true;
				if (x == 0)
				{
					west = false;
				}
				if (x == 76)
				{
					east = false;
				}
				if (y == 0)
				{
					north = false;
				}
				if (y == 76)
				{
					south = false;
				}
				Node nodeNorth = null;
				Node nodeEast = null;
				Node nodeSouth = null;
				Node nodeWest = null;
				if (north)
				{
					nodeNorth = grid[x][y - 1];
				}
				if (east)
				{
					nodeEast = grid[x + 1][y];
				}
				if (south)
				{
					nodeSouth = grid[x][y + 1];
				}
				if (west)
				{
					nodeWest = grid[x - 1][y];
				}
				grid[x][y].north = nodeNorth;
				grid[x][y].east = nodeEast;
				grid[x][y].south = nodeSouth;
				grid[x][y].west = nodeWest;
			}
		}
	}

	public void placeApple()
	{
		int x = 0;//column
		int y = 0;//row
		int yRand = 55;
		if (!firstTurn)
		{
			yRand = RandomUtils.randomInt(freeRows);
		}
		y = freeRowToRealRow(yRand);
		int xRand = 55;
		if (!firstTurn)
		{
			xRand = RandomUtils.randomInt(freeSpacesInRow[y]);
		}
		x = freeSpaceInRowToRealSpaceInRow(y, xRand);
		appleX = x;
		appleY = y;
		field[x][y] = state.APPLE;
		grid[x][y].passable = true;
		grid[x][y].appleField = true;
		firstTurn = false;
	}

	private int freeRows;
	private int[] freeSpacesInRow;

	private int freeRowToRealRow(int freeRowY)
	{
		int realY = 0;
		int fakeY = 0;
		for (int i = 0; i < 77; i++)
		{
			if (freeSpacesInRow[realY] == 0)
			{
				realY++;
				continue;
			}
			if (fakeY == freeRowY)
				break;
			fakeY++;
			realY++;
		}
		return realY;
	}

	private int freeSpaceInRowToRealSpaceInRow(int rowY, int freeSpaceX)
	{
		int realX = 0;
		int fakeX = 0;
		for (int i = 0; i < 77; i++)
		{
			if (field[realX][rowY] != state.EMPTY)
			{
				realX++;
				continue;
			}
			if (fakeX == freeSpaceX)
				break;
			fakeX++;
			realX++;
		}
		return realX;
	}

	public void placeAppleLegacy()
	{
		if ((emptyPlaces--) > 0)
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
			appleX = x;
			appleY = y;
			field[x][y] = state.APPLE;
			if (log)
			{
				System.out.println("Placed the apple after " + tries + " tries");
			}
		}
	}

	public int[] getDistanceToApple()
	{
		int x = appleX - headX;
		int y = appleY - headY;

		return new int[]{x, y};
	}

	public int getDistance(state desiredState, direction moveDirection)
	{
		int end = 0;
		int x = headX;
		int y = headY;
		try
		{
			while (field[x][y] != desiredState)
			{
				end++;
				switch (moveDirection)
				{
				case NORTH:
					y--;
					break;
				case SOUTH:
					y++;
					break;
				case EAST:
					x++;
					break;
				case WEST:
					x--;
					break;
				}
			}
		} catch (ArrayIndexOutOfBoundsException e)
		{
			//nicht vorhanden
			return -999;
		}

		return end;
	}

	public boolean willDie(direction moveDirection)
	{
		return willDie(headX, headY, moveDirection);
	}

	public boolean willDie(int x, int y, direction moveDirection)
	{
		int moveX = x;
		int moveY = y;

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
		if (moveX < 0 || moveX > 76 || moveY < 0 || moveY > 76)
		{
			return false;
		}
		state s = field[moveX][moveY];
		return isDeadly(s);
	}

	public boolean isDeadly(state s)
	{
		return !(s == state.APPLE || s == state.EMPTY || s == state.TAILEAST || s == state.TAILNORTH || s == state.TAILSOUTH || s == state.TAILWEST);
	}

	public int[] posInMoveDirection(direction moveDirection)
	{
		return posInMoveDirection(headX, headY, moveDirection);
	}

	public int[] posInMoveDirection(int x, int y, direction moveDirection)
	{
		int moveX = x;
		int moveY = y;

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
		return new int[]{moveX, moveY};
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
			grid[headX][headY].passable = false;
			if ((--freeSpacesInRow[headY]) == 0)
			{
				freeRows--;
			}
		} else
		{
			int prevTailY = tailY;
			state prevTailState = field[tailX][tailY];
			field[tailX][tailY] = state.EMPTY;
			grid[tailX][tailY].passable = true;
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
			grid[tailX][tailY].passable = true;
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
			grid[headX][headY].passable = false;
			if (headY == prevTailY)
			{
				return;
			}
			if ((--freeSpacesInRow[headY]) == 0)
			{
				freeRows--;
			}
			if ((++freeSpacesInRow[prevTailY]) == 1)
			{
				freeRows++;
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
		WEST,
	}

}


