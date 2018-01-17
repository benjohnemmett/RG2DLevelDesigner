package control;

import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.event.ListSelectionEvent;

import model.LBCodeGen;
import model.LBDatabase;
import model.LBGroup;
import model.LBSpriteInstance;
import view.GroupWindow;
import view.LayoutWindow;
import view.LevelWindow;
import view.SpriteWindow;

public class MainController {
	
	public LayoutWindow lwin;
	public SpriteWindow swin;
	public LevelWindow vwin;
	public GroupWindow gwin;
	public LBDatabase db;

	public MainController() {

		this.vwin = new LevelWindow(this);
		this.swin = new SpriteWindow(this);
		this.lwin = new LayoutWindow(this);
		this.gwin = new GroupWindow(this);
		
		db = LBDatabase.getInstance();
		
		// Test stuff
		db.GamePath = "/Users/ben/Documents/workspace/FiretruckGame2D/";
		db.LoadSpriteDirectory();
		
		int il = db.CreateNewLevel("Level_01", 1);
		
		lwin.level = db.LevelArray.get(il);
		
		swin.DisplaySpriteList();
		vwin.DisplayLevelList();
		
	}
	
	public void keyTypedHandler(KeyEvent e){
		//System.out.println("MC:Key Typed " + e.getKeyChar());
		
		switch(e.getKeyChar()){
			case KeyEvent.VK_BACK_SPACE:{
				System.out.println("DELETE!!!");
				
				lwin.level.SpriteInstanceArray.remove(lwin.selectedSprite);
				lwin.selectedSprite = null;
				
				lwin.DrawLevel();
				
				break;
			}
			case 'g':{
				int nextGroupIdx = db.GroupArray.size() + 1;
				int ig = db.CreateNewGroup("Group_0" + nextGroupIdx);
				
				gwin.activeGroup = ig;
				
				//swin.DisplaySpriteList();
				//vwin.DisplayLevelList();
				gwin.DisplayGroupList();
				break;
				
			}
			case 'l':{
				int nextLevel = db.LevelArray.size() + 1;
				int il = db.CreateNewLevel("Level_0" + nextLevel, nextLevel);
				
				lwin.level = db.LevelArray.get(il);
				vwin.activeLevel = il;
				
				swin.DisplaySpriteList();
				vwin.DisplayLevelList();
				break;
				
			}
			case 'o':{

				db.loadDatabase(db.GamePath + "Data.bin");

				//Draw first level by default
				// TODO get level selected my level window or, set level window selection to 0.
				lwin.level = db.LevelArray.get(0);
				
				//UPdate views
				lwin.DrawLevel();
				swin.DisplaySpriteList();
				vwin.DisplayLevelList();
				gwin.DisplayGroupList();
				break;
				
			} 
			case 's':{
	
					System.out.println("Saving Data.");
					db.saveDatabase(db.GamePath + "Data.bin");	
					break;
			} 
			case 'w':{

				System.out.println("Writing Code.");
				try {
					LBCodeGen.GenCode();
				} catch (IOException e1) {
					System.out.println("Failed to generate brightscript code!");
					e1.printStackTrace();
				}
				break;
			}
			default: {
				System.out.println("MC: Warning: " + e.getKeyChar() + " Key pressed. Doing nothing about it.");
			}
		
		}

	}
	public void levelSelectionChangeHandler(ListSelectionEvent e) {

		System.out.println("MC: Level selection changed");
		
		//Update level
		if(vwin.activeLevel >= 0){
			lwin.level = db.LevelArray.get(vwin.activeLevel);
			lwin.DrawLevel();
		}
		
	}
	
	public void groupSelectionChangeHandler(){
		//Get active group
		int ag = this.gwin.groupList.getSelectedIndex();
		
		//Check if valid selection is made
		if(ag < 0){
			return;
		}
		
		LBGroup g = db.GroupArray.get(ag);
		
		//set active sprite instance to selected group
		if(lwin.selectedSprite != null){
			lwin.selectedSprite.group = g;
		}
		
	}
	
	/**
	 * Checks the sprite window and returns true if a sprite is selected. 
	 * 
	 * @return
	 */
	public boolean validSpriteSelected() {
		
		int idx = swin.spriteList.getSelectedIndex();
		
		if(idx < 0){
			return false;
		}
		return true;
		
	}
	/**
	 * Checks the sprite window and gets the index of the currently selected sprite.
	 * 
	 * @return
	 */
	public int getSelectedSpriteIndex(){

		return swin.spriteList.getSelectedIndex();
	}

	public int getSelectedGroupIndex() {

		return gwin.groupList.getSelectedIndex();

	}

	public void spriteInstanceClickHandler(LBSpriteInstance si) {
		
		int groupIdx = db.GroupArray.indexOf(si.group);

		if(groupIdx < 0){
			gwin.groupList.clearSelection();;
		} else {
			gwin.groupList.setSelectedIndex(groupIdx);
		}
		
		
	}



	
	

}
