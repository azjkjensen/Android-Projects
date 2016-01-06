package image_editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ImageEditor {

	public static void main(String[] args) {
		System.out.println("yo");
//	public static void main("input.txt", "output.txt") {

        String fileName = args[0];
		
        String currentLine = null;
        
        FileReader fileReader;
        
        System.out.println(new File(".").getAbsolutePath());
        try {
		fileReader = new FileReader(fileName);
	        
	        BufferedReader bufferedReader = new BufferedReader(fileReader);
	        
	        while((currentLine = bufferedReader.readLine()) != null) {
	        	if(!"P3".equals(currentLine) && !currentLine.startsWith("#")){
	        		System.out.println(currentLine);
	        	}
	        	else {
	        		System.out.println("Getting rid of comments and silly P3 stuff...");
	        	}
	        }
	        
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
