package model;

public class LBSprite {
	
	public String name;	//Name of the file
	public int hight;
	public int width;
	public String imagePath; //Path to image for displaying this object
	public String buildPath; // Path to use when building Brightscript code
	
	public LBSprite(String name, int width, int height) {

		this.name = name;
		this.width = width;
		this.hight = height;
	}

}
