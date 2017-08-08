package ctt;

import java.io.File;

public class Model {
    
    private int x;
    private String path;
    public Model(){
        x = 0;
    }
    
    public Model(int x)  ///// Model Constructor
    {
        this.x = x;
    }
    
    public void incX(){
        x++;
    }
    
    public int getX(){
        return x;
    }
    
    public String getJarPath()
    {
    	File f = new File(System.getProperty("java.class.path"));
     	File dir = f.getAbsoluteFile().getParentFile();
        path=dir.toString();
     	return  path;
    }
}