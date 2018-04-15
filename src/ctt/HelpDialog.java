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
    
    String text="<H4 align=center>College Time Table CTT is a Free" +
    		" and Open Source Software</H4><P align=justify p style=margin-left:10px>College Time Table is a" +
    		"Free and Open Source Software to Generate, Edit, Modify and Manage School / College Time" +
    		"Table.</P>";
    JEditorPane editor = new JEditorPane("text/html",text);
      editor.setEditable(false);
      p1.setLayout(new BorderLayout());
      JScrollPane pane = new JScrollPane(editor);
      p1.add(pane,BorderLayout.CENTER);
    
    
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
 