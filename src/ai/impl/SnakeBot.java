package ai.impl;
import snake.spielfeld.Spielfeld;
import utils.MathUtils;
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

		int in6 = MathUtils.getMin(spielfeld.getDistance(Spielfeld.state.BODYHORIZONTAL, left), spielfeld.getDistance(Spielfeld.state.BODYVERTICAL, left),
								   spielfeld.getDistance(Spielfeld.state.BODYNORTHEAST, left), spielfeld.getDistance(Spielfeld.state.BODYNORTHWEST, left),
								   spielfeld.getDistance(Spielfeld.state.BODYSOUTHEAST, left), spielfeld.getDistance(Spielfeld.state.BODYSOUTHWEST, left));
		int in7 = MathUtils.getMin(spielfeld.getDistance(Spielfeld.state.BODYHORIZONTAL, forward), spielfeld.getDistance(Spielfeld.state.BODYVERTICAL, forward),
								   spielfeld.getDistance(Spielfeld.state.BODYNORTHEAST, forward), spielfeld.getDistance(Spielfeld.state.BODYNORTHWEST, forward),
								   spielfeld.getDistance(Spielfeld.state.BODYSOUTHEAST, forward), spielfeld.getDistance(Spielfeld.state.BODYSOUTHWEST, forward));
		int in8 = MathUtils.getMin(spielfeld.getDistance(Spielfeld.state.BODYHORIZONTAL, right), spielfeld.getDistance(Spielfeld.state.BODYVERTICAL, right),
								   spielfeld.getDistance(Spielfeld.state.BODYNORTHEAST, right), spielfeld.getDistance(Spielfeld.state.BODYNORTHWEST, right),
								   spielfeld.getDistance(Spielfeld.state.BODYSOUTHEAST, right), spielfeld.getDistance(Spielfeld.state.BODYSOUTHWEST, right));

		int nearestDist = 1;

		if (in3 <= nearestDist || in6 <= nearestDist)
		{
			leftAllowed = false;
		}
		if (in4 <= nearestDist || in7 <= nearestDist)
		{
			forwardAllowed = false;
		}
		if (in5 <= nearestDist || in8 <= nearestDist)
		{
			rightAllowed = false;
		}
		double leftMod = 1.0;
		double forwardMod = 1.0;
		double rightMod = 1.0;

		leftMod -= 1 / in3;
		leftMod -= 1 / in6;
		forwardMod -= 1 / in4;
		forwardMod -= 1 / in7;
		rightMod -= 1 / in5;
		rightMod -= 1 / in8;

		double modMod = 0.5;

		if (in1 < 0)
		{
			switch (moveDir)
			{
			case NORTH:
				leftMod += modMod;
				break;
			case SOUTH:
				rightMod += modMod;
				break;
			case EAST:
				forwardMod -= modMod;
				break;
			case WEST:
				forwardMod += modMod;
				break;
			}
		} else if (in1 == 0)
		{
			switch (moveDir)
			{
			case NORTH:
				if (in2 < 0)
				{
					forwardMod += modMod;
				}
				if (in2 > 0)
				{
					forwardMod -= modMod;
				}
				break;
			case SOUTH:
				if (in2 < 0)
				{
					forwardMod -= modMod;
				}
				if (in2 > 0)
				{
					forwardMod += modMod;
				}
				break;
			case EAST:
				if (in2 < 0)
				{
					leftMod += modMod;
				}
				if (in2 > 0)
				{
					rightMod += modMod;
				}
				break;
			case WEST:
				if (in2 < 0)
				{
					rightMod += modMod;
				}
				if (in2 > 0)
				{
					leftMod += modMod;
				}
				break;
			}
		} else
		{
			switch (moveDir)
			{
			case NORTH:
				rightMod += modMod;
				break;
			case SOUTH:
				leftMod += modMod;
				break;
			case EAST:
				forwardMod += modMod;
				break;
			case WEST:
				forwardMod -= modMod;
				break;
			}
		}

		if (in2 < 0)
		{
			switch (moveDir)
			{
			case NORTH:
				forwardMod += modMod;
				break;
			case SOUTH:
				forwardMod -= modMod;
				break;
			case EAST:
				leftMod += modMod;
				break;
			case WEST:
				rightMod += modMod;
				break;
			}
		} else if (in2 == 0)
		{
			switch (moveDir)
			{
			case NORTH:
				if (in1 < 0)
				{
					leftMod += modMod;
				}
				if (in1 > 0)
				{
					rightMod += modMod;
				}
				break;
			case SOUTH:
				if (in1 < 0)
				{
					rightMod += modMod;
				}
				if (in1 > 0)
				{
					leftMod += modMod;
				}
				break;
			case EAST:
				if (in1 < 0)
				{
					forwardMod -= modMod;
				}
				if (in1 > 0)
				{
					forwardMod += modMod;
				}
				break;
			case WEST:
				if (in1 < 0)
				{
					forwardMod += modMod;
				}
				if (in1 > 0)
				{
					forwardMod -= modMod;
				}
				break;
			}
		} else
		{
			switch (moveDir)
			{
			case NORTH:
				rightMod += modMod;
				break;
			case SOUTH:
				leftMod += modMod;
				break;
			case EAST:
				forwardMod += modMod;
				break;
			case WEST:
				forwardMod -= modMod;
				break;
			}
		}

		if (leftAllowed && (leftMod >= forwardMod && leftMod >= rightMod))
		{
			spielfeld.setMoveDirection(left);
			return;
		}
		if (forwardAllowed && (forwardMod >= leftMod && forwardMod >= rightMod))
		{
			spielfeld.setMoveDirection(forward);
			return;
		}
		if (rightAllowed && (rightMod >= leftMod && rightMod >= forwardMod))
		{
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

}
