package ctt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


public class LoadFile 
{
	
static String BrowseAndGetTimeTableFile()
{String fylename="";
File f = new File(System.getProperty("java.class.path"));
File dir = f.getAbsoluteFile().getParentFile();
String path = dir.toString();
JFileChooser chooser = new JFileChooser(path);
FileNameExtensionFilter filter = new FileNameExtensionFilter(
    "CTT", "ctt");
chooser.setFileFilter(filter);
int returnVal = chooser.showOpenDialog(null);
if(returnVal == JFileChooser.APPROVE_OPTION)
{
	fylename=chooser.getSelectedFile().getPath();
}
return fylename;
	
}
	
	
	
static String GetLastFileIfAny()
 {  String fylename="";
	String LastFile;

	File f = new File(System.getProperty("java.class.path"));
	File dir = f.getAbsoluteFile().getParentFile();
	String path = dir.toString();
	LastFile=path+="/LastFile.txt";
	//Default File Path
	
	//Check if Last File Exists
	File varTempDir = new File(LastFile); 
    boolean exists = varTempDir.exists();
	
    if(exists)  ///if LastFile exists, then read the oldfilepath 
	{   
	
    	BufferedReader reader=null;
    	try { 	reader = new BufferedReader(new FileReader(LastFile));}
    	catch (FileNotFoundException e1) {e1.printStackTrace();}
	
    	String line = null;
    	try { line = reader.readLine(); }
    	catch (IOException e) {	e.printStackTrace();   }
    	if(line!=null) //defualt path exists
    	{	
     	////check existence of LastFile
    	File varTmpDir = new File(line); 
    	boolean exists1 = varTmpDir.exists();
    	if(exists1) return line;   ////defualt file with path exists so return it
    	}
    	//else  do nothing and go down to browse dialog
	}
	
	return fylename;
	
  }



static void WriteLastFile(String path)
 {
	 
		File f = new File(System.getProperty("java.class.path"));
    	File dir = f.getAbsoluteFile().getParentFile();
    	String jarpath = dir.toString();
    	String fnem=jarpath+"/LastFile.txt";
    	
    	FileWriter f0=null;
    	try {f0 = new FileWriter(fnem); }       catch (IOException e1){e1.printStackTrace();}
    	 
    	// try { f0.write(newLine);	} 
    	
    	 try { f0.write(path); }    
         catch (IOException e) {e.printStackTrace();}
    	 
    	 String newLine = System.getProperty("line.separator");
    	 
    	 System.out.println(path);
    	 
    	 try { f0.write(newLine); }    
         catch (IOException e) {e.printStackTrace();}
    	 try {f0.close();} catch (IOException e) {e.printStackTrace();}
     }    

	
static String BrowseAndSaveTimeTableFile()
{String fylename="";
File f = new File(System.getProperty("java.class.path"));
File dir = f.getAbsoluteFile().getParentFile();
String path = dir.toString();
JFileChooser chooser = new JFileChooser(path);
chooser.setDialogTitle("Specify a file to save");
FileNameExtensionFilter filter = new FileNameExtensionFilter(
    "CTT", "ctt");
chooser.setFileFilter(filter);
int returnVal = chooser.showSaveDialog(null);
if(returnVal == JFileChooser.APPROVE_OPTION)
{
	fylename=chooser.getSelectedFile().getPath();
}

return fylename;
	
}

	

/*
FileChooser fileChooser = new JFileChooser();
fileChooser.setDialogTitle("Specify a file to save");   
 
int userSelection = fileChooser.showSaveDialog(parentFrame);
 
if (userSelection == JFileChooser.APPROVE_OPTION) {
    File fileToSave = fileChooser.getSelectedFile();
    System.out.println("Save as file: " + fileToSave.getAbsolutePath());
}
*/


	 
 }
















