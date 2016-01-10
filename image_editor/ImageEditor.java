package image_editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ImageEditor {

	public static void main(String[] args) throws Exception {

        String fileName = args[0];
        String outputFileName = args[1];
		File file = new File(fileName);
        FileReader fileReader;
        
        try {
        	fileReader = new FileReader(file);
	        
	        BufferedReader bufferedReader = new BufferedReader(fileReader);
	        Scanner scanner = new Scanner(bufferedReader);
	        scanner.useDelimiter("\\s*#[^\\n]*\\n|\\s+");
	        
	        Image workingImage = new Image(scanner);

	        switch(args[2]){
	        case "invert":
	        	workingImage.invert();
	        	break;
	        case "grayscale":
	        	workingImage.grayscale();
	        	break;
	        case "emboss":
	        	workingImage.emboss();
	        	break;
	        case "motionblur":
	        	workingImage.motionBlur(Integer.parseInt(args[3]));
	        	break;
	        default:
	        	System.out.println("Sorry, wrong perameter.\nPlease try again with a correct option.");
	        }
	        workingImage.writeToFile(outputFileName, workingImage.pixelsCopy);
	        
	        bufferedReader.close();
		} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("Unable to open file '" + fileName + "'");  
		} catch(IOException ex) {
	            System.out.println(
	                "Error reading file '" 
	                + fileName + "'");
	    }
	}
}
