package main;
import ai.impl.AI;
import io.gitlab.scisneromam.utils.TableRenderer;
import org.neuroph.nnet.learning.BackPropagation;
import snake.engine.GameEngine;
import snake.gui.Gui;
import snake.spielfeld.Spielfeld;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
/**
 * Created by scisneromam on 17.02.2018.
 */
public class GameMaster
{

	private Spielfeld spielfeld;
	private GameEngine gameEngine;
	private Gui gui;
	private ArrayList<AI> aiArrayList;
	private ArrayList<String> outComes;

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
		List<String> outComeList = new ArrayList<>();

		for (AI ai : aiArrayList)
		{
			long time = System.currentTimeMillis();
			System.out.println("----------");
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
			} catch (InterruptedException | InvocationTargetException e)
			{
				e.printStackTrace();
			}
			System.out.println(ai.getName() + " is Controlling the run!");
			gameEngine.startRun();
			System.out.println(ai.getName() + " finished its run with " + gameEngine.getTurn() + " turns and a score of " + gameEngine.getScore() + ".");
			System.out.println(ai.getName() + " took " + (System.currentTimeMillis() - time));

			ai.setFitness(gameEngine.getTurn() + gameEngine.getScore() * 100);
			String outCome = ai.getName() + ": Turns: " + gameEngine.getTurn() + " Score: " + gameEngine.getScore() + " Fitness: " + ai.getFitness();
			outComeList.add(outCome);
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

		TableRenderer tableRenderer = new TableRenderer();
		tableRenderer.setHeader(aiArrayList.stream().map(AI::getName).toArray());
		tableRenderer.addRow(outComeList.toArray());

	}

}
