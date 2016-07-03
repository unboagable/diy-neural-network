package utilities;

import java.util.Random;

public class RandomSingleton {
	static Random r;
	
	/**
	* This method is used to generate a random 
	* double between the bounds
	* @param rangeMin minimum (inclusive)
	* @param rangeMax maximum (inclusive)
	* @return double within range
	*/
	public static double randomDouble(double rangeMin, double rangeMax) {
		if (r == null){
			r = new Random();
		}
		double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
		return randomValue;
	}
	
	/**
	* This method is used to generate a random 
	* integer between the bounds
	* @param rangeMin minimum (inclusive)
	* @param rangeMax maximum (inclusive)
	* @return int within range
	*/
	public static int randomInt(int rangeMin, int rangeMax){
		if (r == null){
			r = new Random();
		}
		int randomValue = rangeMin + r.nextInt((rangeMax+1 - rangeMin));
		return randomValue;	
	}

}
