package utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

//http://stackoverflow.com/questions/17279049/reading-a-idx-file-type-in-java

public class IdxReader {
	private static JFileChooser chooser = new JFileChooser();

    public static void main(String[] args) {
        FileInputStream inImage = null;
        FileInputStream inLabel = null;

        String inputImagePath = promptForFile(true, "choose image file");
        String inputLabelPath = promptForFile(true,"choose label file");

        String outputPath = promptForFile(false,"choose output folder")+"/";
        
        System.out.println(outputPath);

        int[] hashMap = new int[10]; 

        try {
            inImage = new FileInputStream(inputImagePath);
            inLabel = new FileInputStream(inputLabelPath);

            int magicNumberImages = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
            int numberOfImages = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
            int numberOfRows  = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());
            int numberOfColumns = (inImage.read() << 24) | (inImage.read() << 16) | (inImage.read() << 8) | (inImage.read());

            int magicNumberLabels = (inLabel.read() << 24) | (inLabel.read() << 16) | (inLabel.read() << 8) | (inLabel.read());
            int numberOfLabels = (inLabel.read() << 24) | (inLabel.read() << 16) | (inLabel.read() << 8) | (inLabel.read());

            BufferedImage image = new BufferedImage(numberOfColumns, numberOfRows, BufferedImage.TYPE_INT_ARGB);
            int numberOfPixels = numberOfRows * numberOfColumns;
            int[] imgPixels = new int[numberOfPixels];

            for(int i = 0; i < numberOfImages; i++) {

                if(i % 100 == 0) {System.out.println("Number of images extracted: " + i);}

                for(int p = 0; p < numberOfPixels; p++) {
                    int gray = 255 - inImage.read();
                    imgPixels[p] = 0xFF000000 | (gray<<16) | (gray<<8) | gray;
                }

                image.setRGB(0, 0, numberOfColumns, numberOfRows, imgPixels, 0, numberOfColumns);

                int label = inLabel.read();

                hashMap[label]++;
                File outputfile = new File(outputPath + label + "_0" + hashMap[label] + ".png");

                ImageIO.write(image, "png", outputfile);
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (inImage != null) {
                try {
                    inImage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inLabel != null) {
                try {
                    inLabel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static String promptForFile(boolean file, String title){ 
    	chooser.setDialogTitle(title);
    	
    	if (file){chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);}
    	else{chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);}
    	
    	chooser.setAcceptAllFileFilterUsed(false);
    	int returnVal=chooser.showOpenDialog(null);

    	if (returnVal == JFileChooser.APPROVE_OPTION) {
    	  return chooser.getSelectedFile().toString();
    	}
    	return "";
    }

}
