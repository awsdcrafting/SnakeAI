package ai.impl;
import main.Settings;
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
	private int snakeID = 0;

	public SnakeFitnessFunction(Spielfeld spielfeld, GameEngine gameEngine, Gui gui)
	{
		this.spielfeld = spielfeld;
		this.gameEngine = gameEngine;
		this.gui = gui;
	}

	@Override
	protected double evaluate(Organism organism, NeuralNetwork neuralNetwork)
	{
		BaseAI baseAI = new BaseAI(spielfeld, neuralNetwork);
		gameEngine.setAi(baseAI);
		spielfeld.setNumber(organism.getInnovationId());
		spielfeld.setUp(77, 77);
		gameEngine.run();
		/*int maxTurns = 5625;
		int turns = gameEngine.getTurn();
		double baseAppleValue = gameEngine.getScore() * maxTurns;
		double appleMod = 5;
		double appleValue = baseAppleValue * appleMod;
		int appleTurns = maxTurns;
		while(appleTurns<turns){
			appleTurns += maxTurns;
		}
		appleValue /= (appleTurns + 1 - turns);

		double fitness = turns + appleValue;*/
		double fitness = getFitness(gameEngine.getTurn(), gameEngine.getScore());
		if (Settings.debugOutput)
		{
			System.out.println(
					"Snake " + (++snakeID) + " survived " + gameEngine.getTurn() + " turns and achieved a score of " + gameEngine.getScore() + " and has a fitness of: " + fitness);
		}
		spielfeld.setLastFitness(fitness);
		return fitness;
	}

	public static double getFitness(int turns, int score)
	{
		int maxTurns = 5625;
		double baseAppleValue = score * maxTurns;
		double appleMod = 5;
		double appleValue = baseAppleValue * appleMod;
		int appleTurns = maxTurns;
		if (appleTurns < turns)
		{
			appleTurns = turns;
		}
		appleValue /= (appleTurns + 1 - turns);

		double fitness = turns + appleValue;
		if (appleValue == 0 && turns >= maxTurns)
		{
			fitness /= 4;
		}
		return fitness;
	}

}
