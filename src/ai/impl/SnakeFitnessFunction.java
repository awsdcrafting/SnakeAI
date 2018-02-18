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
		return gameEngine.getTurn() + gameEngine.getScore() * 5625 * 1.5;
	}

}
