package ai.impl;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.NeuralNetworkFactory;
import org.neuroph.util.TransferFunctionType;
import snake.spielfeld.Spielfeld;
import utils.MathUtils;
/**
 * Created by scisneromam on 17.02.2018.
 */
public class BaseAI extends AI
{

	NeuralNetwork neuralNetwork;

	public void save()
	{
		neuralNetwork.save(name);
	}

	public BaseAI(Spielfeld spielfeld)
	{
		super(spielfeld);
		name = "BaseAI";
		neuralNetwork = NeuralNetworkFactory.createMLPerceptron("24 16 4", TransferFunctionType.SIGMOID);
		neuralNetwork.randomizeWeights();
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
		double[] output = neuralNetwork.getOutput();

		String out = "North: " + output[0] + " East: " + output[1] + " South: " + output[2] + " West: " + output[3];
		int choice = -1;
		double best = 0;
		for (int i = 0; i < output.length; i++)
		{
			if (output[i] > best)
			{
				choice = i;
				best = output[i];
			}
		}
		switch (choice)
		{
		case 0:
			spielfeld.setMoveDirection(Spielfeld.direction.NORTH);
			out = out + " Choice: " + Spielfeld.direction.NORTH;
			break;
		case 1:
			spielfeld.setMoveDirection(Spielfeld.direction.EAST);
			out = out + " Choice: " + Spielfeld.direction.EAST;
			break;
		case 2:
			spielfeld.setMoveDirection(Spielfeld.direction.SOUTH);
			out = out + " Choice: " + Spielfeld.direction.SOUTH;
			break;
		case 3:
			spielfeld.setMoveDirection(Spielfeld.direction.WEST);
			out = out + " Choice: " + Spielfeld.direction.WEST;
			break;
		}
		out = out + " Going to: " + spielfeld.getMoveDirection();
		System.out.println(out);

	}
	@Override
	public void reset()
	{

	}
}
