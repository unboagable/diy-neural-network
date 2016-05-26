package sigmoidNeuron;

import neurons.TransferFunctionNeuron;

public abstract class SigmoidNeuron extends TransferFunctionNeuron{
	
	double transferFunction(double sum){
		double den=1.0+Math.exp(-1.0*sum);
		return 1.0/den;
	}

}
