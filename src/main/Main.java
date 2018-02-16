package main;
import snake.engine.GameEngine;
import snake.gui.Gui;
import snake.gui.SpielfeldGui;
import snake.spielfeld.Spielfeld;

import javax.swing.*;
/**
 * Created by scisneromam on 15.02.2018.
 */
public class Main
{

	public static void main(String[] args) throws InterruptedException
	{
		GameEngine gameEngine = new GameEngine();
		SwingUtilities.invokeLater(new Runnable()
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
				gameEngine.setGui(gui);
				SwingUtilities.invokeLater(new Runnable()
				{
					@Override
					public void run()
					{
						gameEngine.run();
					}
				});
			}
		});
	}

}
