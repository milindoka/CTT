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

public class Controller {

    private Model model;
    private View view;
    private ActionListener SaveAL,LoadAL;
    
    
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
              public void actionPerformed(ActionEvent actionEvent) {                  
                  LoadTT();
              }
        };                
        view.getLoadBT().addActionListener(LoadAL);
        
        
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
    		   view.SetData(temp[j], view.table,i,j);
    	   
    	}
    	
    	
       }
    







}  
    
    
    
    
    
