package shiffmanSinglePerceptron;

public class LinearPerceptron extends BasicPerceptron{
	private double[] weights;
	
	public LinearPerceptron(int n) {
		weights=new double[n+1]; //1 more for bias (constant)
		for (int i = 0; i < weights.length; i++) {//The weights are picked randomly to start.
			weights[i] = random(-1,1);
		}
	}
	
	public int feedforward(double[] inputs) {
		double sum = 0;
		int wl=weights.length;
	    for (int i = 0; i < wl-1; i++) {
	      sum += inputs[i]*weights[i];
	    }
	    sum += weights[wl-1];
	    //Result is the sign of the sum, -1 or +1. Here the perceptron is making a guess. Is it on one side of the line or the other?
	    return activate(sum);
	  }
	
	public void train(double[] inputs, int desired) {
		int guess = feedforward(inputs);
		double error = desired - guess;
		int wl=weights.length;
		for (int i = 0; i < wl-1; i++) {
			weights[i] += c * error * inputs[i];
		}
		weights[wl-1] += c * error; //adjust bias
	}
	
	public double[] getWeights() {
		return weights;
	}

}
