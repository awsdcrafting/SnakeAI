package ai.impl;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.NeuralNetworkFactory;
import org.neuroph.util.TransferFunctionType;
import snake.spielfeld.Spielfeld;
import utils.MathUtils;

import java.util.Vector;
/**
 * Created by scisneromam on 17.02.2018.
 */
public class BaseAI extends AI
{

	protected NeuralNetwork neuralNetwork;
	protected boolean log;

	public void setLog(boolean log)
	{
		this.log = log;
	}

	public void save()
	{
		neuralNetwork.save(name);
	}

	public BaseAI(Spielfeld spielfeld)
	{
		super(spielfeld);
		name = "BaseAI";
		baseName = "BaseAI";
		neuralNetwork = NeuralNetworkFactory.createMLPerceptron("24 16 4", TransferFunctionType.SIGMOID);
		neuralNetwork.randomizeWeights();
		neuralNetwork.setLearningRule(new BackPropagation());
	}

	public BaseAI(Spielfeld spielfeld, NeuralNetwork neuralNetwork)
	{
		super(spielfeld);
		name = "BaseAI";
		baseName = "BaseAI";
		this.neuralNetwork = neuralNetwork;
	}

	@Override
	public void zug()
	{
		int in1 = spielfeld.getDistance(Spielfeld.state.APPLE, Spielfeld.direction.NORTH);
		int in2 = spielfeld.getDistance(Spielfeld.state.APPLE, Spielfeld.direction.NORTHEAST);
		int in3 = spielfeld.getDistance(Spielfeld.state.APPLE, Spielfeld.direction.EAST);
		int in4 = spielfeld.getDistance(Spielfeld.state.APPLE, Spielfeld.direction.SOUTHEAST);
		int in5 = spielfeld.getDistance(Spielfeld.state.APPLE, Spielfeld.direction.SOUTH);
		int in6 = spielfeld.getDistance(Spielfeld.state.APPLE, Spielfeld.direction.SOUTHWEST);
		int in7 = spielfeld.getDistance(Spielfeld.state.APPLE, Spielfeld.direction.WEST);
		int in8 = spielfeld.getDistance(Spielfeld.state.APPLE, Spielfeld.direction.NORTHWEST);

		int in9 = spielfeld.getDistance(Spielfeld.state.WALL, Spielfeld.direction.NORTH);
		int in10 = spielfeld.getDistance(Spielfeld.state.WALL, Spielfeld.direction.NORTHEAST);
		int in11 = spielfeld.getDistance(Spielfeld.state.WALL, Spielfeld.direction.EAST);
		int in12 = spielfeld.getDistance(Spielfeld.state.WALL, Spielfeld.direction.SOUTHEAST);
		int in13 = spielfeld.getDistance(Spielfeld.state.WALL, Spielfeld.direction.SOUTH);
		int in14 = spielfeld.getDistance(Spielfeld.state.WALL, Spielfeld.direction.SOUTHWEST);
		int in15 = spielfeld.getDistance(Spielfeld.state.WALL, Spielfeld.direction.WEST);
		int in16 = spielfeld.getDistance(Spielfeld.state.WALL, Spielfeld.direction.NORTHWEST);

		int in17 = MathUtils.getMin(spielfeld.getDistance(Spielfeld.state.BODYHORIZONTAL, Spielfeld.direction.NORTH),
									spielfeld.getDistance(Spielfeld.state.BODYVERTICAL, Spielfeld.direction.NORTH),
									spielfeld.getDistance(Spielfeld.state.BODYNORTHEAST, Spielfeld.direction.NORTH),
									spielfeld.getDistance(Spielfeld.state.BODYNORTHWEST, Spielfeld.direction.NORTH),
									spielfeld.getDistance(Spielfeld.state.BODYSOUTHEAST, Spielfeld.direction.NORTH),
									spielfeld.getDistance(Spielfeld.state.BODYSOUTHWEST, Spielfeld.direction.NORTH));
		int in18 = MathUtils.getMin(spielfeld.getDistance(Spielfeld.state.BODYHORIZONTAL, Spielfeld.direction.NORTHEAST),
									spielfeld.getDistance(Spielfeld.state.BODYVERTICAL, Spielfeld.direction.NORTHEAST),
									spielfeld.getDistance(Spielfeld.state.BODYNORTHEAST, Spielfeld.direction.NORTHEAST),
									spielfeld.getDistance(Spielfeld.state.BODYNORTHWEST, Spielfeld.direction.NORTHEAST),
									spielfeld.getDistance(Spielfeld.state.BODYSOUTHEAST, Spielfeld.direction.NORTHEAST),
									spielfeld.getDistance(Spielfeld.state.BODYSOUTHWEST, Spielfeld.direction.NORTHEAST));
		int in19 = MathUtils.getMin(spielfeld.getDistance(Spielfeld.state.BODYHORIZONTAL, Spielfeld.direction.EAST),
									spielfeld.getDistance(Spielfeld.state.BODYVERTICAL, Spielfeld.direction.EAST),
									spielfeld.getDistance(Spielfeld.state.BODYNORTHEAST, Spielfeld.direction.EAST),
									spielfeld.getDistance(Spielfeld.state.BODYNORTHWEST, Spielfeld.direction.EAST),
									spielfeld.getDistance(Spielfeld.state.BODYSOUTHEAST, Spielfeld.direction.EAST),
									spielfeld.getDistance(Spielfeld.state.BODYSOUTHWEST, Spielfeld.direction.EAST));
		int in20 = MathUtils.getMin(spielfeld.getDistance(Spielfeld.state.BODYHORIZONTAL, Spielfeld.direction.SOUTHEAST),
									spielfeld.getDistance(Spielfeld.state.BODYVERTICAL, Spielfeld.direction.SOUTHEAST),
									spielfeld.getDistance(Spielfeld.state.BODYNORTHEAST, Spielfeld.direction.SOUTHEAST),
									spielfeld.getDistance(Spielfeld.state.BODYNORTHWEST, Spielfeld.direction.SOUTHEAST),
									spielfeld.getDistance(Spielfeld.state.BODYSOUTHEAST, Spielfeld.direction.SOUTHEAST),
									spielfeld.getDistance(Spielfeld.state.BODYSOUTHWEST, Spielfeld.direction.SOUTHEAST));
		int in21 = MathUtils.getMin(spielfeld.getDistance(Spielfeld.state.BODYHORIZONTAL, Spielfeld.direction.SOUTH),
									spielfeld.getDistance(Spielfeld.state.BODYVERTICAL, Spielfeld.direction.SOUTH),
									spielfeld.getDistance(Spielfeld.state.BODYNORTHEAST, Spielfeld.direction.SOUTH),
									spielfeld.getDistance(Spielfeld.state.BODYNORTHWEST, Spielfeld.direction.SOUTH),
									spielfeld.getDistance(Spielfeld.state.BODYSOUTHEAST, Spielfeld.direction.SOUTH),
									spielfeld.getDistance(Spielfeld.state.BODYSOUTHWEST, Spielfeld.direction.SOUTH));
		int in22 = MathUtils.getMin(spielfeld.getDistance(Spielfeld.state.BODYHORIZONTAL, Spielfeld.direction.SOUTHWEST),
									spielfeld.getDistance(Spielfeld.state.BODYVERTICAL, Spielfeld.direction.SOUTHWEST),
									spielfeld.getDistance(Spielfeld.state.BODYNORTHEAST, Spielfeld.direction.SOUTHWEST),
									spielfeld.getDistance(Spielfeld.state.BODYNORTHWEST, Spielfeld.direction.SOUTHWEST),
									spielfeld.getDistance(Spielfeld.state.BODYSOUTHEAST, Spielfeld.direction.SOUTHWEST),
									spielfeld.getDistance(Spielfeld.state.BODYSOUTHWEST, Spielfeld.direction.SOUTHWEST));
		int in23 = MathUtils.getMin(spielfeld.getDistance(Spielfeld.state.BODYHORIZONTAL, Spielfeld.direction.WEST),
									spielfeld.getDistance(Spielfeld.state.BODYVERTICAL, Spielfeld.direction.WEST),
									spielfeld.getDistance(Spielfeld.state.BODYNORTHEAST, Spielfeld.direction.WEST),
									spielfeld.getDistance(Spielfeld.state.BODYNORTHWEST, Spielfeld.direction.WEST),
									spielfeld.getDistance(Spielfeld.state.BODYSOUTHEAST, Spielfeld.direction.WEST),
									spielfeld.getDistance(Spielfeld.state.BODYSOUTHWEST, Spielfeld.direction.WEST));
		int in24 = MathUtils.getMin(spielfeld.getDistance(Spielfeld.state.BODYHORIZONTAL, Spielfeld.direction.NORTHWEST),
									spielfeld.getDistance(Spielfeld.state.BODYVERTICAL, Spielfeld.direction.NORTHWEST),
									spielfeld.getDistance(Spielfeld.state.BODYNORTHEAST, Spielfeld.direction.NORTHWEST),
									spielfeld.getDistance(Spielfeld.state.BODYNORTHWEST, Spielfeld.direction.NORTHWEST),
									spielfeld.getDistance(Spielfeld.state.BODYSOUTHEAST, Spielfeld.direction.NORTHWEST),
									spielfeld.getDistance(Spielfeld.state.BODYSOUTHWEST, Spielfeld.direction.NORTHWEST));
		neuralNetwork.setInput(in1, in2, in3, in4, in5, in6, in7, in8, in9, in10, in11, in12, in13, in14, in15, in16, in17, in18, in19, in20, in21, in22, in23, in24);
		neuralNetwork.calculate();
		Vector output = neuralNetwork.getOutput();

		String out = "Left: " + output.get(0) + " Forward: " + output.get(1) + " Right: " + output.get(2);
		int choice = -1;
		double best = 0;
		for (int i = 0; i < output.size(); i++)
		{
			if ((double) output.get(i) > best)
			{
				choice = i;
				best = (double) output.get(i);
			}
		}
		Spielfeld.direction left = Spielfeld.direction.EAST;
		Spielfeld.direction forward = Spielfeld.direction.EAST;
		Spielfeld.direction right = Spielfeld.direction.EAST;
		switch (spielfeld.getMoveDirection())
		{
		case NORTH:
			left = Spielfeld.direction.WEST;
			forward = Spielfeld.direction.NORTH;
			right = Spielfeld.direction.EAST;
			break;
		case SOUTH:
			left = Spielfeld.direction.EAST;
			forward = Spielfeld.direction.SOUTH;
			right = Spielfeld.direction.WEST;
			break;
		case EAST:
			left = Spielfeld.direction.NORTH;
			forward = Spielfeld.direction.EAST;
			right = Spielfeld.direction.SOUTH;
			break;
		case WEST:
			left = Spielfeld.direction.SOUTH;
			forward = Spielfeld.direction.WEST;
			right = Spielfeld.direction.NORTH;
			break;
		}
		switch (choice)
		{
		case 0:
			spielfeld.setMoveDirection(left);
			out = out + " Choice: left";
			break;
		case 1:
			spielfeld.setMoveDirection(forward);
			out = out + " Choice: forward";
			break;
		case 2:
			spielfeld.setMoveDirection(right);
			out = out + " Choice: right";
			break;
		}
		out = out + " Going to: " + spielfeld.getMoveDirection();
		if (log)
		{
			System.out.println(out);
		}
	}

	@Override
	public void reset()
	{

	}
}
