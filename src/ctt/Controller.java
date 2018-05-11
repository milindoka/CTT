package ctt;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

public class Controller {

	private String week[];
    private Model model;
    private View view;
    private RemoveCDG rcdg;
    private FindAndReplace far;
    private ActionListener SaveAL,LoadAL,SetprnAL,GlobalCountsAL,DEMObuttonAL;
    private ActionListener PRINTCURRENTbuttonAL, REMGAPDbuttonAL;
    private ActionListener REMCLASHbuttonAL,MULTIFRIZbuttonAL,CLEARFRIZbuttonAL;
    private ActionListener PRINTINDIbuttonAL,PRINTCLASSbuttonAL,PRINTMASTERbuttonAL;
    private ActionListener FINDREPLACEbuttonAL,WIZARD01buttonAL;
    private ActionListener INSERTROWbuttonAL,SAVEASbuttonAL,WIZARD02buttonAL,DELETEROWbuttonAL;
    private ActionListener SCHOOLbuttonAL,HELPbuttonAL;
    private int PRINT_TYPE_INDI=0;
    private int PRINT_TYPE_CLASS=1;
    private String currentfilename="New-Untitled"; ///including path
    public void show(String msg) 
    {JOptionPane.showMessageDialog(null, msg);}
    public void show(int msg)
    {JOptionPane.showMessageDialog(null, msg);}
    public void show(long msg)
    {JOptionPane.showMessageDialog(null, msg);}

    
    String Header[]={"SCHOOL/COLLEGE","","","",""};
    
    public Controller(Model model, View view)
    {
        this.model = model;
        this.view = view;
    }
    
