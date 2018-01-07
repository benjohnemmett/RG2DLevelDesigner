package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Utils.LBDataUtils;
import control.MainController;
import model.LBCodeGen;
import model.LBDatabase;
import model.LBLevel;
import model.LBSpriteInstance;

// Based on help from http://mrbool.com/showing-images-in-a-swing-jframe-in-java/24594

public class LayoutWindow extends JFrame{
	
	public int width = 1280;
	public int height = 720;
	public int activeLevel = 0;
	public SpriteWindow swin;
	private LBDatabase db = LBDatabase.getInstance();
	public LBLevel level;
	
	public MainController mc;
	
	private MouseAdapter ma = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			
			LayoutWindow win = (LayoutWindow) e.getComponent();
			int activeSprite = win.mc.getSelectedSpriteIndex();
			if(activeSprite < 0){ return; }
			
			level.addSprite(activeSprite, e.getX(), e.getY());
			
			win.DrawLevel();
			
			System.out.println("Add new Object! " + e.getX() + ", " + e.getY());
		}
	};
	
	private KeyListener kl = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {
			System.out.println("LWIN:Key Typed " + e.getKeyChar());
			mc.keyTypedHandler(e);

//			if(e.getKeyChar() == 's'){
//				
//				System.out.println("Saving Data.");
//				db.saveDatabase(db.GamePath + "Data.bin");
//
//			} else if (e.getKeyChar() == 'o') {
//				
//				db.loadDatabase(db.GamePath + "Data.bin");
//
//				LayoutWindow win = (LayoutWindow)e.getSource();
//				win.level = db.LevelArray.get(0);
//				win.DrawLevel();
//				
//			} else {
//				System.out.println("Writing Code.");
//				try {
//					LBCodeGen.GenCode();
//				} catch (IOException e1) {
//					System.out.println("Failed to generate brightscript code!");
//					e1.printStackTrace();
//				}
//			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("Key Pressed");
			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			System.out.println("Key Released");
			
		}
		
	};

	public LayoutWindow(MainController mc) {
		
		this.mc = mc;
		
		this.setVisible(true);
		this.setSize(this.width, this.height);
		this.setLocation(360, 0);
		  
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		addMouseListener(ma);
		this.addKeyListener(kl);
	}
	
	public void DrawLevel() {
		
		this.getContentPane().removeAll(); //// **** Remember this pain in the neck!! Calling this.removeAll() will remove the rootPane & disallow any further rendering in this JFRame
		
		for(LBSpriteInstance si : level.SpriteInstanceArray){
			
			// TODO Do something smarter here to cache sprite image data
			String path = db.GamePath + "components/sprites/" + db.SpriteArray.get(si.dbIndex).filename;
			
			JSprite sprite = new JSprite(new ImageIcon(path).getImage(), si );
			
			
			MouseAdapter ma = new MouseAdapter() {
				
				private int x_start;
				private int y_start;
				
                @Override
                public void mousePressed(MouseEvent e) {
                	JSprite js = (JSprite) e.getComponent();
                	System.out.println("Clicked point " + js.getLocation() + ", width" + js.getWidth());
                	
                	// Check bounds
                    java.awt.Point p = new java.awt.Point(e.getLocationOnScreen());
                    SwingUtilities.convertPointFromScreen(p, e.getComponent());
                    
                    if(!e.getComponent().contains(p)) {return;}
                    
                	//Point l = js.getLocation();
                	x_start = e.getXOnScreen();
                	y_start = e.getYOnScreen();
                	
                }

                @Override
                public void mouseDragged(MouseEvent e) {

                	int x_end = e.getXOnScreen();
                	int y_end = e.getYOnScreen();
                	
                	//Point delta = new Point(x_end - x_start, y_end - y_start);

                	JSprite js = (JSprite) e.getComponent();

                	System.out.println("Point Before" + js.getLocation() );
                	
                	Point p = js.getLocation();
                	js.myX = p.x + (x_end - x_start);
                	js.myY = p.y + (y_end - y_start);
                	
                	System.out.println("Point After" + js.getLocation() );
                	
                	repaint();
                	
                	x_start = x_end;
                	y_start = y_end;
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {

                	JSprite js = (JSprite) e.getComponent();
                	
                	//Update the database
                	js.si.x = (int) js.myX;
                	js.si.y = (int) js.myY;
                	
                	repaint();
                	
                }
			};

			sprite.addMouseListener(ma);
			sprite.addMouseMotionListener(ma);
			
			add(sprite);
			
		}
		
		repaint();
		setVisible(true);
		//setSize(1280,720);
		  
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	
//	public static void main(String args[]){
//		MainWindow win = new MainWindow();
//		win.setVisible(true);
//		win.setSize(win.width, win.height);
//		  
//		win.setDefaultCloseOperation(EXIT_ON_CLOSE);
//	}

}

//Represents a sprite instance, used internally to MainWindow
class JSprite extends JPanel {

	  private Image img;
	  public int dbIndex;
	  public LBSpriteInstance si;
	  public int myX;
	  public int myY;
	  public Dimension mySize;
	  private LBDatabase db = LBDatabase.getInstance();


	  public JSprite(Image img, LBSpriteInstance si) {
	    this.img = img;
	    this.si = si;
	    this.mySize = new Dimension(db.SpriteArray.get(si.dbIndex).width, db.SpriteArray.get(si.dbIndex).height);

	    this.myX = si.x;
	    this.myY = si.y;

	    setSize(this.mySize);
	  }

	  public void paintComponent(Graphics g) {
		  this.setSize(this.mySize);
		  this.setLocation(myX, myY);
		  
		  super.paintComponent(g);
		  g.drawImage(img, 0, 0, null);
	  }
	  
	  public Dimension getImageSize(){
		  return new Dimension(img.getWidth(null), img.getHeight(null));
	  }

}
