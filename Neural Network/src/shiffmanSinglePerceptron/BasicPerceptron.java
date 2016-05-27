package shiffmanSinglePerceptron;

/**
 * @author Chang-Hyun
 *
 */

//http://natureofcode.com/book/chapter-10-neural-networks/

public abstract class BasicPerceptron implements ShiffmanPerceptron{
	protected final double c = 0.01;
	
	protected int activate(double sum) {
		//Return a 1 if positive, -1 if negative.
		if (sum > 0) return 1;
		else return -1;
	}
	
}
