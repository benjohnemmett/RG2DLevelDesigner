package view;

import java.awt.Color;
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
	
	public SpriteWindow swin;
	private LBDatabase db = LBDatabase.getInstance();
	
	public LBLevel level;
	public int activeLevel = 0;
	public LBSpriteInstance selectedSprite = null;
	
	
	public MainController mc;
	
	private MouseAdapter ma = new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			
			LayoutWindow win = (LayoutWindow) e.getComponent();
			int activeSprite = win.mc.getSelectedSpriteIndex();
			if(activeSprite < 0){ return; }
			
			int sii = level.addSprite(activeSprite, e.getX(), e.getY());
			
			//Add to group
			int activeGroup = win.mc.getSelectedGroupIndex();
			if(activeGroup >= 0){
				level.SpriteInstanceArray.get(sii).AddToGroup(db.GroupArray.get(activeGroup));
			}
			
			win.DrawLevel();

			System.out.println("Add new Object! " + e.getX() + ", " + e.getY());
			System.out.println("\t" + level.SpriteInstanceArray.get(sii));
		}
	};
	
	private KeyListener kl = new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {
			System.out.println("LWIN:Key Typed " + e.getKeyChar());
			mc.keyTypedHandler(e);

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
		this.setLocation(240, 0);
		  
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
			
			if(this.selectedSprite == si){
				//System.out.println(" * Found Selected sprite");
				sprite.isSelected = true;
			}
			
			MouseAdapter ma = new MouseAdapter() {
				
				private int x_start;
				private int y_start;
				
				@Override
				public void mouseClicked(MouseEvent e){
                	JSprite js = (JSprite) e.getComponent();

                    LayoutWindow lwin = ((LayoutWindow)((view.JSprite) e.getComponent()).getParent().getParent().getParent().getParent());
					lwin.selectedSprite = js.si;
					
                	System.out.println("Clicked point " + js.getLocation() + ", width" + js.getWidth() + " selected " + js.si);
                	
                	lwin.DrawLevel();
				}
				
                @Override
                public void mousePressed(MouseEvent e) {
                	JSprite js = (JSprite) e.getComponent();
                	System.out.println("Pressed point " + js.getLocation() + ", width" + js.getWidth());
                	
                	// Check bounds
                    java.awt.Point p = new java.awt.Point(e.getLocationOnScreen());
                    SwingUtilities.convertPointFromScreen(p, e.getComponent());
                    
                    //if(!e.getComponent().contains(p)) {return;}
                    
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

                	//System.out.println("Point Before" + js.getLocation() );
                	
                	Point p = js.getLocation();
                	js.myX = p.x + (x_end - x_start);
                	js.myY = p.y + (y_end - y_start);
                	
                	//System.out.println("Point After" + js.getLocation() );
                	
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
	  public  boolean isSelected = false;


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
		  
		  if(this.isSelected ){
			  g.setColor(new Color(255,0,0));
			  g.drawRect(1, 1, this.getWidth()-2, this.getHeight()-2);
		  }
	  }
	  
	  public Dimension getImageSize(){
		  return new Dimension(img.getWidth(null), img.getHeight(null));
	  }

}


