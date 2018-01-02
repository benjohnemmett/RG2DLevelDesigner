package view;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import model.LBDatabase;
import model.LBLevel;
import model.LBSpriteInstance;

// Based on help from http://mrbool.com/showing-images-in-a-swing-jframe-in-java/24594

public class MainWindow extends JFrame{
	
	public int width = 1280;
	public int height = 720;
	public int activeLevel = 0;
	public SpriteWindow swin;
	
	private MouseAdapter ma = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			MainWindow win = (MainWindow) e.getComponent();
			
			LBDatabase db = LBDatabase.getInstance();
			LBLevel l = db.LevelArray.get(win.activeLevel);
			int activeSprite = win.swin.spriteList.getSelectedIndex();
			
			l.addSprite(activeSprite, e.getX(), e.getY());
			
			win.DrawLevel(l);
			
			System.out.println("Add new Object! " + e.getX() + ", " + e.getY());
		}
	};

	public MainWindow(int width, int height) {

		this.width = width;
		this.height = height;
		
		this.setVisible(true);
		this.setSize(this.width, this.height);
		  
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		addMouseListener(ma);
	}

	public MainWindow() {
		
		this.setVisible(true);
		this.setSize(this.width, this.height);
		  
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		addMouseListener(ma);
	}
	
	public void DrawLevel(LBLevel l) {
		
		this.getContentPane().removeAll(); //// **** Remember this pain in the neck!! Calling this.removeAll() will remove the rootPane & disallow any further rendering in this JFRame
		
		LBDatabase db = LBDatabase.getInstance();
		
		for(LBSpriteInstance si : l.SpriteInstanceArray){
			
			// TODO Do something smarter here to cache sprite image data
			String path = db.GamePath + "components/sprites/" + db.SpriteArray.get(si.dbIndex).name;
			
			JSprite sprite = new JSprite(new ImageIcon(path).getImage(), si );
			
			
			MouseAdapter ma = new MouseAdapter() {
				
				private int x_start;
				private int y_start;
				
                @Override
                public void mousePressed(MouseEvent e) {
                	JSprite js = (JSprite) e.getComponent();
                	System.out.println("Clicked point " + js.getLocation() + ", width" + js.getWidth());
                	
                	// Set back to correct size TODO: Determine where incorrect size is set
                	js.setSize(js.getImageSize());
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
                	
                	p.translate(x_end - x_start, y_end - y_start);
                	
                	js.setLocation(p);
                	
                	System.out.println("Point After" + js.getLocation() );
			
                	System.out.println("Released " + this);
                	
                	repaint();
                	
                	x_start = x_end;
                	y_start = y_end;
                }
                
                @Override
                public void mouseReleased(MouseEvent e) {

                	JSprite js = (JSprite) e.getComponent();
                	
                	//Update the database
                	js.si.x = (int) js.getLocation().getX();
                	js.si.y = (int) js.getLocation().getY();
                	
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


	  public JSprite(Image img, LBSpriteInstance si) {
	    this.img = img;
	    this.si = si;
	    Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));

	    this.setLocation(new Point(si.x, si.y));
	    
	    setSize(size);
	  }

	  public void paintComponent(Graphics g) {
		  super.paintComponent(g);
		  g.drawImage(img, 0, 0, null);
	  }
	  
	  public Dimension getImageSize(){
		  return new Dimension(img.getWidth(null), img.getHeight(null));
	  }

}
