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
	
}
