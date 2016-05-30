package sigmoidNeuron;

public class Network {
	int layers;
	int[] sizes;
	double[][] forwardResults;
	double[][] backwardsResults;
	
	public Network(int[] lsizes){
		layers=lsizes.length;
		sizes=lsizes;
	}
	
	public void train(double[] inputs, double[] desiredOutput){
		//TODO: implement
	}
	
	public double[] getOutput(double[] inputs){
		//TODO: implement
		return new double[0];
	}

}
