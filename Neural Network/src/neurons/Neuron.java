package neurons;

public 	abstract class Neuron {
	protected double[] weights;
	
	protected int activate(double sum) {
		//Return a 1 if positive, -1 if negative.
		if (sum > 0) return 1;
		else return -1;
	}
	
	public double getSum(double[] inputs) {
		double sum = 0;
		int wl=weights.length;
	    for (int i = 0; i < wl-1; i++) {
	      sum += inputs[i]*weights[i];
	    }
	    sum += weights[wl-1];
		return sum;
	}
	
	public abstract double feedforward(double[] inputs);
	
	public int getResult(double[] inputs){
		return activate(feedforward(inputs));
	}

}