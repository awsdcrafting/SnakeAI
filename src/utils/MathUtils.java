package utils;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/**
 * Created by scisneromam on 17.02.2018.
 */
public class MathUtils
{

	public static int getMin(Integer... ints){
		List<Integer> list = Arrays.asList(ints);
		return Collections.min(list);
	}

}
