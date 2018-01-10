package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;

import control.MainController;
import model.LBDatabase;
import model.LBSprite;

public class SpriteWindow extends JFrame{

	public int width = 240;
	public int height = 300;
	public JList<String> spriteList;
	public int activeSprite;
	public MainController mc;
	
	private KeyListener kl = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {
			System.out.println("SWIN:Key Typed " + e.getKeyChar());
			mc.keyTypedHandler(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("SWIN:Key Pressed");
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			System.out.println("SWIN:Key Released");
			
		}
		
	};

	public SpriteWindow(MainController mc) {
		
		this.mc = mc;
		
		this.setVisible(true);
		this.setSize(this.width, this.height);
		this.setLocation(0, 0);
		
		spriteList = new JList<String>();
		
		this.add(spriteList);
		
		DisplaySpriteList();
		  
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		this.addKeyListener(kl);
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
