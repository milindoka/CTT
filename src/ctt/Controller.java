package ctt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.sun.xml.internal.ws.util.StringUtils;

public class Controller {

    private Model model;
    private View view;
    private RemoveCDG rcdg;
    private ActionListener SaveAL,LoadAL,PrinAL,SetprnAL,GlobalCountsAL,DEMObuttonAL;
    private ActionListener REMCLASHbuttonAL,MULTIFRIZbuttonAL,CLEARFRIZbuttonAL;
    private ActionListener PRINTINDIbuttonAL,PRINTCLASSbuttonAL,PRINTMASTERbuttonAL;
    
    private int globalDC;
    private int globalGC;
    public Controller(Model model, View view)
    {
        this.model = model;
        this.view = view;
     
	     System.out.println(model.getJarPath()); ///set JAR path in model variable path;
    }
    
    public void control()
    {   rcdg=new RemoveCDG();
        rcdg.setView(view);
 /////Set Preferred Printer on Startup
        
        SetPrinter sp=new SetPrinter();
        String printername=sp.LoadPreferences();
        if(printername==null) printername="No Printer";
        model.setPrinterName(printername);
        view.getSetPRN().setText("Printer : "+ printername);
    	
    	
    	
    	
        SaveAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) {                  
                  SaveTT();
              }
        };                
        view.getSaveBT().addActionListener(SaveAL);
        
        LoadAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {                  
                  LoadTT();
              }
        };                
        view.getLoadBT().addActionListener(LoadAL);
        
        PrinAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {            
            	             	 
              }
        };                
        view.getPrinCU().addActionListener(PrinAL);
        
        SetprnAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {                  
                  SetDefaultPrinter();
              }
        };                
        view.getSetPRN().addActionListener(SetprnAL);
        
        
        GlobalCountsAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {                  
                  rcdg.CalculateGlobalCounts();
                  rcdg.DisplayAllCounts();
              }
        };                
        view.getGLOBALCOUNTbutton().addActionListener(GlobalCountsAL);
        
        
        
        DEMObuttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {                  
                  SetDemoTimeTable();
              }
        };                
        view.getDEMObutton().addActionListener(DEMObuttonAL);
        
        


        REMCLASHbuttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {                  
               rcdg.RemoveClashGapDoubles();   
             //  rcdg.startnow();
              }
            	};

        view.getREMCLASHbutton().addActionListener(REMCLASHbuttonAL);
        
        
        MULTIFRIZbuttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {      String str = JOptionPane.showInputDialog(null, "Freeze Cells Containg Following String");            
                     view.MultiFrizCellsContaing(str);
               }
            	};
               
        view.getMULTIFRIZbutton().addActionListener(MULTIFRIZbuttonAL);
        
        
        CLEARFRIZbuttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {                  
            	view.ClearColorMatrix();       
               }
            	};
               
        view.getCLEARFRIZbutton().addActionListener(CLEARFRIZbuttonAL);

        
        
        
        PRINTINDIbuttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {                  

              }
            	};
               
        view.getPRINTINDIbutton().addActionListener(PRINTINDIbuttonAL);
        
        
        PRINTCLASSbuttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {                  
 
              }
            	};
               
        view.getPRINTCLASSbutton().addActionListener(PRINTCLASSbuttonAL);
        
        PRINTMASTERbuttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {                  
            	  PrepareMaster();
                  PrinCUTT();
   
              }
            	};
               
        view.getPRINTMASTERbutton().addActionListener(PRINTMASTERbuttonAL);
    }
    
