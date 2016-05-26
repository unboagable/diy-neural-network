package shiffmanSinglePerceptron;

public class Trainer {
	public double[] inputs; //A "Trainer" object stores the inputs and the correct answer.
	public int answer;
	 
	public Trainer(double x, double y, int a) {
		inputs = new double[2];
		inputs[0] = x;
		inputs[1] = y;
		answer = a;
	}
}