package image_editor;

import java.util.Scanner;

public class Image {

	public Image(Scanner scanner){
        scanner.useDelimiter("\\s|#.*\n");
//		String skipExpression = "#.*\n";
		
		String p = scanner.nextLine(); //Pass over the "P3" at the beginning of the file
//	    System.out.println(p + " --- end of P");
  	    
	    int w = scanner.nextInt(); //Get width of file
//	    System.out.println("width: " + w);
	    int h = scanner.nextInt(); //Get height of file
//	    System.out.println("height: " + h);
	    scanner.next(); //Pass over the max value. We assume a max of 255
	    
		while(scanner.hasNext()){
			while(scanner.hasNextInt()){
	//			scanner.skip(skipExpression);
				System.out.println("next: " + scanner.next());
			}
			if(scanner.hasNext()){
				String nextLine = scanner.nextLine();
				if(nextLine.startsWith("#")){
					scanner.nextLine();
				}
			}
		}
		
	    scanner.close();
	}
	public String hello(){
	    	String hello = "yo foool";
	    	return hello;
	    }
    
	
}
