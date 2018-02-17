package main;
import ai.impl.AI;
import snake.engine.GameEngine;
import snake.gui.Gui;
import snake.spielfeld.Spielfeld;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
/**
 * Created by scisneromam on 17.02.2018.
 */
public class GameMaster
{

	Spielfeld spielfeld;
	GameEngine gameEngine;
	Gui gui;
	ArrayList<AI> aiArrayList;
	ArrayList<String> outComes;

	private void fitnessFunction(AI ai){
		//(turns - (turns/(score+1)))
	}

	private int starts = 1;

	public GameMaster()
	{
		aiArrayList = new ArrayList<>();
		outComes = new ArrayList<>();
	}

	public GameMaster(Spielfeld spielfeld, GameEngine gameEngine, Gui gui)
	{
		this.spielfeld = spielfeld;
		this.gameEngine = gameEngine;
		this.gui = gui;
		aiArrayList = new ArrayList<>();
		outComes = new ArrayList<>();
	}

	public GameMaster(Spielfeld spielfeld, GameEngine gameEngine, Gui gui, ArrayList<AI> aiArrayList)
	{
		this.spielfeld = spielfeld;
		this.gameEngine = gameEngine;
		this.gui = gui;
		this.aiArrayList = aiArrayList;
		outComes = new ArrayList<>();
	}

	public void setSpielfeld(Spielfeld spielfeld)
	{
		this.spielfeld = spielfeld;
	}
	public void setGameEngine(GameEngine gameEngine)
	{
		this.gameEngine = gameEngine;
	}
	public void setGui(Gui gui)
	{
		this.gui = gui;
	}
	public void addAI(AI ai)
	{
		aiArrayList.add(ai);
	}

	public void testAIs()
	{
		for (AI ai : aiArrayList)
		{
			long time = System.currentTimeMillis();
			System.out.println("----------");
			ai.reset();
			try
			{
				SwingUtilities.invokeAndWait(new Runnable()
				{
					@Override
					public void run()
					{
						gameEngine.setAi(ai);
						spielfeld.setUp(77, 77);
						gui.repaint();
					}
				});
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			} catch (InvocationTargetException e)
			{
				e.printStackTrace();
			}
			System.out.println(ai.getName() + " is Controlling the run!");
			gameEngine.run();
			System.out.println(ai.getName() + " finished its run with " + gameEngine.getTurn() + " turns and a score of " + gameEngine.getScore() + ".");
			System.out.println(ai.getName() + " took " + (System.currentTimeMillis() - time));
			outComes.add(ai.getName() + ": Turns: " + gameEngine.getTurn() + " Score: " + gameEngine.getScore());
			System.out.println("----------");

			try
			{
				Thread.sleep(2500);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}

		}

		for (int i = 0; i < 3; i++)
		{
			System.out.println("----------");
		}

		for (String s : outComes)
		{
			System.out.println(s);
		}
		outComes.add("-----"+(starts++));

	}

}