    public void control()
    {   rcdg=new RemoveCDG();
        rcdg.setView(view);
        far=new FindAndReplace();
        far.setView(view);
        view.SetTitle(currentfilename);
        WeekDays wd=new WeekDays();
        week = Arrays.copyOf(wd.weekdays, wd.weekdays.length);
        /////Set Preferred Printer on Startup
        SetPrinter sp=new SetPrinter();
        String printername=sp.LoadPreferences();
        if(printername==null) printername="No Printer";
        model.setPrinterName(printername);
        view.getSetPRN().setText("Printer : "+ printername);
        
        view.getSCHOOLbutton().setText(Header[0]);
        view.collegename=Header[0];
        view.getSetPRN().setText("Printer : "+ printername);
    	
        
    	String lastfile = LoadFile.GetLastFileIfAny();
    	if(lastfile.length()!=0)  LoadTT(lastfile); 
  /////////////Actions  	
    	
    	
    	INSERTROWbuttonAL=new ActionListener()
    	{
            public void actionPerformed(ActionEvent actionEvent) 
            {                  
            	int rowcount=view.table.getRowCount();
            	int currentrow=view.table.getSelectedRow();
            	if(currentrow<0) return;
            	
           	  for(int i=rowcount-2;i>=currentrow;i--)
           		 for(int j=0;j<7;j++)
           	        {  String str=view.GetData(view.table,i,j);
           	           int cellcolor=view.ColorMatrix[i][j];
           	           
           	           view.SetData(str, view.table, i+1,j);
           	           view.ColorMatrix[i+1][j]=cellcolor;
           	        }
           	  
           	for(int j=0;j<7;j++) view.SetData("", view.table, currentrow,j);

            }
    	};
        view.getINSERTROWbutton().addActionListener(INSERTROWbuttonAL);    	
    	

        SAVEASbuttonAL=new ActionListener()
    	{
            public void actionPerformed(ActionEvent actionEvent) 
            {  SaveAs(); 
            
            }
    	};
        view.getSAVEASbutton().addActionListener(SAVEASbuttonAL);
        
        
        WIZARD02buttonAL=new ActionListener()
    	{
            public void actionPerformed(ActionEvent actionEvent) 
            {               
            	Wizard02 w2 = new Wizard02();
            	w2.setView(view);
            	w2.CollectAllClasses();
            	w2.setModal(true);
            	w2.setVisible(true);
            	
            }
    	};
        view.getWIZARD02button().addActionListener(WIZARD02buttonAL);
        
        
        DELETEROWbuttonAL=new ActionListener()
    	{
            public void actionPerformed(ActionEvent actionEvent) 
            {      
             	int rowcount=view.table.getRowCount();
            	int currentrow=view.table.getSelectedRow();
            	if(currentrow<0) return;
            	
           	  for(int i=currentrow;i<rowcount-2;i++)
           		 for(int j=0;j<7;j++)
           	        {  String str=view.GetData(view.table,i+1,j);
           	           int cellcolor=view.ColorMatrix[i+1][j];
           	           
           	           view.SetData(str, view.table, i,j);
           	           view.ColorMatrix[i][j]=cellcolor;
           	        }

            }
    	};
        view.getDELETEROWbutton().addActionListener(DELETEROWbuttonAL);
    	
    	WIZARD01buttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent)
              {  
            	  String timearray[]={"12:30-01:10","01:10-01:50","01:50-02:30",
            			  			  "03:00-03:40","03:40-04:20","04:20-05:00"};

            	  
            	  
            	  int result=WarnBefore();
            	  if(result==JOptionPane.OK_OPTION)
            	  {
            		  String AH="ABCDEFGH";
            		  view.ClearMasterTable();
            		  view.ClearColorMatrix();
            		  ///create time-class column
            		  for(int i=0;i<6;i++)
            		  	{ view.SetData(timearray[i], view.table, i*18,0);
            			  for(int j=0;j<8;j++)
            				  view.SetData("FY-"+AH.charAt(j), view.table, i*18+j+1,0);
            			  for(int j=0;j<8;j++)
            				  view.SetData("SY-"+AH.charAt(j), view.table, i*18+j+9,0);
            		  	}
            		  		
            			  
            		  currentfilename="New-Untitled"; ///including path
            	      view.SetTitle(currentfilename);
            	  }
            	
              }
        };                
        view.getWizard01BT().addActionListener(WIZARD01buttonAL);
    	
    	
    	
    	FINDREPLACEbuttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) {                  
                  far.FindAndReplaceRoutine();
              }
        };                
        view.getFindReplaceBT().addActionListener(FINDREPLACEbuttonAL);
    	    		
    	
        SaveAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) {                  
                  PrepareSave();
              }
        };                
        view.getSaveBT().addActionListener(SaveAL);

        
        
        LoadAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {         
            	   LoadTimeTable();
              }
        };                
        view.getLoadBT().addActionListener(LoadAL);
        
        
        
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
        
        


        REMGAPDbuttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {           
            	  rcdg.onlyclash=false;
            	  OnRemoveClashThread();
              }
            	};

        view.getREMGAPDbutton().addActionListener(REMGAPDbuttonAL);
        
        
        REMCLASHbuttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {           
            	  rcdg.onlyclash=true;
            	  OnRemoveClashThread();
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
              {  PrintAllIndi();                  

              }
            	};
               
        view.getPRINTINDIbutton().addActionListener(PRINTINDIbuttonAL);
        
        
        PRINTCLASSbuttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {                  
               PrintAllClases();
              }
        };
               
        view.getPRINTCLASSbutton().addActionListener(PRINTCLASSbuttonAL);
        
        PRINTMASTERbuttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {      
            	  view.ClearTable3();
            	  PrepareMaster();
                  PrintMaster();
   
              }
            	};
               
        view.getPRINTMASTERbutton().addActionListener(PRINTMASTERbuttonAL);
        
   
        PRINTCURRENTbuttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {   PrintCurrent();    
            	
              }
       	};
               
        view.getPRINTCURRENTbutton().addActionListener(PRINTCURRENTbuttonAL);
        
        
        SCHOOLbuttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {       
            	  String schoolname = JOptionPane.showInputDialog(null, "Input School-College Name");
            	  
            	  if(schoolname!=null) 
            		  { Header[0]=schoolname;
            		    view.getSCHOOLbutton().setText(Header[0]);
            		  }
            		  
            	  
              }
       	};
               
        view.getSCHOOLbutton().addActionListener(SCHOOLbuttonAL);
       
        
        HELPbuttonAL = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) 
              {       
            	  HelpDialog hd = new HelpDialog();
            		hd.setModal(true);
            		hd.setVisible(true);
            	   
            	  
              }
       	};
               
        view.getHELPbutton().addActionListener(HELPbuttonAL);

        
  view.frame.addWindowListener(new java.awt.event.WindowAdapter() {
            
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if(view.modified==false) System.exit(0);
                
            	int choice=JOptionPane.showConfirmDialog(view.frame, 
                    "Time Table Modified, do you want to Save Changes ?", "Table Modified Message ", 
                    JOptionPane.YES_NO_CANCEL_OPTION);
                
                if(choice  == JOptionPane.NO_OPTION)  System.exit(0);
                if(choice  == JOptionPane.YES_OPTION) {PrepareSave(); System.exit(0);}
                
            }
        });

  
  
  
  InputMap inputMap2 = view.table.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
  ActionMap actionMap2 = view.table.getActionMap();

  inputMap2.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK), "Savett");
  actionMap2.put("Savett", new AbstractAction() {
      /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent evt) 
		{
			PrepareSave();
            
         }
      
  });            

  
  inputMap2.put(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK), "loadtt");
  actionMap2.put("loadtt", new AbstractAction() {
      /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent evt) 
		{
			LoadTimeTable();
            
         }
      
  });            
  
  
  
        
        
        
 }
