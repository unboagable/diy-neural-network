package exampleProblems;

import sigmoidNeuron.Network;
import utilities.NetworkException;
import utilities.NeuronException;
import utilities.RandomSingleton;

public class CountingSimulation {
	static Network n;
	
	public static void main(String[] args) {
		try {
			int[] sizes={3, 3};
			n=new Network(3, sizes);
			int from = RandomSingleton.randomInt(0,7);
			int to=from+1;
			//train
			for(int i=0; i< 10000; i++){
				from = RandomSingleton.randomInt(0,7);
				to=from+1;
				n.train(intToDoubleArray(from), intToDoubleArray(to));
			}
			
			for (int j=0; j<14; j++){
				System.out.println("--------------------");
				from=j%7;
				System.out.println(from);
				System.out.println(intFromDoubleArray(n.getOutput(intToDoubleArray(from))));
			}
			
			
		}catch (NeuronException e) {
			e.printStackTrace();
		} catch (NetworkException e) {
			e.printStackTrace();
		}
		
	}
	
	private static double[] intToDoubleArray(int x){
		
		double[] toReturn= new double[3];
		int x1 = x % 7;
		
		if (x1 >= 4){
			toReturn[2]=1.0;
			x1-=4;
		}else{toReturn[2]=0.0;}
		
		if (x1 >= 2){
			toReturn[1]=1.0;
			x1-=2;
		}else{toReturn[1]=0.0;}
		
		if (x1 >= 1){
			toReturn[0]=1.0;
			x1-=1;
		}else{toReturn[0]=0.0;}
		
		return toReturn;
	}
	
	private static int intFromDoubleArray(double[] x){
		return (int)(x[0]+x[1]*2.0+x[2]*4.0);
	}

}
