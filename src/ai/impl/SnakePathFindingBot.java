package ai.impl;
import snake.engine.GameEngine;
import snake.spielfeld.Node;
import snake.spielfeld.Spielfeld;
import sun.java2d.loops.FillPath;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
/**
 * Created by scisneromam on 23.02.2018.
 */
public class SnakePathFindingBot extends AI
{

	private AIUtils aiUtils;

	public SnakePathFindingBot(Spielfeld spielfeld)
	{
		super(spielfeld);
		baseName = "SnakePathFindingBot";
		name = baseName;
		aiUtils = new AIUtils(spielfeld);
	}
	@Override
	public void save()
	{

	}

	ArrayList<Node> greedPath = new ArrayList<>();
	ArrayList<Node> fillPath = new ArrayList<>();
	private int greedStep = 0;
	private int maxGreedSteps = 1;

	private long defaultLoopTime = spielfeld.getGameEngine().getLoopTime();
	private int loopTimeMod = 1;

	@Override
	public void zug()
	{

		boolean worked;
		if (greedPath.size() == 0 || greedStep >= maxGreedSteps)
		{
			worked = aiUtils.pathfinding(spielfeld.getAppleX(), spielfeld.getAppleY(), false);
			greedPath = aiUtils.getPath();
			greedStep = 0;
		} else
		{
			worked = true;
		}
		Node p;
		if (worked && greedPath.size() > 0)
		{
			spielfeld.setSnakeBodyColor(Color.YELLOW);
			spielfeld.setSnakeHeadColor(Color.BLUE);
			p = greedPath.remove(greedPath.size() - 1);
			while (p.x == spielfeld.getHeadX() && p.y == spielfeld.getHeadY() && greedPath.size() > 0)
			{
				p = greedPath.remove(greedPath.size() - 1);
			}
			if (spielfeld.getGameEngine().getLoopTime() > defaultLoopTime)
			{
				spielfeld.getGameEngine().setLoopTime(defaultLoopTime);
			}
			greedStep++;

		} else
		{
			System.out.println("Pathfinding didnt work - trying to fill now");
			p = aiUtils.fill(spielfeld.getMoveDirection());
			spielfeld.setSnakeBodyColor(Color.BLUE);
			spielfeld.setSnakeHeadColor(Color.YELLOW);
			if (spielfeld.getGameEngine().getLoopTime() == defaultLoopTime)
			{
				spielfeld.getGameEngine().setLoopTime(defaultLoopTime * loopTimeMod);
			}
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

		Spielfeld.direction[] directions = AIUtils.determineDirections(spielfeld.getMoveDirection());

		Spielfeld.direction left = directions[0];
		Spielfeld.direction forward = directions[1];
		Spielfeld.direction right = directions[2];

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
}