package sigmoidNeuron;

import neurons.Neuron;
import utilities.NetworkException;
import utilities.NeuronException;

public class Network {
	private int trainingpoints;
	private int[] sizes;
	private int inputSize;
	double[][] propagateResults;
	Neuron[][] neurons;
	
	//input, hidden+output layers
	public Network(int inputS, int[] lsizes){
		trainingpoints=0;
		inputSize=inputS;
		sizes=lsizes;
		
		int layers=sizes.length;
		
		neurons= new Neuron[layers][];
		propagateResults = new double[layers][];
		
		neurons[0]= new Neuron[sizes[0]];
		propagateResults[0]= new double[sizes[0]]; //note: defaulted to 0
		
		for(int i=0; i < sizes[0]; i++){ //first layer
			neurons[0][i]= new SigmoidNeuron(inputSize);
		}
		
		for(int j=1; j < layers; j++){
			neurons[j]= new Neuron[sizes[j]];
			propagateResults[j]= new double[sizes[j]];
			for(int k=0; k < sizes[j]; k++){
				neurons[j][k]= new SigmoidNeuron(sizes[j-1]);
			}
		}
	}
	
	public void train(double[] inputs, double[] desiredOutput) throws NeuronException, NetworkException{
		
		if (inputs == null || desiredOutput == null || inputs.length != inputSize 
				|| desiredOutput.length != sizes[sizes.length-1]){
			throw new NetworkException("");
		}
		
		int layers=sizes.length;
		
		propogateForward(inputs);
		
		double[] forresults=finalizeOutput(propagateResults[sizes.length-1]); //converges better
		//double[] forresults=propagateResults[sizes.length-1]; 
		
		for(int k=0; k < sizes[layers-1]; k++){  //sum ahead last layer
			propagateResults[layers-1][k]= forresults[k]-desiredOutput[k];
		}
		
		for(int m=layers-1; m > 0; m--){
			for(int j=0; j < sizes[m-1]; j++){ //set sums ahead to 0 for layer before  
				propagateResults[m-1][j]= 0.0;
			}
			for(int p=0; p < sizes[m]; p++){ 
				double[] myAddition= neurons[m][p].backPropagate(propagateResults[m][p]);//to add to sums for layer before
				for (int i=0; i < sizes[m-1]; i++){
					propagateResults[m-1][i] += myAddition[i];
				}
			}
		}
		
		for(int k=0; k < sizes[0]; k++){ //first layer
			neurons[0][k].backPropagate(propagateResults[0][k]);
		}
		
		trainingpoints++;
		
	}
	
	private void propogateForward(double[] inputs) throws NeuronException{
		
		int layers=sizes.length;
		
		for(int i=0; i < sizes[0]; i++){ //first layer
			propagateResults[0][i]= neurons[0][i].feedForward(inputs);
		}
		
		for(int j=1; j < layers; j++){
			for(int k=0; k < sizes[j]; k++){ 
				propagateResults[j][k]= neurons[j][k].feedForward(propagateResults[j-1]);
			}
		}
	}
	
	public double[] getOutput(double[] inputs) throws NeuronException{
		propogateForward(inputs);
		return finalizeOutput(propagateResults[sizes.length-1]);
	}

	private double[] finalizeOutput(double[] ds) {
		for(int i=0; i< ds.length; i++){
			if (ds[i]>0.5){ds[i]=1.0;}
			else{ds[i]=0.0;}
		}
		return ds;
	}

	public int getInputSize() {
		return inputSize;
	}

	public int[] getSizes() {
		return sizes;
	}
	
	public double[] getNeuronWeights(int layer, int neuron){
		return neurons[layer][neuron].getWeights();
	}
	
	public void setNeuronWeights(int layer, int neuron, double[] weights){
		neurons[layer][neuron].setWeights(weights);;
	}
	
	public double getNeuronBias(int layer, int neuron){
		return neurons[layer][neuron].getBias();
	}
	
	public void setNeuronBias(int layer, int neuron, double bias){
		neurons[layer][neuron].setBias(bias);;
	}

	public int getTrainingPoints() {
		return trainingpoints;
	}

	public void setTrainingPoints(int trainingruns) {
		this.trainingpoints = trainingruns;
	}

}
