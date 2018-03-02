package ctt;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.HashSet;

public class PrepareAllClasses implements Printable
{	
	
	
	
	public void show(String msg) 
	{JOptionPane.showMessageDialog(null, msg);}
	public void show(int msg)
	{JOptionPane.showMessageDialog(null, msg);}
	public void show(long msg)
	{JOptionPane.showMessageDialog(null, msg);}
	

	
	public boolean ALL=false;
	int totalpages,totallines;
    ArrayList<String> oldList=new ArrayList<String>();
	ArrayList<String> newList;
	
	View view;
	int ROWPOINTER;
	public void setView(View vu)  {	this.view=vu; }
             
	PrintAllIndies PAI;
	  ///initialize printing parameters
  //	JTable table;

	  int timecolsize,othercolsize,linesperpage;
      
      PrepareAllClasses()
      {   
    	  timecolsize=100;othercolsize=65;linesperpage=42;
    	  PAI=new PrintAllIndies(); 
    	  PAI.setView(view);
      }
      
	///// Here the whole  JAVA Printing Mechanism Starts 
	/////  Note 'implements Printable above', It includes the Print Mechanism to our Program
	/////
	

	public PrintService findPrintService(String printerName)  //supporting function
    {
        for (PrintService service : PrinterJob.lookupPrintServices())
        {
            if (service.getName().equalsIgnoreCase(printerName))
                return service;
        }
        return null;
    }
		
