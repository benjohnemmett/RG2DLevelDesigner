package view;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import control.MainController;
import model.LBDatabase;
import model.LBLevel;
import model.LBSprite;

public class LevelWindow extends JFrame{
	

	public int width = 240;
	public int height = 720;
	public JList<String> levelList;
	public int activeLevel;
	public MainController mc;
	
	private KeyListener kl = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {
			System.out.println("VWIN:Key Typed " + e.getKeyChar());
			mc.keyTypedHandler(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("VWIN:Key Pressed");
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			System.out.println("VWIN:Key Released");
			
		}
		
	};
	
	public LevelWindow(MainController mc){
		
		this.mc = mc;
	
		this.setVisible(true);
		this.setSize(this.width, this.height);
		
		levelList = new JList<String>();
		this.add(levelList);
		
		levelList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				JList<?> list = (JList<?>)e.getSource();
				
				LevelWindow vwin = (LevelWindow)list.getParent().getParent().getParent().getParent();
				vwin.activeLevel = list.getSelectedIndex();
				
				mc.levelSelectionChangeHandler(e);
				
			}
			
		});
		
		
		DisplayLevelList();
		  
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.addKeyListener(kl);
		
	}
	
	public void DisplayLevelList(){
		
		System.out.println("Loading Levels");
		LBDatabase db = LBDatabase.getInstance();
		
		DefaultListModel<String> levelNames = new DefaultListModel<String>();
		
		for(LBLevel l :db.LevelArray){
			System.out.println("\tLoading " + l.name);
			levelNames.addElement(l.name);
		}
		
		levelList.setModel(levelNames);
	}

}
