package main;
import ai.impl.BaseAI;
import ai.impl.BaseAI2;
import ai.impl.RandomAI;
import org.neuroph.contrib.neat.gen.persistence.PersistenceException;
import snake.engine.GameEngine;
import snake.gui.Gui;
import snake.gui.SpielfeldGui;
import snake.spielfeld.Spielfeld;

import javax.swing.*;
import java.io.BufferedReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
/**
 * Created by scisneromam on 15.02.2018.
 */
public class Main
{

	public static void main(String[] args) throws InterruptedException
	{
		long loopTime = 50;
		long generations;
		int population;
		if (args.length > 0)
		{
			try
			{
				loopTime = Long.parseLong(args[0]);
				if(args.length>1){
					generations = Long.parseLong(args[1]);
					if(args.length>2)
					{
						population = Integer.parseInt(args[2]);
					}
				}
			} catch (NumberFormatException nfe)
			{
				System.out.println("Ignoriere " + args[0] + " da keine zahl");
			}
		}
		GameEngine gameEngine = new GameEngine(loopTime);
		//GameMaster gameMaster = new GameMaster(null, null, null);
		EvolutionMaster evolutionMaster = new EvolutionMaster(null,null,null);

		try
		{
			SwingUtilities.invokeAndWait(new Runnable()
			{
				@Override
				public void run()
				{

					Spielfeld spielfeld = new Spielfeld(75, 75);
					gameEngine.setSpielfeld(spielfeld);
					Gui gui = new Gui("SNAKE", 800, 800);
					SpielfeldGui spielfeldGui = new SpielfeldGui();
					spielfeldGui.setBounds(15, 15, 770, 770);
					spielfeldGui.setSpielfeld(spielfeld);
					gui.addToContentPane(spielfeldGui);
					gui.initialize();
					gui.repaint();
					gui.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
					gameEngine.setGui(gui);
					evolutionMaster.setGameEngine(gameEngine);
					evolutionMaster.setGui(gui);
					evolutionMaster.setSpielfeld(spielfeld);

				}
			});
		} catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		try
		{
			evolutionMaster.evolve();
		} catch (PersistenceException e)
		{
			e.printStackTrace();
		}

	}

	private static void setupAIs(GameMaster gameMaster, Spielfeld spielfeld)
	{
		//gameMaster.addAI(new RandomAI(spielfeld,77));
		for(int i = 0;i<5;i++)
		{
			gameMaster.addAI(new BaseAI(spielfeld));
			gameMaster.addAI(new BaseAI2(spielfeld));
		}

	}

}
