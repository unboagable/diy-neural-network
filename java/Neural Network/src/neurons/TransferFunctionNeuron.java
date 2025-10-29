package neurons;

import utilities.NeuronException;

public abstract class TransferFunctionNeuron extends BasicNeuron{
	
	protected abstract double transferFunction(double sum);
	
	public double subclassFeedForward(double[] inputs) throws NeuronException {
	    return transferFunction(getSum(inputs));
	}

}
