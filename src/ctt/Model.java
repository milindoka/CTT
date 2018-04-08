package ctt;

import java.io.File;

public class Model {
    private String printername;
  
    private String path;
    
    public String getPrinterName()
    {
        return printername;
    }
    
    public  void setPrinterName(String pn)
    {
        printername=pn;
    }
    
    public String getJarPath()
    {
    	File f = new File(System.getProperty("java.class.path"));
     	File dir = f.getAbsoluteFile().getParentFile();
        path=dir.toString();
     	return  path;
    }
    
}