package ai.impl;
import org.neuroph.contrib.neat.gen.Organism;
import org.neuroph.contrib.neat.gen.operations.fitness.AbstractFitnessFunction;
import org.neuroph.core.NeuralNetwork;
import snake.engine.GameEngine;
import snake.gui.Gui;
import snake.spielfeld.Spielfeld;
/**
 * Created by scisneromam on 17.02.2018.
 */
public class SnakeFitnessFunction extends AbstractFitnessFunction
{

	Spielfeld spielfeld;
	GameEngine gameEngine;
	Gui gui;
	private boolean log = true;
	private int snakeID = 0;

	public SnakeFitnessFunction(Spielfeld spielfeld, GameEngine gameEngine, Gui gui, boolean log)
	{
		this.spielfeld = spielfeld;
		this.gameEngine = gameEngine;
		this.gui = gui;
		this.log = log;
	}

	@Override
	protected double evaluate(Organism organism, NeuralNetwork neuralNetwork)
	{
		BaseAI baseAI = new BaseAI(spielfeld, neuralNetwork);
		baseAI.setLog(log);
		gameEngine.setAi(baseAI);
		spielfeld.setUp(77, 77);
		gameEngine.run();
		int maxTurns = 5625;
		int turns = gameEngine.getTurn();
		double baseAppleValue = gameEngine.getScore() * maxTurns;
		double appleMod = 5;
		double appleValue = baseAppleValue * appleMod;
		appleValue /= (maxTurns*gameEngine.getScore() + 1 - turns);

		double fitness = turns + appleValue;
		if (log)
		{
			System.out.println(
					"Snake " + (++snakeID) + " survived " + turns + " turns and achieved a score of " + gameEngine.getScore() + " and has a fitness of: " + fitness);
		}
		return fitness;
	}

}
