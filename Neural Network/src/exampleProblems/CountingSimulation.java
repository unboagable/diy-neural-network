package exampleProblems;

import sigmoidNeuron.Network;
import utilities.NetworkException;
import utilities.NeuronException;

public class CountingSimulation {
	static Network n;
	
	public static void main(String[] args) {
		
		int totalRuns=0;
		int trails=10;
		for(int i=0; i<trails; i++){
			totalRuns+=oneRun();
		}
		
		System.out.print("Average over ");
    	System.out.println(trails);
    	System.out.print(" trails:");
    	
    	System.out.println(totalRuns/trails);
		
	}
	
	private static int oneRun(){
		int[] sizes={3, 3};
		n=new Network(3, sizes);
		
		double correctPercent = 0.0;
		int run=0;
		
		
		while(correctPercent < 0.99){
    		trainAI();
    		run++;
    		correctPercent=judgeAI();
    	}
    	
    	System.out.print("Reach min classification rate at run: ");
    	System.out.println(run);
    	
    	return run;
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
	
	private static void trainAI(){
		try {
			for(int i=0; i< 8; i++){
				n.train(intToDoubleArray(i), intToDoubleArray((i+1)%7));
			}
		} catch (NeuronException e) {
			e.printStackTrace();
		} catch (NetworkException e) {
			e.printStackTrace();
		}
	}
	
	private static double judgeAI(){
		double correctRate=0.0;
		int correct=0;
		int judged=0;
		try {
			for (int j=0; j<8; j++){
				if (((j+1)%7)==intFromDoubleArray(n.getOutput(intToDoubleArray(j)))){
					correct++;
				}
				judged++;
			}
		} catch (NeuronException e) {
			e.printStackTrace();
		}
		correctRate=((double) correct)/((double) judged);
		//System.out.println(correctRate);
		return correctRate;
	}

}
