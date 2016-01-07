package image_editor;

import java.util.Arrays;
import java.util.Scanner;

public class Image {
	public Pixel[][] pixels;
	public int width;
	public int height;

	public Image(Scanner scanner){
        scanner.useDelimiter("\\s|#.*\r*\n");
//		String skipExpression = "#.*\n";

		String p = scanner.next(); //Pass over the "P3" at the beginning of the file
  	    System.out.println(p);
		scanner.next();
		System.out.println(scanner.next()+"@");
	    width = scanner.nextInt(); //Get width of file
	    System.out.println("width: " + width);
	    height = scanner.nextInt(); //Get height of file
	    System.out.println("height: " + height);

	    pixels = new Pixel[height][width];

		String maxVal = scanner.next(); //Pass over the max value. We assume a max of 255
//		System.out.println("max value: " + maxVal);

		maxVal = scanner.next(); //Pass over the max value. We assume a max of 255
		System.out.println("max value: " + maxVal);
	    
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
	    		pixels[i][j] = new Pixel(scanner);
			}
		}
		System.out.println("here");
	    scanner.close();
	}

	public void invert(){
		Pixel[][] pixelsCopy = new Pixel[height][width];
		pixelsCopy = Arrays.copyOf(pixels, pixels.length);
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				pixelsCopy[i][j].red = 255 - pixels[i][j].red;
				pixelsCopy[i][j].green = 255 - pixels[i][j].green;
				pixelsCopy[i][j].blue = 255 - pixels[i][j].blue;
			}
		}
	}

	public void grayscale(){
		Pixel[][] pixelsCopy = new Pixel[height][width];
		pixelsCopy = Arrays.copyOf(pixels, pixels.length);
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				int grayShade = averageColor(pixels[i][j]);
				pixelsCopy[i][j].red = grayShade;
				pixelsCopy[i][j].green = grayShade;
				pixelsCopy[i][j].blue = grayShade;
			}
		}
	}

	public int averageColor(Pixel pixel){
		return (pixel.red + pixel.green + pixel.blue)/3;
	}

	public void emboss(){
		Pixel[][] pixelsCopy = new Pixel[height][width];
		pixelsCopy = Arrays.copyOf(pixels, pixels.length);
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				int redDiff = pixels[i][j].red - pixels[i - 1][j - 1].red;
				int greenDiff = pixels[i][j].green - pixels[i - 1][j - 1].green;
				int blueDiff = pixels[i][j].blue - pixels[i - 1][j - 1].blue;
			}
		}
	}

	public int maxDiff(int one, int two, int three){
		int max = one;
		if(two > one){
			max = two;
			if(three > two) max = three;
		}
		if(three > one){
			max = three;
		}
		return max;

	}

	public void motionBlur(){

	}
}
