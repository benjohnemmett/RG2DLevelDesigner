package view;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import control.MainController;
import model.LBDatabase;
import model.LBGroup;
import model.LBSprite;

public class GroupWindow extends JFrame{
	
	public int width = 240;
	public int height = 300;
	public JList<String> groupList;
	public int activeGroup;
	public MainController mc;
	
	
	public GroupWindow(MainController mc) {
		
		this.mc = mc;
		
		this.setVisible(true);
		this.setSize(this.width, this.height);
		this.setLocation(0, 600);
		
		groupList = new JList<String>();
		
		this.add(groupList);
		
		//DisplayGroupList();
		  
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	
		groupList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				JList<?> list = (JList<?>)e.getSource();
				System.out.println("Group WIndow Selection changed! \n\t" + e.toString() + "\n\t" + list.getSelectedIndex());
				
//				LevelWindow vwin = (LevelWindow)list.getParent().getParent().getParent().getParent();
//				vwin.activeLevel = list.getSelectedIndex();
//				
//				mc.levelSelectionChangeHandler(e);
				
			}
			
		});
		
		//this.addKeyListener(kl);
	}
	
	public void DisplayGroupList(){
		LBDatabase db = LBDatabase.getInstance();
		
		DefaultListModel<String> groupNames = new DefaultListModel<String>();
		
		for(LBGroup g : db.GroupArray){
			groupNames.addElement(g.name);
		}
		
		groupList.setModel(groupNames);
	}

}
