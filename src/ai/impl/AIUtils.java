package ai.impl;
import snake.spielfeld.Node;
import snake.spielfeld.Spielfeld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * Project: SnakeAI
 * Created by scisneromam on 21.08.2018.
 *
 * @author scisneromam
 * ---------------------------------------------------------------------
 * Copyright © 2018 | scisneromam | All rights reserved.
 */
public class AIUtils
{

	Spielfeld spielfeld;

	public AIUtils(Spielfeld spielfeld)
	{
		this.spielfeld = spielfeld;
	}

	public static Spielfeld.direction[] determineDirections(Spielfeld.direction moveDirection)
	{
		Spielfeld.direction left;
		Spielfeld.direction forward;
		Spielfeld.direction right;
		Spielfeld.direction backward;
		switch (moveDirection)
		{
		case NORTH:
			left = Spielfeld.direction.WEST;
			forward = Spielfeld.direction.NORTH;
			right = Spielfeld.direction.EAST;
			backward = Spielfeld.direction.SOUTH;
			break;
		case SOUTH:
			left = Spielfeld.direction.EAST;
			forward = Spielfeld.direction.SOUTH;
			right = Spielfeld.direction.WEST;
			backward = Spielfeld.direction.NORTH;
			break;
		case EAST:
			left = Spielfeld.direction.NORTH;
			forward = Spielfeld.direction.EAST;
			right = Spielfeld.direction.SOUTH;
			backward = Spielfeld.direction.WEST;
			break;
		case WEST:
			left = Spielfeld.direction.SOUTH;
			forward = Spielfeld.direction.WEST;
			right = Spielfeld.direction.NORTH;
			backward = Spielfeld.direction.EAST;
			break;
		default:
			left = Spielfeld.direction.EAST;
			forward = Spielfeld.direction.EAST;
			right = Spielfeld.direction.EAST;
			backward = Spielfeld.direction.EAST;
		}
		return new Spielfeld.direction[]{left, forward, right, backward};
	}

	ArrayList<Node> path = new ArrayList<>();

	public ArrayList<Node> getPath()
	{
		return path;
	}

	/**
	 * A* pathfinding
	 */
	public boolean pathfinding(int endX, int endY, boolean ignorePassable)
	{
		System.out.println("pathfinding");
		spielfeld.resetGrid();
		ArrayList<Node> openList = new ArrayList<>();
		ArrayList<Node> closedList = new ArrayList<>();
		path = new ArrayList<>();
		Node start = spielfeld.getNode(spielfeld.getHeadX(), spielfeld.getHeadY());
		Node end = spielfeld.getNode(endX, endY);
		openList.add(start);
		while (!openList.isEmpty())
		{
			Collections.sort(openList);
			Node currentNode = openList.remove(0);
			if (currentNode.x == end.x && currentNode.y == end.y)
			{
				Node temp = currentNode;
				path.add(temp);
				while (temp.previous != null)
				{
					temp = temp.previous;
					path.add(temp);
				}
				return true;
			}

			closedList.add(currentNode);

			expandNode(currentNode, end, ignorePassable, openList, closedList);

			if (openList.size() == 0)
			{
				if (currentNode.x == end.x && currentNode.y == end.y)
				{
					Node temp = currentNode;
					path.add(temp);
					while (temp.previous != null)
					{
						temp = temp.previous;
						path.add(temp);
					}
					return false;
				}
			}

		}
		return false;
	}

	private boolean isOpposite(Spielfeld.direction moveDir, Spielfeld.direction testDir)
	{
		return (moveDir == Spielfeld.direction.NORTH && testDir == Spielfeld.direction.SOUTH) || (moveDir == Spielfeld.direction.EAST && testDir == Spielfeld.direction.WEST) ||
			   (moveDir == Spielfeld.direction.SOUTH && testDir == Spielfeld.direction.NORTH) || (moveDir == Spielfeld.direction.WEST && testDir == Spielfeld.direction.EAST);
	}

