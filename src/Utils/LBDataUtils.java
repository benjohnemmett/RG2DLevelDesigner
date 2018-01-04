package Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.LBDatabase;

public class LBDataUtils {

	public LBDataUtils() {
		// TODO Auto-generated constructor stub
		
	}
	
	public static void writeData(String filename) throws IOException{
		// Write to disk with FileOutputStream
		FileOutputStream f_out = new 
			FileOutputStream(filename);

		// Write object with ObjectOutputStream
		ObjectOutputStream obj_out = new
			ObjectOutputStream (f_out);

		// Write object out to disk
		obj_out.writeObject ( LBDatabase.getInstance() );
		
	}
	
	public static void readData(String filename) throws IOException, ClassNotFoundException{
		// Read from disk using FileInputStream
		FileInputStream f_in = new 
			FileInputStream(filename);

		// Read object using ObjectInputStream
		ObjectInputStream obj_in = 
			new ObjectInputStream (f_in);

		// Read an object
		Object obj = obj_in.readObject();

		if (obj instanceof LBDatabase)
		{
			// Cast object
			LBDatabase db = (LBDatabase) obj;
			
			LBDatabase.setInstance(db);
		}
	}

}
