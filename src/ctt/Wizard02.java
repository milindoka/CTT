package ctt;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class Wizard02 extends JDialog
{
ArrayList<String> oldList=new ArrayList<String>();
ArrayList<String> newList;
View view;
public void setView(View vu)  {	this.view=vu; }
JComboBox<String> claslist;
JLabel Lcount;
JTextField sub,tea;




public Wizard02() 
{
	
	sub=new JTextField(4);
    sub.setDocument(new JTextFieldLimit(3));
    sub.setPreferredSize(new Dimension(100,28));

    tea=new JTextField(3);
    tea.setDocument(new JTextFieldLimit(2));
    tea.setPreferredSize(new Dimension(100,28));
    
	
	setBounds(0,0 , 500, 375);
	setTitle("Wizard-02 - Add Time Table Entries");
	setLocationRelativeTo(null);
    
	JPanel toppanel=new JPanel();
	toppanel.setLayout(new GridLayout(4,1));
	
	JLabel MsgLabel= new JLabel("Add Lecture Entried in SUB(TR) Format",JLabel.CENTER);
	toppanel.add(new JLabel(" ",JLabel.CENTER));
	toppanel.add(MsgLabel);
	toppanel.add(new JLabel(" ",JLabel.CENTER));
	toppanel.add(new JLabel(" ",JLabel.CENTER));

	Lcount=new JLabel("Total : ");
	
	JPanel centerpanel=new JPanel();
	//centerpanel.setSize(new Dimension(100,100));

    JLabel bracketLeft=new JLabel("(");
    bracketLeft.setFont(new Font("Serif", Font.PLAIN, 20));
    
    JLabel bracketRite=new JLabel(")");
    bracketRite.setFont(new Font("Serif", Font.PLAIN, 20));
    claslist = new JComboBox<String>();
    claslist.setPreferredSize(new Dimension(100,28));
    
    
    final PopupMenuListener listener = new PopupMenuListener() {
        //boolean initialized = false;


		@Override
		public void popupMenuCanceled(PopupMenuEvent arg0) {
			// TODO Auto-generated method stub

			
		}

		@Override
		public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
			// TODO Auto-generated method stub
			SwingUtilities.invokeLater(new Runnable(){

        	    public void run()
        	    {  
        	       claslist.showPopup();
        	     
        	    }
        	});
		}

		@Override
		public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
			// TODO Auto-generated method stub
		
 		}
      };
    
    claslist.addPopupMenuListener(listener);
    
    claslist.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            //
            // Get the source of the component, which is our combo
            // box.
            //
            JComboBox claslist = (JComboBox) event.getSource();

            Object selected = claslist.getSelectedItem();
            String clas=selected.toString();
        	String subject=sub.getText();
        	String teacher=tea.getText();
        	String L32=subject+"("+teacher+")";
            Updatecount(clas,L32);

        }
    });
 
    tea.getDocument().addDocumentListener(new DocumentListener() {

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateLabel(e);
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            updateLabel(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateLabel(e);
        }

        private void updateLabel(DocumentEvent e) {
            java.awt.EventQueue.invokeLater(new Runnable() {

                @Override
                public void run()
                {
                	String clas=(String) claslist.getSelectedItem();	
                    String subject=sub.getText();
                    String teacher=tea.getText();
                	String L32=subject+"("+teacher+")";
                	Updatecount(clas,L32);
                }
            });
        }
    });

      
    centerpanel.add(claslist);

    
    centerpanel.add(sub);
    centerpanel.add(bracketLeft);
    
    centerpanel.add(tea);
    centerpanel.add(bracketRite);
    centerpanel.add(Lcount);
	
    


    sub.getDocument().addDocumentListener(new DocumentListener() {

        @Override
        public void changedUpdate(DocumentEvent e) {
            updateLabel(e);
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            updateLabel(e);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateLabel(e);
        }

        private void updateLabel(DocumentEvent e) {
            java.awt.EventQueue.invokeLater(new Runnable() {

                @Override
                public void run()
                {
                	String clas=(String) claslist.getSelectedItem();	
                    String subject=sub.getText();
                    String teacher=tea.getText();
                	String L32=subject+"("+teacher+")";
                	
                	Updatecount(clas,L32);
                }
            });
        }
    });

    
    
    JLabel labelWEST = new JLabel("    ");
	JLabel labelEAST = new JLabel("    ");
	
	
	Container dlgpane=getContentPane();
	dlgpane.setLayout(new BorderLayout());

	dlgpane.add(labelWEST,BorderLayout.WEST);
	dlgpane.add(toppanel,BorderLayout.NORTH);
	dlgpane.add(centerpanel,BorderLayout.CENTER);
	dlgpane.add(labelEAST,BorderLayout.EAST);
	JPanel buttonpanel=new JPanel();



	// Button Add
	JButton btnAdd = new JButton(" Add ");
	btnAdd.addActionListener(new ActionListener() 
	{
	public void actionPerformed(ActionEvent e) 
	{
	
	String clas=(String) claslist.getSelectedItem();	
    String subject=sub.getText();
    if(subject.length()!=3) return;
    String teacher=tea.getText();
    if(teacher.length()!=2) return;
	String L32=subject+"("+teacher+")";
	
	
	if(!AddOneLecture(clas,L32))
	view.toast.AutoCloseMsg("Empty cell NOT available, Try another Division or Increase Time Table Slots");
	
	Updatecount(clas,L32);
	//claslist.showPopup();
	//dispose();
	}
	});
	buttonpanel.add(btnAdd);
	
	
	//////Remove Button
	JButton btnRemove = new JButton(" Remove ");
	btnRemove.addActionListener(new ActionListener() 
	{
	public void actionPerformed(ActionEvent e) 
	{
	
	String clas=(String) claslist.getSelectedItem();	
    String subject=sub.getText();
    String teacher=tea.getText();
	String L32=subject+"("+teacher+")";
	
	if(!RemoveOneLecture(clas,L32))
	//Toast.AutoCloseMsg("No Such Lecture Found");
	
	Updatecount(clas,L32);
	}
	});
	buttonpanel.add(btnRemove);
	
	
	
	// Button Finish
	JButton btnFinish = new JButton(" Finish ");
	btnFinish.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) 
	{
		claslist.removePopupMenuListener(listener);  //removeActionListener(this);
		dispose();
	}
	});
	buttonpanel.add(btnFinish);

	dlgpane.add(buttonpanel,BorderLayout.SOUTH);
}


