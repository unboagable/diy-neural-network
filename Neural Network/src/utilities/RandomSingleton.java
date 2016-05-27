package utilities;

import java.util.Random;

public class RandomSingleton {
	static Random r;
	
	public static double random(double rangeMin, double rangeMax) {
		if (r == null){
			r = new Random();
		}
		double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
		return randomValue;
	}

}
