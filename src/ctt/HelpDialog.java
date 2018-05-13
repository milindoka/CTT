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
    		+ " with New Time Table (Same as Wizard1). Wizard1 gives you a skeleton, where" +
    		" you have to edit/modify class and time slot. After completeting wizard1, please enter fixed" +
    		" lectures such as practicals or tutorials which you do not want to move anywhere." +
    		" These lectures should be Freezed (Yellow). Freezed lecures will not be moved" +
    		" by Clash or Gap Remove Routines." +
    		" After entering fixed lectures go to Wizard2.</P>";
    TEXTp2+="<H4 align=center>Quickly build Time Table ....</H4>"+
"<ol>"+
"<li> Use Wizard1 to clean the table and Fill Sample Time Slots.</li>"+
"<li> Replace preset time slots with your own time slots, also put your classes.</li>"+
"<li> Do not write anything in time row, write Lectures (Periods) at appropriate places.</li>"+
"<li> Enter Fixed Lectures, such as practicals. Split lectures are separated by comma. Freeze fixed lectures.</li>"+
"<li> After Freezed Lectures, enter normal lectures. Do not write anything in time row.</li>"+
"<li> You can use Wizard2 to populate the Master or you can enter them manually and multiply by copy paste.</li>"+
"<li> Always enter lectures in SUB(TR) format, 3 letters for subject and 2 letters for Teacher.</li>"+
"<li> Moving Cell-Pointer to any Lecture Item will show the respective Individual Time Table.</li>"+
"<li> Moving the Cell-Pointer to any Class, will show respective class time table.</li>"+
"<li> (CC) Clash-Count is the total number of clashes. Red cell indicates clash.</li>"+
"<li> (GC) Gap-Count is the total number of Free Lectures between First and Last Lecture.</li>"+
"<li> (DC) Double-Count is the total pairs of repeated lectures.</li>"+
  "</ol>";
    
    JEditorPane EDITORp2 = new JEditorPane("text/html",TEXTp2);
    EDITORp2.setEditable(false);
    p2.setLayout(new BorderLayout());
    JScrollPane pane2 = new JScrollPane(EDITORp2);
    p2.add(pane2,BorderLayout.CENTER);
    
    
//////Content p3

 String TEXTp3="<H4 align=center> Input Details</H4>"
 		+ "<P align=justify p style=margin:10px>After understanding the basic working " +
 		"of the CTT, you can start building the actual time table. All the entries are"
 		+ " to be done in Master Time Table (Left Side Spreadesheet). The individual and class"
 		+ " time tables are updated immediately. Remember that parallel lectures (split lectures) " +
 		" are to entered in the same cell, separated by comma (, ). Please refer demo time table"
 		+ " if you are in doubt.</P>";
 TEXTp3+="<H4 align=center>Points to Remember ....</H4>"+
"<ol>"+
"<li> Save your work periodically, preferably in different incremental files TT01, TT02,.. etc</li>"+
"<li> You can copy and paste any cell or selected cells to speed up your work.</li>"+
"<li> Complete the Master Time Table in all respect, do not worry about clashes.</li>"+
"<li> If you want specific lectures at specific places, for example Practicals, put them first.</li>"+
"<li> Freeze these lecture by Freeze button. Freezed lectures will be shown by Yellow color cells.</li>"+
"<li> Freezed lectures will not be moved during Remove-clash and Remove-Gap routins.</li>"+
"<li> Check that the required number of lectures are allocated to classes and individuals.</li>"+
"<li> Just move the cell pointer to get lecture count and distribution of respective individual or class.</li>"+
"<li> Once you complete entries, save the time table. You may take additional backup of your work.</li>"+
"<li> Finally Run the [Remove-Clashes] routine and your time table will be ready in a minute or two.</li>"+
"</ol>";
 
 JEditorPane EDITORp3 = new JEditorPane("text/html",TEXTp3);
 EDITORp3.setEditable(false);
 p3.setLayout(new BorderLayout());
 JScrollPane pane3 = new JScrollPane(EDITORp3);
 p3.add(pane3,BorderLayout.CENTER);
 

 
//////Content p4

String TEXTp4="<H4 align=center>Other Details</H4>"
	+ "<P align=justify p style=margin:10px>Once your time table is complete and clashes are " 
	+"removed by Remove-Clashes routine, you can go for fine tuning of the time table."
	+" You can manually do minor changes as per your requirements. The automated Remove-Gap-Doubles"
	+ " routine can marginally improve your time table. This rotine will remove double lectures " +
	" and spread them over week. It also removes unnecessary gaps between the lectures, so lectures are"
	+ " arranged in consecutive blocks on each day. This is very convenietnt as teachers get uninterrupted free time" +
	" to complete their non-teaching work.</P>";
TEXTp4+="<H4 align=center>Points to Remember ....</H4>"+
"<ol>"+
"<li> Always Save your work before running Remove-Clashes or Remove-Gaps routines.</li>"+
"<li> These routines make major changes in your time table. So use them carefully.</li>"+
"<li> Freezed (Yellow) lectures are unaffected by Remove Clash/Gap Routines.</li>"+
"<li> Practical lectures are generally Freezed lectures due to Lab availability.</li>"+
"<li> Enter your college name using corresponding button. Changed name will appear on Button.</li>"+
"<li> Ensure that there are no clashes for any teacher using global counts button.</li>"+
"<li> Just move the cell pointer to get lecture count and distribution of respective individual or class.</li>"+
"<li> You can also use small edit box to display any individual time table.</li>"+
"<li> Once you finalize Master, Save it. Set Printer and print all the reports."+
"</ol>";

