package ctt;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
 

public class Wizard02 extends JDialog 
{
public String sName;

public Wizard02() 
{
setBounds(0,0 , 500, 275);
setTitle("Wizard - Step 2");
setLocationRelativeTo(null);
Container dlgpane=getContentPane();
dlgpane.setLayout(new BorderLayout());
JLabel label0 = new JLabel("Test");
JLabel label1 = new JLabel("Test",JLabel.CENTER);
JLabel label2 = new JLabel("Test",JLabel.CENTER);
JLabel label4 = new JLabel("Test",JLabel.CENTER);
JLabel label3 = new JLabel("Test");
final JTextField name = new JTextField(5);
//name.setBounds(57, 36, 175, 20);
dlgpane.add(label0,BorderLayout.WEST);
//dlgpane.add(name,BorderLayout.CENTER);
dlgpane.add(label1,BorderLayout.NORTH);
dlgpane.add(label2,BorderLayout.CENTER);
dlgpane.add(label3,BorderLayout.EAST);
dlgpane.add(label4,BorderLayout.SOUTH);
// Button OK
JButton btnOK = new JButton("OK");
btnOK.addActionListener(new ActionListener() 
{
public void actionPerformed(ActionEvent e) {
sName = name.getText();
dispose();
}
});

//btnOK.setBounds(70, 93, 78, 23);
//dlgpane.add(btnOK,BorderLayout.SOUTH);
 
// Button Cancel
JButton btnCancel = new JButton("Cancel");
btnCancel.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) 
{
sName = "";
dispose();
}
});
//getContentPane().add(btnCancel);
 
}
}
 