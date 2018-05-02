package ai.impl;
import snake.spielfeld.Node;
import snake.spielfeld.Spielfeld;

import java.util.ArrayList;
import java.util.Collections;
/**
 * Created by scisneromam on 23.02.2018.
 */
public class SnakePathFindingBot extends AI
{

	public SnakePathFindingBot(Spielfeld spielfeld)
	{
		super(spielfeld);
		baseName = "SnakePathFindingBot";
		name = baseName;
	}
	@Override
	public void save()
	{

	}

	@Override
	public void zug()
	{
		boolean worked = pathfinding(spielfeld.getAppleX(), spielfeld.getAppleY(), false);
		Node p = null;
		if (worked)
		{
			if (path.size() > 0)
			{
				p = path.remove(path.size() - 1);
				if (path.size() > 0)
				{
					while (p.x == spielfeld.getHeadX() && p.y == spielfeld.getHeadY() && path.size() > 0)
					{
						p = path.remove(path.size() - 1);
					}
				}

			}
		} else
		{
			System.out.println("Pathfinding didnt work");
			p = decide(spielfeld.getMoveDirection());
		}

		int dx = p.x - spielfeld.getHeadX();
		int dy = p.y - spielfeld.getHeadY();

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

		Spielfeld.direction left = Spielfeld.direction.EAST;
		Spielfeld.direction forward = Spielfeld.direction.EAST;
		Spielfeld.direction right = Spielfeld.direction.EAST;
		switch (spielfeld.getMoveDirection())
		{
		case NORTH:
			left = Spielfeld.direction.WEST;
			forward = Spielfeld.direction.NORTH;
			right = Spielfeld.direction.EAST;
			break;
		case SOUTH:
			left = Spielfeld.direction.EAST;
			forward = Spielfeld.direction.SOUTH;
			right = Spielfeld.direction.WEST;
			break;
		case EAST:
			left = Spielfeld.direction.NORTH;
			forward = Spielfeld.direction.EAST;
			right = Spielfeld.direction.SOUTH;
			break;
		case WEST:
			left = Spielfeld.direction.SOUTH;
			forward = Spielfeld.direction.WEST;
			right = Spielfeld.direction.NORTH;
			break;
		}

		String out = "";
		boolean found = false;

		int tries = 0;
		while (spielfeld.willDie(spielfeld.getHeadX(), spielfeld.getHeadY(), choice))
		{
			if (choice == left)
			{
				choice = forward;
			} else if (choice == forward)
			{
				choice = right;
			} else
			{
				choice = left;
			}
			tries++;
			if (tries > 3)
			{
				break;
			}
		}

		if (choice == left)
		{
			out = "left";
			found = true;
		}
		if (choice == forward)
		{
			out = "forward";
			found = true;
		}
		if (choice == right)
		{
			out = "right";
			found = true;
		}
		if (!found)
		{
			System.out.println("error! trying to go backward");
		}

		System.out.println("Choice: " + out + " - " + choice);
		spielfeld.setMoveDirection(choice);
	}
	@Override
	public void reset()
	{

	}

	ArrayList<Node> openList = new ArrayList<>();
	ArrayList<Node> closedList = new ArrayList<>();
	ArrayList<Node> path = new ArrayList<>();

	/**
	 * A* pathfinding
	 */
	private boolean pathfinding(int endX, int endY, boolean ignorePassable)
	{
		System.out.println("pathfinding");
		spielfeld.resetGrid();
		openList = new ArrayList<>();
		closedList = new ArrayList<>();
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

			expandNode(currentNode, end, ignorePassable);

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
		return (moveDir == Spielfeld.direction.NORTH && testDir == Spielfeld.direction.SOUTH) || (moveDir == Spielfeld.direction.EAST && testDir == Spielfeld.direction.WEST) || (
				moveDir == Spielfeld.direction.SOUTH && testDir == Spielfeld.direction.NORTH) || (moveDir == Spielfeld.direction.WEST && testDir == Spielfeld.direction.EAST);
	}

	private void expandNode(Node currentNode, Node end, boolean ignorePassable)
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

	private Node fill()
	{
		return null;
	}

	private Node decide(Spielfeld.direction moveDirection)
	{

		Node outputNode = spielfeld.getNode(spielfeld.getHeadX(), spielfeld.getHeadY());

		Spielfeld.direction left = Spielfeld.direction.EAST;
		Spielfeld.direction forward = Spielfeld.direction.EAST;
		Spielfeld.direction right = Spielfeld.direction.EAST;

		switch (moveDirection)
		{
		case NORTH:
			left = Spielfeld.direction.WEST;
			forward = Spielfeld.direction.NORTH;
			right = Spielfeld.direction.EAST;
			break;
		case SOUTH:
			left = Spielfeld.direction.EAST;
			forward = Spielfeld.direction.SOUTH;
			right = Spielfeld.direction.WEST;
			break;
		case EAST:
			left = Spielfeld.direction.NORTH;
			forward = Spielfeld.direction.EAST;
			right = Spielfeld.direction.SOUTH;
			break;
		case WEST:
			left = Spielfeld.direction.SOUTH;
			forward = Spielfeld.direction.WEST;
			right = Spielfeld.direction.NORTH;
			break;
		}

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
		return i > 0 && i < 77;
	}

	public boolean floodFillPath(Spielfeld.direction moveDirection)
	{
		//determine boundaries
		Spielfeld.direction left = Spielfeld.direction.EAST;
		Spielfeld.direction forward = Spielfeld.direction.EAST;
		Spielfeld.direction right = Spielfeld.direction.EAST;

		switch (moveDirection)
		{
		case NORTH:
			left = Spielfeld.direction.WEST;
			forward = Spielfeld.direction.NORTH;
			right = Spielfeld.direction.EAST;
			break;
		case SOUTH:
			left = Spielfeld.direction.EAST;
			forward = Spielfeld.direction.SOUTH;
			right = Spielfeld.direction.WEST;
			break;
		case EAST:
			left = Spielfeld.direction.NORTH;
			forward = Spielfeld.direction.EAST;
			right = Spielfeld.direction.SOUTH;
			break;
		case WEST:
			left = Spielfeld.direction.SOUTH;
			forward = Spielfeld.direction.WEST;
			right = Spielfeld.direction.NORTH;
			break;
		}
		Spielfeld.direction choice = forward;

		int x = spielfeld.getHeadX();
		int y = spielfeld.getHeadY();
		if (!spielfeld.willDie(x, y, forward))
		{
			choice = forward;
		} else if (!spielfeld.willDie(x, y, left))
		{
			choice = left;
		} else if (!spielfeld.willDie(x, y, right))
		{
			choice = right;
		} else
		{
			//trapped
			return false;
		}

		return false;
	}
}