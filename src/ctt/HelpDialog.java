package ctt;


import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.*;



public class HelpDialog extends JDialog 
{

public HelpDialog() 
{
    JPanel p1=new JPanel();  
    p1.setPreferredSize(new Dimension(200, 200));
    p1.setBackground(Color.RED);
    
    JPanel p2=new JPanel();  
    p2.setPreferredSize(new Dimension(200, 200));
    p2.setBackground(Color.GREEN);
    
    JPanel p3=new JPanel();  
    p3.setPreferredSize(new Dimension(200, 200));
    p3.setBackground(Color.BLUE);
    
    JTabbedPane tp=new JTabbedPane();  
    tp.setBounds(50,50,200,200);  
    tp.add("Pane1",p1);  
    tp.add("Pane2",p2);  
    tp.add("Pane3",p3);    
	
    setBounds(0,0 , 500, 275);
    setTitle("Tabbed Pane Demo");
    setLocationRelativeTo(null);

    Container dlgpane=getContentPane();
    dlgpane.setLayout(new BorderLayout());
    dlgpane.add(tp,BorderLayout.NORTH);

   }
}
 