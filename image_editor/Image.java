package image_editor;

import java.util.Scanner;

public class Image {
	Pixel[][] pixels;
	public Image(Scanner scanner){
        scanner.useDelimiter("\\s|#.*\r*\n*");
//		String skipExpression = "#.*\n";
		
		String p = scanner.nextLine(); //Pass over the "P3" at the beginning of the file
//	    System.out.println(p + " --- end of P");
  	    
	    int width = scanner.nextInt(); //Get width of file
//	    System.out.println("width: " + width);
	    int height = scanner.nextInt(); //Get height of file
//	    System.out.println("height: " + height);
	    
	    pixels = new Pixel[height][width];
	    
	    scanner.next(); //Pass over the max value. We assume a max of 255
	    
//		for(int i = 0; i < height; i++){
//			for(int j = 0; j < width; j++){
//	    		pixels[i][j] = new Pixel(scanner);
	    		pixels[0][0] = new Pixel(scanner);
//			}
//		}
		
	    scanner.close();
	}
	public String hello(){
	    	String hello = "yo foool";
	    	return hello;
	    }
    
	
}
