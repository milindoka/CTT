package ctt;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class View {
      
    private JFrame frame;
    private JButton SaveBT,LoadBT,PrinCU,SetPRN;
    private DefaultTableModel model;
    private DefaultTableModel model2;
    JTable table; int ROWCOUNT=100;  ///
   
    JTable table2;int ROWCOUNT2=500;  ///
    int COLCOUNT=7;
    
    ListSelectionModel listSelectionModel;
    
    public void SetData(Object obj, JTable table, int row_index, int col_index)    {  table.getModel().setValueAt(obj,row_index,col_index);  }
    public String GetData(JTable table, int row_index, int col_index) {  return (String) table.getModel().getValueAt(row_index, col_index); }    
    public void SetData2(Object obj, int row_index, int col_index)   
      {  table2.getModel().setValueAt(obj,row_index,col_index);  } 
   
    
    class MyColListener implements ListSelectionListener {
        
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting())
            {
            	new Thread(new Runnable() {
                    public void run()
                    {
                        refresh();
                    }
               }).start();
            }
        }
    }

    class MyRowListener implements ListSelectionListener {
      	 
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) 
            { ///threaded refresh to avoid sloth in individual and class display
            	new Thread(new Runnable() {
                    public void run() 
                    {
                        refresh();
                    }
               }).start();
            }
        }
    }

    
    
    private void refresh()
    {  ClearIndividualTable();
    int row = table.getSelectedRow();
    int col = table.getSelectedColumn();
    String str = (String)table.getValueAt(row, col);
    if(str.length()==0) return;
    if(col==0){  DisplayClass(str); return; }
    int left=str.indexOf("(");
    int rite=str.indexOf(")");
    if(left<0 || rite<0) return;
    final String teachercode = str.substring(str.indexOf("("),str.indexOf(")")+1);
    DisplayIndividual(teachercode);
    DeleteLastEmptyTimeSlots();
    }
    
    
    public View(String text){
        frame = new JFrame("View");                  
        frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        
        ////// Create JTable ---------------------------
        
           Object columnNames[] = { "Time", "MON", "TUE" ,"WED","THU","FRI","SAT"};
            
          //  JTable table = new JTable(rowData, columnNames);
           table = new JTable(new DefaultTableModel(columnNames, 0))
            { ////added tooltip

                /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public String getToolTipText( MouseEvent e )
                {
                    int row = rowAtPoint( e.getPoint() );
                    int column = columnAtPoint( e.getPoint() );

                    Object value = getValueAt(row, column);
                    return value == null ? null : value.toString();
                }
            };
            

         
            table.setRowHeight(20);
            table.setCellSelectionEnabled(true);     	
            model =  (DefaultTableModel) table.getModel();
            for(int i=0;i<ROWCOUNT;i++) model.addRow(new Object[]{"", "", "","","","",""});
            
            table.getColumnModel().getColumn(0).setMinWidth(100);
            ExcelAdapter myAd = new ExcelAdapter(table);
            JScrollPane scrollPane = new JScrollPane(table);

            
            //////////////    Focus Listner
            //////////////
            
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
                /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

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
                       table.getModel().setValueAt("", row, col);
                   }
                }
            });            
     /////////------------------------------------------------------------------------

            
     ////// Create Table2  ---------------------------
            
            Object columnNames2[] = { "Time", "MON", "TUE" ,"WED","THU","FRI","SAT"};
             
           //  JTable table = new JTable(rowData, columnNames);
            table2 = new JTable(new DefaultTableModel(columnNames2, 0))
             { ////added tooltip

                 /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

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
             for(int i=0;i<ROWCOUNT2;i++) model2.addRow(new Object[]{"", "", "","","","",""});
             
             table2.getColumnModel().getColumn(0).setMinWidth(100);
             JScrollPane scrollPane2 = new JScrollPane(table2);
        frame.getContentPane().setLayout(new GridLayout(1,2));                                          
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           
        frame.setSize(400,400);
        frame.setLocationRelativeTo(null);
        
        
        LoadBT = new JButton("Load Time Table");        
        SaveBT = new JButton("Save Time Table");
        PrinCU = new JButton("Print Current Time Table");
        SetPRN = new JButton("Set Printer");
        
        JPanel buttonPanel=new JPanel(); buttonPanel.setLayout(new GridLayout(5,1,2,2));
        buttonPanel.add(SaveBT);
        buttonPanel.add(LoadBT);
        buttonPanel.add(PrinCU);
        buttonPanel.add(SetPRN);
        
        JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
        panel.add(scrollPane2, BorderLayout.SOUTH);
        panel.add(buttonPanel, BorderLayout.NORTH);
        
        frame.add(scrollPane);
        frame.add(panel);
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
    { return PrinCU;
    }
    

    public JButton getSetPRN()
    { return SetPRN;
    }
    
    
    public void DisplayClass(String clas)
    { 
	String temp="",currenttime="";
	if(clas.contains(":")) return;
	if(clas.length()==0) return;
    int currentrow=0;    	

    int clasrow=0; ///initialize class table row pointer
    //SetData(currenttime,table2,clasrow,0); ///set first time slot in individual
   // int LAST=0;
   // for(LAST=ROWCOUNT-2;)
    
    while(currentrow<ROWCOUNT-1)   	
	{  ///update time slot if next time slot starts
    	temp=GetData(table,currentrow,0); 
    	if(temp.contains(":"))
    	 { currenttime=temp; 
    	   SetData(currenttime,table2,clasrow,0); ///set new time slot in individual
    	 }
    	
        if(temp.contains(clas)) 
         {  
        	for(int col=1;col<7;col++)
        	SetData(GetData(table,currentrow,col),table2,clasrow,col);
        	clasrow++;
        	
         }
       
       currentrow++;
	}
   
    
    }
    
    public void DisplayIndividual(String ind)
    {   int lecturecount=0;
      
    	String temp="",currenttime="";    int currentrow=0; boolean foundlecture=false;    	
    	
        ////Get First Time Slot
        for(currentrow=0;currentrow<ROWCOUNT-1;currentrow++)
        	{ temp=GetData(table,currentrow,0); if(temp.contains(":")) { currenttime=temp; break; }}
    	if(!currenttime.contains(":")) return; ///no time slot  found
    	////////////////////
        
    	currentrow++;
        int indirow=0; ///initialize individual row pointer
        SetData(currenttime,table2,indirow,0); ///set first time slot in individual
      
        while(currentrow<ROWCOUNT-1)   	
    	{  ///update time slot if next time slot starts
        	temp=GetData(table,currentrow,0); 
        	if(temp.contains(":"))
        	 { ////// if lecture found set new time slot otherwise overwrite time slot
        	   currenttime=temp; currentrow++; if(foundlecture) indirow++;  
        	   SetData(currenttime,table2,indirow,0); 
        	  // foundlecture=false;
        	 }
           
           for(int col=1;col<7;col++)
           {temp=GetData(table,currentrow,col);
            if(temp.contains(ind)) 
             {  foundlecture=true;
            	SetData(GetData(table,currentrow,0),table2,indirow,col);
                lecturecount++;
             }
           }
           currentrow++; 
    		
    	}
        
        JTableHeader th = table2.getTableHeader();
    	TableColumnModel tcm = th.getColumnModel();
    	TableColumn tc ;
    	tc= tcm.getColumn(0);
		
        String LC=String.format("%s-%d",ind.substring(1,ind.length()-1),lecturecount);
        tc.setHeaderValue(LC);th.repaint();
    }
    

    public void DeleteLastEmptyTimeSlots()
    {     String temp;
           //boolean foundlecture=false;   

           for (int i = ROWCOUNT2-1; i>1 ;i--)
     	        { 
    		      for(int j = 1; j < COLCOUNT; j++)
    		      {temp=GetData(table2,i,j);
    		       if(temp.length()!=0) return;
     	          }
    		      table2.setValueAt("", i,0);
     	      }
    	
    }
    
    
    public void ClearIndividualTable() /////same as class table
    {
    	   for (int i = 0; i < ROWCOUNT2; i++)
    	      for(int j = 0; j < table2.getColumnCount(); j++)
    	      {
    	          table2.setValueAt("", i, j);
    	      }
    	   
   	}
    
}