//////////////    
///////////////    
///////////// Methods called in action listeners ////////////////////    

    
    
    public void LoadTimeTable()
    {
    	String fnem=LoadFile.BrowseAndGetTimeTableFile();
    	if(fnem.length()==0) return;
    	view.ClearMasterTable();
    	view.ClearColorMatrix();
    	view.ClearIndividualTable();
    	view.ClearTable3();
      LoadTT(fnem);
    }
    
    
    public void OnRemoveClashThread()
    {
    
    	Thread.UncaughtExceptionHandler h = new Thread.UncaughtExceptionHandler() {
    	    public void uncaughtException(Thread th, Throwable ex) {
    	        System.out.println("Uncaught exception: " + ex);
    	    }
    	};
    	Thread t = new Thread() {
    	    public void run() {
    	        System.out.println("Processing ...");
    	        try {
    	           Thread.sleep(100);
    	          rcdg.RemoveClashGapDoubles();
    	        } catch (InterruptedException e) {
    	            System.out.println("Interrupted.");
    	        }
    	        throw new RuntimeException();
    	    }
    	};
    	t.setUncaughtExceptionHandler(h);
    	t.start();
    	
    	
    }
    

    private void PrintAllIndi()
    {
      view.ClearTable3();
      PrepareIndividualPrint pip=new PrepareIndividualPrint();
      pip.setView(view);
      String printername=model.getPrinterName();
      pip.CollectAllTeachers();
      pip.PrintIndividuals(printername);
    	
    	
    }
    
    private void PrintAllClases()
    { view.ClearTable3();
      PrepareAllClasses pac=new PrepareAllClasses();
      pac.setView(view);
      pac.CollectAllClasses();
      
      String printername=model.getPrinterName();
     
      pac.PrintIndividuals(printername);
    	
    }
    
    
    public void ClearFreez()
    {
       view.ClearColorMatrix();
    }
    
    public int ModifiedDialog()
    {   if(!view.modified) return -999;
    	int choice=JOptionPane.showConfirmDialog
    			(view.frame, 
                "Time Table Modified, do you want to Save Changes ?", "Table Modified Message ", 
                JOptionPane.YES_NO_CANCEL_OPTION);
           return choice;
    }
    
    
	public void SetDemoTimeTable()
    {
	    if (view.table.isEditing()) view.table.getCellEditor().stopCellEditing();
		
		int choice=ModifiedDialog();
		if(choice!=-999) //if modified
		{
		   if(choice==JOptionPane.CANCEL_OPTION) return;
		   if(choice==JOptionPane.YES_OPTION) PrepareSave();
   	     
          }
		
		  view.ClearMasterTable();   
          view.ClearColorMatrix();
 
          for (int i=0;i<Demo.demoarr.length;i++)
		{    
			   for(int j=0;j<Demo.demoarr[i].length;j++)
			   {
				   view.SetData(Demo.demoarr[i][j],view.table,i,j);
			   }
		}	    
		currentfilename="New-Untitled"; ///including path
		view.SetTitle(currentfilename);
    }
    
    private void SetDefaultPrinter()
    { 	
    	SetPrinter sp=new SetPrinter();
        String printername=sp.SelectPrinter();
        view.getSetPRN().setText("Printer : "+ printername);
        model.setPrinterName(printername);
        sp.SavePreferences();
    }
    
  
    private void PrintMaster()
    { 
      PrintMaster pm=new PrintMaster();
      pm.setView(view);
      String printername=model.getPrinterName();
      System.out.println(printername);
      pm.PrintMasterChart(printername);
    }
 
    private void PrintCurrent()
    { 
    	
    	int col = view.table.getSelectedColumn();
    	if(col<0) return;
     
    	PrintIndi pi=new PrintIndi();
        pi.setView(view);
        String printername=model.getPrinterName();
    	
    	
      if(col==0) 
        { PrepareSingleClassToPrint();
    	  pi.SetPrintType(PRINT_TYPE_CLASS);
         pi.PrintIndividuals(printername);
          return;
        
        }
      pi.SetPrintType(PRINT_TYPE_INDI);
      pi.PrintIndividuals(printername);
    //  pi.CollectAllTeachers();
    }
 
    
    
    
    String SixTupleColor(int row)
    {String temp="";
    
       for(int c=1;c<7;c++)
    	   if(view.ColorMatrix[row][c]==0) temp+="0"; else temp+="1";
    
    return temp;
    	
    }

    private void SaveAs()
    {if (view.table.isEditing())
	     view.table.getCellEditor().stopCellEditing();

	String fnem="";
	File f = new File(System.getProperty("java.class.path"));
	File dir = f.getAbsoluteFile().getParentFile();
	String path = dir.toString();
	fnem=LoadFile.BrowseAndSaveTimeTableFile();
    if (fnem.length()==0) return;
    if (!fnem.endsWith(".CTT") && !fnem.endsWith(".ctt")) fnem += ".CTT";
    currentfilename=fnem;
    SaveTT(fnem);
    }
    
    
    private void PrepareSave()   /// 
    {	if (view.table.isEditing())
	     view.table.getCellEditor().stopCellEditing();

	String fnem="";
	File f = new File(System.getProperty("java.class.path"));
	File dir = f.getAbsoluteFile().getParentFile();
	String path = dir.toString();
	if(currentfilename.contains("New-Untitled"))
	   { //System.out.println("T"); 
		fnem=LoadFile.BrowseAndSaveTimeTableFile();
	      if (fnem.length()==0) return;
	      if (!fnem.endsWith(".CTT") && !fnem.endsWith(".ctt")) fnem += ".CTT";
	      currentfilename=fnem;
	      
	   }
	else	
		fnem=currentfilename;  /////contains full path
     
	 SaveTT(fnem);
    	
    }
    
    private void SaveTT(String fnem)
    {   
    	
    	FileWriter f0=null;
    	try {f0 = new FileWriter(fnem); }       catch (IOException e1){e1.printStackTrace();}
    	 String newLine = System.getProperty("line.separator");
    	// try { f0.write(newLine);	} 
    	 int rowcount=view.table.getRowCount();
    	
    	 try { f0.write(Header[0]); }    
         catch (IOException e) {e.printStackTrace();}
    	 try { f0.write(newLine);}                        //// New line after every row
         catch (IOException e) {e.printStackTrace();}
    	 
    	 try { f0.write("Reserved"); }    
         catch (IOException e) {e.printStackTrace();}
    	 try { f0.write(newLine);}                        //// New line after every row
         catch (IOException e) {e.printStackTrace();}
    	 
    	 try { f0.write("Reserved"); }    
         catch (IOException e) {e.printStackTrace();}
    	 try { f0.write(newLine);}                        //// New line after every row
         catch (IOException e) {e.printStackTrace();}
    	 
    	 try { f0.write("Reserved"); }    
         catch (IOException e) {e.printStackTrace();}
    	 try { f0.write(newLine);}                        //// New line after every row
         catch (IOException e) {e.printStackTrace();}
    	 
    	 try { f0.write("Reserved"); }    
         catch (IOException e) {e.printStackTrace();}
    	 try { f0.write(newLine);}                        //// New line after every row
         catch (IOException e) {e.printStackTrace();}
    	 
    	 
    	 
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
    	 
     	Toast.AutoCloseMsg("File Saved");
     	LoadFile.WriteLastFile(fnem);
    	view.SetTitle(fnem);
    	view.modified=false;
     }
    	
    private void LoadTT(String ttfile)
    {
    	 currentfilename=ttfile;
    	BufferedReader reader=null;
    	try { 	reader = new BufferedReader(new FileReader(ttfile));}
    	catch (FileNotFoundException e1) {e1.printStackTrace();}
    	
    	
    	String line = null;
    	
    	for(int i=0;i<5;i++)
    	try { if((line = reader.readLine()) != null) { Header[i]=line; } } 
    	catch (IOException e) {	e.printStackTrace();   }
    
    	
    	
    	ArrayList<String> strArray = new ArrayList<String>();
    	
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
    	LoadFile.WriteLastFile(ttfile);
    	view.SetTitle(ttfile);
    	view.getSCHOOLbutton().setText(Header[0]);
    	view.collegename=Header[0];
    	view.modified=false;
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
    
    
    
    int GetLastTime()
    {    String temp="";
    	 int currentrow=0;    	
    		////Get First Time Slot
    	    for(currentrow=view.MROWS-1;currentrow>0;currentrow--)
    	    	{ temp=view.Matrix[currentrow][0]; 
    	    	  if(temp.length()>0) break;
    	    	}
    		return currentrow;
    }
    
    
    void PrepareSingleClassToPrint()
    { int lt=GetLastTime();
     // show(lt);
      view.ClearTable3();
      int currentrow=0;
      int maxsplit=0; // maximum split count for time slot
      String temp,temp1[];
      for(int i=0;i<=lt;i++)
	  {
		 temp=view.Matrix[i][0];
		 if(temp.length()==0) continue; //skip blank line
	
       view.SetData3(temp,currentrow,0);  ///Time - Copy as it is
       maxsplit=1;
       for(int j=1;j<7;j++) ///check lecture cells
       {temp=view.Matrix[i][j];
    	if(!temp.contains(","))
    		{ view.SetData3(temp,currentrow,j);  ///No "," so Copy as it is
    		 continue;
    		}
    		// else at least one comma exists
    			temp1=temp.split(",");
    			int count=temp1.length;
    			for(int k=0;k<count;k++)
    				{view.SetData3(temp1[k],currentrow+k,j);
    				
    				}
    			if(maxsplit<count) maxsplit=count;
    	
       }
       currentrow=currentrow+maxsplit;
	  }
	  view.SetData3("$END",currentrow,0);
   }
      
    
   void PrepareMaster()
   { view.ClearTable3();
	 String temp,temp1[];
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
		 {   if(currentrow !=0) { view.SetData3("$BLANKLINE", currentrow,0);
			                      currentrow++;  }  ///skip first exceptional blank
			 
			// String week[]={"SAT","SUN","MON","TUE","WED","THU"};
		     view.SetData3(temp,currentrow,0);  ///time
			 for(int j=0;j<6;j++ )
				 view.SetData3(week[j+1],currentrow,j+1); /// week days
			 currentrow++;
			 continue;
		 }
		 
       view.SetData3(temp,currentrow,0);  ///Copy as it is, must be class name FY-A,FY-B etc
       maxsplit=1;
       for(int j=1;j<7;j++) ///check lecture cells
       {temp=view.GetData(view.table,i,j);
    	if(!temp.contains(","))
    		{ view.SetData3(temp,currentrow,j);  ///No "," so Copy as it is
    		 continue;
    		}
    		// else at least one comma exists
    			temp1=temp.split(",");
    			int count=temp1.length;
    			for(int k=0;k<count;k++)
    				{view.SetData3(temp1[k],currentrow+k,j);
    				}
    			if(maxsplit<count) maxsplit=count;
       }
       currentrow=currentrow+maxsplit;
	  }
	  view.SetData3("$END",currentrow,0);
	  System.out.println(m);
	  System.out.println(n);
   }
   
   
   int WarnBefore()
   {
   int choice=JOptionPane.showOptionDialog(null, 
	        "This Will Remove The Current Time Table, Continue ?", 
	        "Warning ..", 
	        JOptionPane.OK_CANCEL_OPTION, 
	        JOptionPane.INFORMATION_MESSAGE, 
	        null, 
	        new String[]{"Yes, I have Saved Time Table", "No, Will Save and Come back"}, // this is the array
	        "default");
   
   
   return choice;
	   
   }
   
}  
    
    
   