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
    JPanel p2=new JPanel();  
    JPanel p3=new JPanel();  
    JPanel p4=new JPanel();  
    JPanel p5=new JPanel();  
    JPanel p6=new JPanel();  

    
    
    
    
    JTabbedPane tp=new JTabbedPane();  
    tp.setBounds(50,50,300,500);  
    tp.add("Introduction",p1);  
    tp.add("Quick Start",p2);  
    tp.add("Input Details",p3);    
    tp.add("Other Details",p4);  
    tp.add("Wizard",p5);  
    tp.add("More Open Source..",p6);    
	
    
    setBounds(0,0 , 800, 500);
    setTitle("Time Table Help");
    setLocationRelativeTo(null);

    Container dlgpane=getContentPane();
    dlgpane.setLayout(new BorderLayout());
    dlgpane.add(tp,BorderLayout.CENTER);

   }
}
 