package neurons;

import utilities.NeuronException;

public 	abstract class BasicNeuron {
	protected double[] weights;
	protected double bias;
	
	protected int activate(double sum) {
		//Return a 1 if positive, -1 if negative.
		if (sum > 0) return 1;
		else return 0;
	}
	
	public double getSum(double[] inputs) throws NeuronException {
		double sum = 0;
		int wl=weights.length;
	    for (int i = 0; i < wl; i++) {
	    	if (inputs[i] > 1.0 || inputs[i] < 0.0){
	    		throw new NeuronException("input out of bounds");
	    	}
	    	sum += inputs[i]*weights[i];
	    }
	    sum += bias;
		return sum;
	}
	
	public abstract double feedforward(double[] inputs) throws NeuronException;
	
	public int getResult(double[] inputs) throws NeuronException{
		return activate(feedforward(inputs));
	}

}
