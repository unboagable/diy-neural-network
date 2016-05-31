package utilities;

import java.util.Random;

public class RandomSingleton {
	static Random r;
	
	public static double randomDouble(double rangeMin, double rangeMax) {
		if (r == null){
			r = new Random();
		}
		double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
		return randomValue;
	}
	
	public static int randomInt(int rangeMin, int rangeMax){
		if (r == null){
			r = new Random();
		}
		int randomValue = rangeMin + r.nextInt((rangeMax+1 - rangeMin));
		return randomValue;	
	}

}
