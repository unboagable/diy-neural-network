package shiffmanSinglePerceptron;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

import utilities.NeuronException;
import utilities.RandomSingleton;

class ShiffmanPerceptronPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4674391243062016404L;
	ShiffmanPerceptron ptron;
	Random r = new Random();
	Trainer[] training = new Trainer[2000];
	int count = 0;
	private int width=640;
	private int height=360;

	double f(double x){  //formula for a line
		return 0.5*x+1;
	}
	
	double fPtron(double x){  //formula for a line
		double[] pweights=ptron.getWeights();
		double bias=ptron.getBias();
		return (-1*pweights[0]/pweights[1])*x+(-1*bias/pweights[1]);
	}
	
	public void setup() {
		super.setPreferredSize(new Dimension(width, height));

		ptron = new ShiffmanPerceptron(2);

		// Make 2,000 training points.
		for (int i = 0; i < training.length; i++) {
			double x = RandomSingleton.randomDouble(0,width);
			double y = RandomSingleton.randomDouble(0,height);
			//[full] Is the correct answer 1 or -1?
			int answer = 1;
			if (y < f(x)) answer = 0;
			//[end]
			training[i] = new Trainer(x, y, answer); //to keep them less than 1
		}
		
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		setBackground(Color.WHITE);
		
		try{
		
		ptron.train(training[count].inputs, training[count].answer);
		// For animation, we are training one point at a time.
		count = (count + 1) % training.length;
		
		g.drawLine(0, xyCordinateY((int)f(0)), width, xyCordinateY((int)f(width)));
		g.drawLine(0, xyCordinateY((int)fPtron(0)), width, xyCordinateY((int)fPtron(width)));
		
		int guess;
		for (int i = 0; i < count; i++) {
			guess = ptron.getResult(training[i].inputs);
			//[full] Show the classification�no fill for -1, black for +1.
			g.drawOval((int) (training[i].inputs[0]*training[i].scale)-4, xyCordinateY((int) (training[i].inputs[1]*training[i].scale)-4), 8, 8);
			if (guess > 0) {g.fillOval((int) (training[i].inputs[0]*training[i].scale)-4,xyCordinateY((int) (training[i].inputs[1]*training[i].scale)-4), 8, 8);}
		}
		}catch(NeuronException e){
			//do nothing
		}
		
	}
	
	public void oneMoreTraining() {
		repaint();
	}
	
	private int xyCordinateY(int y){
		return height-y;
	}
	
}
