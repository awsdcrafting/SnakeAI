package ai.impl;
import snake.spielfeld.Spielfeld;
import utils.MathUtils;
import utils.RandomUtils;

import java.util.Random;
/**
 * Created by scisneromam on 20.02.2018.
 */
public class SnakeBot extends AI
{
	public SnakeBot(Spielfeld spielfeld)
	{
		super(spielfeld);
		name = "SnakeBot";
		baseName = "SnakeBot";
	}

	@Override
	public void save()
	{

	}
	@Override
	public void zug()
	{
		Spielfeld.direction left = Spielfeld.direction.EAST;
		Spielfeld.direction forward = Spielfeld.direction.EAST;
		Spielfeld.direction right = Spielfeld.direction.EAST;

		boolean leftAllowed = true;
		boolean forwardAllowed = true;
		boolean rightAllowed = true;

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
		int in1 = in0[0];
		int in2 = in0[1];

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

		double leftMod = 1.0;
		double forwardMod = 1.0;
		double rightMod = 1.0;

		leftAllowed = !(spielfeld.willDie(left) || willBeDeadEnd(left));
		forwardAllowed = !(spielfeld.willDie(forward) || willBeDeadEnd(forward));
		rightAllowed = !(spielfeld.willDie(right) || willBeDeadEnd(right));

		int min = in3;
		if (in6 < in3 && in6 != -999)
		{
			min = in6;
		}
		int min1 = min;
		double mod = min / 10;
		if (mod > 1 || mod == -99)
		{
			mod = 1;
		}
		leftMod -= (1 - mod);

		min = in4;
		if (in7 < in4 && in7 != -999)
		{
			min = in7;
		}
		int min2 = min;
		mod = min / 10;
		if (mod > 1 || mod == -99)
		{
			mod = 1;
		}
		forwardMod -= (1 - mod);

		min = in5;
		if (in8 < in5 && in8 != -999)
		{
			min = in8;
		}
		int min3 = min;
		mod = min / 10;
		if (mod > 1 || mod == -99)
		{
			mod = 1;
		}
		rightMod -= (1 - mod);

		double modMod = 0.5;

		Spielfeld.direction appleXDir = null;
		Spielfeld.direction appleYDir = null;

		if (in1 < 0)
		{
			switch (moveDir)
			{
			case NORTH:
				leftMod += modMod * -in1;
				appleXDir = left;
				break;
			case SOUTH:
				rightMod += modMod * -in1;
				appleXDir = right;
				break;
			case EAST:
				forwardMod -= modMod * -in1;
				break;
			case WEST:
				forwardMod += modMod * -in1;
				appleXDir = forward;
				break;
			}
		} else if (in1 == 0)
		{
			switch (moveDir)
			{
			case NORTH:
				if (in2 < 0)
				{
					forwardMod += modMod * 10;
					appleXDir = forward;
				}
				if (in2 > 0)
				{
					forwardMod -= modMod * 10;
				}
				break;
			case SOUTH:
				if (in2 < 0)
				{
					forwardMod -= modMod * 10;
				}
				if (in2 > 0)
				{
					forwardMod += modMod * 10;
					appleXDir = forward;
				}
				break;
			case EAST:
				if (in2 < 0)
				{
					leftMod += modMod * 10;
					appleXDir = left;
				}
				if (in2 > 0)
				{
					rightMod += modMod * 10;
					appleXDir = right;
				}
				break;
			case WEST:
				if (in2 < 0)
				{
					rightMod += modMod * 10;
					appleXDir = right;
				}
				if (in2 > 0)
				{
					leftMod += modMod * 10;
					appleXDir = left;
				}
				break;
			}
		} else
		{
			switch (moveDir)
			{
			case NORTH:
				rightMod += modMod * in1;
				appleXDir = right;
				break;
			case SOUTH:
				leftMod += modMod * in1;
				appleXDir = left;
				break;
			case EAST:
				forwardMod += modMod * in1;
				appleXDir = forward;
				break;
			case WEST:
				forwardMod -= modMod * in1;
				break;
			}
		}

		if (in2 < 0)
		{
			switch (moveDir)
			{
			case NORTH:
				forwardMod += modMod * -in2;
				appleYDir = forward;
				break;
			case SOUTH:
				forwardMod -= modMod * -in2;
				break;
			case EAST:
				leftMod += modMod * -in2;
				appleYDir = left;
				break;
			case WEST:
				rightMod += modMod * -in2;
				appleYDir = right;
				break;
			}
		} else if (in2 == 0)
		{
			switch (moveDir)
			{
			case NORTH:
				if (in1 < 0)
				{
					leftMod += modMod * 10;
					appleYDir = left;
				}
				if (in1 > 0)
				{
					rightMod += modMod * 10;
					appleYDir = right;
				}
				break;
			case SOUTH:
				if (in1 < 0)
				{
					rightMod += modMod * 10;
					appleYDir = right;
				}
				if (in1 > 0)
				{
					leftMod += modMod * 10;
					appleYDir = left;
				}
				break;
			case EAST:
				if (in1 < 0)
				{
					forwardMod -= modMod * 10;
				}
				if (in1 > 0)
				{
					forwardMod += modMod * 10;
					appleYDir = forward;
				}
				break;
			case WEST:
				if (in1 < 0)
				{
					forwardMod += modMod * 10;
					appleYDir = forward;
				}
				if (in1 > 0)
				{
					forwardMod -= modMod * 10;
				}
				break;
			}
		} else
		{
			switch (moveDir)
			{
			case NORTH:
				forwardMod -= modMod * in2;
				break;
			case SOUTH:
				forwardMod += modMod * in2;
				appleYDir = forward;
				break;
			case EAST:
				rightMod += modMod * in2;
				appleYDir = right;
				break;
			case WEST:
				leftMod += modMod * in2;
				appleYDir = left;
				break;
			}
		}
		System.out.println("left: " + leftMod + " forward: " + forwardMod + " right: " + rightMod);
		System.out.println(willBeDeadEnd(left) + " " + leftAllowed + " " + willBeDeadEnd(forward) + " " + forwardAllowed + " " + willBeDeadEnd(right) + " " + rightAllowed);

		Spielfeld.direction choice = decide(leftMod, forwardMod, rightMod, leftAllowed, forwardAllowed, rightAllowed, left, forward, right, appleXDir, appleYDir, in1, in2);

		if (choice != null)
		{
			String out = "";
			if (choice == left)
			{
				out = "left";
			}
			if (choice == forward)
			{
				out = "forward";
			}
			if (choice == right)
			{
				out = "right";
			}

			System.out.println("Choice: " + out + " - " + choice);
			spielfeld.setMoveDirection(choice);
			return;
		}

		System.out.println("well something went wrong?");
		if (leftAllowed)
		{
			System.out.println("Choice: left " + left);
			spielfeld.setMoveDirection(left);
			return;
		}
		if (forwardAllowed)
		{
			System.out.println("Choice: forward " + forward);
			spielfeld.setMoveDirection(forward);
			return;
		}
		if (rightAllowed)
		{
			System.out.println("Choice: right " + right);
			spielfeld.setMoveDirection(right);
			return;
		}
		System.out.println("looool");

		spielfeld.setMoveDirection(forward);

	}
	@Override
	public void reset()
	{

	}

