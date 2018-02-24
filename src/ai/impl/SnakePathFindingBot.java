package ai.impl;
import snake.spielfeld.Node;
import snake.spielfeld.Spielfeld;
/**
 * Created by scisneromam on 23.02.2018.
 */
public class SnakePathFindingBot extends AI
{

	Node[][] grid;
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

	private void generateGrid()
	{
		for (int x = 1; x < 76; x++)
		{
			for (int y = 1; y < 76; y++)
			{

			}
		}
	}

}

