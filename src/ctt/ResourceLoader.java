package ctt;

import java.awt.Image;
import java.awt.Toolkit;
public class ResourceLoader {
 
    static ResourceLoader rl = new ResourceLoader();
     
    public static Image loadImage(String imageName)
    {
    	
        return Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource(imageName));
    }
}