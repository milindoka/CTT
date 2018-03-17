package ctt;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
 

public class Wizard02 extends JDialog 
{
public String sName;

public Wizard02() 
{
setBounds(100, 100, 296, 175);
setTitle("Input Dialog");
setLocationRelativeTo(null);
getContentPane().setLayout(null);
// Create Input
final JTextField name = new JTextField();
name.setBounds(57, 36, 175, 20);
getContentPane().add(name);
 
// Button OK
JButton btnOK = new JButton("OK");
btnOK.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
sName = name.getText();
dispose();
}
});

btnOK.setBounds(70, 93, 78, 23);
getContentPane().add(btnOK);
 
// Button Cancel
JButton btnCancel = new JButton("Cancel");
btnCancel.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
sName = "";
dispose();
}
});
btnCancel.setBounds(158, 93, 74, 23);
getContentPane().add(btnCancel);
 
}
}
 