	private void expandNode(Node currentNode, Node end, boolean ignorePassable, ArrayList<Node> openList, ArrayList<Node> closedList)
	{

		if (currentNode.north != null)
		{
			if (!closedList.contains(currentNode.north) && currentNode.north.passable || (ignorePassable && !isOpposite(spielfeld.getMoveDirection(), Spielfeld.direction.NORTH)))
			{

				int tempG = currentNode.g + 1;
				if (!openList.contains(currentNode.north))
				{
					currentNode.north.g = tempG;
					currentNode.north.h = heuristic(currentNode.north.x, currentNode.north.y, end.x, end.y);
					currentNode.north.f = currentNode.north.g + currentNode.north.h;
					currentNode.north.previous = currentNode;
					openList.add(currentNode.north);
				} else
				{
					if (tempG < currentNode.north.g)
					{
						currentNode.north.g = tempG;
						currentNode.north.f = currentNode.north.g + currentNode.north.h;
						currentNode.north.previous = currentNode;
					}
				}

			}
		}
		if (currentNode.east != null)
		{
			if (!closedList.contains(currentNode.east) && currentNode.east.passable || (ignorePassable && !isOpposite(spielfeld.getMoveDirection(), Spielfeld.direction.EAST)))
			{

				int tempG = currentNode.g + 1;
				if (!openList.contains(currentNode.east))
				{
					currentNode.east.g = tempG;
					currentNode.east.h = heuristic(currentNode.east.x, currentNode.east.y, end.x, end.y);
					currentNode.east.f = currentNode.east.g + currentNode.east.h;
					currentNode.east.previous = currentNode;
					openList.add(currentNode.east);
				} else
				{
					if (tempG < currentNode.east.g)
					{
						currentNode.east.g = tempG;
						currentNode.east.f = currentNode.east.g + currentNode.east.h;
						currentNode.east.previous = currentNode;
					}
				}

			}
		}
		if (currentNode.south != null)
		{
			if (!closedList.contains(currentNode.south) && currentNode.south.passable || (ignorePassable && !isOpposite(spielfeld.getMoveDirection(), Spielfeld.direction.SOUTH)))
			{

				int tempG = currentNode.g + 1;
				if (!openList.contains(currentNode.south))
				{
					currentNode.south.g = tempG;
					currentNode.south.h = heuristic(currentNode.south.x, currentNode.south.y, end.x, end.y);
					currentNode.south.f = currentNode.south.g + currentNode.south.h;
					currentNode.south.previous = currentNode;
					openList.add(currentNode.south);
				} else
				{
					if (tempG < currentNode.south.g)
					{
						currentNode.south.g = tempG;
						currentNode.south.f = currentNode.south.g + currentNode.south.h;
						currentNode.south.previous = currentNode;
					}
				}

			}
		}
		if (currentNode.west != null)
		{
			if (!closedList.contains(currentNode.west) && currentNode.west.passable || (ignorePassable && !isOpposite(spielfeld.getMoveDirection(), Spielfeld.direction.WEST)))
			{

				int tempG = currentNode.g + 1;
				if (!openList.contains(currentNode.west))
				{
					currentNode.west.g = tempG;
					currentNode.west.h = heuristic(currentNode.west.x, currentNode.west.y, end.x, end.y);
					currentNode.west.f = currentNode.west.g + currentNode.west.h;
					currentNode.west.previous = currentNode;
					openList.add(currentNode.west);
				} else
				{
					if (tempG < currentNode.west.g)
					{
						currentNode.west.g = tempG;
						currentNode.west.f = currentNode.west.g + currentNode.west.h;
						currentNode.west.previous = currentNode;
					}
				}

			}
		}

	}

	private int heuristic(int startX, int startY, int endX, int endY)
	{

		int x = Math.abs(startX - endX);
		int y = Math.abs(startY - endY);
		return x + y;

	}

