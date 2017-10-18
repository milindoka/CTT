package ctt;

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
	  public void PrintList()
              {
	         PrinterJob job = PrinterJob.getPrinterJob();
	         job.setPrintable(this);
	        
	         ////Widening the print AREA.
	         //Java Keeps preset Margins of about 1 inch on all corners
                 //Top Left Corner is  (0,0), then width and height
	         HashPrintRequestAttributeSet pattribs=new HashPrintRequestAttributeSet();
	         pattribs.add(new MediaPrintableArea(0, 0, 210, 297, MediaPrintableArea.MM));
	         // 210 x 297  A4 size paper in Millimiters.
	         
	         
	         boolean ok = job.printDialog();
	         if (ok) {
	             try {
	                  job.print(pattribs);
	             } catch (PrinterException ex) {
	             }
	         }
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
	       

		return 0;
	 }
///// Here the JAVA Printing Mechanism Ends
/////////////////////////////////////////////////
///////////////////////////////////////////////
		
}