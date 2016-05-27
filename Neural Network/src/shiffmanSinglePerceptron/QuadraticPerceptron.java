package shiffmanSinglePerceptron;

import utilities.RandomSingleton;

public class QuadraticPerceptron extends BasicPerceptron{
	private double weights[][];

	public QuadraticPerceptron(int n)  {
		weights=new double[n+1][]; //1 more for bias (constant)
		for (int i = 0; i < n; i++) {//The weights are picked randomly to start.
			weights[i] = new double[2];
			weights[i][0]=RandomSingleton.random(-1,1); //input^2 coefficient
			weights[i][1]=RandomSingleton.random(-1,1); //input coefficient
		}
		//last weight is the constant so doesn't need an input^2 term
		weights[n]=new double[1];
		weights[n][0]=RandomSingleton.random(-1,1);;
	}

	public int feedforward(double[] inputs) {
		double sum = 0;
		int wl=weights.length;
		
		int we=weights[0].length;
		
		int prod;
	    for (int i = 0; i < wl-1; i++) {
	    	prod=1;
	    	for(int j=0;j<we ;j++){
	    		sum += prod*inputs[i]*weights[i][j];
	    		prod*= inputs[i];
	    	}
	    }
	    sum += weights[wl-1][0];
	    //Result is the sign of the sum, -1 or +1. Here the perceptron is making a guess. Is it on one side of the line or the other?
	    return activate(sum);
	  }

	
	public void train(double[] inputs, int desired) {
		int guess = feedforward(inputs);
		double error = desired - guess;
		
		int wl=weights.length;
		int we=weights[0].length;
		
		int prod;
		for (int i = 0; i < wl-1; i++) {
			prod=1;
	    	for(int j=0;j<we ;j++){
	    		weights[i][j] += c * error *prod* inputs[i];
	    		prod*= inputs[i];
	    	}
		}
		weights[wl-1][0] += c * error; //adjust bias
	}
	
}
