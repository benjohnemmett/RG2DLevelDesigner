package view;

import javax.swing.JFrame;

public class SpriteWindow extends JFrame{

	public int width = 240;
	public int height = 720; 

	public SpriteWindow(int width, int height) {

		this.width = width;
		this.height = height;
		
		this.setVisible(true);
		this.setSize(this.width, this.height);
		  
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public SpriteWindow() {
		// TODO Auto-generated constructor stub
		this.setVisible(true);
		this.setSize(this.width, this.height);
		  
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}
