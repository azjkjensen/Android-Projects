package image_editor;

import java.util.Scanner;

public class Image {

	public Image(Scanner scanner){
//        scanner.useDelimiter("/S");
		String skipper = "#";
		scanner.skip(skipper);
		
		String p = scanner.nextLine(); //Pass over the "P3" at the beginning of the file
	    System.out.println(p + " --- end of P");
//		while(scanner.hasNext()){
			System.out.println(scanner.next());
//		    int w = scanner.nextInt(); //Get width of file
//		    System.out.println("width " + w);
//		    int h = scanner.nextInt(); //Get height of file
//		    System.out.println(h);
//		    scanner.next(); //Pass over the max value. We assume a max of 255
//		}
	}
	public String hello(){
	    	String hello = "yo foool";
	    	return hello;
	    }
    
	
}
