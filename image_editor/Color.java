package image_editor;

public class Color {
	
	private int colorValue;
	
	public Color(int value) throws Exception{
		checkValue(value);
		colorValue = value;
	}
	
	private void checkValue(int value) throws Exception {
		if(value < 0 || value > 255){
			throw new Exception("Color out of range");
		}
	}
	
	public int getColor(){
		return colorValue;
	}
	
	public void setColor(int value) throws Exception{
		checkValue(value);
		colorValue = value;
	}
	
	public void invert(){
		colorValue = 255 - colorValue;
	}
}