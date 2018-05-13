package ctt;

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;

public class FindAndReplace 
{ View view;
  int counter=0;
  private String findstring="";
  private int currentrow=0,currentcol=0;
	public void setView(View vu)
	{
		this.view=vu;
	}
	
	
	public void FindAndReplaceRoutine()
	{
		JTextField Find = new JTextField();
		JTextField Replace = new JTextField();
		String find,replace;
		Object[] message = 
		{
		    "Find :", Find,
		    "Replace :", Replace
		};

		int option = JOptionPane.showConfirmDialog(null, message, "Find and Replace", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) 
		{
			  findstring=Find.getText();
			  replace=Replace.getText();
		
		        //System.out.println(Find.getText());
		      //  System.out.println(Replace.getText());
		    }  
		else return;
		
		counter=0;
		
		String temp;
		
		for(int r=0;r<view.ROWCOUNT-1;r++)	
		 for(int c=0;c<7;c++)
	     	 { 
		      temp=view.GetData(view.table, r, c);
		      
		      if(temp.length()==0) continue;
		      if(temp.contains(findstring)) 
		   		 { String str=temp.replace(findstring,replace);	
		   		   view.SetData(str, view.table,r,c);
		   		   counter++;
		   		 }
	     	 
	     	 } //for loop ends
		String strmsg=String.format("Replaced %d Occurrences",counter);
		Toast.AutoCloseMsg(strmsg);
	} // function ends
	
	
	/*
	public static void scrollToVisible(JTable table, int rowIndex, int vColIndex) {
        if (!(table.getParent() instanceof JViewport)) {
            return;
        }
        JViewport viewport = (JViewport)table.getParent();

        // This rectangle is relative to the table where the
        // northwest corner of cell (0,0) is always (0,0).
        Rectangle rect = table.getCellRect(rowIndex, vColIndex, true);

        // The location of the viewport relative to the table
        Point pt = viewport.getViewPosition();

        // Translate the cell location so that it is relative
        // to the view, assuming the northwest corner of the
        // view is (0,0)
        rect.setLocation(rect.x-pt.x, rect.y-pt.y);

        table.scrollRectToVisible(rect);

        // Scroll the area into view
        //viewport.scrollRectToVisible(rect);
    }
	
*/
	
	void Find()
	{
		 JTextField FindField = new JTextField();
		  // By passing a list of option strings (and a default of null), the focus goes in your custom component
		  String[] options = {"OK", "Cancel"};
		  int result = JOptionPane.showOptionDialog(null,FindField, "Enter a name", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
    
		//  view.table.requestFocus();
		  //
		//  view.table.setColumnSelectionInterval(1,1);
		//  view.table.setRowSelectionInterval(7,7);

		  if(result==JOptionPane.OK_OPTION)
		  {
			  findstring=FindField.getText();

			  String temp;
				
				for(int r=0;r<view.ROWCOUNT-1;r++)	
				 for(int c=0;c<7;c++)
			     	 { 
				      temp=view.GetData(view.table, r, c);
				    
				      if(temp.length()==0) continue;
				      if(temp.contains(findstring)) 
				   		 { 
				   		 
				   		 //scrollToVisible(view.table,r,c);
				 		//  view.table.changeSelection(50,1,false, false);
				 		  view.table.setColumnSelectionInterval(c,c);
				 		 view.table.setRowSelectionInterval(r,r);
				 		view.table.scrollRectToVisible(new Rectangle(view.table.getCellRect(r, 0, true)));
				   		 return;
				   		 }
			     	 
			     	 } //for loop ends
		  }
		  
		  // 
	}
	
	
	void NextFind()
	{   
		if(findstring==null) return;
		if(findstring.length()==0) return;
		String temp;
//	    boolean found=false;
	
	    while(true)
	    {
	    	currentcol++;
	    	if(currentcol>6) { currentcol=0; currentrow++; }
	    	if(currentrow>view.ROWCOUNT-2) { currentrow=0;currentcol=0;return;}
            
	        temp=view.GetData(view.table, currentrow,currentcol);
		    
		      if(temp.length()==0) continue;
		      if(temp.contains(findstring)) 
		   		 { 
		   		 
		   		
		   		if (view.table.isEditing()) view.table.getCellEditor().stopCellEditing();
		   	 
		   		 //  view.table.changeSelection(50,1,false, false);
		 		  view.table.setColumnSelectionInterval(currentcol,currentcol);
		 		  view.table.setRowSelectionInterval(currentrow,currentrow);		
		 		
		 		//view.table.getSelectionModel().setSelectionInterval(i, i);
		 		view.table.scrollRectToVisible(new Rectangle(view.table.getCellRect(currentrow, 0, true)));
		 		  
		 		  
		 		  return;
		   		 }
	    
	    }
				
		
	}
}
