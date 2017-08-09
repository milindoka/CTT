package ctt;


import javax.swing.*;
import java.awt.BorderLayout;

public class View {
      
    private JFrame frame;
    private JLabel label;
    private JButton button;

    
    public View(String text){
        frame = new JFrame("View");                  
        
       ////// Create JTable ---------------------------
        
        Object rowData[][] = { { "Row1-Column1", "Row1-Column2", "Row1-Column3" },
                { "Row2-Column1", "Row2-Column2", "Row2-Column3" } };
            Object columnNames[] = { "Column One", "Column Two", "Column Three" };
            JTable Master = new JTable(rowData, columnNames);

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