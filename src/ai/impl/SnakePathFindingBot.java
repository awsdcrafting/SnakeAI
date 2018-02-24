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
	//alle 5 zuege path neu finden
	int turn = 5;

	@Override
	public void zug()
	{
		System.out.println(turn);
		if (turn == 5)
		{
			turn = 1;
			pathfinding();
		}
		Node p = path.remove(path.size() - 1);
		turn++;
		int dx = p.x - spielfeld.getHeadX();
		int dy = p.y - spielfeld.getHeadY();
		System.out.println("x: "+p.x + " " + spielfeld.getHeadX() + " y: " + p.y + " " + spielfeld.getHeadY());

		Spielfeld.direction choice = Spielfeld.direction.EAST;
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
	private boolean pathfinding()
	{
		System.out.println("pathfinding");
		spielfeld.resetGrid();
		openList = new ArrayList<>();
		closedList = new ArrayList<>();
		path = new ArrayList<>();
		Node start = spielfeld.getNode(spielfeld.getHeadX(), spielfeld.getHeadY());
		Node end = spielfeld.getNode(spielfeld.getAppleX(), spielfeld.getAppleY());
		openList.add(start);
		Collections.sort(openList);
		while (!openList.isEmpty())
		{
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
				path.remove(0);
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

}

