package ai.impl;
import org.neuroph.util.NeuralNetworkFactory;
import org.neuroph.util.TransferFunctionType;
import snake.spielfeld.Spielfeld;
/**
 * Created by scisneromam on 17.02.2018.
 */
public class BaseAI2 extends BaseAI
{

	public BaseAI2(Spielfeld spielfeld)
	{
		super(spielfeld);
		name = "BaseAI2";
		baseName = "BaseAI2";
		neuralNetwork = NeuralNetworkFactory.createMLPerceptron("24 12 3 1 4", TransferFunctionType.SIGMOID);
		neuralNetwork.randomizeWeights();
	}
}
