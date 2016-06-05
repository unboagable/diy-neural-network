package inputProcessers;

import java.awt.image.BufferedImage;

//http://stackoverflow.com/questions/9327768/inputing-png-files-as-a-2d-array-of-intergers-in-java

public class GreyscaleImageProcessor {
	public final static int standardWidth=28;
	public final static int standardHeight=28;
	
	public static double[] getInput(BufferedImage i){
		
		double[] imageArray=new double[standardWidth*standardHeight];
		
		int w = i.getWidth();
		int h = i.getHeight();
		
		if(w != standardWidth || h != standardHeight){
			//just get top-left of image
			w = standardWidth;
			h = standardHeight;
		}
		
		int iai,rgb,r,g,b;
		for (int j = 0; j < w; j++) {
		    for (int k = 0; k < h; k++) {
		    	iai=j*28+k;
		    	rgb = i.getRGB(j, k);
		    	r = (rgb >> 16) & 0xFF;
		    	g = (rgb >> 8) & 0xFF;
		    	b = (rgb & 0xFF);
		    	imageArray[iai] = (((double)(r+g+b))/3.0)/255.0;
		    }
		}
		
		return imageArray;
	}



}
