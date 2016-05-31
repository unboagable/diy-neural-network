package sigmoidNeuron;

import neurons.Neuron;
import neurons.TransferFunctionNeuron;

public final class SigmoidNeuron extends TransferFunctionNeuron implements Neuron{
	
	public SigmoidNeuron(int n){
		initializeNeuron(n);
	}
	
	protected double transferFunction(double sum){
		double den=1.0+Math.exp(-1.0*sum);
		return 1.0/den;
	}

}
