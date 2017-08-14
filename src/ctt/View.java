package ctt;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class View {
      
    private JFrame frame;
    private JLabel label;
    private JButton button;
    private DefaultTableModel model;
    private DefaultTableModel model2;
    JTable table;
    JTable table2;
    
    public void SetData(Object obj, JTable table, int row_index, int col_index)    {  table.getModel().setValueAt(obj,row_index,col_index);  }
            
    public View(String text){
        frame = new JFrame("View");                  
        frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
      
        
        ////// Create JTable ---------------------------
        
           Object rowData[][] = { { "","","","","","","" }};
            Object columnNames[] = { "Time", "MON", "TUE" ,"WED","THU","FRI","SAT"};
            
          //  JTable table = new JTable(rowData, columnNames);
           table = new JTable(new DefaultTableModel(columnNames, 0))
            { ////added tooltip

                public String getToolTipText( MouseEvent e )
                {
                    int row = rowAtPoint( e.getPoint() );
                    int column = columnAtPoint( e.getPoint() );

                    Object value = getValueAt(row, column);
                    return value == null ? null : value.toString();
                }

            	
            	
            };
            
            table.setRowHeight(20);
                       	
            model =  (DefaultTableModel) table.getModel();
            for(int i=0;i<100;i++) model.addRow(new Object[]{"", "", "","","","",""});
            
            table.getColumnModel().getColumn(0).setMinWidth(100);
        //    UCaseTableCellEditor editor = new UCaseTableCellEditor ();
          //  TableColumn col = table.getColumnModel().getColumn(0);
           // col.setCellEditor(editor);
              
            
                 //   table.addKeyListener(mk);
            
            
            ExcelAdapter myAd = new ExcelAdapter(table);

            JScrollPane scrollPane = new JScrollPane(table);
        
            
        ///--------DELETE KEY TO REMOVE CURRENT CELL CONTENT--------------------------------------
            
            
            
            InputMap inputMap = table.getInputMap(JComponent.WHEN_FOCUSED);
            ActionMap actionMap = table.getActionMap();

            inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "delete");
            actionMap.put("delete", new AbstractAction() {
                public void actionPerformed(ActionEvent evt) {
                   // Note, you can use getSelectedRows() and/or getSelectedColumns
                   // to get all the rows/columns that have being selected
                   // and simply loop through them using the same method as
                   // described below.
                   // As is, it will only get the lead selection
                   int row = table.getSelectedRow();
                   int col = table.getSelectedColumn();
                   if (row >= 0 && col >= 0) {
                       row = table.convertRowIndexToModel(row);
                       col = table.convertColumnIndexToModel(col);
                       table.getModel().setValueAt(null, row, col);
                   }
                }
            });            
            /////////------------------------------------------------------------------------

            
     ////// Create Table2  ---------------------------
            
            Object rowData2[][] = { { "","","","","","","" }};
             Object columnNames2[] = { "Time", "MON", "TUE" ,"WED","THU","FRI","SAT"};
             
           //  JTable table = new JTable(rowData, columnNames);
            table2 = new JTable(new DefaultTableModel(columnNames2, 0))
             { ////added tooltip

                 public String getToolTipText( MouseEvent e )
                 {
                     int row = rowAtPoint( e.getPoint() );
                     int column = columnAtPoint( e.getPoint() );

                     Object value = getValueAt(row, column);
                     return value == null ? null : value.toString();
                 }

             	
             	
             };
             
             table2.setRowHeight(20);
                        	
             model2 =  (DefaultTableModel) table2.getModel();
             for(int i=0;i<50;i++) model2.addRow(new Object[]{"", "", "","","","",""});
             
             table2.getColumnModel().getColumn(0).setMinWidth(100);
         //    UCaseTableCellEditor editor = new UCaseTableCellEditor ();
           //  TableColumn col = table.getColumnModel().getColumn(0);
            // col.setCellEditor(editor);
               
             
                  //   table.addKeyListener(mk);
             
             
           //  ExcelAdapter myAd = new ExcelAdapter(table);

             JScrollPane scrollPane2 = new JScrollPane(table2);
         

            
            
            
            
            
            //////////////////
            
            
        frame.getContentPane().setLayout(new BorderLayout());                                          
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           
        frame.setSize(400,400);
        frame.setLocationRelativeTo(null);
        
        
        
        
        JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
        
        
        label = new JLabel(text);
     //   frame.getContentPane().add(label, BorderLayout.CENTER);
        panel.add(scrollPane2, BorderLayout.SOUTH);
        
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