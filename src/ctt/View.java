package ctt;

import javax.print.attribute.AttributeSet;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class View {
      
    private JFrame frame;
    private JLabel label;
    private JButton button;
    private DefaultTableModel model;
        
            
    public View(String text){
        frame = new JFrame("View");                  
        
       ////// Create JTable ---------------------------
        
           Object rowData[][] = { { "","","","","","","" }};
            Object columnNames[] = { "Time", "MON", "TUE" ,"WED","THU","FRI","SAT"};
            
          //  JTable Master = new JTable(rowData, columnNames);
            JTable Master = new JTable(new DefaultTableModel(columnNames, 0));
            
            
            
          
                        
            
           
            
                       	
            model =  (DefaultTableModel) Master.getModel();
            for(int i=0;i<100;i++) model.addRow(new Object[]{"", "", "","","","",""});
           
            Master.getColumnModel().getColumn(0).setMinWidth(100);
        //    UCaseTableCellEditor editor = new UCaseTableCellEditor ();
          //  TableColumn col = Master.getColumnModel().getColumn(0);
           // col.setCellEditor(editor);
              
            
                 //   Master.addKeyListener(mk);
            
            
            ExcelAdapter myAd = new ExcelAdapter(Master);

            JScrollPane scrollPane = new JScrollPane(Master);
        
            
        ///----------------------------------------------
            
            
            
            
            
            
            
            
            
        frame.getContentPane().setLayout(new BorderLayout());                                          
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           
        frame.setSize(400,400);
        frame.setLocationRelativeTo(null);
        
        
        
        
        JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
        
        
        label = new JLabel(text);
     //   frame.getContentPane().add(label, BorderLayout.CENTER);
        panel.add(label, BorderLayout.SOUTH);
        
        button = new JButton("Button");        
        panel.add(button, BorderLayout.NORTH);
        
        frame.add(scrollPane, BorderLayout.WEST);
        frame.add(panel, BorderLayout.EAST);
        
        frame.setVisible(true);
        
    }
        
    public JButton getButton(){
        return button;
    }
    
    public void setText(String text){
        label.setText(text);
    }
    
    
}