void CollectAllClasses()
{ oldList.removeAll(oldList);
  for(int currentrow=0;currentrow<view.ROWCOUNT-1;currentrow++)
    { String str=view.GetData(view.table,currentrow,0);
      if(str.length()==0) continue;
      if(str.contains(":")) continue;       
      oldList.add(str);
     }

   ArrayList<String> newList = new ArrayList<String>(new HashSet<String>(oldList));
   Collections.sort(newList);
   for(int i=0;i<newList.size();i++) 
	   claslist.addItem(newList.get(i));


}


boolean AddOneLecture(String TheClass, String ThreeTwo)
{   
	int rc=view.table.getRowCount();
	for(int i=0;i<rc;i++)
	{ String anvilclas=view.GetData(view.table,i,0);
	  if(anvilclas.contains(TheClass))
	  {   for(int j=1;j<7;j++)
	  		{ String temp=view.GetData(view.table,i,j);
	  		  if(temp.length()==0)
	  			  { view.SetData(ThreeTwo, view.table,i,j);
	  			    return true;
	  			  }
	  		}
		  
	  }
	}
	
	return false;
	
}



boolean RemoveOneLecture(String TheClass, String ThreeTwo)
{   
	int rc=view.table.getRowCount();
	for(int i=rc-1;i>=0;i--)
	{ String anvilclas=view.GetData(view.table,i,0);
	  if(anvilclas.contains(TheClass))
	  {   for(int j=6;j>0;j--)
	  		{ String temp=view.GetData(view.table,i,j);
	  		  if(temp.contains(ThreeTwo))
	  			  { view.SetData("", view.table,i,j);
	  			    return true;
	  			  }
	  		}
		  
	  }
	}
	
	return false;
	
}



void Updatecount(String TheClass, String ThreeTwo)
{  if(ThreeTwo.length()!=7) { Lcount.setText("Total : 00"); return; }
   int counter=0;
	int rc=view.table.getRowCount();
for(int i=0;i<rc;i++)
 { String anvilclas=view.GetData(view.table,i,0);
   if(anvilclas.contains(TheClass))
    {   for(int j=1;j<7;j++)
  		{ String temp=view.GetData(view.table,i,j);
  		  if(temp.length()==0) continue;
  		  if(temp.contains(ThreeTwo)) 
  			  { counter++;
  			  }
  		}
	  
     }
  else ;  //continue next class
 } //end big for loop
	
// Show counter in label;

String cntLabel=String.format("Total : %02d", counter);

Lcount.setText(cntLabel);

}







}