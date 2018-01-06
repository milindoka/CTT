package ctt;

import java.awt.Color;
import java.awt.Graphics;

public class OnePageIndi
{
	View view;
	int timecolsize=100,othercolsize=65,linesperpage=40,cellheight=19;

	public void setView(View vu)  {	this.view=vu; }
	
	void PrintOnePage(int topleftx, int toplefty,Graphics g,int pageno)
	{int  tlx=topleftx, tly=toplefty;
		DrawSideWallTextLine(tlx,tly,g,0);
	}
	 
	
	private void DrawSideWallTextLine(int x,int y,Graphics g,int TableRowNo)
	{ 
	  int currentleft=x,currenttop=y;
	  
	 int horizontalwidth=timecolsize+6*othercolsize;
	    
     String  temp=view.GetData(view.table2, TableRowNo,0);
     DrawSideWallText(temp,currentleft,currenttop, timecolsize, cellheight, g);
     currentleft+=timecolsize;
		        
		  for(int i=1;i<7;i++) 
		  { //g.drawString("| Test", currentleft, currenttop);
			temp=view.GetData(view.table2,TableRowNo,i);
		    DrawSideWallText(temp,currentleft,currenttop, othercolsize, cellheight, g);
		    currentleft+=othercolsize;
		  }
	  currentleft=x;
	  
	  currenttop+=cellheight;
	  }
	 
      //g.drawLine(currentleft,currenttop,currentleft+horizontalwidth,currenttop);
	
	 
	 void DrawSideWallText(String str,int tlx,int tly, int boxwidth, int boxheight, Graphics pg)
	 {
		 pg.drawLine(tlx, tly, tlx, tly+boxheight);
		 pg.drawLine(tlx+boxwidth, tly, tlx+boxwidth, tly+boxheight);
	//	 pg.fillRect(tlx, tly,boxwidth, boxheight);
		 int stringLen = (int)  pg.getFontMetrics().getStringBounds(str, pg).getWidth(); 
		 int stringHeight=(int) pg.getFontMetrics().getStringBounds(str, pg).getHeight();
		 
	        int start = boxwidth/2 - stringLen/2;  
	        pg.drawString(str, start + tlx, tly+(boxheight-stringHeight)/2 +stringHeight-2);
	        
	 }
	 
}
