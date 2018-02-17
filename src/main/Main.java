package main;
import ai.impl.BaseAI;
import ai.impl.BaseAI2;
import ai.impl.RandomAI;
import org.neuroph.contrib.neat.gen.persistence.PersistenceException;
import org.neuroph.core.NeuralNetwork;
import snake.engine.GameEngine;
import snake.gui.Gui;
import snake.gui.SpielfeldGui;
import snake.spielfeld.Spielfeld;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
/**
 * Created by scisneromam on 15.02.2018.
 */
public class Main
{

	private static boolean bGui = true;
	private static boolean bLog = true;
	private static boolean runSaved = false;
	private static BaseAI baseAI = null;
	private static String path = "Winner";

	public static void main(String[] args) throws InterruptedException
	{
		long time = System.currentTimeMillis();

		long loopTime = 50;
		long generations = 100;
		int population = 25;
		

		if (args.length > 0)
		{
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
			} else
			{
				try
				{
					loopTime = Long.parseLong(args[0]);
					if (args.length > 1)
					{
						generations = Long.parseLong(args[1]);
						if (args.length > 2)
						{
							population = Integer.parseInt(args[2]);
							if (args.length > 3)
							{
								if (args[3].equalsIgnoreCase("speed") || args[3].equalsIgnoreCase("fast") || args[3].equalsIgnoreCase("speedevolve") || args[3]
										.equalsIgnoreCase("fastevolve"))
								{
									bGui = false;
									bLog = false;
									loopTime = 0;
								}
							}
						}
					}
				} catch (NumberFormatException nfe)
				{
					System.out.println("Ignoriere " + args[0] + " da keine zahl");
				}
			}
		}
		GameEngine gameEngine = new GameEngine(loopTime);
		//GameMaster gameMaster = new GameMaster(null, null, null);
		EvolutionMaster evolutionMaster = new EvolutionMaster(null, null, null);
		evolutionMaster.setGenerations(generations);
		evolutionMaster.setPopulation(population);

		try
		{
			SwingUtilities.invokeAndWait(new Runnable()
			{
				@Override
				public void run()
				{

					Spielfeld spielfeld = new Spielfeld(75, 75);
					gameEngine.setSpielfeld(spielfeld);
					if (bGui)
					{
						Gui gui = new Gui("SNAKE", 800, 800);
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
					if (runSaved)
					{
						baseAI = new BaseAI(spielfeld, NeuralNetwork.load(path));
					}
					gameEngine.setLog(bLog);
					evolutionMaster.setLog(bLog);
					spielfeld.setLog(bLog);
					evolutionMaster.setGameEngine(gameEngine);
					evolutionMaster.setSpielfeld(spielfeld);

				}
			});
		} catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		if (!runSaved)
		{
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
			gameEngine.setAi(baseAI);
			gameEngine.run();
		}

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
