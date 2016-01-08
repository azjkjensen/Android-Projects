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
		File file = new File(fileName);
		
//        String currentLine = null;
        
        FileReader fileReader;
        
//        System.out.println(new File(fileName).getAbsolutePath());
        try {
        	fileReader = new FileReader(file);
	        
	        BufferedReader bufferedReader = new BufferedReader(fileReader);
	        Scanner scanner = new Scanner(bufferedReader);
	        scanner.useDelimiter("\\s*#[^\\n]*\\n|\\s+");
	        
	        Image workingImage = new Image(scanner);

	        workingImage.invert();
	        
	        bufferedReader.close();

	} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Unable to open file '" + fileName + "'");  
	}
	catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");
        }
        
	}

}
