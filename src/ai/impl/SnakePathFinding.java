package ai.impl;
import snake.spielfeld.Spielfeld;
/**
 * Created by scisneromam on 23.02.2018.
 */
public class SnakePathFinding extends AI
{
	public SnakePathFinding(Spielfeld spielfeld)
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

	}
	@Override
	public void reset()
	{

	}

	private int schaetze(int x, int y, int headX, int headY)
	{
		int out = Math.abs(x - headX) - 1 + Math.abs(y - headY) - 1;
		if (out < 0)
		{
			out = 0;
		}
		return out;
	}



}