JEditorPane EDITORp4 = new JEditorPane("text/html",TEXTp4);
EDITORp4.setEditable(false);
p4.setLayout(new BorderLayout());
JScrollPane pane4 = new JScrollPane(EDITORp4);
p4.add(pane4,BorderLayout.CENTER);

//////Content p5

String TEXTp5="<H4 align=center>Wizard</H4>"
+ "<P align=justify p style=margin:10px>Wizard is useful to create time table from scratch." 
+" If you already have last years time table, then loading it and making few changes can give you the required time table."
+" Hence Wizard may not be required. If you are going to create time table first time then Wizard can be used. Wizard is divided in two steps."
+ " First step (Wizard1) cleans the existing time table from memory and gives the formatted time and class entries." +
" You need to change those entries according to your needs. Use cut and paste or Find/Replace to do these changes quickly."
+ " Next do Wizart2 to put Lecture Items. The time and class slots in wizard1 will be used i Wizard2." +
" If you already have class and time slots from previous time table, you can delete all Lecture Items and directly run Wizard2.</P>";
TEXTp5+="<H4 align=center>Steps to Generate time table using Wizard ....</H4>"+
"<ol>"+
"<li> Save your work first before running Wizard. Also take proper backup before you begin.</li>"+
"<li> Run Wizard1, it will clean the Master Sheet and fill it with Time and Class slots.</li>"+
"<li> Using these Slots as guide, replace your own Time and Class Slots. Use Copy-Paste or Find-Replace.</li>"+
"<li> Put the Fixed Slot Lectures, such as Practicals and Freeze them by Ctrl-D</li>" +
"<li> Run Wizard2. Enter Lecture Items as per your need. </li>"+
"<li> You can Run Wizard2 any time later if you need to correct lecture allocation.</li>"+
"<li> You can add Lecture Items manually also in Master Sheet. But stick to SUB(TR) Format.</li>"+
"<li> After you complete Wizard2, there will be many clashes in the time table. This is normal.</li>"+
"<li> Finally Run Remove-Gap-Doubles. This will remove all the clashes making a balanced time table.</li>"+
"<li> Once you finalize Master, Save it. Set Printer and print all the reports."+
"</ol>";

JEditorPane EDITORp5 = new JEditorPane("text/html",TEXTp5);
EDITORp5.setEditable(false);
p5.setLayout(new BorderLayout());
JScrollPane pane5 = new JScrollPane(EDITORp5);
p5.add(pane5,BorderLayout.CENTER);
 

//////Content p6

String str = "<a href=\"www.samteksystems.com\" >samteksystems</a>";


String TEXTp6="<H4 align=center>More Free and Open Source Software ..</H4>"
+ "<P align=justify p style=margin:10px>There is an urgent need of good quality open source software for school in India." 
+" Teachers and Educational Institutes are unaware of the fact that ONLY open source software can give right computer education."
+" Linux Users Group Mumbai, has started approaching Schools and Colleges to spread awareness about FOSS"
+ " (Free and Open Source Sofwares). This softwares is a small contributions to help teachers and at the same time" +
"  support Free Software Movement " +
"  I request all to join this group.<BR>"+
" Website : www.ilug-bom.org.in</P>";
TEXTp6+="<H4 align=center>Please Try the following Free Softwares ....</H4>"+
"<ul>"+
"<li> MarkList - An Android App to Create Mark Lists Quickly and easily Print them. Availbale on Playstore.</li>"+
"<li> MRKprint - Print Marklists created by Mobile App.</li>"+
"<li> MRKcollector - Compile all Mark Lists to One Single Result Data File.</li>"+
"<li> ResultView - View and Print the School/College Result from the Compiled Data File.</li>" +
"<li> Download <a href=https://sourceforge.net/projects/collegetimetable/>https://sourceforge.net/projects/collegetimetable/</a></li>"+
"<li> Download <a href=https://sourceforge.net/projects/marklist/>https://sourceforge.net/projects/marklist/</a></li>"+
"<li> All Source Codes : <a href=https://github.com/milindoka>https://github.com/milindoka</a></li>"+
"<li> For Comments and Suggestions, please email : oak444@gmail.com </li>"+
"</ul>";

JEditorPane EDITORp6 = new JEditorPane("text/html",TEXTp6);
EDITORp6.setEditable(false);
p6.setLayout(new BorderLayout());
JScrollPane pane6 = new JScrollPane(EDITORp6);
p6.add(pane6,BorderLayout.CENTER);

//////-------------------    
    
    JTabbedPane tp=new JTabbedPane();  
    tp.setBounds(50,50,300,500);  
    tp.add("Introduction",p1);  
    tp.add("Quick Start",p2);  
    tp.add("Input Details",p3);    
    tp.add("Other Details",p4);  
    tp.add("Wizard",p5);  
    tp.add("More Open Source..",p6);    
	
    
    setBounds(0,0 , 800, 600);
    setTitle("Time Table Help");
    setLocationRelativeTo(null);

    Container dlgpane=getContentPane();
    dlgpane.setLayout(new BorderLayout());
    dlgpane.add(tp,BorderLayout.CENTER);

   }
}
 