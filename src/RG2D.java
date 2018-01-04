import java.io.IOException;

import model.LBCodeGen;
import model.LBDatabase;
import model.LBLevel;
import view.MainWindow;
import view.SpriteWindow;

public class RG2D {

	public static void main(String args[]) throws IOException{
		
		
		LBDatabase db = LBDatabase.getInstance();
		
		// Test stuff
		db.GamePath = "/Users/ben/Documents/workspace/FiretruckGame2D/";
		int il = db.CreateNewLevel("Level_01", 1);
		
		db.LoadSpriteDirectory();
		
		LBLevel l = db.LevelArray.get(il);

		l.addSprite(0, 10, 0);
		l.addSprite(1, 100, 50);
		
		db.print();
		// End test stuff
		
		MainWindow win = new MainWindow();
		SpriteWindow swin = new SpriteWindow();
		
		win.swin = swin;
		
		win.DrawLevel(l);
		
		
	}
	
}
