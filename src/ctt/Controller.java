package ctt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Controller {

    private Model model;
    private View view;
    private ActionListener actionListener;
    
    public Controller(Model model, View view){
        this.model = model;
        this.view = view;
       // String path;
     
	     System.out.println(model.getJarPath()); ///set JAR path in model variable path;

                          
    }
    
    public void contol()
    {        
        actionListener = new ActionListener()
        {
              public void actionPerformed(ActionEvent actionEvent) {                  
                  linkBtnAndLabel();
              }
        };                
        view.getButton().addActionListener(actionListener);   
    }
    
    private void linkBtnAndLabel()
    {
    	File f = new File(System.getProperty("java.class.path"));
    	File dir = f.getAbsoluteFile().getParentFile();
    	String path = dir.toString();
    	String fnem=path+"/test.ctt";
    	System.out.println(fnem);
    	
    	
    	FileWriter f0=null;
    	try {f0 = new FileWriter(fnem); }       catch (IOException e1){e1.printStackTrace();}
    	 String newLine = System.getProperty("line.separator");
    	// try { f0.write(newLine);	}        
    	
    	 for(int i=0;i<10;i++)
    	 {  for(int j=0;j<6;j++)
    	 
    		 //System.out.println(view.GetData(view.table,i,j)+"#");
    	 {
    	      try { f0.write(view.GetData(view.table,i,j)+"#"); }    catch (IOException e) {e.printStackTrace();}
    	 }
    	 try { f0.write(view.GetData(view.table,i,6));}      catch (IOException e) {e.printStackTrace();}
    	 try { f0.write(newLine);}      catch (IOException e) {e.printStackTrace();}
    
    	 }
    	 try {f0.close();} catch (IOException e) {e.printStackTrace();}
    	 
     }
    	// toast.AutoCloseMsg("File Saved");

    	
    	
    	
    }  
    
    
    
    
    
