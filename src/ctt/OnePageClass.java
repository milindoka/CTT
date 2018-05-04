package ctt;

import java.awt.Graphics;
import java.util.Arrays;

public class OnePageClass
{
	View view;
	int timecolsize=100,othercolsize=65,linesperpage=40,cellheight=19;
	int horizontalwidth=timecolsize+6*othercolsize;
	int MidWidth=3*othercolsize;
	public void setView(View vu)  {	this.view=vu; }
	private String week[]; //={"TIME","MON","TUE","WED","THU","FRI","SAT"} from WeekDays.java 
	OnePageClass()
	{WeekDays wd=new WeekDays();
     week = Arrays.copyOf(wd.weekdays, wd.weekdays.length);
	}
	
	
	void PrintHeaderRow(int topleftx, int toplefty,Graphics g,int pageno)
	{   int  tlx=topleftx, tly=toplefty;
		g.drawLine(tlx,tly,tlx+horizontalwidth,tly); /////// top line
		g.drawLine(tlx,tly, tlx, tly+cellheight); //leftmost wall
		PrintRightWallText(view.LectureCount,tlx,tly, timecolsize, g); // time with right wall
		tlx=tlx+timecolsize;
		PrintRightWallText(view.collegename,tlx,tly, MidWidth,g); // mid cell
		tlx=tlx+MidWidth;
		PrintRightWallText("Total Lectures =",tlx,tly, 3*othercolsize,g); // last cell
		tly=tly+cellheight;
		tlx=topleftx;  ///back to leftmost position
		g.drawLine(tlx,tly,tlx+horizontalwidth,tly); /////// bot line
	}
	
	
	
	private void PrintWeekLine(int x,int y,Graphics g)
	{ 
	  int currentleft=x,currenttop=y;
	  g.drawLine(currentleft, currenttop, currentleft, currenttop+cellheight); //leftmost wall   
      PrintRightWallText(week[0],currentleft,currenttop, timecolsize, g); /// time with right wall
      currentleft+=timecolsize;
      
		  for(int i=1;i<7;i++) 
		  { 
		    PrintRightWallText(week[i],currentleft,currenttop, othercolsize,g);  /// week text with right wall
		    currentleft+=othercolsize;
		  }
	  currentleft=x;
	  currenttop+=cellheight;
	  g.drawLine(x,currenttop,x+horizontalwidth,currenttop); /////// bot line	
	}

	
	
	
	 void PrintRightWallText(String str,int tlx,int tly, int boxwidth,Graphics pg)
	 {
		 pg.drawLine(tlx+boxwidth, tly, tlx+boxwidth, tly+cellheight);
		 int stringLen = (int)  pg.getFontMetrics().getStringBounds(str, pg).getWidth(); 
		 int stringHeight=(int) pg.getFontMetrics().getStringBounds(str, pg).getHeight();
		 
	        int start = boxwidth/2 - stringLen/2;  
	        pg.drawString(str, start + tlx, tly+(cellheight-stringHeight)/2 +stringHeight-2);
	        
	 }
		
	void PrintOnePage(int topleftx, int toplefty,Graphics g,int pageno)
	{
		int  tlx=topleftx, tly=toplefty;
		int lastrow=GetLastRow();
		
		PrintHeaderRow(tlx,tly,g,pageno);
		tly+=cellheight;
		PrintWeekLine(tlx,tly,g);
		tly+=cellheight;
	    for(int row=0;row<lastrow;row++)
	    	{ String  temp=view.GetData(view.table3, row,0);
	    	  if(temp.length()!=0) //then top wall
	    	  { g.drawLine(tlx,tly,tlx+horizontalwidth,tly);
	    	    
	    	  }
	    	  DrawOpenWallTextLine(tlx,tly,g,row);     
	    	  tly+=cellheight;
	    	 
	    	}
	    g.drawLine(tlx,tly,tlx+horizontalwidth,tly); /////// bot line
	}
	 
	 int GetLastTimeRow()
	    {    String temp="";
	    	 int currentrow=0;    	
	    		////Get First Time Slot
	    	    for(currentrow=view.ROWCOUNT3-1;currentrow>0;currentrow--)
	    	    	{ temp=view.GetData(view.table3,currentrow,0); 
	    	    	  if(temp.length()>0) break;
	    	    	}
	    		return currentrow;
	    }
	 
	 int GetLastRow()
	    {    String temp="";
	    	 int currentrow=0;    	
	    		////Get First Time Slot
	    	    for(currentrow=view.ROWCOUNT3-1;currentrow>0;currentrow--)
	    	    	{ temp=view.GetData(view.table3,currentrow,0); 
	    	    	  if(temp.length()>0) break;
	    	    	}
	    		return currentrow;
	    }


		private void DrawOpenWallTextLine(int x,int y,Graphics g,int row)
		{ 
		  int currentleft=x,currenttop=y,cellheight=19;
          String temp;
		  
          temp=view.GetData(view.table3,row,0);
          PrintSideWallBoxedString(temp,currentleft,currenttop, timecolsize, cellheight, g);
          currentleft+=timecolsize;
			  
		  for(int c=1;c<7;c++) 
		  {
			  temp=view.GetData(view.table3,row,c);
			       
			  PrintSideWallBoxedString(temp,currentleft,currenttop, othercolsize, cellheight, g);
			    currentleft+=othercolsize;
		   }
		  
	      //g.drawLine(currentleft,currenttop,currentleft+horizontalwidth,currenttop);
		}
		

		void PrintSideWallBoxedString(String str,int tlx,int tly, int boxwidth, int boxheight, Graphics pg)
		 {
			 pg.drawLine(tlx, tly, tlx, tly+boxheight);
			 pg.drawLine(tlx+boxwidth, tly, tlx+boxwidth, tly+boxheight);
			 int stringLen = (int)  pg.getFontMetrics().getStringBounds(str, pg).getWidth(); 
			 int stringHeight=(int) pg.getFontMetrics().getStringBounds(str, pg).getHeight();
			 
		        int start = boxwidth/2 - stringLen/2;  
		        pg.drawString(str, start + tlx, tly+(boxheight-stringHeight)/2 +stringHeight-2);
		        
		 }


	 
	 
	 
	 
	 
	 
	 
}
