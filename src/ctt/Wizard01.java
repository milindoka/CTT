package ctt;


import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
 
public class Wizard01 extends JDialog
{
    public Wizard01()
    {
        super();
        JPanel panel=new JPanel();
        panel.add(new JLabel("Hello dialog"));
        this.getContentPane().add(panel);
    }
    
    public Wizard01(Frame mf,String title,boolean modal){
        super(mf,title,modal);
        this.setSize(300,200);
        JPanel panel=new JPanel();
        panel.add(new JLabel("Hello dialog"));
        this.getContentPane().add(panel);
        this.setVisible(true);
    }
}