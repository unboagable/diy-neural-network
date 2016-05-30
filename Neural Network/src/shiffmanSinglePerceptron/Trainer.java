package shiffmanSinglePerceptron;

class Trainer {
	public double[] inputs; //A "Trainer" object stores the inputs and the correct answer.
	public int answer;
	public double scale;
	 
	public Trainer(double x, double y, int a) {
		scale = x;
		inputs = new double[2];
		inputs[0] = x/x;
		inputs[1] = y/x;
		answer = a;
	}
}