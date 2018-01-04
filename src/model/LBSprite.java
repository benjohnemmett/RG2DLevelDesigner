package model;

import Utils.LBStringUtils;

import java.awt.Image;
import java.util.regex.*;

import javax.swing.ImageIcon;

public class LBSprite implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5981578797526393059L;
	
	public String filename;	//Name of the file
	public String filepath;
	public String name;	// Defaults to name of file without extension
	public int height;
	public int width;
	private LBDatabase db = LBDatabase.getInstance();
	//public Image img;
	
	@Deprecated
	public String imagePath; //Path to image for displaying this object
	@Deprecated
	public String buildPath; // Path to use when building Brightscript code
	
	public LBSprite(String filename) {
		
		this.filename = filename;
		this.name = LBStringUtils.removeExtension(filename);
		this.filepath = db.GamePath + "components/sprites/" + this.filename;
		Image img = new ImageIcon(this.filepath).getImage();
		
		//Determine width & height by filename or image size
		// fire_spritesheet_w50_h60.png
		
		Pattern p = Pattern.compile(".+_w(\\d+)_h(\\d+)");
		Matcher m = p.matcher(this.name);
		
		System.out.println("Loading! " + this.name );
		if(m.matches()){
			System.out.println("->Fount spritesheet! " + this.name  + " 1) " + m.group(0) + " 2) " + m.group(1) + " 3) " + m.group(2));
			this.width = Integer.parseInt(m.group(1));
			this.height = Integer.parseInt(m.group(2));
		} else {
			//Default to using the whole image
			this.width = img.getWidth(null);
			this.height = img.getHeight(null);
		}
		
		
	}

}
