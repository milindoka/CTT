package ctt;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class Wizard02 extends JDialog 
{
public String Response;

public Wizard02() 
{
	setBounds(0,0 , 500, 275);
	setTitle("BorderLayout and JDialog Demo");
	setLocationRelativeTo(null);

	
	JPanel centerpanel=new JPanel();
	//centerpanel.setSize(new Dimension(100,100));

	
	JComboBox<String> claslist = new JComboBox<String>();
    claslist.addItem("asdf");
    claslist.addItem("abcd");
     centerpanel.add(claslist);
    
    JTextField subteacherpair=new JTextField(8);
    centerpanel.add(subteacherpair);
    
                                           ////init,min,max,step
    SpinnerModel value = new SpinnerNumberModel(5,  1,   30, 1);   
    JSpinner spinner = new JSpinner(value);   
    centerpanel.add(spinner);
	
	JLabel labelWEST = new JLabel("WEST");
	JLabel labelNORTH = new JLabel("NORTH",JLabel.CENTER);
	JLabel labelCENTER = new JLabel("CENTER",JLabel.CENTER);
	JLabel labelEAST = new JLabel("EAST");
	
	Container dlgpane=getContentPane();
	dlgpane.setLayout(new BorderLayout());

	dlgpane.add(labelWEST,BorderLayout.WEST);
	dlgpane.add(labelNORTH,BorderLayout.NORTH);
	dlgpane.add(centerpanel,BorderLayout.CENTER);
	dlgpane.add(labelEAST,BorderLayout.EAST);
	JPanel buttonpanel=new JPanel();



	// Button OK
	JButton btnOK = new JButton("OK");
	btnOK.addActionListener(new ActionListener() 
	{
	public void actionPerformed(ActionEvent e) 
	{
	Response = "You Pressed OK";
	dispose();
	}
	});


	buttonpanel.add(btnOK);
	 
	// Button Cancel
	JButton btnCancel = new JButton("Cancel");
	btnCancel.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) 
	{
		Response = "You Pressed CANCEL";
		dispose();
	}
	});
	buttonpanel.add(btnCancel);

	dlgpane.add(buttonpanel,BorderLayout.SOUTH);

	
}
}
 