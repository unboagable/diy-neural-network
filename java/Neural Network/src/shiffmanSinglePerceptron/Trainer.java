package shiffmanSinglePerceptron;

class Trainer {
	public double[] inputs; //A "Trainer" object stores the inputs and the correct answer.
	public int answer;
	public double scale;
	 
	public Trainer(double x, double y, int a) {
		scale = Math.max(x, y);
		
		//so values will be between 0.0 and 1.0
		
		inputs = new double[2];
		inputs[0] = x/scale;
		inputs[1] = y/scale;
		answer = a;
	}
}