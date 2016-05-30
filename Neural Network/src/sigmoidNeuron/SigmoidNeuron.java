package sigmoidNeuron;

import neurons.Neuron;
import neurons.TransferFunctionNeuron;

public abstract class SigmoidNeuron extends TransferFunctionNeuron implements Neuron{
	
	protected double transferFunction(double sum){
		double den=1.0+Math.exp(-1.0*sum);
		return 1.0/den;
	}

}
