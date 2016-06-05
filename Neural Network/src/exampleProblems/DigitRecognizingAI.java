package exampleProblems;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.imageio.ImageIO;

import inputProcessers.GreyscaleImageProcessor;
import sigmoidNeuron.Network;
import utilities.IdxReader;
import utilities.NetworkException;
import utilities.NeuronException;


//http://stackoverflow.com/questions/11300847/load-and-display-all-the-images-from-a-folder

public class DigitRecognizingAI {
	static Network n;

    // array of supported extensions (use a List if you prefer)
    static final String[] EXTENSIONS = new String[]{
        "gif", "png", "bmp" // and other formats you need
    };
    // filter to identify images based on their extensions
    static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };
    
    public static void main(String[] args) {
    	int[] layerSizes={15,10};
    	n = new Network(784, layerSizes);
    	
    	String trainingLocation=IdxReader.promptForFile(false, "select folder with Training Images")+"/";
    	String judgeLocation=IdxReader.promptForFile(false, "select folder with judge Images")+"/";
    	
    	double correctPercent=0.0;
    	int run = 0;
    	
    	while(correctPercent < 0.80){
    		trainAI(trainingLocation);
    		run++;
    		correctPercent=judgeAI(judgeLocation);
    	}
    	
    	System.out.print("Reach min classification rate at run: ");
    	System.out.println(run);
    	System.out.print("Classification rate: ");
    	System.out.println(correctPercent);
    	
    	for(int i=0; i<3; i++){
    		String imageLocation=IdxReader.promptForFile(true, "choose 28*28 digit image to guess");
    		File im= new File(imageLocation);
    		BufferedImage img;
			try {
				img = ImageIO.read(im);
				double[] guess=n.getOutput(GreyscaleImageProcessor.getInput(img));
				int guessDigit=networkOuputToResult(guess);
				if (guessDigit > 0){
					System.out.print("Network guess: ");
					System.out.println(guessDigit);
				}
				else{System.out.println("Wrong output");}
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NeuronException e) {
				e.printStackTrace();
			}
    		
    	}
    }
	
	static void trainAI(String trainingLocation){
		
		File dir= new File(trainingLocation);
		
		if (n == null){return;}

		if (dir.isDirectory()) { // make sure it's a directory
			for (final File f : dir.listFiles(IMAGE_FILTER)) {
				BufferedImage img = null;
		         
				try {
					img = ImageIO.read(f);
					int b = Character.getNumericValue((f.getName()).charAt(0));
					n.train(GreyscaleImageProcessor.getInput(img), toNetworkOuput(b));
				} catch (final IOException e) {
					e.printStackTrace();
				} catch (NeuronException e) {
					e.printStackTrace();
				} catch (NetworkException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	static double judgeAI(String judgeLocation){
		
		int correct=0;
		int judged=0;
		
		File dir= new File(judgeLocation);
		
		if (n == null){return 2.0;}

		if (dir.isDirectory()) { // make sure it's a directory
			for (final File f : dir.listFiles(IMAGE_FILTER)) {
				BufferedImage img = null;
		         
				try {
					img = ImageIO.read(f);
					int b = Character.getNumericValue((f.getName()).charAt(0));
					
					double[] guess=n.getOutput(GreyscaleImageProcessor.getInput(img));
					int guessDigit=networkOuputToResult(guess);
					
					judged++;
					if (guessDigit == b){correct++;}
					
				} catch (final IOException e) {
					e.printStackTrace();
				} catch (NeuronException e) {
					e.printStackTrace();
				}
			}
		}
		
		double classificationRate=((double) correct)/((double) judged);
		
		System.out.println(classificationRate);
		
		return classificationRate;
	}
	
	static double[] toNetworkOuput(int x){
		double[] no=new double[10];
		no[x]=1.0;
		return no;
	}
	
	static int networkOuputToResult(double[] guess){
		int guessDigit=-1;
		for(int j=0;j<10;j++){
			if (guess[j] == 1.0){
				if (guessDigit != -1){
					guessDigit = -2;
					break;
				}
				guessDigit = j;
			}
		}
		return guessDigit;
	}

}
