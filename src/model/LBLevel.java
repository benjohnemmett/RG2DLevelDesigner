package model;

import java.util.ArrayList;

public class LBLevel implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5467844722801133491L;
	
	public String name;
	public int number;
	public ArrayList<LBSpriteInstance> SpriteInstanceArray = new ArrayList<LBSpriteInstance>();

	public LBLevel(String name, int number) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.number = number;
	}
	
	public void addSprite(int DBIndex, int x, int y) {
		LBSpriteInstance si = new LBSpriteInstance(DBIndex, x, y);
		this.SpriteInstanceArray.add(si);
	}
	
	public String toString(){
		return String.valueOf(this.number) + " : " + this.name + " [ " + String.valueOf(this.SpriteInstanceArray.size()) + " sprites ]"; 
	}

}
