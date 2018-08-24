package ai.impl;
import main.Settings;
import snake.spielfeld.Node;
import snake.spielfeld.Spielfeld;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.*;
import java.util.ArrayList;
/**
 * Created by scisneromam on 23.02.2018.
 */
public class SnakePathFindingBot extends AI
{

	private AIUtils aiUtils;

	public SnakePathFindingBot(Spielfeld spielfeld)
	{
		this(spielfeld, 1, 1);
		baseName = "SnakePathFindingBot";
		name = baseName;
		aiUtils = new AIUtils(spielfeld);
	}

	public SnakePathFindingBot(Spielfeld spielfeld, int maxGreedSteps, int minFillSteps)
	{
		super(spielfeld);
		this.maxGreedSteps = maxGreedSteps;
		this.minFillSteps = minFillSteps;
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
	private int maxGreedSteps = 75;
	private int fillSteps = 0;
	private int minFillSteps = 3;

	private long defaultLoopTime = spielfeld.getGameEngine().getLoopTime();
	private int loopTimeMod = 2;

	@Override
	public void zug()
	{

		boolean worked;
		Node headNode = spielfeld.getNode(spielfeld.getHeadX(), spielfeld.getHeadY());
		if (fillSteps == 0)
		{
			if (greedPath.size() == 0 || greedStep >= maxGreedSteps)
			{
				worked = aiUtils.pathfinding(headNode, spielfeld.getNode(spielfeld.getAppleX(), spielfeld.getAppleY()), false);
				greedPath = aiUtils.getPath();
				greedStep = 0;
			} else
			{
				worked = true;
			}
		} else
		{
			worked = false;
		}
		Node newNode;
		if (worked && greedPath.size() > 0)
		{
			spielfeld.setSnakeBodyColor(Color.YELLOW);
			spielfeld.setSnakeHeadColor(Color.BLUE);
			newNode = greedPath.remove(greedPath.size() - 1);
			while (newNode.x == spielfeld.getHeadX() && newNode.y == spielfeld.getHeadY() && greedPath.size() > 0)
			{
				newNode = greedPath.remove(greedPath.size() - 1);
			}
			if (spielfeld.getGameEngine().getLoopTime() > defaultLoopTime)
			{
				spielfeld.getGameEngine().setLoopTime(defaultLoopTime);
			}
			greedStep++;

		} else
		{
			if (Settings.debugOutput)
			{
				spielfeld.getGameEngine().log("Pathfinding didnt work - trying to fill now");
			}
			newNode = aiUtils.fill(spielfeld.getMoveDirection());
			spielfeld.setSnakeBodyColor(Color.BLUE);
			spielfeld.setSnakeHeadColor(Color.YELLOW);
			if (spielfeld.getGameEngine().getLoopTime() == defaultLoopTime)
			{
				spielfeld.getGameEngine().setLoopTime(defaultLoopTime * loopTimeMod);
			}
			if (fillSteps == 0)
			{
				fillSteps = minFillSteps;
			}
			fillSteps--;
		}

		Spielfeld.direction choice = aiUtils.determineChoice(newNode);

		Spielfeld.direction[] directions = AIUtils.determineDirections(spielfeld.getMoveDirection());

		Spielfeld.direction left = directions[0];
		Spielfeld.direction forward = directions[1];
		Spielfeld.direction right = directions[2];

		String out = "";
		boolean found = false;

		int tries = 0;
		if (worked)
		{
			if (aiUtils.isATrap(choice))
			{
				if (Settings.debugOutput)
				{
					spielfeld.getGameEngine().log("Greed would be deadly going somewhere else");
				}
				newNode = aiUtils.fill(spielfeld.getMoveDirection());
				choice = aiUtils.determineChoice(newNode);
				spielfeld.setSnakeBodyColor(Color.GREEN);
				if (spielfeld.getGameEngine().getLoopTime() == defaultLoopTime)
				{
					spielfeld.getGameEngine().setLoopTime(defaultLoopTime * loopTimeMod);
				}
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
			if (Settings.debugOutput)
			{
				spielfeld.getGameEngine().log("error! trying to go backward");
			}
		}
		spielfeld.getGameEngine().log("Choice: " + out + " - " + choice);
		spielfeld.setMoveDirection(choice);
	}

	@Override
	public void reset()
	{

	}

	@Override
	public String toString()
	{
		return "SnakePathFindingBot{" + "name='" + name + "\'," + "maxGreedSteps=" + maxGreedSteps + ", minFillSteps=" + minFillSteps + "}";
	}
}