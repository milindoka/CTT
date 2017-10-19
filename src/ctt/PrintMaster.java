package ctt;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class PrintMaster implements Printable
{	
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
	

		
	  public void PrintMasterChart(String printername)
              {
		  
		  PrintService ps = findPrintService(printername);
		  if(ps==null) ps = PrintServiceLookup.lookupDefaultPrintService(); 
		  if(ps==null) return;
		   
	         PrinterJob job = PrinterJob.getPrinterJob();
	         job.setJobName("Master");
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
		int tlx=(int) pf.getImageableX()+10,tly=(int) pf.getImageableY()+10;
		
		int w=(int) pf.getImageableWidth()-20,h=(int)pf.getImageableHeight()-20;
		
		 if (pageno > 0)             // We have only one page, and 'page no' is zero-based
		    {  return NO_SUCH_PAGE;  // After NO_SUCH_PAGE, printer will stop printing.
	        }
           
		 g.drawRect(tlx,tly, w, h);
	      DrawOpenWallTextLine(30,30,g);

		return 0;
	 }
	
	
	
	private void DrawOpenWallTextLine(int x,int y,Graphics g)
	{ 
	 ///initialize printing parameters

	  int timecolsize=90,othercolsize=60;
	  int currentleft=x,currenttop=y;
	  
	  ///-------
	  g.drawString("| Time",currentleft,currenttop);
	  currentleft+=timecolsize;
	  for(int i=0;i<7;i++) 
		  { g.drawString("| Test", currentleft, currenttop);
		    currentleft+=othercolsize;
		  }
	  
	  	
	}
	
	
	
}	
