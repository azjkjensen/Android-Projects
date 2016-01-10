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
	
	public Pixel(int redVal, int greenVal, int blueVal) throws Exception{
		red = new Color(redVal);
		green = new Color(greenVal);
		blue = new Color(blueVal);
	}
	
	public void invert(){
		red.invert();
		green.invert();
		blue.invert();
	}
	
	public String toString(){
		return red.getColor() + " " + green.getColor() + " " + blue.getColor() + " ";
	}
}
