/**
 * 
 */
package singlePerceptron;

/**
 * @author Chang-Hyun
 *
 */

//http://natureofcode.com/book/chapter-10-neural-networks/

public interface Perceptron {
	
	public int feedforward(double[] inputs);
	
	public void train(double[] inputs, int desired);
	
}
