package main;
import ai.impl.*;
import io.gitlab.scisneromam.utils.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.neuroph.contrib.neat.gen.persistence.PersistenceException;
import org.neuroph.core.NeuralNetwork;
import snake.engine.GameEngine;
import snake.gui.Gui;
import snake.gui.SpielfeldGui;
import snake.spielfeld.Spielfeld;
import utils.RandomUtils;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
/**
 * Created by scisneromam on 15.02.2018.
 */
public class Main
{

	private static boolean bGui = true;
	private static boolean runSaved = false;
	private static AI ai = null;
	private static String path = "Winner";

	public static void main(String[] args) throws InterruptedException
	{
		long time = System.currentTimeMillis();

		long loopTime = 5;
		long generations = 100;
		int population = 25;
		double fitness = -1;
		if (args.length > 0)
		{
			if(ArrayUtils.contains(args, "nogui")){
				bGui = false;
			}
			if (args[0].equalsIgnoreCase("run"))
			{
				runSaved = true;
				if (args.length > 1)
				{
					path = args[1];
				} else
				{
					path = "Winner";
				}
			} else if (args[0].equalsIgnoreCase("fitness"))
			{
				try
				{
					if (args.length > 1)
					{
						fitness = Double.parseDouble(args[1]);
						if (args.length > 2)
						{
							population = Integer.parseInt(args[2]);
							if (args.length > 3)
							{
								if (args[3].equalsIgnoreCase("speed") || args[3].equalsIgnoreCase("fast") || args[3].equalsIgnoreCase("speedevolve") ||
									args[3].equalsIgnoreCase("fastevolve"))
								{
									bGui = false;
									loopTime = 0;
								} else
								{
									loopTime = Long.parseLong(args[3]);
								}
							}
						}
					}
				} catch (NumberFormatException nfe)
				{
					System.out.println("Ignoriere " + args[1] + "(oder " + args[3] + ")" + " da keine zahl");
				}
			} else
			{
				int t = 0;
				try
				{
					loopTime = Long.parseLong(args[t++]);
					if (args.length > 1)
					{
						generations = Long.parseLong(args[t++]);
						if (args.length > 2)
						{
							population = Integer.parseInt(args[t++]);
							if (args.length > 3)
							{
								if (args[3].equalsIgnoreCase("speed") || args[3].equalsIgnoreCase("fast") || args[3].equalsIgnoreCase("speedevolve") ||
									args[3].equalsIgnoreCase("fastevolve"))
								{
									bGui = false;
									loopTime = 0;
								}
							}
						}
					}
				} catch (NumberFormatException nfe)
				{
					System.out.println("Ignoriere " + args[t] + " da keine zahl");
				}
			}
		}
		GameEngine gameEngine = new GameEngine(loopTime);
		//GameMaster gameMaster = new GameMaster(null, null, null);
		EvolutionMaster evolutionMaster = new EvolutionMaster(null, null, null);
		evolutionMaster.setGenerations(generations);
		evolutionMaster.setPopulation(population);
		evolutionMaster.setFitness(fitness);

		long seed = RandomUtils.randomLong();
		System.out.println(seed);
		Spielfeld spielfeld = new Spielfeld(75, 75, seed);
		spielfeld.setPopulation(population);
		spielfeld.setSeed(seed);

		gameEngine.setSpielfeld(spielfeld);
		spielfeld.setGameEngine(gameEngine);
		evolutionMaster.setGameEngine(gameEngine);
		evolutionMaster.setSpielfeld(spielfeld);
		if (bGui)
		{
			try
			{
				SwingUtilities.invokeAndWait(new Runnable()
				{
					@Override
					public void run()
					{

						Gui gui = new Gui("SNAKE - seed: " + seed, 800, 800);
						SpielfeldGui spielfeldGui = new SpielfeldGui();
						spielfeldGui.setBounds(15, 15, 770, 770);
						spielfeldGui.setSpielfeld(spielfeld);
						gui.addToContentPane(spielfeldGui);
						gui.initialize();
						gui.repaint();
						gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
						gameEngine.setGui(gui);
						evolutionMaster.setGui(gui);

					}
				});
			} catch (InvocationTargetException e)
			{
				e.printStackTrace();
			}
		}
		if (runSaved)
		{
			spielfeld.setMode("Run saved");
			if (path.equalsIgnoreCase("snakebot"))
			{
				ai = new SnakeBot(spielfeld);
			} else if (path.equalsIgnoreCase("snakepathfindingbot"))
			{
				ai = new SnakePathFindingBot(spielfeld);
			} else
			{
				ai = new BaseAI(spielfeld, NeuralNetwork.load(path));
			}
		}

		if (!runSaved)
		{
			if (fitness != 0)
			{
				spielfeld.setMode("Evolve Fitness");
				spielfeld.setGoal((long) fitness);
			} else
			{
				spielfeld.setMode("Evolve Generations");
				spielfeld.setGoal(generations);
			}
			try
			{
				evolutionMaster.evolve();
			} catch (PersistenceException e)
			{
				e.printStackTrace();
			}
			System.out.println("Did " + generations + " generations with a population of " + population + " in " + (System.currentTimeMillis() - time) + "ms.");
		} else
		{
			gameEngine.setAi(ai);
			gameEngine.run();
		}

	}

	private static void setUp()
	{

	}

	private static void setupAIs(GameMaster gameMaster, Spielfeld spielfeld)
	{
		//gameMaster.addAI(new RandomAI(spielfeld,77));
		for (int i = 0; i < 5; i++)
		{
			gameMaster.addAI(new BaseAI(spielfeld));
			gameMaster.addAI(new BaseAI2(spielfeld));
		}

	}

}