	public Node decide(Spielfeld.direction moveDirection)
	{

		Node outputNode = spielfeld.getNode(spielfeld.getHeadX(), spielfeld.getHeadY());

		Spielfeld.direction[] directions = AIUtils.determineDirections(moveDirection);

		Spielfeld.direction left = directions[0];
		Spielfeld.direction forward = directions[1];
		Spielfeld.direction right = directions[2];

		//11 nodes 4 links 4 rechts 3 vorne

		Node[] leftNodes = new Node[4];
		Node[] rightNodes = new Node[4];
		Node[] forwardNodes = new Node[3];

		int x = spielfeld.getHeadX();
		int y = spielfeld.getHeadY();
		int forwardX = x;
		int forwardY = y;
		int leftX = x;
		int leftY = y;
		int rightX = x;
		int rightY = y;

		switch (left)
		{
		case NORTH:
			if (isValid(y - 2))
			{
				for (int i = 0; i < 4; i++)
				{
					if (isValid(x + i - 1))
					{
						leftNodes[i] = spielfeld.getNode(x + i - 1, y - 2);
					}
				}
			}
			leftY = y - 1;
			break;
		case SOUTH:
			if (isValid(y + 2))
			{
				for (int i = 0; i < 4; i++)
				{
					if (isValid(x + i - 1))
					{
						leftNodes[i] = spielfeld.getNode(x + i - 1, y + 2);
					}
				}
			}
			leftY = y + 1;
			break;
		case EAST:
			if (isValid(x + 2))
			{
				for (int i = 0; i < 4; i++)
				{
					if (isValid(y + i - 1))
					{
						leftNodes[i] = spielfeld.getNode(x + 2, y + i - 1);
					}
				}
			}
			leftX = x + 1;
			break;
		case WEST:
			if (isValid(x - 2))
			{
				for (int i = 0; i < 4; i++)
				{
					if (isValid(y + i - 1))
					{
						leftNodes[i] = spielfeld.getNode(x + 2, y + i - 1);
					}
				}
			}
			leftX = x - 1;
			break;
		}

		switch (right)
		{
		case NORTH:
			if (isValid(y - 2))
			{
				for (int i = 0; i < 4; i++)
				{
					if (isValid(x + i - 1))
					{
						rightNodes[i] = spielfeld.getNode(x + i - 1, y - 2);
					}
				}
			}
			rightY = y - 1;
			break;
		case SOUTH:
			if (isValid(y + 2))
			{
				for (int i = 0; i < 4; i++)
				{
					if (isValid(x + i - 1))
					{
						rightNodes[i] = spielfeld.getNode(x + i - 1, y - 2);
					}
				}
			}
			rightY = y + 1;
			break;
		case EAST:
			if (isValid(x + 2))
			{
				for (int i = 0; i < 4; i++)
				{
					if (isValid(y + i - 1))
					{
						rightNodes[i] = spielfeld.getNode(x + 2, y + i - 1);
					}
				}
			}
			rightX = x + 1;
			break;
		case WEST:
			if (isValid(x - 2))
			{
				for (int i = 0; i < 4; i++)
				{
					if (isValid(y + i - 1))
					{
						rightNodes[i] = spielfeld.getNode(x - 2, y + i - 1);
					}
				}
			}
			rightX = x - 1;
			break;
		}

		switch (forward)
		{
		case NORTH:
			if (isValid(y - 2))
			{
				for (int i = 0; i < 3; i++)
				{
					if (isValid(x + i - 1))
					{
						forwardNodes[i] = spielfeld.getNode(x + i - 1, y - 2);
					}
				}
			}
			forwardY = y - 1;
			break;
		case SOUTH:
			if (isValid(y + 2))
			{
				for (int i = 0; i < 3; i++)
				{
					if (isValid(x + i - 1))
					{
						forwardNodes[i] = spielfeld.getNode(x + i - 1, y - 2);
					}
				}
			}
			forwardY = y + 1;
			break;
		case EAST:
			if (isValid(x + 2))
			{
				for (int i = 0; i < 3; i++)
				{
					if (isValid(y + i - 1))
					{
						forwardNodes[i] = spielfeld.getNode(x + 2, y + i - 1);
					}
				}
			}
			forwardX = x + 1;
			break;
		case WEST:
			if (isValid(x - 2))
			{
				for (int i = 0; i < 3; i++)
				{
					if (isValid(y + i - 1))
					{
						forwardNodes[i] = spielfeld.getNode(x + 2, y + i - 1);
					}
				}
			}
			forwardX = x - 1;
			break;
		}

		boolean forwardAllowed = true;
		boolean leftAllowed = true;
		boolean rightAllowed = true;
		for (int i = 0; i < 4; i++)
		{
			if (leftNodes[i] != null && !leftNodes[i].passable)
			{
				leftAllowed = false;
			}
		}
		for (int i = 0; i < 4; i++)
		{
			if (rightNodes[i] != null && !rightNodes[i].passable)
			{
				rightAllowed = false;
			}
		}
		for (int i = 0; i < 3; i++)
		{
			if (forwardNodes[i] != null && !forwardNodes[i].passable)
			{
				forwardAllowed = false;
			}
		}
		if (spielfeld.willDie(x, y, forward) || spielfeld.isDeadly(spielfeld.getState(forwardX, forwardY)))
		{
			forwardAllowed = false;
		}
		if (spielfeld.willDie(x, y, right) || spielfeld.isDeadly(spielfeld.getState(rightX, rightY)))
		{
			rightAllowed = false;
		}
		if (spielfeld.willDie(x, y, left) || spielfeld.isDeadly(spielfeld.getState(leftX, leftY)))
		{
			leftAllowed = false;
		}
		boolean doMore = true;
		if (forwardAllowed && doMore)
		{
			doMore = false;
			outputNode = spielfeld.getNode(forwardX, forwardY);
		}
		if (leftAllowed && doMore)
		{
			doMore = false;
			outputNode = spielfeld.getNode(leftX, leftY);
		}
		if (rightAllowed && doMore)
		{
			doMore = false;
			outputNode = spielfeld.getNode(rightX, rightY);
		}

		return outputNode;
	}

