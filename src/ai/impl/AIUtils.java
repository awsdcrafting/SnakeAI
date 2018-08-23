package ai.impl;
import snake.spielfeld.Node;
import snake.spielfeld.Spielfeld;

import java.net.NoRouteToHostException;
import java.security.acl.LastOwnerException;
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

	public Spielfeld.direction determineChoice(Node newNode)
	{
		int dx = newNode.x - spielfeld.getHeadX();
		int dy = newNode.y - spielfeld.getHeadY();

		Spielfeld.direction choice = spielfeld.getMoveDirection();
		if (dx == -1)
		{
			choice = Spielfeld.direction.WEST;
		}
		if (dx == 1)
		{
			choice = Spielfeld.direction.EAST;
		}
		if (dy == -1)
		{
			choice = Spielfeld.direction.NORTH;
		}
		if (dy == 1)
		{
			choice = Spielfeld.direction.SOUTH;
		}
		return choice;
	}

	ArrayList<Node> path = new ArrayList<>();

	public ArrayList<Node> getPath()
	{
		return path;
	}

	/**
	 * A* pathfinding
	 */
	public boolean pathfinding(Node start, Node end, boolean ignorePassable)
	{
		System.out.println("pathfinding");
		spielfeld.resetGrid();
		ArrayList<Node> openList = new ArrayList<>();
		ArrayList<Node> closedList = new ArrayList<>();
		path = new ArrayList<>();
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

		Node currentNode = spielfeld.getNode(spielfeld.getHeadX(), spielfeld.getHeadY());

		Spielfeld.direction[] directions = AIUtils.determineDirections(moveDirection);

		Spielfeld.direction left = directions[0];
		Spielfeld.direction forward = directions[1];
		Spielfeld.direction right = directions[2];

		Spielfeld.direction prioTurn = left;
		Spielfeld.direction otherTurn = right;

		Spielfeld.direction choice = null;

		System.out.println("LastChoice: " + lastChoice);

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
			} else if (isSave(otherTurn))
			{
				choice = otherTurn;
				if (otherTurn == left)
				{
					lastChoice = Choice.LEFT;
				} else
				{
					lastChoice = Choice.RIGHT;
				}
			} else
			{
				System.out.println("Didnt found save");
				if (!isATrap(forward))
				{
					choice = forward;
					lastChoice = Choice.FORWARD;
				} else if (!isATrap(prioTurn))
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
			} else if (isSave(right))
			{
				choice = right;
				lastChoice = Choice.RIGHT;
			} else
			{
				if (!isATrap(left))
				{
					choice = left;
					lastChoice = Choice.FORWARD;
				} else if (!isATrap(forward))
				{
					choice = forward;
					lastChoice = Choice.FORWARD;
				} else
				{
					choice = right;
					lastChoice = Choice.RIGHT;

				}
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
			} else if (isSave(left))
			{
				choice = left;
				lastChoice = Choice.LEFT;
			} else
			{
				System.out.println("Didnt found save");
				if (!isATrap(right))
				{
					choice = right;
					lastChoice = Choice.RIGHT;
				} else if (!isATrap(forward))
				{
					choice = forward;
					lastChoice = Choice.FORWARD;
				} else
				{
					choice = left;
					lastChoice = Choice.LEFT;

				}
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
		return !isATrapPathFinding(moveDirection) && !isATrapRecursive(spielfeld.getNodeIn(moveDirection, 1), moveDirection);
	}

	public boolean isATrap(Spielfeld.direction moveDirection)
	{
		if (isATrapRecursive(spielfeld.getNodeIn(moveDirection, 1), moveDirection))
		{
			return true;
		}
		return isATrapPathFinding(moveDirection);
	}

	private boolean isATrapPathFinding(Spielfeld.direction moveDirection)
	{

		Node headNode = spielfeld.getNode(spielfeld.getHeadX(), spielfeld.getHeadY());
		Node movingToNode = spielfeld.getNodeIn(moveDirection, 1);

		if (deadlySurroundAmount(headNode, moveDirection) == 3)
		{
			return true;
		}
		if (spielfeld.isDeadly(spielfeld.getState(movingToNode.x, movingToNode.y)))
		{
			return true;
		}
		if (deadlySurroundAmount(movingToNode, moveDirection) == 3)
		{
			return true;
		}
		if (deadlySurroundAmount(movingToNode, moveDirection) == 2 && deadlySurroundAmount(headNode, spielfeld.getMoveDirection()) < 2)
		{
			int[] northXY = spielfeld.getXYInFrom(Spielfeld.direction.NORTH, 1, movingToNode.x, movingToNode.y);
			Spielfeld.state northState = (isValid(northXY[0]) && isValid(northXY[1])) ? spielfeld.getState(northXY[0], northXY[1]) : Spielfeld.state.WALL;
			int[] eastXY = spielfeld.getXYInFrom(Spielfeld.direction.EAST, 1, movingToNode.x, movingToNode.y);
			Spielfeld.state eastState = (isValid(eastXY[0]) && isValid(eastXY[1])) ? spielfeld.getState(eastXY[0], eastXY[1]) : Spielfeld.state.WALL;
			int[] southXY = spielfeld.getXYInFrom(Spielfeld.direction.SOUTH, 1, movingToNode.x, movingToNode.y);
			Spielfeld.state southState = (isValid(southXY[0]) && isValid(southXY[1])) ? spielfeld.getState(southXY[0], southXY[1]) : Spielfeld.state.WALL;
			int[] westXY = spielfeld.getXYInFrom(Spielfeld.direction.WEST, 1, movingToNode.x, movingToNode.y);
			Spielfeld.state westState = (isValid(westXY[0]) && isValid(westXY[1])) ? spielfeld.getState(westXY[0], westXY[1]) : Spielfeld.state.WALL;

			Node startingNode = null;
			int[] startNorthXY = spielfeld.getXYInFrom(Spielfeld.direction.NORTH, 1, headNode.x, headNode.y);
			Spielfeld.state startNorthState = (isValid(startNorthXY[0]) && isValid(startNorthXY[1])) ? spielfeld.getState(startNorthXY[0], startNorthXY[1]) : Spielfeld.state.WALL;
			int[] startEastXY = spielfeld.getXYInFrom(Spielfeld.direction.EAST, 1, headNode.x, headNode.y);
			Spielfeld.state startEastState = (isValid(startEastXY[0]) && isValid(startEastXY[1])) ? spielfeld.getState(startEastXY[0], startEastXY[1]) : Spielfeld.state.WALL;
			int[] startSouthXY = spielfeld.getXYInFrom(Spielfeld.direction.SOUTH, 1, headNode.x, headNode.y);
			Spielfeld.state startSouthState = (isValid(startSouthXY[0]) && isValid(startSouthXY[1])) ? spielfeld.getState(startSouthXY[0], startSouthXY[1]) : Spielfeld.state.WALL;
			int[] startWestXY = spielfeld.getXYInFrom(Spielfeld.direction.WEST, 1, headNode.x, headNode.y);
			Spielfeld.state startWestState = (isValid(startWestXY[0]) && isValid(startWestXY[1])) ? spielfeld.getState(startWestXY[0], startWestXY[1]) : Spielfeld.state.WALL;

			if (!spielfeld.isDeadly(startNorthState) && Spielfeld.direction.NORTH != moveDirection)
			{
				startingNode = spielfeld.getNode(startNorthXY[0], startNorthXY[1]);
			}
			if (!spielfeld.isDeadly(startEastState) && Spielfeld.direction.EAST != moveDirection)
			{
				startingNode = spielfeld.getNode(startEastXY[0], startEastXY[1]);
			}
			if (!spielfeld.isDeadly(startSouthState) && Spielfeld.direction.SOUTH != moveDirection)
			{
				startingNode = spielfeld.getNode(startSouthXY[0], startSouthXY[1]);
			}
			if (!spielfeld.isDeadly(startWestState) && Spielfeld.direction.WEST != moveDirection)
			{
				startingNode = spielfeld.getNode(startWestXY[0], startWestXY[1]);
			}

			if (startingNode == null)
			{
				return true;
			}

			if (!spielfeld.isDeadly(northState) && !isOpposite(moveDirection, Spielfeld.direction.NORTH) && Spielfeld.direction.NORTH != moveDirection)
			{
				return !pathfinding(startingNode, spielfeld.getNode(northXY[0], northXY[1]), false);
			}
			if (!spielfeld.isDeadly(eastState) && !isOpposite(moveDirection, Spielfeld.direction.EAST) && Spielfeld.direction.EAST != moveDirection)
			{
				return !pathfinding(startingNode, spielfeld.getNode(eastXY[0], eastXY[1]), false);
			}
			if (!spielfeld.isDeadly(southState) && !isOpposite(moveDirection, Spielfeld.direction.SOUTH) && Spielfeld.direction.SOUTH != moveDirection)
			{
				return !pathfinding(startingNode, spielfeld.getNode(southXY[0], southXY[1]), false);
			}
			if (!spielfeld.isDeadly(westState) && !isOpposite(moveDirection, Spielfeld.direction.WEST) && Spielfeld.direction.WEST != moveDirection)
			{
				return !pathfinding(startingNode, spielfeld.getNode(westXY[0], westXY[1]), false);
			}
		}

		return false;
	}

	private boolean isATrapRecursive(Node startingNode, Spielfeld.direction moveDirection)
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
				return isATrapRecursive(spielfeld.getNode(northXY[0], northXY[1]), Spielfeld.direction.NORTH);
			}
			if (!spielfeld.isDeadly(eastState) && !isOpposite(moveDirection, Spielfeld.direction.EAST))
			{
				return isATrapRecursive(spielfeld.getNode(eastXY[0], eastXY[1]), Spielfeld.direction.EAST);
			}
			if (!spielfeld.isDeadly(southState) && !isOpposite(moveDirection, Spielfeld.direction.SOUTH))
			{
				return isATrapRecursive(spielfeld.getNode(southXY[0], southXY[1]), Spielfeld.direction.SOUTH);
			}
			if (!spielfeld.isDeadly(westState) && !isOpposite(moveDirection, Spielfeld.direction.WEST))
			{
				return isATrapRecursive(spielfeld.getNode(westXY[0], westXY[1]), Spielfeld.direction.WEST);
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
