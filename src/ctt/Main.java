package ctt;


import javax.swing.SwingUtilities;

public class Main
{
    public static void main(String[] args) {           
        SwingUtilities.invokeLater(new Runnable()   
        {
            @Override
            public void run() {                                           
            	Model model = new Model(0);
            	View view = new View("-"); 
            	Controller controller = new Controller(model,view);
            	controller.contol();
            }
        });  
    }
}