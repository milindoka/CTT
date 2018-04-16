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
    
    String TEXTp1="<H4 align=center>College Time Table (CTT) is a Free" +
    		" and Open Source Software</H4><P align=justify p style=margin:10px>College Time Table is a" +
    		" Free and Open Source Software. You can use it to Generate, Edit, Modify and Manage School/College Time" +
    		"Table. It is written in Java so you can use it on Linux, Windows, Mac or"
    		+ " on any Operating System where Java is installed.</P>";
    TEXTp1+="<H4 align=center>-- Fetures --</H4>" +
"<ol>"+
"<li> Simple, Fast, Accurate. Extremly small size and Portable App.</li>"+
"<li> Starts generating time table instantly and on-the-fly.</li>"+
"<li> Detects clashes,gaps between the letures, removes them automatically.</li>"+
"<li> Detects double lectures and spreads them over the week.</li>"+
"<li> Freezes particular lecture(s), if required, during auto adjustment routine.</li>"+
"<li> Swaps Teachers or Subjects within seconds.</li>"+
"<li> Handles Parallel Lectures and Practicals easily.</li>"+
"<li> Optional Wizard to generate time table from scratch.</li>"+
"<li> Prints all type of reports - Classes, Teachers and Master.</li>"+
"<li> Excellent User Interface.</li>"+
  "</ol>";
    
    JEditorPane EDITORp1 = new JEditorPane("text/html",TEXTp1);
    EDITORp1.setEditable(false);
    p1.setLayout(new BorderLayout());
    JScrollPane pane1 = new JScrollPane(EDITORp1);
    p1.add(pane1,BorderLayout.CENTER);
    
////// Content p2

    String TEXTp2="<H4 align=center> Quick Start</H4>"
    		+ "<P align=justify p style=margin:10px>College Time Table (CTT)" +
    		" is very user friendly application. Since all the commands are self explainatory, one"
    		+ " can easily start using CTT in a few minutes. Its Spreadsheet design gives "
    		+ "a comfortable look and feel for a computer novice." +
    		" Take a quick look at demo timetable. Do some trials on it and then start"
    		+ " with Wizard1 followed by Wizard2.</P>";
    TEXTp2+="<H4 align=center>Quickly build Time Table ....</H4>"+
"<ol>"+
"<li> Use Wizard1 to clean the table and Fill Sample Time Slots.</li>"+
"<li> Replace preset time slots with your own time slots, also put your classes.</li>"+
"<li> Do not write anything in time row, write Lectures (Periods) at appropriate places.</li>"+
"<li> Detects double lectures and spreads them over the week.</li>"+
"<li> Freezes particular lecture(s), if required, during auto adjustment routine.</li>"+
"<li> Swaps Teachers or Subjects within seconds.</li>"+
"<li> Handles Parallel Lectures and Practicals easily.</li>"+
"<li> Optional Wizard to generate time table from scratch.</li>"+
"<li> Prints all type of reports - Classes, Teachers and Master.</li>"+
"<li> Excellent User Interface.</li>"+
  "</ol>";
    
    JEditorPane EDITORp2 = new JEditorPane("text/html",TEXTp2);
    EDITORp2.setEditable(false);
    p2.setLayout(new BorderLayout());
    JScrollPane pane2 = new JScrollPane(EDITORp2);
    p2.add(pane2,BorderLayout.CENTER);
    
    
    
    
//////-------------------    
    
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
 