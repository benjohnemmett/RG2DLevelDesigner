package model;

public class LBSprite {
	
	public String name;
	public int hight;
	public int width;
	public String imagePath; //Path to image for displaying this object
	public String buildPath; // Path to use when building Brightscript code
	// Image ...?
	
	public LBSprite(String name, int width, int height) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.width = width;
		this.hight = height;
	}

}