	public boolean isValid(int i)
	{
		return i > 0 && i < spielfeld.getWidth();
	}

	private Choice lastChoice = Choice.FORWARD;

	public Node fill(Spielfeld.direction moveDirection)
	{

		Spielfeld.direction[] directions = AIUtils.determineDirections(moveDirection);

		Spielfeld.direction left = directions[0];
		Spielfeld.direction forward = directions[1];
		Spielfeld.direction right = directions[2];

		Spielfeld.direction prioTurn = left;
		Spielfeld.direction otherTurn = right;

		Spielfeld.direction choice = null;

		if (spielfeld.getNodeIn(left, 1).x != spielfeld.getHeadX())
		{

			if (spielfeld.getHeadX() <= spielfeld.getWidth() / 2)
			{
				prioTurn = right;
				otherTurn = left;
			}

		} else
		{
			if (spielfeld.getHeadY() <= spielfeld.getHeight() / 2)
			{
				prioTurn = right;
				otherTurn = left;
			}
		}

		switch (lastChoice)
		{
		case FORWARD:
			if (isSave(forward))
			{
				choice = forward;
				lastChoice = Choice.FORWARD;
			} else if (isSave(prioTurn))
			{
				choice = prioTurn;
				if (prioTurn == left)
				{
					lastChoice = Choice.LEFT;
				} else
				{
					lastChoice = Choice.RIGHT;
				}
			} else
			{
				choice = otherTurn;
				if (otherTurn == left)
				{
					lastChoice = Choice.LEFT;
				} else
				{
					lastChoice = Choice.RIGHT;
				}
			}
			break;
		case LEFT:
			if (isSave(left))
			{
				choice = left;
				lastChoice = Choice.LEFT;
			} else if (isSave(forward))
			{
				choice = forward;
				lastChoice = Choice.FORWARD;
			} else
			{
				choice = right;
				lastChoice = Choice.RIGHT;
			}
			break;
		case RIGHT:
			if (isSave(right))
			{
				choice = right;
				lastChoice = Choice.RIGHT;
			} else if (isSave(forward))
			{
				choice = forward;
				lastChoice = Choice.FORWARD;
			} else
			{
				choice = left;
				lastChoice = Choice.LEFT;
			}
			break;
		}
		if (choice == null)
		{
			return decide(moveDirection);
		} else
		{
			return spielfeld.getNodeIn(choice, 1);
		}
	}

	public boolean isSave(Spielfeld.direction moveDirection)
	{
		if (spielfeld.isDeadly(spielfeld.getStateIn(moveDirection, 1)))
		{
			return false;
		}
		if (spielfeld.isDeadly(spielfeld.getStateIn(moveDirection, 2)))
		{
			return false;
		}
		return !isATrap(spielfeld.getNodeIn(moveDirection, 2), moveDirection);
	}

