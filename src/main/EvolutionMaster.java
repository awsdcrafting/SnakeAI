package main;
import ai.impl.SnakeFitnessFunction;
import org.neuroph.contrib.neat.experiment.xor.XorExperiment;
import org.neuroph.contrib.neat.experiment.xor.XorFitnessFunction;
import org.neuroph.contrib.neat.gen.*;
import org.neuroph.contrib.neat.gen.impl.SimpleNeatParameters;
import org.neuroph.contrib.neat.gen.operations.mutation.WeightMutationOperation;
import org.neuroph.contrib.neat.gen.operations.selector.NaturalSelectionOrganismSelector;
import org.neuroph.contrib.neat.gen.operations.speciator.DynamicThresholdSpeciator;
import org.neuroph.contrib.neat.gen.persistence.Persistence;
import org.neuroph.contrib.neat.gen.persistence.PersistenceException;
import org.neuroph.contrib.neat.gen.persistence.impl.DirectoryOutputPersistence;
import org.neuroph.contrib.neat.gen.persistence.impl.SerializationDelegate;
import org.neuroph.contrib.neat.gen.persistence.impl.serialize.JavaSerializationDelegate;
import org.neuroph.core.NeuralNetwork;
import snake.engine.GameEngine;
import snake.gui.Gui;
import snake.spielfeld.Spielfeld;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;
/**
 * Created by scisneromam on 17.02.2018.
 */
public class EvolutionMaster
{
	private static Logger s_log = Logger.getLogger(EvolutionMaster.class.getName());

	Spielfeld spielfeld;
	GameEngine gameEngine;
	Gui gui;

	private boolean log = true;

	private long generations = 100;
	private int population = 25;
	private double fitness = -1;

	public EvolutionMaster(Spielfeld spielfeld, GameEngine gameEngine, Gui gui)
	{
		this.spielfeld = spielfeld;
		this.gameEngine = gameEngine;
		this.gui = gui;
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

	public void setLog(boolean log)
	{
		this.log = log;
	}

	public void setGenerations(long generations)
	{
		this.generations = generations;
	}

	public void setPopulation(int population)
	{
		this.population = population;
	}

	public void setFitness(double fitness)
	{
		this.fitness = fitness;
	}

	public void evolve() throws PersistenceException
	{
		LocalDateTime localDateTime = LocalDateTime.now();
		String time = localDateTime.getDayOfMonth() + "_" + localDateTime.getMonthValue() + "_" + localDateTime.getYear() + "-" + localDateTime.getHour() + "_" + localDateTime
				.getMinute() + "_" + localDateTime.getSecond();
		SimpleNeatParameters simpleNeatParameters = new SimpleNeatParameters();
		simpleNeatParameters.setFitnessFunction(new SnakeFitnessFunction(spielfeld, gameEngine, gui, log));
		simpleNeatParameters.setPopulationSize(population);
		if (fitness != -1)
		{
			simpleNeatParameters.setMaximumFitness(fitness);
		} else
		{
			simpleNeatParameters.setMaximumGenerations(generations);
		}
		WeightMutationOperation weightMutationOperation = ((WeightMutationOperation) simpleNeatParameters.getMutationOperators().get(2));
		weightMutationOperation.setMaxWeightPertubation(weightMutationOperation.getMaxWeightPertubation() * 2);
		weightMutationOperation.setProbabilityOfNewWeight(weightMutationOperation.getProbabilityOfNewWeight() * 2);
		NaturalSelectionOrganismSelector naturalSelectionOrganismSelector = new NaturalSelectionOrganismSelector();
		naturalSelectionOrganismSelector.setKillUnproductiveSpecies(true);
		naturalSelectionOrganismSelector.setElitismEnabled(true);
		simpleNeatParameters.setOrganismSelector(naturalSelectionOrganismSelector);
		DynamicThresholdSpeciator dynamicThresholdSpeciator = new DynamicThresholdSpeciator();
		dynamicThresholdSpeciator.setMaxSpecies(population / 5 > 4 ? population / 5 : 4);
		simpleNeatParameters.setSpeciator(dynamicThresholdSpeciator);

		String path = "";
		if (fitness == -1)
		{
			path = generations + "_" + population + "-" + time;
		} else
		{
			path = fitness + "_" + population + "-" + time;
		}
		simpleNeatParameters.setPersistence((Persistence) new DirectoryOutputPersistence("path", (SerializationDelegate) new JavaSerializationDelegate(false)));

		ArrayList<NeuronGene> inputGenes = new ArrayList<>();
		for (int i = 0; i < 8; i++)
		{
			inputGenes.add(new NeuronGene(NeuronType.INPUT, simpleNeatParameters));
		}
		ArrayList<NeuronGene> outputGenes = new ArrayList<>();
		for (int i = 0; i < 3; i++)
		{
			outputGenes.add(new NeuronGene(NeuronType.OUTPUT, simpleNeatParameters));
		}

		Evolver evolver = Evolver.createNew(simpleNeatParameters, inputGenes, outputGenes);
		Organism organism = evolver.evolve();
		s_log.info("Winning organism is " + organism.getInnovationId());

		NeuralNetwork neuralNetwork = simpleNeatParameters.getNeuralNetworkBuilder().createNeuralNetwork(organism);
		neuralNetwork.save("Winner_" + path);
	}

}
