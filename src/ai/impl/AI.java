package ai.impl;
import snake.spielfeld.Spielfeld;
/**
 * Created by scisneromam on 17.02.2018.
 */
public abstract class AI implements Comparable<AI>
{

	protected String name;
	protected String baseName;
	protected int fitness;
	protected Spielfeld spielfeld;

	public AI(Spielfeld spielfeld)
	{
		this.spielfeld = spielfeld;
	}

	public abstract void save();

	public String getName()
	{
		return name;
	}

	public String getBaseName()
	{
		return baseName;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public abstract void zug();

	public abstract void reset();

	public int getFitness()
	{
		return fitness;
	}

	public void setFitness(int fitness)
	{
		this.fitness = fitness;
	}

	@Override
	public int compareTo(AI anotherAI)
	{
		return fitness - anotherAI.fitness;
	}

}
