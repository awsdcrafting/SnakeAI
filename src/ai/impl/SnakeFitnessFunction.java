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

	public SnakeFitnessFunction(Spielfeld spielfeld, GameEngine gameEngine, Gui gui)
	{
		this.spielfeld = spielfeld;
		this.gameEngine = gameEngine;
		this.gui = gui;
	}


	@Override
	protected double evaluate(Organism organism, NeuralNetwork neuralNetwork)
	{
		gameEngine.setAi(new BaseAI(spielfeld,neuralNetwork));
		spielfeld.setUp(77,77);
		gameEngine.run();
		return gameEngine.getTurn() + gameEngine.getScore() * 100;
	}



}
