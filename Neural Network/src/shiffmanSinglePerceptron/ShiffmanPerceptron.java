/**
 * 
 */
package shiffmanSinglePerceptron;

import neurons.Neuron;
import utilities.RandomSingleton;

/**
 * @author Chang-Hyun
 *
 */

//http://natureofcode.com/book/chapter-10-neural-networks/

public class ShiffmanPerceptron extends Neuron{
	protected final double c = 0.01;
	
	public ShiffmanPerceptron(int n) {
		weights=new double[n+1]; //1 more for bias (constant)
		for (int i = 0; i < weights.length; i++) {//The weights are picked randomly to start.
			weights[i] = RandomSingleton.random(-1,1);
		}
	}
	
	public double feedforward(double[] inputs) {
	    return getSum(inputs);
	}
	
	public void train(double[] inputs, int desired) {
		int guess = getResult(inputs);
		double error = desired - guess;
		int wl=weights.length;
		for (int i = 0; i < wl-1; i++) {
			weights[i] += c * error * inputs[i];
		}
		weights[wl-1] += c * error; //adjust bias
	}
	
	public double[] getWeights() {
		return weights;
	}
}