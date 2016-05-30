/**
 * 
 */
package shiffmanSinglePerceptron;

import neurons.BasicNeuron;
import utilities.NeuronException;
import utilities.RandomSingleton;

/**
 * @author Chang-Hyun
 *
 */

//http://natureofcode.com/book/chapter-10-neural-networks/

public class ShiffmanPerceptron extends BasicNeuron{
	protected final double c = 0.01;
	
	public ShiffmanPerceptron(int n) {
		weights=new double[n];
		for (int i = 0; i < weights.length; i++) {//The weights are picked randomly to start.
			weights[i] = RandomSingleton.random(-1,1);
		}
		bias=RandomSingleton.random(-1,1);
	}
	
	public double feedforward(double[] inputs) throws NeuronException {
	    return getSum(inputs);
	}
	
	public void train(double[] inputs, int desired) throws NeuronException {
		int guess = getResult(inputs);
		double error = desired - guess;
		int wl=weights.length;
		for (int i = 0; i < wl; i++) {
			weights[i] += c * error * inputs[i];
		}
		bias += c * error; //adjust bias
	}
	
	public double[] getWeights() {
		return weights;
	}
	
	public double getBias(){
		return bias;
	}
}
