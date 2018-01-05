package view;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;

import control.MainController;
import model.LBDatabase;
import model.LBSprite;

public class SpriteWindow extends JFrame{

	public int width = 240;
	public int height = 720;
	public JList spriteList;
	public int activeSprite;
	public MainController mc;

//	public SpriteWindow(int width, int height) {
//
//		this.width = width;
//		this.height = height;
//		
//		this.setVisible(true);
//		this.setSize(this.width, this.height);
//		  
//		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
//	}

	public SpriteWindow(MainController mc) {
		
		this.mc = mc;
		
		this.setVisible(true);
		this.setSize(this.width, this.height);
		
		spriteList = new JList();
		
		this.add(spriteList);
		
		DisplaySpriteList();
		  
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void DisplaySpriteList(){
		LBDatabase db = LBDatabase.getInstance();
		
		DefaultListModel<String> spriteNames = new DefaultListModel<String>();
		
		for(LBSprite s :db.SpriteArray){
			spriteNames.addElement(s.name);
		}
		
		spriteList.setModel(spriteNames);
	}

}
