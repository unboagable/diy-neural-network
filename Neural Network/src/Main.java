import neuralNetwork.Perceptron;

/**
 * 
 */

/**
 * @author Chang-Hyun
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Perceptron p = new Perceptron(3);
		double[] point = {50,-12,1};
		int result = p.feedforward(point);
	}

}
