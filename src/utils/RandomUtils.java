package utils;
import java.util.Random;
/**
 * Created by scisneromam on 15.02.2018.
 */
public class RandomUtils
{

	private static Random random = new Random();

	public static int randomInt(int max)
	{
		return randomInt(0, max);
	}

	public static int randomInt(int min, int max)
	{
		return random.nextInt(max - min) + min;
	}

	public static double randomDouble()
	{
		return random.nextDouble();
	}

}
