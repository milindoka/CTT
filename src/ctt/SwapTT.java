package ctt;

import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;


public class SwapTT 
{ View view;
  int counter=0;
	public void setView(View vu)
	{
		this.view=vu;
		
	}
	
	
	public void SwapRoutine()
	{
		JTextField t1 = new JTextField();
		JTextField t2 = new JTextField();
		String str1,str2;
		Object[] message = 
		{
		    "Teacher Code 1 :", t1,
		    "Teacher Code 2 :", t2
		};

		int option = JOptionPane.showConfirmDialog(null, message, "Swap Time Tables ", JOptionPane.OK_CANCEL_OPTION);
		if (option == JOptionPane.OK_OPTION) 
		{
			  str1=t1.getText();
			  str2=t2.getText();
		
		        //System.out.println(Find.getText());
		      //  System.out.println(Replace.getText());
		    }  
		else return;
		
		//counter=0;
		
		String temp;
		
		for(int r=0;r<view.ROWCOUNT-1;r++)	
		  for(int c=0;c<7;c++)
	     	 { 
		      temp=view.GetData(view.table, r, c);
		      
		      if(temp.length()==0) continue;
		      if(temp.contains(str1)) 
		   		 { String str=temp.replace(str1,"$$");	
		   		   view.SetData(str, view.table,r,c);
		 //  		   counter++;
		   		 }
	     	 
	     	 } //for loop ends
		
		for(int r=0;r<view.ROWCOUNT-1;r++)	
			  for(int c=0;c<7;c++)
		     	 { 
			      temp=view.GetData(view.table, r, c);
			      if(temp.length()==0) continue;
			      if(temp.contains(str2)) 
			   		 { String str=temp.replace(str2,str1);	
			   		   view.SetData(str, view.table,r,c);
			   		 }
		     	 
		     	 } //for loop ends
		
		counter=0;
		for(int r=0;r<view.ROWCOUNT-1;r++)	
			  for(int c=0;c<7;c++)
		     	 { 
			      temp=view.GetData(view.table, r, c);
			      
			      if(temp.length()==0) continue;
			      if(temp.contains("$$")) 
			   		 { String str=temp.replace("$$",str2);	
			   		   view.SetData(str, view.table,r,c);
			   		   counter++;
			   		 }
		     	 
		     	 } //for loop ends
		
		
		
		
		String strmsg=String.format("Exchanged %d Entries",counter);
	    view.toast.AutoCloseMsg(strmsg);
	} // function ends
	
	
	
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
	

	
	
}
