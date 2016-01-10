package image_editor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Image {
	public Pixel[][] pixels;
	public Pixel[][] pixelsCopy;
	public int width;
	public int height;
	String outputFile;

	public Image(Scanner scanner){
		try{	
			scanner.next(); //Pass over the "P3" at the beginning of the file

		    width = scanner.nextInt(); //Get width of file
//		    System.out.println("width: " + width);
		    height = scanner.nextInt(); //Get height of file
//		    System.out.println("height: " + height);
	
		    pixels = new Pixel[height][width];
	
			scanner.next(); //Pass over the max value. We assume a max of 255
		    
			for(int i = 0; i < height; i++){
				for(int j = 0; j < width; j++){
		    		pixels[i][j] = new Pixel(scanner);
				}
			}
			pixelsCopy = new Pixel[height][width];
		    scanner.close();
		}
		catch(Exception e){
			System.out.println("Error in Image class\n" + e.getMessage());
		}
	}

	public void invert() throws Exception{
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				pixelsCopy[i][j] = new Pixel(pixels[i][j].red.getColor(), pixels[i][j].green.getColor(), pixels[i][j].blue.getColor());
				pixelsCopy[i][j].invert();
			}
		}
	}

	public void grayscale() throws Exception{
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				pixelsCopy[i][j] = new Pixel(pixels[i][j].red.getColor(), pixels[i][j].green.getColor(), pixels[i][j].blue.getColor());
				int grayShade = averageColor(pixelsCopy[i][j]);
				pixelsCopy[i][j].red.setColor(grayShade);
				pixelsCopy[i][j].green.setColor(grayShade);
				pixelsCopy[i][j].blue.setColor(grayShade);
			}
		}
	}

	private int averageColor(Pixel pixel){
		return (pixel.red.getColor() + pixel.green.getColor() + pixel.blue.getColor())/3;
	}

	public void emboss() throws Exception{
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
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
				
				pixelsCopy[i][j] = new Pixel(v,v,v);
			}
		}
	}

	private int maxDiff(int one, int two, int three){
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

	public void motionBlur(int blurVal) throws Exception {
		if(blurVal <= 0){
			throw new Exception("Incorrect motion blur input");
		}
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				pixelsCopy[i][j] = blurAverage(i,j,blurVal);
			}
		}
	}
	
	private Pixel blurAverage(int x, int y, int blurVal) throws Exception{
		int redTotal = 0;
		int greenTotal = 0;
		int blueTotal = 0;
		
		int blurBoundary = (y + blurVal);
		
		if(blurBoundary > width){
			blurBoundary = width;
		}
		for(int i = y; i < blurBoundary; i++){
			redTotal += pixels[x][i].red.getColor();
			greenTotal += pixels[x][i].green.getColor();
			blueTotal += pixels[x][i].blue.getColor();
		}
		int redAv = redTotal/(blurVal);
		int greenAv = greenTotal/(blurVal);
		int blueAv = blueTotal/(blurVal);
		return new Pixel(redAv, greenAv, blueAv);
	}
	
	public void writeToFile(String fileName, Pixel[][] pixelsToWrite) throws IOException {
		System.out.println("Writing to file: " + fileName);
        StringBuilder output = new StringBuilder("P3\r\n# Test Comment");
        output.append("\r\n").append(width).append(" ").append(height).append("\r\n").append("255");
        for (int i = 0; i < height; i++ ) {
        	output.append("\r\n");
            for (int j = 0; j < width; j++){
				output.append(pixelsToWrite[i][j].toString());//[currentRow_yVal][currentColumn_xVal]
            }
        }
        FileWriter fw = new FileWriter(new File(fileName));
        fw.write(output.toString());
        fw.close();
    }
}
