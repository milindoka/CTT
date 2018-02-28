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

public class PrintIndi implements Printable
{	
	public boolean ALL=false;
	int totalpages,totallines;
    ArrayList<String> oldList=new ArrayList<String>();
	ArrayList<String> newList;
	View view;
	int PRINT_TYPE=0;
	public void setView(View vu)  {	this.view=vu; }
    public void SetPrintType(int pt) {PRINT_TYPE=pt;}         
	OnePageIndi opi;
	OnePageClass opc;
	  ///initialize printing parameters
  //	JTable table;

	  int timecolsize,othercolsize,linesperpage;
      
      PrintIndi()
      {   
    	  timecolsize=100;othercolsize=65;linesperpage=40;
    	  opi=new OnePageIndi(); 
    	  opi.setView(view);
    	  
    	  opc=new OnePageClass(); 
    	  opc.setView(view);
    	  
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
	         job.setJobName("current");
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
		
		 if (pageno >0)              // We have only one page, and 'page no' is zero-based
		    {  return NO_SUCH_PAGE;  // After NO_SUCH_PAGE, printer will stop printing.
	        }
		 Font MyFont = new Font("Courier", Font.PLAIN,10);
		 g.setFont(MyFont);
		 if(PRINT_TYPE==0)
		   { opi.setView(view); opi.PrintOnePage(tlx,tly,g,pageno);  }
		 if(PRINT_TYPE==1)
		 { opc.setView(view); opc.PrintOnePage(tlx,tly,g,pageno);  }
	
	     return 0;
	 }
	
	/*
	
	void CollectAllTeachers()
	{
	oldList.removeAll(oldList);
	
	for(int currentrow=0;currentrow<view.ROWCOUNT-1;currentrow++)
		for(int col=1;col<7;col++)
	       {String str=view.GetData(view.table,currentrow,col);
	       
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
	}
	*/
	
}	
