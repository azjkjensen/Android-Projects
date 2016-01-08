package image_editor;

import java.util.Arrays;
import java.util.Scanner;

public class Image {
	public Pixel[][] pixels;
	public int width;
	public int height;

	public Image(Scanner scanner){
		try{	
			String p = scanner.next(); //Pass over the "P3" at the beginning of the file
	  	    System.out.println(p);

		    width = scanner.nextInt(); //Get width of file
		    System.out.println("width: " + width);
		    height = scanner.nextInt(); //Get height of file
		    System.out.println("height: " + height);
	
		    pixels = new Pixel[height][width];
	
			String maxVal = scanner.next(); //Pass over the max value. We assume a max of 255
			System.out.println("max value: " + maxVal);
		    
			for(int i = 0; i < height; i++){
				for(int j = 0; j < width; j++){
		    		pixels[i][j] = new Pixel(scanner);
				}
			}
			System.out.println("here");
		    scanner.close();
		}
		catch(Exception e){
			System.out.println("Error in Image class\n" + e.getMessage());
		}
	}

	public void invert() throws Exception{
		Pixel[][] pixelsCopy = new Pixel[height][width];
		pixelsCopy = Arrays.copyOf(pixels, pixels.length);
		for(Pixel[] row : pixelsCopy){
			for(Pixel p : row){
				p.invert();
			}
		}
	}

	public void grayscale() throws Exception{
		Pixel[][] pixelsCopy = new Pixel[height][width];
		pixelsCopy = Arrays.copyOf(pixels, pixels.length);
		for(Pixel[] row : pixelsCopy){
			for(Pixel p : row){
				int grayShade = averageColor(p);
				p.red.setColor(grayShade);
				p.green.setColor(grayShade);
				p.blue.setColor(grayShade);
			}
		}
	}

	public int averageColor(Pixel pixel){
		return (pixel.red.getColor() + pixel.green.getColor() + pixel.blue.getColor())/3;
	}

	public void emboss() throws Exception{
		Pixel[][] pixelsCopy = new Pixel[height][width];
		pixelsCopy = Arrays.copyOf(pixels, pixels.length);
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				int v;
				if(i == 0 || j == 0){
					v = 128;
				}
				else {
					int redDiff = pixels[i][j].red.getColor() - pixels[i - 1][j - 1].red.getColor();
					int greenDiff = pixels[i][j].green.getColor() - pixels[i - 1][j - 1].green.getColor();
					int blueDiff = pixels[i][j].blue.getColor() - pixels[i - 1][j - 1].blue.getColor();

					int max = maxDiff(redDiff, greenDiff, blueDiff);
					v = 128 + max;
				}

				if(v < 0) v = 0;
				if(v > 255) v = 255;

				pixelsCopy[i][j].red.setColor(v);
				pixelsCopy[i][j].green.setColor(v);
				pixelsCopy[i][j].blue.setColor(v);
			}
		}
	}

	public int maxDiff(int one, int two, int three){
		int max = one;
		if(Math.abs(two) > Math.abs(one)){
			max = two;
			if(Math.abs(three) > Math.abs(two)) max = three;
		}
		if(Math.abs(three) > Math.abs(one)){
			max = three;
		}
		return max;

	}

	public void motionBlur(){

	}
}
