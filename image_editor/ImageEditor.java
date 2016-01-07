package image_editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

import image_editor.Image;

public class ImageEditor {

	public static void main(String[] args) {

        String fileName = args[0];
		File file = new File(fileName);
		
//        String currentLine = null;
        
        FileReader fileReader;
        
//        System.out.println(new File(fileName).getAbsolutePath());
        try {
        	fileReader = new FileReader(file);
	        
	        BufferedReader bufferedReader = new BufferedReader(fileReader);
	        Scanner scanner = new Scanner(bufferedReader);
	        
	        Image workingImage = new Image(scanner);
//	        System.out.println(workingImage.hello());
//	        System.out.println(workingImage.pixels.length);
	        bufferedReader.close();

	} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Unable to open file '" + fileName + "'");  
	}
	catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                  
            // Or we could just do this: 
            // ex.printStackTrace();
        }
        
//		Scanner inputFile = null;
//		try {
//			inputFile = new Scanner(file);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("here");
//		
//		String input = inputFile.next();
//		System.out.println(input);
//		inputFile.close();
	}

}
