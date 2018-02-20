package ai.impl;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.learning.BackPropagation;
import org.neuroph.util.NeuralNetworkFactory;
import org.neuroph.util.TransferFunctionType;
import snake.spielfeld.Spielfeld;
import sun.security.provider.ConfigFile;
import utils.MathUtils;

import java.util.Vector;
/**
 * Created by scisneromam on 17.02.2018.
 */
public class BaseAI extends AI
{

	protected NeuralNetwork neuralNetwork;
	protected boolean log = true;

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
		neuralNetwork = NeuralNetworkFactory.createMLPerceptron("24 16 3", TransferFunctionType.SIGMOID);
		neuralNetwork.randomizeWeights();
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
		Spielfeld.direction left = Spielfeld.direction.EAST;
		Spielfeld.direction forward = Spielfeld.direction.EAST;
		Spielfeld.direction right = Spielfeld.direction.EAST;

		Spielfeld.direction moveDir = spielfeld.getMoveDirection();

		switch (moveDir)
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

		int[] in0 = spielfeld.getDistanceToApple();
		int in1 = 0;
		int in2 = 0;
		if (in0[0] < 0)
		{
			switch (moveDir)
			{
			case NORTH:
				in1 = -5;
				break;
			case SOUTH:
				in1 = 5;
				break;
			case EAST:
				in1 = -5;
				break;
			case WEST:
				in1 = 0;
				break;
			}
		} else if (in0[0] == 0)
		{
			switch (moveDir)
			{
			case NORTH:
				if (in0[1] < 0)
				{
					in1 = 0;
				}
				if (in0[1] > 0)
				{
					in1 = -5;
				}
				break;
			case SOUTH:
				if (in0[1] < 0)
				{
					in1 = -5;
				}
				if (in0[1] > 0)
				{
					in1 = 0;
				}
				break;
			case EAST:
				if (in0[1] < 0)
				{
					in1 = -5;
				}
				if (in0[1] > 0)
				{
					in1 = 5;
				}
				break;
			case WEST:
				if (in0[1] < 0)
				{
					in1 = 5;
				}
				if (in0[1] > 0)
				{
					in1 = -5;
				}
				break;
			}
		} else
		{
			switch (moveDir)
			{
			case NORTH:
				in1 = 5;
				break;
			case SOUTH:
				in1 = -5;
				break;
			case EAST:
				in1 = 0;
				break;
			case WEST:
				in1 = -5;
				break;
			}
		}

		if (in0[1] < 0)
		{
			switch (moveDir)
			{
			case NORTH:
				in2 = 0;
				break;
			case SOUTH:
				in2 = -5;
				break;
			case EAST:
				in2 = -5;
				break;
			case WEST:
				in2 = 5;
				break;
			}
		} else if (in0[1] == 0)
		{
			switch (moveDir)
			{
			case NORTH:
				if (in0[0] < 0)
				{
					in2 = -5;
				}
				if (in0[0] > 0)
				{
					in2 = 5;
				}
				break;
			case SOUTH:
				if (in0[0] < 0)
				{
					in2 = 5;
				}
				if (in0[0] > 0)
				{
					in2 = -5;
				}
				break;
			case EAST:
				if (in0[0] < 0)
				{
					in2 = -5;
				}
				if (in0[0] > 0)
				{
					in2 = 0;
				}
				break;
			case WEST:
				if (in0[0] < 0)
				{
					in2 = 0;
				}
				if (in0[0] > 0)
				{
					in2 = -5;
				}
				break;
			}
		} else
		{
			switch (moveDir)
			{
			case NORTH:
				in2 = 5;
				break;
			case SOUTH:
				in2 = -5;
				break;
			case EAST:
				in2 = 0;
				break;
			case WEST:
				in2 = -5;
				break;
			}
		}

		int in3 = spielfeld.getDistance(Spielfeld.state.WALL, left);
		int in4 = spielfeld.getDistance(Spielfeld.state.WALL, forward);
		int in5 = spielfeld.getDistance(Spielfeld.state.WALL, right);

		int in6 = MathUtils.getMin(spielfeld.getDistance(Spielfeld.state.BODYHORIZONTAL, left), spielfeld.getDistance(Spielfeld.state.BODYVERTICAL, left),
								   spielfeld.getDistance(Spielfeld.state.BODYNORTHEAST, left), spielfeld.getDistance(Spielfeld.state.BODYNORTHWEST, left),
								   spielfeld.getDistance(Spielfeld.state.BODYSOUTHEAST, left), spielfeld.getDistance(Spielfeld.state.BODYSOUTHWEST, left));
		int in7 = MathUtils.getMin(spielfeld.getDistance(Spielfeld.state.BODYHORIZONTAL, forward), spielfeld.getDistance(Spielfeld.state.BODYVERTICAL, forward),
								   spielfeld.getDistance(Spielfeld.state.BODYNORTHEAST, forward), spielfeld.getDistance(Spielfeld.state.BODYNORTHWEST, forward),
								   spielfeld.getDistance(Spielfeld.state.BODYSOUTHEAST, forward), spielfeld.getDistance(Spielfeld.state.BODYSOUTHWEST, forward));
		int in8 = MathUtils.getMin(spielfeld.getDistance(Spielfeld.state.BODYHORIZONTAL, right), spielfeld.getDistance(Spielfeld.state.BODYVERTICAL, right),
								   spielfeld.getDistance(Spielfeld.state.BODYNORTHEAST, right), spielfeld.getDistance(Spielfeld.state.BODYNORTHWEST, right),
								   spielfeld.getDistance(Spielfeld.state.BODYSOUTHEAST, right), spielfeld.getDistance(Spielfeld.state.BODYSOUTHWEST, right));
		neuralNetwork.setInput(in1, in2, in3, in4, in5, in6, in7, in8);
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
