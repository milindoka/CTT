package ctt;


import java.awt.Font;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main
{
	
	/////Change the Whole Application Font, only one call in 
	public static void setUIFont (javax.swing.plaf.FontUIResource f){
	    java.util.Enumeration keys = UIManager.getDefaults().keys();
	    while (keys.hasMoreElements()) {
	      Object key = keys.nextElement();
	      Object value = UIManager.get (key);
	      if (value instanceof javax.swing.plaf.FontUIResource)
	        UIManager.put (key, f);
	      }
	    } 
///////END of CHANGE FONT ROUTINE	
	
	
	
	 public static void main(String[] args)
	 {
		   
///////ALL CAPS - UPPERASE GLOBALLY  
		// Intercept all key events prior sending them to the focused component
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
		    public boolean dispatchKeyEvent(KeyEvent e) {
		  // This example converts all typed keys to upper case
		  if (e.getID() == KeyEvent.KEY_TYPED) {
		    e.setKeyChar(Character.toUpperCase(e.getKeyChar()));
		 
		  }
		  // setting discardEvent to true will not forward the key event to the focused component
		  boolean discardEvent = false;
		  return discardEvent;
	
		    }

		});
	
		
	///////////////END OF CAPS - UPPERCASE ROUTINE
		
		
        SwingUtilities.invokeLater(new Runnable()   
        {
            public void run() {                                           
            	
            	setUIFont (new javax.swing.plaf.FontUIResource("Serif",Font.BOLD,13));
            	
            	Model model = new Model();
            	View view = new View("-"); 
            	Controller controller = new Controller(model,view);
            	controller.control();
            }
        });  
    }
}