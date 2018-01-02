import model.LBDatabase;
import model.LBLevel;
import view.MainWindow;
import view.SpriteWindow;

public class RG2D {

	public static void main(String args[]){
		
		MainWindow win = new MainWindow();
		SpriteWindow swin = new SpriteWindow();
		
		LBDatabase db = LBDatabase.getInstance();
		
		// Test stuff
		db.GamePath = "/Users/ben/Documents/workspace/FiretruckGame2D/";
		int il = db.CreateNewLevel("Level_01", 1);
		
		int is = db.CreateNewSprite("texture_brick01_60p.png", 60, 60);
		
		LBLevel l = db.LevelArray.get(il);

		l.addSprite(is, 10, 0);
		l.addSprite(is, 100, 50);
		
		db.print();
		
		win.DrawLevel(l);
		
	}
	
}
