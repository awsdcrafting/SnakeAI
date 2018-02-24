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
		//depth first
	}
	@Override
	public void zug()
	{

	}
	@Override
	public void reset()
	{

	}

	private double schaetze(int startX, int startY, int endX, int endY)
	{

		int x = Math.abs(startX - endX);
		int y = Math.abs(startY - endY);
		return x + y;

	}

}

