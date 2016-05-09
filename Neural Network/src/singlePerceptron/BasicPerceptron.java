package singlePerceptron;

import java.util.Random;

/**
 * @author Chang-Hyun
 *
 */

//http://natureofcode.com/book/chapter-10-neural-networks/

abstract class BasicPerceptron implements Perceptron{
	protected final double c = 0.01;

	protected double random(double rangeMin, double rangeMax) {
		Random r = new Random();
		double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
		return randomValue;
	}
	
	protected int activate(double sum) {
		//Return a 1 if positive, -1 if negative.
		if (sum > 0) return 1;
		else return -1;
	}
	
}
