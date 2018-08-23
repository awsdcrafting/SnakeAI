package ai.impl;
import main.Settings;
import org.neuroph.core.NeuralNetwork;
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

		int[] appleIns = getAppleIns(moveDir);
		int in1 = appleIns[0];
		int in2 = appleIns[1];

		if (Settings.debugOutput)
		{
			System.out.println(in1 + " " + in2);
		}

		int in3 = spielfeld.getDistance(Spielfeld.state.WALL, left);
		int in4 = spielfeld.getDistance(Spielfeld.state.WALL, forward);
		int in5 = spielfeld.getDistance(Spielfeld.state.WALL, right);

		int in6 = MathUtils.getMinWO(-999, spielfeld.getDistance(Spielfeld.state.BODYHORIZONTAL, left), spielfeld.getDistance(Spielfeld.state.BODYVERTICAL, left),
									 spielfeld.getDistance(Spielfeld.state.BODYNORTHEAST, left), spielfeld.getDistance(Spielfeld.state.BODYNORTHWEST, left),
									 spielfeld.getDistance(Spielfeld.state.BODYSOUTHEAST, left), spielfeld.getDistance(Spielfeld.state.BODYSOUTHWEST, left));
		int in7 = MathUtils.getMinWO(-999, spielfeld.getDistance(Spielfeld.state.BODYHORIZONTAL, forward), spielfeld.getDistance(Spielfeld.state.BODYVERTICAL, forward),
									 spielfeld.getDistance(Spielfeld.state.BODYNORTHEAST, forward), spielfeld.getDistance(Spielfeld.state.BODYNORTHWEST, forward),
									 spielfeld.getDistance(Spielfeld.state.BODYSOUTHEAST, forward), spielfeld.getDistance(Spielfeld.state.BODYSOUTHWEST, forward));
		int in8 = MathUtils.getMinWO(-999, spielfeld.getDistance(Spielfeld.state.BODYHORIZONTAL, right), spielfeld.getDistance(Spielfeld.state.BODYVERTICAL, right),
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
		if (Settings.debugOutput)
		{
			System.out.println(out);
		}

	}

	@Override
	public void reset()
	{

	}

	private int[] getAppleIns(Spielfeld.direction moveDir)
	{
		int mod = 5;
		int plus = 0;
		int[] in0 = spielfeld.getDistanceToApple();
		int in1 = in0[0] * mod + (in0[0] > 0 ? plus : in0[0] != 0 ? -plus : 0);
		int in2 = in0[1] * mod + (in0[1] > 0 ? plus : in0[1] != 0 ? -plus : 0);
		boolean change = true;
		if (change)
		{
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
		}

		in1 = in1 * mod + (in0[0] > 0 ? plus : in0[0] != 0 ? -plus : 0);
		in2 = in2 * mod + (in0[1] > 0 ? plus : in0[1] != 0 ? -plus : 0);
		return new int[]{in1, in2};
	}
}
