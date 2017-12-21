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
    private ActionListener SaveAL,LoadAL,PrinAL,SetprnAL,GlobalCountsAL,DEMObuttonAL,REMCLASHbuttonAL;
    private int globalCC;
    private int globalDC;
    private int globalGC;
 //   private int improvedCC,improvedDC,improvedGC;    
    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
       // String path;
     
	     System.out.println(model.getJarPath()); ///set JAR path in model variable path;

                          
    }
    
    public void contol()
    {        
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
            	  PrepareMaster();
                 // PrinCUTT();
               	 
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
            	RemoveClashGapDoubles();       
               }
            	};
               
        view.getREMCLASHbutton().addActionListener(REMCLASHbuttonAL);
        
        
        
        GlobalCountsAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {                  
                  CalculateGlobalCounts();
                  DisplayAllCounts();
              }
        };                
        view.getb5().addActionListener(GlobalCountsAL);
        
        
        
        /////Set Preferred Printer on Startup
        
        SetPrinter sp=new SetPrinter();
        String printername=sp.LoadPreferences();
        if(printername==null) printername="No Printer";
        model.setPrinterName(printername);
        view.getSetPRN().setText("Printer : "+ printername+"  (Click To Change)");
        
    }
    
    
    public void RemoveClashGapDoubles()
    {int sourcerow=1;int sourcecol=1;
     CalculateGlobalCounts();
    String class1,class2;
    for(sourcerow=1;sourcerow<30;sourcerow++)
    { for(sourcecol=1;sourcecol<7;sourcecol++)
       {
    	if(view.ColorMatrix[sourcerow][sourcecol]==1) continue;
    	for(int r=0;r<30;r++)
    	
    	  { for(int c=1;c<7;c++)
    		  {if(view.ColorMatrix[r][c]==1) continue;
    		  class1=view.GetData(view.table,sourcerow,0);
    		  class2=view.GetData(view.table,r,0);
    		  if(!class1.contains(class2)) continue;
              //JOptionPane.showMessageDialog(null, class1+"="+class2)    		  
    		   ExchangeCells(sourcerow,sourcecol,r,c);
    		  }
    	   }
        }
    CalculateGlobalCounts();
    DisplayAllCounts();
     
    //  String plate=String.format("%d %d %d", improved)
      JOptionPane.showMessageDialog(null,"continue");
    }
     
    JOptionPane.showMessageDialog(null,"Over");    
    }
    
    String o1,o2;
    int occ,odc,ogc;
    int sCCbefore,sDCbefore,sGCbefore;
    int sCCafter,sDCafter,sGCafter;
    int tCCbefore,tDCbefore,tGCbefore;
    int tCCafter,tDCafter,tGCafter;
    
    public void ExchangeCells(int r1,int c1,int r2,int c2)
    { //String tcode;
      o1=view.GetData(view.table, r1,c1);
      if(o1.length()==0) return;    
      o2=view.GetData(view.table, r2,c2);
      if(o2.length()==0) return;
     // CalculateGlobalCounts();
      getIndividualCounts(o1);
      sCCbefore=occ;sDCbefore=odc;sGCbefore=ogc;
      getIndividualCounts(o2);
      tCCbefore=occ;tDCbefore=odc;tGCbefore=ogc;
      ////// Now exchange CELLS
      view.SetData(o2, view.table,r1,c1); 
      view.SetData(o1, view.table,r2,c2);
      
      ///count again .....////////////
      
      
      getIndividualCounts(o1);
      sCCafter=occ;sDCafter=odc;sGCafter=ogc;
      getIndividualCounts(o2);
      tCCafter=occ;tDCafter=odc;tGCafter=ogc;
      
      /////// Evaluate EXHANGE carefully      
      
      if(sCCafter+tCCafter<sCCbefore+tCCbefore) return; ///clash decreased then return
      if(sCCafter+tCCafter>sCCbefore+tCCbefore) //clash increased so restore cells
    	  {view.SetData(o1, view.table,r1,c1);  // and then return;
           view.SetData(o2, view.table,r2,c2); 
           return;
          } 
      
      ///now clash counts are unchanged by EXHANGE so look for additional optimization if possible
      if(sDCafter+tDCafter<sDCbefore+tDCbefore) return; ///doubles decreased so return
      if(sDCafter+tDCafter>sDCbefore+tDCbefore) // doubles increased so restore cells
	  {view.SetData(o1, view.table,r1,c1);  // and then return;
       view.SetData(o2, view.table,r2,c2); 
       return;
      } 

       ////  Now doubles and clashes are unchanged so look for gaps reduction if possible
      if(sGCafter+tGCafter>sGCbefore+tGCbefore) // gaps increased so restore cells
	  {view.SetData(o1, view.table,r1,c1);  // and then return;
       view.SetData(o2, view.table,r2,c2); 
       return;
      }
      // now gaps are equal or less
      //  other counts are unchanged so no need to reset cells 
      
    }
    
   private void getIndividualCounts(String cell) ///nonempty cell string, including , separated lectures
   { occ=0; odc=0; ogc=0; /////initialize original counts
    String tcode;
    if(cell.contains(",")) 
		{ String parts[]=cell.split(",");
		  for(int i=0;i<parts.length;i++)
			  { tcode=parts[i].substring(parts[i].indexOf("("),parts[i].indexOf(")")+1);
			    view.CreateIndi(tcode); 	
			    view.CountGaps();
			    view.CountDoubles();
			 
			    occ+=view.CC;odc+=view.DC;ogc+=view.GC;
			  }
		  
		}
   else ///single lecture
   { tcode = cell.substring(cell.indexOf("("),cell.indexOf(")")+1);
     view.CreateIndi(tcode);
	    view.CountGaps();
	    view.CountDoubles();
     
      occ=view.CC;odc=view.DC;ogc=view.GC;
   }
	   
	   
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
    
    private void CalculateGlobalCounts()
    { 	 Set<String> names = new LinkedHashSet<>();
         String temp,tcode;
    	 for(int r=0;r<view.ROWCOUNT-1;r++)	
    		 for(int c=1;c<7;c++)
    			 { 
    			   temp=view.GetData(view.table, r, c);
    			   if(temp.length()==0) continue;
    			   if(temp.contains(",")) 
    			   		{ String parts[]=temp.split(temp);
    			   		  for(int i=0;i<parts.length;i++)
    			   			  { tcode=parts[i].substring(parts[i].indexOf("("),parts[i].indexOf(")")+1);
    			   			    names.add(tcode); 			  
    			   			  }
    			   		  continue;
    			   		}
    			   
    			   
    			   
    			   tcode = temp.substring(temp.indexOf("("),temp.indexOf(")")+1);
    			   names.add(tcode); 
    			 }
        
    	 globalCC=0;globalDC=0;globalGC=0;
    	 for (String name : names) 
    	 { view.CreateIndi(name);
    	   view.CountDoubles();
    	   view.CountGaps();
    	   globalCC+=view.CC;globalDC+=view.DC;globalGC+=view.GC;
    		 
    		}
    
       	 
    }
    
    public void DisplayAllCounts()
    {String countLabel=String.format("Global CC : %d  Global DC : %d  Global GC : %d",  globalCC,globalDC,globalGC);   
    view.ShowGlobalCounts(countLabel);
    	
    }
  /*  
    public void DisplayImprovedCounts()
    {String countLabel=String.format("Global CC : %d  Global DC : %d  Global GC : %d",improvedCC,improvedDC,improvedGC);   
    view.ShowGlobalCounts(countLabel);
    }
    */
    private void PrinCUTT()
    { 
      PrintMaster pm=new PrintMaster();
      String printername=model.getPrinterName();
      System.out.println(printername);
      pm.SetTable(view.table);
      pm.PrintMasterChart(printername);
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
    		 for(int j=0;j<6;j++)
    	        {  try { f0.write(view.GetData(view.table,i,j)+"#"); }    
    	           catch (IOException e) {e.printStackTrace();}
    	        }
    	    try { f0.write(view.GetData(view.table,i,6));}   ////last Saturday cell, No # here     
    	          catch (IOException e) {e.printStackTrace();}
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
    	   
    	   for(int j=0;j<temp.length;j++)
    		   view.SetData(temp[j].trim(), view.table,i,j);
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
		 {   String week[]={"Mon","Tue","Wed","Thu","Fri","Sat"};
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
    
    
    
    
    

   