package ai.impl;
import snake.spielfeld.Node;
import snake.spielfeld.Spielfeld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
		pathfinding(spielfeld.getAppleX(), spielfeld.getAppleY());
		Node p = null;
		if (path.size() > 0)
		{
			p = path.remove(path.size() - 1);
			if (path.size() > 0)
			{
				while (p.x == spielfeld.getHeadX() && p.y == spielfeld.getHeadY())
				{
					p = path.remove(path.size() - 1);
				}
			}
		} else
		{

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
	private boolean pathfinding(int endX, int endY)
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

			expandNode(currentNode, end);

		}
		return false;
	}

	private void expandNode(Node currentNode, Node end)
	{

		if (currentNode.north != null)
		{
			if (!closedList.contains(currentNode.north) && currentNode.north.passable)
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
			if (!closedList.contains(currentNode.east) && currentNode.east.passable)
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
			if (!closedList.contains(currentNode.south) && currentNode.south.passable)
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
			if (!closedList.contains(currentNode.west) && currentNode.west.passable)
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
			leftNodes[0] = spielfeld.getNode(x - 1, y - 2);
			leftNodes[1] = spielfeld.getNode(x, y - 2);
			leftNodes[2] = spielfeld.getNode(x + 1, y - 2);
			leftNodes[3] = spielfeld.getNode(x + 2, y - 2);
			leftY = y - 1;
			break;
		case SOUTH:
			leftNodes[0] = spielfeld.getNode(x - 1, y + 2);
			leftNodes[1] = spielfeld.getNode(x, y + 2);
			leftNodes[2] = spielfeld.getNode(x + 1, y + 2);
			leftNodes[3] = spielfeld.getNode(x + 2, y + 2);
			leftY = y + 1;
			break;
		case EAST:
			leftNodes[0] = spielfeld.getNode(x + 2, y - 1);
			leftNodes[1] = spielfeld.getNode(x + 2, y);
			leftNodes[2] = spielfeld.getNode(x + 2, y + 1);
			leftNodes[3] = spielfeld.getNode(x + 2, y + 2);
			leftX = x + 1;
			break;
		case WEST:
			leftNodes[0] = spielfeld.getNode(x - 2, y - 1);
			leftNodes[1] = spielfeld.getNode(x - 2, y);
			leftNodes[2] = spielfeld.getNode(x - 2, y + 1);
			leftNodes[3] = spielfeld.getNode(x - 2, y + 2);
			leftX = x - 1;
			break;
		}

		switch (right)
		{
		case NORTH:
			rightNodes[0] = spielfeld.getNode(x - 1, y - 2);
			rightNodes[1] = spielfeld.getNode(x, y - 2);
			rightNodes[2] = spielfeld.getNode(x + 1, y - 2);
			rightNodes[3] = spielfeld.getNode(x + 2, y - 2);
			rightY = y - 1;
			break;
		case SOUTH:
			rightNodes[0] = spielfeld.getNode(x - 1, y + 2);
			rightNodes[1] = spielfeld.getNode(x, y + 2);
			rightNodes[2] = spielfeld.getNode(x + 1, y + 2);
			rightNodes[3] = spielfeld.getNode(x + 2, y + 2);
			rightY = y + 1;
			break;
		case EAST:
			rightNodes[0] = spielfeld.getNode(x + 2, y - 1);
			rightNodes[1] = spielfeld.getNode(x + 2, y);
			rightNodes[2] = spielfeld.getNode(x + 2, y + 1);
			rightNodes[3] = spielfeld.getNode(x + 2, y + 2);
			rightX = x + 1;
			break;
		case WEST:
			rightNodes[0] = spielfeld.getNode(x - 2, y - 1);
			rightNodes[1] = spielfeld.getNode(x - 2, y);
			rightNodes[2] = spielfeld.getNode(x - 2, y + 1);
			rightNodes[3] = spielfeld.getNode(x - 2, y + 2);
			rightX = x - 1;
			break;
		}

		switch (forward)
		{
		case NORTH:
			forwardNodes[0] = spielfeld.getNode(x - 1, y - 2);
			forwardNodes[1] = spielfeld.getNode(x, y - 2);
			forwardNodes[2] = spielfeld.getNode(x + 1, y - 2);
			forwardY = y - 1;
			break;
		case SOUTH:
			forwardNodes[0] = spielfeld.getNode(x - 1, y + 2);
			forwardNodes[1] = spielfeld.getNode(x, y + 2);
			forwardNodes[2] = spielfeld.getNode(x + 1, y + 2);
			forwardY = y + 1;
			break;
		case EAST:
			forwardNodes[0] = spielfeld.getNode(x + 2, y - 1);
			forwardNodes[1] = spielfeld.getNode(x + 2, y);
			forwardNodes[2] = spielfeld.getNode(x + 2, y + 1);
			forwardX = x + 1;
			break;
		case WEST:
			forwardNodes[0] = spielfeld.getNode(x - 2, y - 1);
			forwardNodes[1] = spielfeld.getNode(x - 2, y);
			forwardNodes[2] = spielfeld.getNode(x - 2, y + 1);
			forwardX = x - 1;
			break;
		}

		boolean forwardAllowed = true;
		boolean leftAllowed = true;
		boolean rightAllowed = true;
		for (int i = 0; i < 4; i++)
		{
			if (!leftNodes[i].passable)
			{
				leftAllowed = false;
			}
		}
		for (int i = 0; i < 4; i++)
		{
			if (!rightNodes[i].passable)
			{
				rightAllowed = false;
			}
		}
		for (int i = 0; i < 3; i++)
		{
			if (!forwardNodes[i].passable)
			{
				forwardAllowed = false;
			}
		}
		if (spielfeld.willDie(x, y, forward))
		{
			forwardAllowed = false;
		}
		if (spielfeld.willDie(x, y, right))
		{
			rightAllowed = false;
		}
		if (spielfeld.willDie(x, y, left))
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

}