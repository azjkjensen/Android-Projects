package image_editor;

import java.util.Scanner;

public class Pixel {
	public Color red;
	public Color green;
	public Color blue;
	
	public Pixel(Scanner scanner) throws Exception{
		red = new Color(scanner.nextInt());

		green = new Color(scanner.nextInt());
		
		blue = new Color(scanner.nextInt());
	}
	
	public void invert(){
		red.invert();
		green.invert();
		blue.invert();
	}
}