	  public void PrintIndividuals(String printername)
              {
		  PrintService ps = findPrintService(printername);
		  if(ps==null) ps = PrintServiceLookup.lookupDefaultPrintService(); 
		  if(ps==null) return;
		
		  
	         PrinterJob job = PrinterJob.getPrinterJob();
	         job.setJobName("Indi");
	         try {
				job.setPrintService(ps);
			} catch (PrinterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         job.setPrintable(this);
	        
	         ////Widening the print AREA.
	         //Java Keeps preset Margins of about 1 inch on all corners
             //Top Left Corner is  (0,0), then width and height
	         HashPrintRequestAttributeSet pattribs=new HashPrintRequestAttributeSet();
	         pattribs.add(new MediaPrintableArea(0, 0, 210, 297, MediaPrintableArea.MM));
	         // 210 x 297  A4 size paper in Millimiters.
	  
	         boolean ok = true; ///job.printDialog();
	         if (ok) 
	             try {
	                  job.print(pattribs);
	             } catch (PrinterException ex) {}
	         }
	
	public int print(Graphics g, PageFormat pf, int pageno)
			throws PrinterException
	{
		int tlx=(int) pf.getImageableX()+70,tly=(int) pf.getImageableY()+10;
		int w=(int) pf.getImageableWidth()-20,h=(int)pf.getImageableHeight()-20;
		
		 if (pageno >totalpages)              // We have only one page, and 'page no' is zero-based
		    {  return NO_SUCH_PAGE;  // After NO_SUCH_PAGE, printer will stop printing.
	        }
		 Font MyFont = new Font("Courier", Font.PLAIN,10);
		 g.setFont(MyFont);
		 PAI.setView(view);
		 
		 PAI.PrintOnePage(tlx,tly,g,pageno,linesperpage);  ///left, top and graphics g
	    // System.out.println("printing ends");
	
	     return 0;
	 }
	
	 int GetLastRow()
	    {    String temp="";
	    	 int currow=0;    	
	    	    for(currow=view.MROWS-1;currow>0;currow--)
	    	    	{ temp=view.Matrix[currow][1]; 
	    	    	  if(temp.length()>0) return currow;
	    	    	}
	    		return currow;
	    }

	
	void CollectAllClasses()
	{
	oldList.removeAll(oldList);
	
	for(int currentrow=0;currentrow<view.ROWCOUNT-1;currentrow++)
	       {String str=view.GetData(view.table,currentrow,0);
	        if(str.length()==0) continue;
	        if(str.contains(":")) continue;       
  	       oldList.add(str);
	       
	       }
		
	ArrayList<String> newList = new ArrayList<String>(new HashSet<String>(oldList));
  
//	System.out.println(newList.size());
    for(int i=0;i<newList.size();i++)
    	System.out.println(newList.get(i));

	ROWPOINTER=0;
	int currentpage=1;
	String sr="";
	view.ClearIndividualTable();
	int TotalClasses=3; //newList.size();
	
	for(int i=0;i<2;i++)
	  { 
		//int lt=GetLastTime();
		
		view.CreateClass(newList.get(i));
	    view.DeleteLastTimeSlot();
	    PrepareSingleClassToPrint();
	  
	  //  int maxsplit=0; // maximum split count for time slot
	      
	  }
	  view.SetData2("$END",ROWPOINTER,0);
	  ROWPOINTER++;
       /*
	    int lr=GetLastRow();
        if(currentrow+lr+4>currentpage*linesperpage)  //lr+blankline+FF=lr+2 
         { sr.format("%d-",currentrow);
           view.table2.setValueAt("$END", currentrow,0);
           
           currentrow=currentpage*linesperpage;  
           String str=String.format("$FF %d",currentrow);
           view.table2.setValueAt(str, currentrow,0);
           currentpage++;currentrow++;
         }
        sr.format("%d-",currentrow);
      //  lcount.format("-MM%d",view.lecturecount);
       view.table2.setValueAt(view.LectureCount,currentrow,0); 
       view.table2.setValueAt("SIWS College",currentrow,1);
       view.table2.setValueAt(view.allcounts,currentrow,2);
       currentrow++;
        for (int r = 0; r <= lr; r++)
  	       { for(int c = 0; c < 7; c++)
  	    	   
  	    	   {sr.format("%d-",currentrow);
  	    	    view.table2.setValueAt(view.Matrix[r][c], currentrow,c);
  	    	   
  	    	   }
  	           currentrow++; 
  	       }
        view.table2.setValueAt("$BLANKLINE", currentrow,0);
        
        currentrow++;  ///blank row between two individuals
	  }	
	view.table2.setValueAt("$END", currentrow,0);
	 totalpages=currentpage-1;
   */

	}
	
	
	void PrepareSingleClassToPrint()
    { int lt=GetLastTime();
     // show(lt);
//      view.ClearIndividualTable();
      int maxsplit=0; // maximum split count for time slot
      String temp,temp1[];
      for(int i=0;i<=lt;i++)
	  {
		 temp=view.Matrix[i][0];
		 if(temp.length()==0) continue; //skip blank line
	
       view.SetData2(temp,ROWPOINTER,0);  ///Time - Copy as it is
       maxsplit=1;
       for(int j=1;j<7;j++) ///check lecture cells
       {temp=view.Matrix[i][j];
    	if(!temp.contains(","))
    		{ view.SetData2(temp,ROWPOINTER,j);  ///No "," so Copy as it is
    		 continue;
    		}
    		// else at least one comma exists
    			temp1=temp.split(",");
    			int count=temp1.length;
    			for(int k=0;k<count;k++)
    				{view.SetData2(temp1[k],ROWPOINTER+k,j);
    				
    				}
    			if(maxsplit<count) maxsplit=count;
       }
       ROWPOINTER=ROWPOINTER+maxsplit;
	  }
	  view.SetData2("$BLANKLINE",ROWPOINTER,0);
	  ROWPOINTER++;
   }
    
	

    
    int GetLastTime()
    {    String temp="";
    	 int currentrow=0;    	
    		////Get First Time Slot
    	    for(currentrow=view.MROWS-1;currentrow>0;currentrow--)
    	    	{ temp=view.Matrix[currentrow][0]; 
    	    	  if(temp.length()>0) break;
    	    	}
    		return currentrow;
    }
	
	
}	
