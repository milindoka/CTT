package ctt;


import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.SwingUtilities;

public class Main
{
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
            @Override
            public void run() {                                           
            	Model model = new Model();
            	View view = new View("-"); 
            	Controller controller = new Controller(model,view);
            	controller.contol();
            }
        });  
    }
}