/**
 * 
 */
package shiffmanSinglePerceptron;

import neurons.BasicNeuron;
import neurons.Neuron;
import utilities.NeuronException;

/**
 * @author Chang-Hyun
 *
 */

//http://natureofcode.com/book/chapter-10-neural-networks/

public final class ShiffmanPerceptron extends BasicNeuron implements Neuron{
	
	public ShiffmanPerceptron(int n) {
		initializeNeuron(n);
	}
	
	public double subclassFeedForward(double[] inputs) throws NeuronException {
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
