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
		name = "BaseAI";
		baseName = "BaseAI";
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

		int in6 = MathUtils.getMinWO(-999,spielfeld.getDistance(Spielfeld.state.BODYHORIZONTAL, left), spielfeld.getDistance(Spielfeld.state.BODYVERTICAL, left),
								   spielfeld.getDistance(Spielfeld.state.BODYNORTHEAST, left), spielfeld.getDistance(Spielfeld.state.BODYNORTHWEST, left),
								   spielfeld.getDistance(Spielfeld.state.BODYSOUTHEAST, left), spielfeld.getDistance(Spielfeld.state.BODYSOUTHWEST, left));
		int in7 = MathUtils.getMinWO(-999,spielfeld.getDistance(Spielfeld.state.BODYHORIZONTAL, forward), spielfeld.getDistance(Spielfeld.state.BODYVERTICAL, forward),
								   spielfeld.getDistance(Spielfeld.state.BODYNORTHEAST, forward), spielfeld.getDistance(Spielfeld.state.BODYNORTHWEST, forward),
								   spielfeld.getDistance(Spielfeld.state.BODYSOUTHEAST, forward), spielfeld.getDistance(Spielfeld.state.BODYSOUTHWEST, forward));
		int in8 = MathUtils.getMinWO(-999,spielfeld.getDistance(Spielfeld.state.BODYHORIZONTAL, right), spielfeld.getDistance(Spielfeld.state.BODYVERTICAL, right),
								   spielfeld.getDistance(Spielfeld.state.BODYNORTHEAST, right), spielfeld.getDistance(Spielfeld.state.BODYNORTHWEST, right),
								   spielfeld.getDistance(Spielfeld.state.BODYSOUTHEAST, right), spielfeld.getDistance(Spielfeld.state.BODYSOUTHWEST, right));

		System.out.println(in6 + " " + in7 + " " + in8);

		int nearestDist = 5;
		boolean near1 = Math.abs(in1) <= 3;
		boolean near2 = Math.abs(in2) <= 3;
		if (near1 && near2)
		{
			if ((Math.abs(in1) >= 1 && Math.abs(in2) >= 1) || Math.abs(in1) == 0 || Math.abs(in2) == 0)
			{
				nearestDist = 1;
			}
		} else
		{
			if (Math.abs(in1) <= nearestDist && Math.abs(in2) <= nearestDist)
			{
				nearestDist = 3;
			} else
			{
				nearestDist = 5;
			}
		}

		double leftMod = 1.0;
		double forwardMod = 1.0;
		double rightMod = 1.0;

		leftAllowed = !((in3 <= nearestDist && in3 != -999) || (in6 <= nearestDist && in6 != -999) || spielfeld.willDie(left));
		forwardAllowed = !((in4 <= nearestDist && in4 != -999) || (in7 <= nearestDist && in7 != -999) || spielfeld.willDie(forward));
		rightAllowed = !((in5 <= nearestDist && in5 != -999) || (in8 <= nearestDist && in8 != -999) || spielfeld.willDie(right));

		int prevNearDist = nearestDist;
		while (!leftAllowed && !forwardAllowed && !rightAllowed && nearestDist > 1)
		{
			nearestDist--;
			leftAllowed = !((in3 <= nearestDist && in3 != -999) || (in6 <= nearestDist && in6 != -999) || spielfeld.willDie(left));
			forwardAllowed = !((in4 <= nearestDist && in4 != -999) || (in7 <= nearestDist && in7 != -999) || spielfeld.willDie(forward));
			rightAllowed = !((in5 <= nearestDist && in5 != -999) || (in8 <= nearestDist && in8 != -999) || spielfeld.willDie(right));
		}
		nearestDist = prevNearDist;

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

		System.out.println(in1 + " " + in2 + " " + leftMod + " " + forwardMod + " " + rightMod);

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
		System.out.println(min1 + " " + leftAllowed + " " + min2 + " " + forwardAllowed + " " + min3 + " " + rightAllowed);

		if (leftAllowed)
		{
			if ((leftMod > forwardMod && leftMod > rightMod))
			{
				System.out.println("Choice: left " + left);
				spielfeld.setMoveDirection(left);
				return;
			} else
			{
				if ((leftMod > forwardMod || leftMod == forwardMod) && !forwardAllowed)
				{
					System.out.println("Choice: left " + left);
					spielfeld.setMoveDirection(left);
					return;
				}
				if ((leftMod > rightMod || leftMod == rightMod) && !rightAllowed)
				{
					System.out.println("Choice: left " + left);
					spielfeld.setMoveDirection(left);
					return;
				}
			}
		}
		if (forwardAllowed)
		{
			if ((forwardMod > leftMod && forwardMod > rightMod))
			{
				System.out.println("Choice: forward " + forward);
				spielfeld.setMoveDirection(forward);
				return;
			} else
			{
				if ((forwardMod > leftMod || forwardMod == leftMod) && !leftAllowed)
				{
					System.out.println("Choice: forward " + forward);
					spielfeld.setMoveDirection(forward);
					return;
				}
				if ((forwardMod > rightMod || forwardMod == rightMod) && !rightAllowed)
				{
					System.out.println("Choice: forward " + forward);
					spielfeld.setMoveDirection(forward);
					return;
				}
			}
		}
		if (rightAllowed)
		{
			if ((rightMod > leftMod && rightMod > forwardMod))
			{
				System.out.println("Choice: right " + right);
				spielfeld.setMoveDirection(right);
				return;
			} else
			{
				if ((rightMod > leftMod || rightMod == leftMod) && !leftAllowed)
				{
					System.out.println("Choice: right " + right);
					spielfeld.setMoveDirection(right);
					return;
				}
				if ((rightMod > forwardMod || rightMod == forwardMod) && !forwardAllowed)
				{
					System.out.println("Choice: right " + right);
					spielfeld.setMoveDirection(right);
					return;
				}
			}
		}

		if (rightMod == leftMod || forwardMod == rightMod || forwardMod == leftMod)
		{
			if (decideByApple(appleXDir, appleYDir, in1, in2) != null)
			{
				return;
			}
		}

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
				System.out.println("Choice: " + appleXDir);
				spielfeld.setMoveDirection(appleXDir);
				return appleXDir;
			} else if (Math.abs(in1) > Math.abs(in2) && !spielfeld.willDie(appleYDir))
			{
				System.out.println("Choice: " + appleYDir);
				spielfeld.setMoveDirection(appleYDir);
				return appleYDir;
			}
			boolean b = RandomUtils.randomBoolean();
			if (b)
			{
				if (!spielfeld.willDie(appleXDir))
				{
					System.out.println("Choice: " + appleXDir);
					spielfeld.setMoveDirection(appleXDir);
					return appleXDir;
				}
			} else
			{
				if (!spielfeld.willDie(appleYDir))
				{
					System.out.println("Choice: " + appleYDir);
					spielfeld.setMoveDirection(appleYDir);
					return appleYDir;
				}
			}
		} else
		{
			if (appleXDir != null && !spielfeld.willDie(appleXDir))
			{
				System.out.println("Choice: " + appleXDir);
				spielfeld.setMoveDirection(appleXDir);
				return appleXDir;
			} else
			{
				if (appleYDir != null && !spielfeld.willDie(appleYDir))
				{
					System.out.println("Choice: " + appleYDir);
					spielfeld.setMoveDirection(appleYDir);
					return appleYDir;
				}
			}
		}
		return null;
	}
}
