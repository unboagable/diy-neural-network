package neurons;

import utilities.NeuronException;
import utilities.RandomSingleton;

public 	abstract class BasicNeuron {
	protected double[] weights;
	protected double bias;
	protected final double c = 0.01;  //eta
	private double[] lastInputs;
	private double lastFeedForward;
	
	protected void initializeNeuron(int n){
		weights=new double[n];
		lastInputs=new double[n];
		for (int i = 0; i < weights.length; i++) {//The weights are picked randomly to start.
			weights[i] = RandomSingleton.randomDouble(-1,1);
		}
		bias=RandomSingleton.randomDouble(-1,1);
	}
	
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
	    	lastInputs[i]=inputs[i];
	    }
	    sum += bias;
		return sum;
	}
	
	public double feedForward(double[] inputs) throws NeuronException{
		lastFeedForward=subclassFeedForward(inputs);
		return lastFeedForward;
	}
	
	public abstract double subclassFeedForward(double[] inputs) throws NeuronException;
	
	public int getResult(double[] inputs) throws NeuronException{
		return activate(feedForward(inputs));
	}
	
	public double[] backPropagate(double sA){
		int wl=weights.length;
		
		double[] sB=new double[wl]; //this neurons addition to the sums to be propagated backwards
		double mDelta=lastFeedForward*(1-lastFeedForward)*sA;
		
	    for (int i = 0; i < wl; i++) {
	    	weights[i] -= c*mDelta*lastInputs[i];
	    	sB[i]=weights[i]*mDelta;
	    }
	    
	    bias -= c*mDelta;
	    
	    return sB;
		
	}

}
