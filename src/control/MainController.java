package control;

import model.LBDatabase;
import view.LayoutWindow;
import view.LevelWindow;
import view.SpriteWindow;

public class MainController {
	
	public LayoutWindow lwin;
	public SpriteWindow swin;
	public LevelWindow vwin;
	public LBDatabase db;

	public MainController() {

		this.lwin = new LayoutWindow(this);
		this.swin = new SpriteWindow(this);
		this.vwin = new LevelWindow(this);
		
		//Test Stuff
		db = LBDatabase.getInstance();
		
		// Test stuff
		db.GamePath = "/Users/ben/Documents/workspace/FiretruckGame2D/";
		db.LoadSpriteDirectory();
		swin.DisplaySpriteList();
		
		int il = db.CreateNewLevel("Level_01", 1);
		
		lwin.level = db.LevelArray.get(il);
		
	}

	public boolean validSpriteSelected() {
		
		int idx = swin.spriteList.getSelectedIndex();
		
		if(idx < 0){
			return false;
		}
		return true;
		
	}
	
	public int getSelectedSpriteIndex(){

		return swin.spriteList.getSelectedIndex();
	}
	
	

}