	public boolean isATrap(Node startingNode, Spielfeld.direction moveDirection)
	{
		if (deadlySurroundAmount(startingNode, moveDirection) == 3)
		{
			return true;
		}

		if (deadlySurroundAmount(startingNode, moveDirection) == 2)
		{

			int[] northXY = spielfeld.getXYInFrom(Spielfeld.direction.NORTH, 1, startingNode.x, startingNode.y);
			Spielfeld.state northState = (isValid(northXY[0]) && isValid(northXY[1])) ? spielfeld.getState(northXY[0], northXY[1]) : Spielfeld.state.WALL;
			int[] eastXY = spielfeld.getXYInFrom(Spielfeld.direction.EAST, 1, startingNode.x, startingNode.y);
			Spielfeld.state eastState = (isValid(eastXY[0]) && isValid(eastXY[1])) ? spielfeld.getState(eastXY[0], eastXY[1]) : Spielfeld.state.WALL;
			int[] southXY = spielfeld.getXYInFrom(Spielfeld.direction.SOUTH, 1, startingNode.x, startingNode.y);
			Spielfeld.state southState = (isValid(southXY[0]) && isValid(southXY[1])) ? spielfeld.getState(southXY[0], southXY[1]) : Spielfeld.state.WALL;
			int[] westXY = spielfeld.getXYInFrom(Spielfeld.direction.WEST, 1, startingNode.x, startingNode.y);
			Spielfeld.state westState = (isValid(westXY[0]) && isValid(westXY[1])) ? spielfeld.getState(westXY[0], westXY[1]) : Spielfeld.state.WALL;

			if (!spielfeld.isDeadly(northState) && !isOpposite(moveDirection, Spielfeld.direction.NORTH))
			{
				return isATrap(spielfeld.getNode(northXY[0], northXY[1]), Spielfeld.direction.NORTH);
			}
			if (!spielfeld.isDeadly(eastState) && !isOpposite(moveDirection, Spielfeld.direction.EAST))
			{
				return isATrap(spielfeld.getNode(eastXY[0], eastXY[1]), Spielfeld.direction.EAST);
			}
			if (!spielfeld.isDeadly(southState) && !isOpposite(moveDirection, Spielfeld.direction.SOUTH))
			{
				return isATrap(spielfeld.getNode(southXY[0], southXY[1]), Spielfeld.direction.SOUTH);
			}
			if (!spielfeld.isDeadly(westState) && !isOpposite(moveDirection, Spielfeld.direction.WEST))
			{
				return isATrap(spielfeld.getNode(westXY[0], westXY[1]), Spielfeld.direction.WEST);
			}
		}

		return false;
	}

	public int deadlySurroundAmount(Node startingNode, Spielfeld.direction moveDirection)
	{

		AtomicInteger amount = new AtomicInteger();
		int[] northXY = spielfeld.getXYInFrom(Spielfeld.direction.NORTH, 1, startingNode.x, startingNode.y);
		Spielfeld.state northState = (isValid(northXY[0]) && isValid(northXY[1])) ? spielfeld.getState(northXY[0], northXY[1]) : Spielfeld.state.WALL;
		int[] eastXY = spielfeld.getXYInFrom(Spielfeld.direction.EAST, 1, startingNode.x, startingNode.y);
		Spielfeld.state eastState = (isValid(eastXY[0]) && isValid(eastXY[1])) ? spielfeld.getState(eastXY[0], eastXY[1]) : Spielfeld.state.WALL;
		int[] southXY = spielfeld.getXYInFrom(Spielfeld.direction.SOUTH, 1, startingNode.x, startingNode.y);
		Spielfeld.state southState = (isValid(southXY[0]) && isValid(southXY[1])) ? spielfeld.getState(southXY[0], southXY[1]) : Spielfeld.state.WALL;
		int[] westXY = spielfeld.getXYInFrom(Spielfeld.direction.WEST, 1, startingNode.x, startingNode.y);
		Spielfeld.state westState = (isValid(westXY[0]) && isValid(westXY[1])) ? spielfeld.getState(westXY[0], westXY[1]) : Spielfeld.state.WALL;

		HashMap<Spielfeld.direction, Spielfeld.state> stateHashmap = new HashMap<>();
		stateHashmap.put(Spielfeld.direction.NORTH, northState);
		stateHashmap.put(Spielfeld.direction.EAST, eastState);
		stateHashmap.put(Spielfeld.direction.SOUTH, southState);
		stateHashmap.put(Spielfeld.direction.WEST, westState);

		stateHashmap.forEach((direction, state) -> {
			if (!isOpposite(moveDirection, direction) && spielfeld.isDeadly(state))
			{
				amount.getAndIncrement();
			}
		});
		return amount.get();
	}

	public enum Choice
	{
		LEFT,
		FORWARD,
		RIGHT
	}

}
