package model;

import java.util.ArrayList;

/** 
 * 
 * @author ben
 *	Singleton database to hold all levels and sprites
 */
public class LBDatabase {

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
    
    public int CreateNewSprite(String name, int width, int height){
    	
    	LBSprite s = new LBSprite(name, width, height);
    	
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

	public static void main(String[] args) {
		
		LBDatabase db = LBDatabase.getInstance();
		
		db.CreateNewLevel("Level01", 1);
		
		db.print();

	}

}
