package main;
import ai.impl.RandomAI;
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
		if (args.length > 0)
		{
			try
			{
				loopTime = Long.parseLong(args[0]);
			} catch (NumberFormatException nfe)
			{
				System.out.println("Ignoriere " + args[0] + " da keine zahl");
			}
		}
		GameEngine gameEngine = new GameEngine(loopTime);
		GameMaster gameMaster = new GameMaster(null, null, null);

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
					gameMaster.setGameEngine(gameEngine);
					gameMaster.setGui(gui);
					gameMaster.setSpielfeld(spielfeld);
					setupAIs(gameMaster, spielfeld);
				}
			});
		} catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		boolean beendet = false;
		Scanner scanner = new Scanner(System.in);
		while (!beendet)
		{
			gameMaster.testAIs();
			boolean restartPrompt = true;
			while (restartPrompt)
			{
				System.out.println("Restart? Y/n");
				String restart = scanner.nextLine();
				if (restart.equalsIgnoreCase("y") || restart.equalsIgnoreCase("yes") || restart.equalsIgnoreCase("ja") || restart.equalsIgnoreCase("j"))
				{
					beendet = false;
					restartPrompt = false;
				} else if (restart.equalsIgnoreCase("n") || restart.equalsIgnoreCase("no") || restart.equalsIgnoreCase("nein"))
				{
					beendet = true;
					restartPrompt = false;
				} else if (restart.isEmpty() || restart.equalsIgnoreCase(""))
				{
					beendet = false;
					restartPrompt = false;
				} else
				{
					System.out.println("onley no/n and yes/y accepted!");
					restartPrompt = true;
				}
			}

		}

	}

	private static void setupAIs(GameMaster gameMaster, Spielfeld spielfeld)
	{
		gameMaster.addAI(new RandomAI(spielfeld));
		gameMaster.addAI(new RandomAI(spielfeld));
		gameMaster.addAI(new RandomAI(spielfeld));
	}

}
