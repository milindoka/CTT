package ctt;

import java.awt.Color;
import java.awt.Graphics;

public class OnePageIndi
{
	View view;
	int timecolsize=100,othercolsize=65,linesperpage=40,cellheight=19;

	public void setView(View vu)  {	this.view=vu; }
	
	void PrintOnePage(int topleftx, int toplefty,Graphics g,int pageno)
	{
		int  tlx=topleftx, tly=toplefty;
		int horizontalwidth=timecolsize+6*othercolsize;
		
		g.drawLine(tlx,tly,tlx+horizontalwidth,tly); /////// top line
	
		DrawWallTextLine(tlx,tly,g,0);
		tly+=cellheight;
		
		g.drawLine(tlx,tly,tlx+horizontalwidth,tly); /////// top line
	}
	 
	
	private void DrawWallTextLine(int x,int y,Graphics g,int TableRowNo)
	{ 
	 int currentleft=x,currenttop=y;
	 
	 
	 g.drawLine(currentleft, currenttop, currentleft, currenttop+cellheight); //leftmost wall   
     String  temp=view.GetData(view.table2, TableRowNo,0);
     
     DrawRightWallText(temp,currentleft,currenttop, timecolsize, g);         /// time with right wall
     
     currentleft+=timecolsize;
		        
		  for(int i=1;i<7;i++) 
		  { //g.drawString("| Test", currentleft, currenttop);
			temp=view.GetData(view.table2,TableRowNo,i);
		    DrawRightWallText(temp,currentleft,currenttop, othercolsize,g);  /// week text with right wall
		    currentleft+=othercolsize;
		  }
	  currentleft=x;
	  
	  currenttop+=cellheight;
	  }
	 
	
	 
	 void DrawRightWallText(String str,int tlx,int tly, int boxwidth,Graphics pg)
	 {
	//	 pg.drawLine(tlx, tly, tlx, tly+cellheight);
		 pg.drawLine(tlx+boxwidth, tly, tlx+boxwidth, tly+cellheight);
	//	 pg.fillRect(tlx, tly,boxwidth, cellheight);
		 int stringLen = (int)  pg.getFontMetrics().getStringBounds(str, pg).getWidth(); 
		 int stringHeight=(int) pg.getFontMetrics().getStringBounds(str, pg).getHeight();
		 
	        int start = boxwidth/2 - stringLen/2;  
	        pg.drawString(str, start + tlx, tly+(cellheight-stringHeight)/2 +stringHeight-2);
	        
	 }
	 
}
