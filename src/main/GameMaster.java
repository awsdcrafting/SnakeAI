package main;
import ai.impl.AI;
import io.gitlab.scisneromam.utils.TableRenderer;
import snake.engine.GameEngine;
import snake.spielfeld.Spielfeld;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
/**
 * Created by scisneromam on 17.02.2018.
 */
public class GameMaster
{

	private Spielfeld spielfeld;
	private GameEngine gameEngine;
	private ArrayList<AI> aiArrayList;
	private ArrayList<String> outComes;

	public GameMaster()
	{
		aiArrayList = new ArrayList<>();
		outComes = new ArrayList<>();
	}

	public GameMaster(Spielfeld spielfeld, GameEngine gameEngine)
	{
		this.spielfeld = spielfeld;
		this.gameEngine = gameEngine;
		aiArrayList = new ArrayList<>();
		outComes = new ArrayList<>();
	}

	public GameMaster(Spielfeld spielfeld, GameEngine gameEngine, ArrayList<AI> aiArrayList)
	{
		this.spielfeld = spielfeld;
		this.gameEngine = gameEngine;
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
	public void addAI(AI ai)
	{
		aiArrayList.add(ai);
	}

	public void testAIs()
	{
		List<Info> infoList = new ArrayList<>();
		int number = 1;

		for (AI ai : aiArrayList)
		{
			long time = System.currentTimeMillis();
			System.out.println("----------");
			try
			{
				int finalNumber = number;
				SwingUtilities.invokeAndWait(new Runnable()
				{
					@Override
					public void run()
					{
						ai.setName(ai.getName() + "-" + finalNumber);
						gameEngine.setAi(ai);
						spielfeld.setUp(spielfeld.getWidth(), spielfeld.getHeight());
					}
				});
			} catch (InterruptedException | InvocationTargetException e)
			{
				e.printStackTrace();
			}
			number++;
			System.out.println(ai.getName() + " is Controlling the run!");
			gameEngine.startRun();
			System.out.println(ai.getName() + " finished its run with " + gameEngine.getTurn() + " turns and a score of " + gameEngine.getScore() + ".");
			System.out.println(ai.getName() + " took " + (System.currentTimeMillis() - time));

			ai.setFitness(gameEngine.getTurn() + gameEngine.getScore() * 100);
			String outCome = ai.getName() + ": Turns: " + gameEngine.getTurn() + " Score: " + gameEngine.getScore() + " Fitness: " + ai.getFitness();
			infoList.add(new Info(ai.getName(), gameEngine.getTurn(), gameEngine.getScore(), ai.getFitness(), spielfeld.getSeed(), ai.toString()));
			outComes.add(outCome);
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
		System.out.println(spielfeld.getSeed());
		TableRenderer tableRenderer = new TableRenderer();
		tableRenderer.setHeader("Name", "Turns", "Score", "Fitness", "Seed", "AI");
		for (Info info : infoList)
		{
			tableRenderer.addRow(info.name, info.turns, info.score, info.fitness, info.seed, info.aiInfo);
		}
		System.out.print(tableRenderer.buildWithSingleFrame());
	}

	private class Info
	{
		private String name;
		private int turns;
		private int score;
		private int fitness;
		private long seed;
		private String aiInfo;

		public Info(String name, int turns, int score, int fitness, long seed, String aiInfo)
		{
			this.name = name;
			this.turns = turns;
			this.score = score;
			this.fitness = fitness;
			this.seed = seed;
			this.aiInfo = aiInfo;
		}

		@Override
		public String toString()
		{
			return "Info{" + "name='" + name + '\'' + ", turns=" + turns + ", score=" + score + ", fitness=" + fitness + ", seed=" + seed + ", aiInfo='" + aiInfo + '\'' + '}';
		}
	}

}
