package neurons;

import utilities.NeuronException;

public interface Neuron {
	
	public double feedForward(double[] inputs) throws NeuronException;
	
	public int getResult(double[] inputs) throws NeuronException;
	
	public double[] backPropagate(double sumAhead);
	
	public void setWeights(double[] newWeights);
	
	public void setBias(double newBias);

}
