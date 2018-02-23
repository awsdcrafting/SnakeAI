package utils;
import java.util.ArrayList;
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

	public static int getMinWO(int withOut,Integer... ints){
		ArrayList<Integer> list = new ArrayList<>(Arrays.asList(ints));
		while(list.contains(withOut)){
			list.remove(new Integer(withOut));
		}
		if(list.size()>0)
		{
			return Collections.min(list);
		}else{
			return withOut;
		}
	}

}
