package ai.impl;
import org.neuroph.contrib.neat.gen.Organism;
import org.neuroph.contrib.neat.gen.operations.fitness.AbstractFitnessFunction;
import org.neuroph.core.NeuralNetwork;
/**
 * Created by scisneromam on 17.02.2018.
 */
public class SnakeFitnessFunction extends AbstractFitnessFunction
{
	AI ai;

	public SnakeFitnessFunction(AI ai){
		this.ai = ai;
	}

	public void setAI(AI ai){
		this.ai = ai;
	}

	@Override
	protected double evaluate(Organism organism, NeuralNetwork neuralNetwork)
	{
		return ai.fitness;
	}

}
