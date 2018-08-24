package ai.impl;
import main.Logger;
import snake.spielfeld.Spielfeld;
/**
 * Project: SnakeAI
 * Created by scisneromam on 24.08.2018.
 *
 * @author scisneromam
 * ---------------------------------------------------------------------
 * Copyright © 2018 | scisneromam | All rights reserved.
 */
public class Replay extends AI
{
	private Logger logger;
	public Replay(Spielfeld spielfeld, Logger logger)
	{
		super(spielfeld);
		this.logger = logger;
		baseName = "Replay";
		name = logger.getSnakeName() != null ? baseName + "-" + logger.getSnakeName() : baseName;
	}

	@Override
	public void save()
	{

	}
	@Override
	public void zug()
	{
		Logger.Info info = logger.getInfo(spielfeld.getGameEngine().getTurn());
		spielfeld.setMoveDirection(info.getDirection());
		System.out.println(logger.getOutput(spielfeld.getGameEngine().getTurn(), "\n"));
	}
	@Override
	public void reset()
	{

	}
}
