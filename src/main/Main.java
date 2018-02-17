package main;
import ai.RandomAI;
import snake.engine.GameEngine;
import snake.gui.Gui;
import snake.gui.SpielfeldGui;
import snake.spielfeld.Spielfeld;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
/**
 * Created by scisneromam on 15.02.2018.
 */
public class Main
{

	public static void main(String[] args) throws InterruptedException
	{
		GameEngine gameEngine = new GameEngine();
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
					gameEngine.setAi(new RandomAI(spielfeld));
				}
			});
		} catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		gameEngine.run();
	}

}
