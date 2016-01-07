package image_editor;

import java.util.Scanner;

public class Pixel {
	public int red;
	public int green;
	public int blue;
	
	public Pixel(Scanner scanner){
		scanner.next();
		if(scanner.hasNextInt()){
			red = scanner.nextInt();
			System.out.println("red is " + red);

//			System.out.println(scanner.next() + "!");
//			System.out.println(scanner.next() + "!");
		}
		else {
//			.equals("")
			System.out.println("error on red");
		}

		scanner.next();
		if(scanner.hasNextInt()){
			green = scanner.nextInt();
			System.out.println("green is " + green);
		}
		else System.out.println("error on green");

		scanner.next();
		if(scanner.hasNextInt()){
			blue = scanner.nextInt();
			System.out.println("blue is " + blue);
		}
		else System.out.println("error on blue");
	}
}