	private Spielfeld.direction decideByApple(Spielfeld.direction appleXDir, Spielfeld.direction appleYDir, int in1, int in2)
	{
		if (appleXDir != null && appleYDir != null)
		{
			if (Math.abs(in1) < Math.abs(in2) && !spielfeld.willDie(appleXDir))
			{
				return appleXDir;
			} else if (Math.abs(in1) > Math.abs(in2) && !spielfeld.willDie(appleYDir))
			{
				return appleYDir;
			}
			boolean b = RandomUtils.randomBoolean();
			if (b)
			{
				if (!spielfeld.willDie(appleXDir))
				{
					return appleXDir;
				}
			} else
			{
				if (!spielfeld.willDie(appleYDir))
				{
					return appleYDir;
				}
			}
		} else
		{
			if (appleXDir != null && !spielfeld.willDie(appleXDir))
			{
				return appleXDir;
			} else
			{
				if (appleYDir != null && !spielfeld.willDie(appleYDir))
				{
					return appleYDir;
				}
			}
		}
		return null;
	}

	private Spielfeld.direction decide(double leftMod, double forwardMod, double rightMod, boolean leftAllowed, boolean forwardAllowed, boolean rightAllowed,
									   Spielfeld.direction left, Spielfeld.direction forward, Spielfeld.direction right, Spielfeld.direction appleXDir,
									   Spielfeld.direction appleYDir, int in1, int in2)
	{

		Spielfeld.direction choice = null;
		int iChoice = -999;
		double biggest = -999;
		boolean doubled = false;
		if (leftMod > biggest && leftAllowed)
		{
			biggest = leftMod;
			choice = left;
			iChoice = 1;
		}
		if (forwardMod > biggest && forwardAllowed)
		{
			biggest = forwardMod;
			choice = forward;
			iChoice = 2;
		} else if (forwardMod == biggest && forwardAllowed)
		{
			doubled = true;
		}
		if (rightMod > biggest && rightAllowed)
		{
			biggest = rightMod;
			choice = right;
			iChoice = 3;
		} else if (rightMod == biggest && rightAllowed)
		{
			doubled = true;
		}
		if (doubled)
		{
			System.out.println("Doubled! deciding by apple");
			if (appleXDir == left && !leftAllowed)
			{
				appleXDir = null;
			}
			if (appleXDir == forward && !forwardAllowed)
			{
				appleXDir = null;
			}
			if (appleXDir == right && !rightAllowed)
			{
				appleXDir = null;
			}

			if (appleYDir == left && !leftAllowed)
			{
				appleYDir = null;
			}
			if (appleYDir == forward && !forwardAllowed)
			{
				appleYDir = null;
			}
			if (appleYDir == right && !rightAllowed)
			{
				appleYDir = null;
			}
			choice = decideByApple(appleXDir, appleYDir, in1, in2);
		}

		return choice;
	}

	private boolean willBeDeadEnd(Spielfeld.direction moveDirection)
	{

		int[] pos = spielfeld.posInMoveDirection(moveDirection);

		int x = pos[0];
		int y = pos[1];

		Spielfeld.direction left = Spielfeld.direction.EAST;
		Spielfeld.direction forward = Spielfeld.direction.EAST;
		Spielfeld.direction right = Spielfeld.direction.EAST;

		switch (moveDirection)
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

		boolean dead1 = spielfeld.willDie(x, y, left);
		boolean dead2 = spielfeld.willDie(x, y, forward);
		boolean dead3 = spielfeld.willDie(x, y, right);

		return dead1 && dead2 && dead3;

	}

}
