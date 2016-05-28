package sigmoidNeuron;

public class Network {
	int layers;
	int[] sizes;
	
	public Network(int[] lsizes){
		layers=lsizes.length;
		sizes=lsizes;
	}

}
