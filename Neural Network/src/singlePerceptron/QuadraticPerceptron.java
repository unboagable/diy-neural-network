package singlePerceptron;

public class QuadraticPerceptron extends BasicPerceptron{
	private double weights[][];

	public QuadraticPerceptron(int n)  {
		weights=new double[n+1][]; //1 more for bias (constant)
		for (int i = 0; i < n; i++) {//The weights are picked randomly to start.
			weights[i] = new double[2];
			weights[i][0]=random(-1,1);
		}
	}

	public int feedforward(double[] inputs) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void train(double[] inputs, int desired) {
		// TODO Auto-generated method stub
		
	}
	
}
