package neurons;

import utilities.NeuronException;

public interface Neuron {
	
	public void train(double[] inputs, double[] desiredOutput) throws NeuronException;
	
	public double feedforward(double[] inputs) throws NeuronException;
	
	public int getResult(double[] inputs) throws NeuronException;

}
