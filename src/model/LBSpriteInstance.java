package model;

public class LBSpriteInstance implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9071085102756474118L;
	
	public int x;
	public int y;
	public int dbIndex;
	public LBGroup group = null;

	public LBSpriteInstance()  {
		// TODO Auto-generated constructor stub
	}

	public LBSpriteInstance(int DBIndex, int x, int y) {
		// TODO Auto-generated constructor stub
		this.dbIndex = DBIndex;
		this.x = x;
		this.y = y;
	}
	
	public void AddToGroup(LBGroup g){
		this.group = g;
	}
	
	public String toString(){
		if(group == null){
			return "LBSPriteInstance: " + this.dbIndex + ", (" + this.x + "," + this.y + ") Group: NULL"; 
		} else {
			return "LBSPriteInstance: " + this.dbIndex + ", (" + this.x + "," + this.y + ") Group: " + this.group.name; 
		}
	}

}
