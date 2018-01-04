package model;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/** 
 * 
 * @author ben
 *	Singleton database to hold all levels and sprites
 */
public class LBDatabase implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7477137254720794479L;
	
	private static LBDatabase myDB;
	public String GamePath; // Path to the game being built
	public ArrayList<LBLevel> LevelArray = new ArrayList<LBLevel>();
	public ArrayList<LBSprite> SpriteArray = new ArrayList<LBSprite>();
	
	private LBDatabase() {
	}
	
    public static LBDatabase getInstance(){
        if(myDB == null){
        	myDB = new LBDatabase();
        }
        return myDB;
    }
    
    public static void setInstance(LBDatabase db){
    	myDB = db;
    }
    
    public void print(){
    	System.out.println("Levels : ");
    	for(LBLevel l : this.LevelArray){
    		System.out.println("\t" + l);
    	}
    	
    	System.out.println("Sprites : ");
    	for(LBSprite s : this.SpriteArray){
    		System.out.println("\t" + s);
    	}
    }
    
    public int CreateNewSprite(String name){
    	
    	LBSprite s = new LBSprite(name);
    	
    	if( this.SpriteArray.add(s)){
    		return this.SpriteArray.size() -1;
    	}
    	
    	return -99;
    	
    }
    
    public int CreateNewLevel(String name, int number) {
    	
    	LBLevel l = new LBLevel(name, number);
    	
    	if( this.LevelArray.add(l) ){
    		return this.LevelArray.size() - 1;
    	}
    	
    	return -99;
    	
    }
    
    public void LoadSpriteDirectory() {
    	File dir = new File(this.GamePath + "/components/sprites/");
    	File[] files = dir.listFiles(new FilenameFilter() {
    	    public boolean accept(File dir, String name) {
    	        return name.toLowerCase().endsWith(".png");
    	    }
    	});
    	
    	for(File f : files){
    		System.out.println(f.getName());
    		this.CreateNewSprite(f.getName());
    	}
    	
    	
    }

	public static void main(String[] args) {
		
		LBDatabase db = LBDatabase.getInstance();
		
		db.CreateNewLevel("Level01", 1);
		
		db.print();

	}

}
