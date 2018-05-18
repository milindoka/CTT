package ctt;



import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

class Toast extends JFrame 
{
	Font tr;
	JLabel label,label1,label2;
	JDialog dialog;
	
	Toast()
	{tr = new Font("TimesRoman", Font.PLAIN,20);
	label=new JLabel("                           ",JLabel.CENTER);
	label.setForeground(Color.BLUE);
	label.setFont(tr);
	label.setBounds(100,100,100,100);
	label1=new JLabel(" ",JLabel.CENTER);
	label2=new JLabel(" ",JLabel.CENTER);	
	dialog = new JDialog();
	dialog.setBackground(Color.yellow);
    dialog.getContentPane().setBackground(Color.yellow);
	//dialog.setSize(100,100);
	dialog.setAlwaysOnTop(true);   	
	
	dialog.getContentPane().add(label1, BorderLayout.NORTH);
    dialog.getContentPane().add(label, BorderLayout.CENTER);
    dialog.getContentPane().add(label2, BorderLayout.SOUTH);
    
	dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
	dialog.setUndecorated(true);
	dialog.pack();
	dialog.setLocationRelativeTo(null);
	dialog.setVisible(false);
	
	}

    public  void AutoCloseMsg(String quickmsg)
    { 
    	label.setText("   "+quickmsg+"   ");
    	dialog.setLocationRelativeTo(null);
    	dialog.pack();
    	dialog.setVisible(true);
    	//create timer to dispose of dialog after few seconds
    	Timer timer = new Timer(5000, new AbstractAction() {
    	    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent ae) 
			{
    	        dialog.setVisible(false);
    	        
    	    }
    	});
    	timer.setRepeats(false);//the timer should only go off once

    	//start timer to close JDialog as dialog modal we must start the timer before its visible
    	timer.start();
    //	timer.setCoalesce(false);
    }
    
    
}

