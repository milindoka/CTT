package ctt;



import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

class Toast extends JFrame 
{

    public static void AutoCloseMsg(String quickmsg)
    {    Font tr = new Font("TimesRoman", Font.PLAIN,20);
    	JLabel label=new JLabel("   "+quickmsg+"   ",JLabel.CENTER);
    	label.setForeground(Color.BLUE);
    	label.setFont(tr);
    	label.setBounds(100,100,100,100);
    	
    	JLabel label1=new JLabel(" ",JLabel.CENTER);
    	
    	JLabel label2=new JLabel(" ",JLabel.CENTER);
    	final JDialog dialog = new JDialog();
    	
//       dialog.setTitle("Message");
    	//dialog.setModal(true);
        dialog.setBackground(Color.yellow);
        dialog.getContentPane().setBackground(Color.yellow);
    	dialog.setSize(100,100);
    	dialog.setAlwaysOnTop(true);   	
    	
    	dialog.getContentPane().add(label1, BorderLayout.NORTH);
        dialog.getContentPane().add(label, BorderLayout.CENTER);
        dialog.getContentPane().add(label2, BorderLayout.SOUTH);
       
    	dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    	dialog.setUndecorated(true);
            	
    	dialog.pack();
    	dialog.setLocationRelativeTo(null);
    	dialog.setVisible(true);
    	//create timer to dispose of dialog after 2 seconds
    	Timer timer = new Timer(5000, new AbstractAction() {
    	    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent ae) {
    	        dialog.dispose();
    	    }
    	});
    	//timer.setRepeats(false);//the timer should only go off once

    	//start timer to close JDialog as dialog modal we must start the timer before its visible
    	timer.start();
    //	timer.setCoalesce(false);
    }
    
    
}

