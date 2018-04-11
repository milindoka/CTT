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

public class PrintMaster implements Printable
{	
	int totalpages,totallines;
	View view;
    Color gre=new Color(231,231,231);
	public void setView(View vu)  {	this.view=vu; }
             
	  ///initialize printing parameters
  //	JTable table;

	  int timecolsize,othercolsize,linesperpage;
      
      PrintMaster()
      {   
    	  timecolsize=100;othercolsize=65;linesperpage=40;
    	  
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
		
	  public void PrintMasterChart(String printername)
              {
		  
		  totallines=GetTotalLines();
		  totalpages=totallines/linesperpage;
		  if(totalpages>20) totalpages=20; ///resonable limit for total pages
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
		//g.setColor(gre);
		int tlx=(int) pf.getImageableX()+10,tly=(int) pf.getImageableY()+10;
		
		int w=(int) pf.getImageableWidth()-20,h=(int)pf.getImageableHeight()-20;
		
		 if (pageno > totalpages)    // 'page no' is zero-based
		    {  return NO_SUCH_PAGE;  // After NO_SUCH_PAGE, printer will stop printing.
	        }
		 
		 Font MyFont = new Font("Courier", Font.PLAIN,10);
		 g.setFont(MyFont);
		 
	      DrawOpenWallTextLine(50,30,g,pageno);  ///left, top and graphics g
	      System.out.println("printing ends");
		return 0;
	 }
	
	private void DrawOpenWallTextLine(int x,int y,Graphics g,int pn)
	{ 
	  int currentleft=x,currenttop=y,cellheight=19;
	  
	 int horizontalwidth=timecolsize+6*othercolsize;
	    
	  for(int row=0;row<linesperpage;row++)
	  {   
		  
		  String  temp=view.GetData(view.table3, row+pn*linesperpage,0);
		  
		  if(temp.contains("$END")) 
		   { // if(row==0) continue; ///exceptiona END line, leave 
			  g.drawLine(currentleft,currenttop,currentleft+horizontalwidth,currenttop);
			 
			  return;
		   }
		  
		  
		  if(temp.contains("$BLANK")) 
		   { if(row!=0) ///first blank line so no ending  line of previous block
			   	  g.drawLine(currentleft,currenttop,currentleft+horizontalwidth,currenttop);
			  currenttop+=cellheight;
			  continue;
		   }
		  if(temp.length()!=0) //then top wall
		       g.drawLine(currentleft,currenttop,currentleft+horizontalwidth,currenttop);
		       
		  PrintSideWallBoxedString(temp,currentleft,currenttop, timecolsize, cellheight, g);
		        currentleft+=timecolsize;
		        
		  for(int i=1;i<7;i++) 
		  { //g.drawString("| Test", currentleft, currenttop);
			temp=view.GetData(view.table3,row+pn*linesperpage,i);
		    PrintSideWallBoxedString(temp,currentleft,currenttop, othercolsize, cellheight, g);
		    currentleft+=othercolsize;
		  }
	  currentleft=x;
	  
	  currenttop+=cellheight;
	  }
      //g.drawLine(currentleft,currenttop,currentleft+horizontalwidth,currenttop);
	}
	

	void PrintSideWallBoxedString(String str,int tlx,int tly, int boxwidth, int boxheight, Graphics pg)
	 {
		 pg.drawLine(tlx, tly, tlx, tly+boxheight);
		 pg.drawLine(tlx+boxwidth, tly, tlx+boxwidth, tly+boxheight);
		 pg.setColor(gre);
		 pg.fillRect(tlx, tly,boxwidth, boxheight);
		 int stringLen = (int)  pg.getFontMetrics().getStringBounds(str, pg).getWidth(); 
		 int stringHeight=(int) pg.getFontMetrics().getStringBounds(str, pg).getHeight();
		 
	        int start = boxwidth/2 - stringLen/2;  
	        pg.setColor(Color.BLACK);
	        pg.drawString(str, start + tlx, tly+(boxheight-stringHeight)/2 +stringHeight-2);
	        
	 }

	
	
	int GetTotalLines()
    {    String temp="";
    	 int currentrow=0;    	
    		////Get First Time Slot
    	    for(currentrow=view.ROWCOUNT3-1;currentrow>0;currentrow--)
    	    	{ temp=view.GetData(view.table3,currentrow,0); 
    	    	  if(temp.length()>0) break;
    	    	}
    		
    	    
    	    
    	    return currentrow;
    }
	
	
}	
