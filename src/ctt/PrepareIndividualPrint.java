package ctt;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.HashSet;

public class PrepareIndividualPrint implements Printable
{	
	public boolean ALL=false;
	int totalpages,totallines;
    ArrayList<String> oldList=new ArrayList<String>();
	ArrayList<String> newList;
	
	View view;
	public void setView(View vu)  {	this.view=vu; }
             
	PrintAllIndies PAI;
	  ///initialize printing parameters
  //	JTable table;

	  int timecolsize,othercolsize,linesperpage;
      
      PrepareIndividualPrint()
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
	    		return currow+1;
	    }

	
	void CollectAllTeachers()
	{
	oldList.removeAll(oldList);
	
	for(int currentrow=0;currentrow<view.ROWCOUNT-1;currentrow++)
		for(int col=1;col<7;col++)
	       {String str=view.GetData(view.table,currentrow,col);
	        if(str.contains(","))
	         { String temp,temp2[];
	           temp2=str.split(",");
	           for(int j=0;j<temp2.length;j++)
	            {int left=temp2[j].indexOf("(");
	 	         int rite=temp2[j].indexOf(")");
		         if(left<0 || rite<0) continue;
		         String teachercode = temp2[j].substring(temp2[j].indexOf("("),temp2[j].indexOf(")")+1);
		         oldList.add(teachercode);
	            }
	           continue;
	         }
	       
	       
	       int left=str.indexOf("(");
	       int rite=str.indexOf(")");
	       if(left<0 || rite<0) continue;
	       String teachercode = str.substring(str.indexOf("("),str.indexOf(")")+1);
	       oldList.add(teachercode);
	       
	       }
		
	ArrayList<String> newList = new ArrayList<String>(new HashSet<String>(oldList));
  
   System.out.println(newList.size());
    for(int i=0;i<newList.size();i++)
     System.out.println(newList.get(i));

	int  currentrow=1;
	int currentpage=1;
	//String sr="";
	for(int i=0;i<newList.size();i++)
	  { view.CreateIndi(newList.get(i));
	    view.CountGaps();
	    view.CountDoubles();
	    view.DeleteLastTimeSlot();
	    view.CreatePerPerDivisionChart();
	    view.UpdateCounts(newList.get(i));
	    
       
	    int lr=GetLastRow();
        if(currentrow+lr+4>currentpage*linesperpage)  //lr+blankline+FF=lr+2 
         {// sr.format("%d-",currentrow);
           view.table3.setValueAt("$END", currentrow,0);
           
           currentrow=currentpage*linesperpage;  
           String str=String.format("$FF %d",currentrow);
           view.table3.setValueAt(str, currentrow,0);
           currentpage++;currentrow++;
         }
    //    sr.format("%d-",currentrow);
      //  lcount.format("-MM%d",view.lecturecount);
       view.table3.setValueAt(view.LectureCount,currentrow,0); 
       view.table3.setValueAt(view.collegename,currentrow,1);
       view.table3.setValueAt(view.allcounts,currentrow,2);
       currentrow++;
        for (int r = 0; r <= lr; r++)
  	       { for(int c = 0; c < 7; c++)
  	    	   
  	    	   {//sr.format("%d-",currentrow);
  	    	    view.table3.setValueAt(view.Matrix[r][c], currentrow,c);
  	    	   
  	    	   }
  	           currentrow++; 
  	       }
        view.table3.setValueAt("$BLANKLINE", currentrow,0);
        
        currentrow++;  ///blank row between two individuals
	  }	
	view.table3.setValueAt("$END", currentrow,0);
	 totalpages=currentpage-1;
	
	}
	
	
	
	
	
}	
