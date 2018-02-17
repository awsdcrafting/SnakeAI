package ai.impl;
/**
 * Created by scisneromam on 17.02.2018.
 */
public abstract class AI
{

	protected String name;
	protected double fitness;

	public String getName()
	{
		return name;
	}

	public abstract void zug();

	public abstract void reset();

	public double getFitness()
	{
		return fitness;
	}

	public void setFitness(double fitness)
	{
		this.fitness = fitness;
	}

}