///////////// Methods called in action listeners ////////////////////    
    
    public void ClearFreez()
    {
       view.ClearColorMatrix();
    }
    
    
	public void SetDemoTimeTable()
    {
    	//view.SetData(Demo.demoarr[0][0], view.table, 0,0);
		for (int i=0;i<Demo.demoarr.length;i++)
		{    
			   for(int j=0;j<Demo.demoarr[i].length;j++)
			   {
				   view.SetData(Demo.demoarr[i][j],view.table,i,j);
			   }
		}	    

    }
    
    private void SetDefaultPrinter()
    { 	
    	SetPrinter sp=new SetPrinter();
        String printername=sp.SelectPrinter();
        view.getSetPRN().setText("Printer : "+ printername+"  (Click To Change");
        model.setPrinterName(printername);
        sp.SavePreferences();
    }
    
  
    private void PrinCUTT()
    { 
      PrintMaster pm=new PrintMaster();
      pm.setView(view);
      String printername=model.getPrinterName();
      System.out.println(printername);
      pm.SetTable(view.table);
      pm.PrintMasterChart(printername);
    }
    
    String SixTupleColor(int row)
    {String temp="";
    
       for(int c=1;c<7;c++)
    	   if(view.ColorMatrix[row][c]==0) temp+="0"; else temp+="1";
    
    return temp;
    	
    }
    
    private void SaveTT()
    {
    	File f = new File(System.getProperty("java.class.path"));
    	File dir = f.getAbsoluteFile().getParentFile();
    	String path = dir.toString();
    	String fnem=path+"/test.ctt";
    	//System.out.println(fnem);
    	
    	
    	FileWriter f0=null;
    	try {f0 = new FileWriter(fnem); }       catch (IOException e1){e1.printStackTrace();}
    	 String newLine = System.getProperty("line.separator");
    	// try { f0.write(newLine);	} 
    	 int rowcount=view.table.getRowCount();
    	
    	 for(int i=0;i<rowcount-1;i++)
    	 {  
    		 for(int j=0;j<7;j++)
    	        {  try { f0.write(view.GetData(view.table,i,j)+"#"); }    
    	           catch (IOException e) {e.printStackTrace();}
    	        }
    		 
    	    try { f0.write(SixTupleColor(i));}   ////Write row cell Colors as 010010 6-tuple     
	          catch (IOException e) {e.printStackTrace();} ///no "#" after last entry
  		
    	    try { f0.write(newLine);}                        //// New line after every row
    	          catch (IOException e) {e.printStackTrace();}
    
    	 }
    	 try {f0.close();} catch (IOException e) {e.printStackTrace();}

     	// toast.AutoCloseMsg("File Saved");
     }
    	
    private void LoadTT()
    {
    	File f = new File(System.getProperty("java.class.path"));
    	File dir = f.getAbsoluteFile().getParentFile();
    	String path = dir.toString();
    	String fnem=path+"/test.ctt";
    	//System.out.println(fnem);
    	

    	BufferedReader reader=null;
    	try { 	reader = new BufferedReader(new FileReader(fnem));}
    	catch (FileNotFoundException e1) {e1.printStackTrace();}
    	
    	ArrayList<String> strArray = new ArrayList<String>();
    	String line = null;
    	try { while ((line = reader.readLine()) != null) { strArray.add(line); } } 
    	catch (IOException e) {	e.printStackTrace();   }

    	String temp[],stemp;
  	  
    	for(int i=0;i<strArray.size();i++)
    	{  stemp=strArray.get(i);
    	   temp=stemp.split("#");
    	   
    	   for(int j=0;j<7;j++)
    		  view.SetData(temp[j].trim(), view.table,i,j);
    		  
           for(int c=0;c<6;c++) view.ColorMatrix[i][c+1]=temp[7].charAt(c)-'0';
    	   
    	}
    	
       }
    
    int GetFirstRow()
    {    String temp="";
    	 int currentrow=0;    	
    		////Get First Time Slot
    	    for(currentrow=0;currentrow<view.ROWCOUNT-1;currentrow++)
    	    	{ temp=view.GetData(view.table,currentrow,0); 
    	    	  if(temp.length()>0) break;
    	    	}
    		return currentrow;
    }
    
    
    int GetLastRow()
    {    String temp="";
    	 int currentrow=0;    	
    		////Get First Time Slot
    	    for(currentrow=view.ROWCOUNT-1;currentrow>0;currentrow--)
    	    	{ temp=view.GetData(view.table,currentrow,0); 
    	    	  if(temp.length()>0) break;
    	    	}
    		return currentrow;
    }
    
   void PrepareMaster()
   { String temp,temp1[];
     int maxsplit=0; // maximum split count for time slot
	   int m=GetFirstRow();
	  int n=GetLastRow();
	  if(m>n) System.out.println("invalid m,n");
	  int currentrow=0;
	  for(int i=m;i<=n;i++)
	  {
		 temp=view.GetData(view.table,i,0);
		 if(temp.length()==0) continue; //skip blank line
		 
		 if(temp.contains(":"))  ///New time Block, print week day names line 
		 {   currentrow++;  
			 String week[]={"Mon","Tue","Wed","Thu","Fri","Sat"};
		     view.SetData2(temp,currentrow,0);  ///time
			 for(int j=0;j<6;j++ )
				 view.SetData2(week[j],currentrow,j+1); /// week days
			 currentrow++;
			 continue;
		 }
		 
       view.SetData2(temp,currentrow,0);  ///Copy as it is, must be class name FY-A,FY-B etc
       maxsplit=1;
       for(int j=1;j<7;j++) ///check lecture cells
       {temp=view.GetData(view.table,i,j);
    	if(!temp.contains(","))
    		{ view.SetData2(temp,currentrow,j);  ///No "," so Copy as it is
    		 continue;
    		}
    		// else at least one comma exists
    			temp1=temp.split(",");
    			int count=temp1.length;
    			for(int k=0;k<count;k++)
    				{view.SetData2(temp1[k],currentrow+k,j);
    				
    				}
    			if(maxsplit<count) maxsplit=count;
    	
       }
       currentrow=currentrow+maxsplit;
	  }
	  System.out.println(m);
	  System.out.println(n);
   }
    
}  
    
    
    
    
    

   