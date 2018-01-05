package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    
    public void saveDatabase(String filename) {
    	
    	
		FileOutputStream f_out = null;
		try {
			f_out = new  FileOutputStream(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Write object with ObjectOutputStream
		ObjectOutputStream obj_out = null;
		try {
			obj_out = new ObjectOutputStream (f_out);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Write object out to disk
		try {
			obj_out.writeObject(this.GamePath);
			obj_out.writeObject(this.SpriteArray);
			obj_out.writeObject(this.LevelArray);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void loadDatabase(String filename){
		// Read from disk using FileInputStream
		FileInputStream f_in = null;
		try {
			f_in = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// Read object using ObjectInputStream
		ObjectInputStream obj_in = null;
		try {
			obj_in = new ObjectInputStream (f_in);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Read Game Path
		Object obj1 = null;
		try {
			obj1 = obj_in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (obj1 instanceof String)
		{
			// Cast object
			this.GamePath = (String) obj1;
		}

		// Read Sprite Array
		Object obj2 = null;
		try {
			obj2 = obj_in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (obj2 instanceof ArrayList<?>)
		{
			// Cast object
			this.SpriteArray = (ArrayList<LBSprite>) obj2;
		}
		


		// Read Level Array
		Object obj3 = null;
		try {
			obj3 = obj_in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (obj3 instanceof ArrayList<?>)
		{
			// Cast object
			this.LevelArray = (ArrayList<LBLevel>) obj3;
		}
		
		
		
    }

	public static void main(String[] args) {
		
		LBDatabase db = LBDatabase.getInstance();
		
		db.CreateNewLevel("Level01", 1);
		
		db.print();

	}

}
