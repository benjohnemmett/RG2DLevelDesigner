package model;

import java.io.FileWriter;
import java.io.IOException;

public class LBCodeGen {

	private LBCodeGen() throws IOException {
		
	}
	
	public static void GenCode() throws IOException{
		GenLoadSprites();
		GenLoadLevel();
	}
	
	private static void GenLoadLevel() throws IOException{
		
		LBDatabase db = LBDatabase.getInstance();
		
		String filePath = db.GamePath + "/source/RG2DLoadLevel.brs";
		FileWriter fw = new FileWriter(filePath);
		
		// Start function
		fw.write("' Load up level specified by input argument\n");
		fw.write("function rg2dLoadLevel(level as integer) as void\n");
		fw.write("\tg = GetGlobalAA()\n\n");
		
		// Loop over each level
		for(LBLevel l : db.LevelArray){

			fw.write("\tif(level = " + l.number + ") then\n");
			
			for(LBSpriteInstance si : l.SpriteInstanceArray){
				fw.write("\t\tsSprite = g.compositor.NewSprite(" + si.x + ", " + si.y + ", " + "g.r" + db.SpriteArray.get(si.dbIndex).name + ")\n");
				fw.write("\t\tpoPhysObj = physObj(sSprite, invalid)\n");
				//TODO add capability to set collision groups
				 
			}
			
			fw.write("\tend if\n");
			
		}
		
		// End function
		fw.write("end function\n");
		
		fw.close();

	}
	
	private static void GenLoadSprites(){
		
	}

}
