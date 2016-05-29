package neurons;

public abstract class TransferFunctionNeuron extends Neuron{
	
	protected abstract double transferFunction(double sum);
	
	public double feedforward(double[] inputs) {
	    return transferFunction(getSum(inputs));
	}

}
