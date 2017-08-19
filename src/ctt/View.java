package ctt;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class View {
      
    private JFrame frame;
    private JButton SaveBT,LoadBT,PrinCU;
    private DefaultTableModel model;
    private DefaultTableModel model2;
    JTable table;
    JTable table2;
    ListSelectionModel listSelectionModel;
    
    public void SetData(Object obj, JTable table, int row_index, int col_index)    {  table.getModel().setValueAt(obj,row_index,col_index);  }
    public String GetData(JTable table, int row_index, int col_index) {  return (String) table.getModel().getValueAt(row_index, col_index); }    
    
   
    
    class MyColListener implements ListSelectionListener {
        
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting())
            {
             //   System.out.println("valueChanged: " + e.toString());
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();
                String s = (String)table.getValueAt(row, col);
               // System.out.println(s);
            }
        }
    }

    
    
    class MyRowListener implements ListSelectionListener {
   	 
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) 
            {
              //  System.out.println("valueChanged: " + e.toString());
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();
                String s = (String)table.getValueAt(row, col);
             //   System.out.println(s);
            }
        }
    }
    
    
    
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
            
            ExcelAdapter myAd = new ExcelAdapter(table);

            JScrollPane scrollPane = new JScrollPane(table);

            
            //////////////Focus Listner
     
            
            listSelectionModel = table.getSelectionModel();
            table.getSelectionModel()
                    .addListSelectionListener(new MyRowListener());
            table.getColumnModel().getSelectionModel()
                    .addListSelectionListener(new MyColListener());
            table.setSelectionModel(listSelectionModel);
     
            
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
             JScrollPane scrollPane2 = new JScrollPane(table2);
        frame.getContentPane().setLayout(new BorderLayout());                                          
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           
        frame.setSize(400,400);
        frame.setLocationRelativeTo(null);
        
        
        LoadBT = new JButton("Load Time Table");        
        SaveBT = new JButton("Save Time Table");
        PrinCU = new JButton("Print Current Time Table");
        
        JPanel buttonPanel=new JPanel(); buttonPanel.setLayout(new GridLayout(5,1,2,2));
        buttonPanel.add(SaveBT);
        buttonPanel.add(LoadBT);
        buttonPanel.add(PrinCU);
        
        JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
        panel.add(scrollPane2, BorderLayout.SOUTH);
        panel.add(buttonPanel, BorderLayout.NORTH);
        
        frame.add(scrollPane, BorderLayout.WEST);
        frame.add(panel, BorderLayout.EAST);
        
        frame.setVisible(true);
        
    }

    
    
    public JButton getSaveBT()
    {
        return SaveBT;
    }
    
    public JButton getLoadBT()
    {
        return LoadBT;
    }
   
    public JButton getPrinCU()
    {
        return PrinCU;
    }
    
    public void DisplayIndividual(String ind)
    {   int rowcount=table.getRowCount();
    	String temp="",currenttime="";
    	
        int currentrow=0;    	
    	////Get First Time Slot
        for(currentrow=0;currentrow<rowcount-1;currentrow++)
        	{ temp=GetData(table,currentrow,0); if(temp.contains(":")) currenttime=temp; break; }
    	if(!currenttime.contains(":")) return; ///no time slot  found
    	////////////////////
        
    	currentrow++;
        int indirow=0; ///initialize individual row pointer
        SetData(currenttime,table2,indirow,0); ///set first time slot in individual
      
        while(currentrow<rowcount-1)   	
    	{  ///update time slot if next time slot starts
        	temp=GetData(table,currentrow,0); 
        	if(temp.contains(":"))
        	 { currenttime=temp; currentrow++; indirow++;   
        	   SetData(currenttime,table2,indirow,0); ///set new time slot in individual
        	 }
        	
           for(int col=1;col<7;col++)
           {temp=GetData(table,currentrow,col);
            if(temp.contains(ind)) 
             {  SetData(GetData(table,currentrow,0),table2,indirow,col);
             }
           }
           currentrow++;
    		
    	//	if(temp.contains(ind))
    		
    	//	temp.substring()
    	//	if()
    		///return s1.toLowerCase().contains(s2.toLowerCase());
    		
    		
    		
    	//	String str = "Hello (Java)";
    	//	String answer = str.substring(str.indexOf("(")+1,str.indexOf(")"));
    		
    	}
    	
    	
    }
    
    
    
}



