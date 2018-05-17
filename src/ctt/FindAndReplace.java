package ctt;

import java.awt.Rectangle;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

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
	   view.toast.AutoCloseMsg(strmsg);
	} // function ends
	
		
	void Find()
	{
		 JTextField FindField = new JTextField();
		  // By passing a list of option strings (and a default of null), the focus goes in your custom component
		  String[] options = {"OK", "Cancel"};
		  int result = JOptionPane.showOptionDialog(null,FindField, "Enter String To Find", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
    

		  if(result==JOptionPane.OK_OPTION)
		  {
			  findstring=FindField.getText();

			  String temp;
				
				for(currentrow=0;currentrow<view.ROWCOUNT-1;currentrow++)	
				 for(currentcol=0;currentcol<7;currentcol++)
			     	 { 
				      temp=view.GetData(view.table, currentrow, currentcol);
				    
				      if(temp.length()==0) continue;
				      if(temp.contains(findstring)) 
				   		 { 
				   		 
				 		  view.table.setColumnSelectionInterval(currentcol,currentcol);
				 		 view.table.setRowSelectionInterval(currentrow,currentrow);
				 		view.table.scrollRectToVisible(new Rectangle(view.table.getCellRect(currentrow, 0, true)));
				   		 return;
				   		 }
			     	 
			     	 } //for loop ends
		  }
		  
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
