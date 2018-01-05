package ctt;

import java.awt.Color;
import java.awt.Graphics;

public class OnePageIndi
{
	View view;

	public void setView(View vu)  {	this.view=vu; }
	
	void PrintOnePage(int topleftx, int toplefty,Graphics g,int pageno)
	{   int timecolsize=100,othercolsize=65,linesperpage=40;
    	int horizontalwidth=timecolsize+6*othercolsize;
    	int tlx=topleftx,tly=toplefty,boxheight=19;
    	
    	  g.drawString("Hello World", topleftx, toplefty);
	}

	 
	 
	 void PrintSideWallBoxedString(String str,int tlx,int tly, int boxwidth, int boxheight, Graphics pg)
	 {
		 pg.drawLine(tlx, tly, tlx, tly+boxheight);
		 pg.drawLine(tlx+boxwidth, tly, tlx+boxwidth, tly+boxheight);
		 pg.fillRect(tlx, tly,boxwidth, boxheight);
		 int stringLen = (int)  pg.getFontMetrics().getStringBounds(str, pg).getWidth(); 
		 int stringHeight=(int) pg.getFontMetrics().getStringBounds(str, pg).getHeight();
		 
	        int start = boxwidth/2 - stringLen/2;  
	        pg.setColor(Color.BLACK);
	        pg.drawString(str, start + tlx, tly+(boxheight-stringHeight)/2 +stringHeight-2);
	        
	 }
	 
	 
	 


}
