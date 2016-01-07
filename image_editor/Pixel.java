package image_editor;

import java.util.Scanner;

public class Pixel {
	public int red;
	public int green;
	public int blue;
	
	public Pixel(Scanner scanner){
		if(scanner.hasNextInt()){
			red = scanner.nextInt();
			System.out.println(red);

			System.out.println(scanner.next() + "!");
			System.out.println(scanner.next() + "!");
		}
		else System.out.println("error on red");
		

		if(scanner.hasNextInt()){
			green = scanner.nextInt();
		}
		else System.out.println("error on green");
		

		if(scanner.hasNextInt()){
			blue = scanner.nextInt();
		}
		else System.out.println("error on blue");
	}
}
