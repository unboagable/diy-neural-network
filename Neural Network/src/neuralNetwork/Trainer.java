package neuralNetwork;

public class Trainer {
	public double[] inputs; //A "Trainer" object stores the inputs and the correct answer.
	public int answer;
	 
	public Trainer(double x, double y, int a) {
		inputs = new double[3];
		inputs[0] = x;
		inputs[1] = y;
		inputs[2] = 1; //Note that the Trainer has the bias input built into its array.
		answer = a;
	}
}