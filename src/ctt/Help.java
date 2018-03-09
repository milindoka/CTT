package ctt;

import java.awt.Frame;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
 
public class Help extends JDialog
{
    
    public Help(Frame mf,String title,boolean modal) throws IOException
    {
        super(mf,title,modal);
        this.setSize(300,200);
        String text="<H1>A!</H1><P><FONT COLOR=blue>blue</FONT></P>";
        JEditorPane editor = new JEditorPane("text/html",text);
          editor.setEditable(false);
          JScrollPane pane = new JScrollPane(editor);
        this.getContentPane().add(pane);
        this.setVisible(true);
    }
}
