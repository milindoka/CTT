package ctt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class View {
      
	final FreezeCellRenderer fRenderer = new FreezeCellRenderer();
    private JFrame frame;
    private JButton SaveBT,LoadBT,PrinCU,SetPRN,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15;
    private DefaultTableModel model;
    private DefaultTableModel model2;
    JTable table; 
    JTable table2;
    int ROWCOUNT2=25;         /////500;////For Master Print
    int ROWCOUNT=100; ///Main Table
    int COLCOUNT=7;
    
    
    int CC,DC,GC,indirow,lecturecount;
    JLabel cc,dc,gc;
    String ClashCount,DoubleCount,GapCount;   
    ListSelectionModel listSelectionModel;
    
    int ROWS=25,COLS=7;  ////For create display matrix
    String[][] Matrix = new String[ROWS][COLS];
    
    
    
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
    {ClearIndividualTable();
    int row = table.getSelectedRow();
    int col = table.getSelectedColumn();
    String str = (String)table.getValueAt(row, col);
    if(str.length()==0) return;
    if(col==0){  DisplayClass(str); return; }
    int left=str.indexOf("(");
    int rite=str.indexOf(")");
    if(left<0 || rite<0) return;
    final String teachercode = str.substring(str.indexOf("("),str.indexOf(")")+1);
    //DisplayIndividual(teachercode);
    CreateIndi(teachercode);
    CountGaps();
    CountDoubles();
    DeleteLastTimeSlot();
    UpdateDisplay(teachercode);
    UpdateCounts(teachercode);
    
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
     
            
            TableColumnModel colModel = table.getColumnModel();
            int ccc = colModel.getColumnCount();
            for (int c=0; c<ccc; c++)
            {
                TableColumn column = colModel.getColumn(c);
                column.setCellRenderer(fRenderer);
                
            }
            
            
            
            
            
            
            
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

  ///--------ENTER KEY TO FREEZE CURRENT CELL CONTENT--------------------------------------
            
            InputMap inputMap2 = table.getInputMap(JComponent.WHEN_FOCUSED);
            ActionMap actionMap2 = table.getActionMap();

            inputMap2.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Freeze");
            actionMap2.put("Freeze", new AbstractAction() {
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
                      fRenderer.setCellColor(row,col,Color.YELLOW);
                      table.repaint();
                   }
                }
            });            
     /////////---------------END OF FREEZE-------------------------------------
            
            
            
            
            
            
            
            
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
             
         
             final ColoringCellRenderer cellRenderer = new ColoringCellRenderer();
             
             TableColumnModel columnModel = table2.getColumnModel();
             ccc = columnModel.getColumnCount();
             for (int c=0; c<ccc; c++)
             {
                 TableColumn column = columnModel.getColumn(c);
                 column.setCellRenderer(cellRenderer);
                // column.setCellRenderer(fRenderer);
             }
            
        JScrollPane scrollPane2 = new JScrollPane(table2);
        frame.getContentPane().setLayout(new GridLayout(1,2));                                          
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);           
        frame.setSize(400,400);
        frame.setLocationRelativeTo(null);
        
        
        LoadBT = new JButton("Load Time Table");        
        SaveBT = new JButton("Save Time Table");
        PrinCU = new JButton("Print Current Time Table");
        SetPRN = new JButton("Set Printer");
        b5 = new JButton("Button5");
        
        b6 = new JButton("Button6");
        b7 = new JButton("Button7");
        b8 = new JButton("Button8");
        b9 = new JButton("Button9");
        b10= new JButton("Button10");
        
        b11= new JButton("Button11");
        b12= new JButton("Button12");
        b13= new JButton("Button13");
        b14= new JButton("Button14");
        b15= new JButton("Button15");
        
        
        JPanel buttonPanel=new JPanel(); buttonPanel.setLayout(new GridLayout(5,3,2,2));
        buttonPanel.add(SaveBT);
        buttonPanel.add(LoadBT);
        buttonPanel.add(PrinCU);
        buttonPanel.add(SetPRN);
        
        buttonPanel.add(b5);
        buttonPanel.add(b6);
        buttonPanel.add(b7);
        buttonPanel.add(b8);
        buttonPanel.add(b9);
        buttonPanel.add(b10);
        
        buttonPanel.add(b11);
        buttonPanel.add(b12);
        buttonPanel.add(b13);
        buttonPanel.add(b14);
        buttonPanel.add(b15);
        
        
        cc = new JLabel("CC :");
        dc = new JLabel("DC :");
        gc = new JLabel("GC :");
        
        JPanel countpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      //  countpanel.setLayout(new FlowLayout());
        countpanel.setPreferredSize(new Dimension(200, 25));
        countpanel.add(cc); 
        countpanel.add(dc); 
        countpanel.add(gc);
       
        JPanel southpanel=new JPanel();
        southpanel.setLayout(new BorderLayout());
        southpanel.add(scrollPane2, BorderLayout.SOUTH);
        southpanel.add(countpanel,BorderLayout.NORTH);
        
        JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
        panel.add(southpanel, BorderLayout.SOUTH);
        panel.add(buttonPanel, BorderLayout.NORTH);
       // panel.add(nameLabelPanel,BorderLayout.CENTER);
        
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
    
    
    public void CreateIndi(String ind)
    {
    	String sub[];
    	for(int i=0;i<ROWS;i++)
		 for(int j=0;j<COLS;j++) Matrix[i][j]="";  ////clean matrix
    	
    CC=0;DC=0;GC=0;  ///reset all counts
    lecturecount=0;

    String originalclass,newclass;
	String temp="",currenttime="";    int currentrow=0; boolean foundlecture=false;    	
	String temp2;
    ////Get First Time Slot
    for(currentrow=0;currentrow<ROWCOUNT-1;currentrow++)
    	{ temp=GetData(table,currentrow,0); if(temp.contains(":")) { currenttime=temp; break; }}
	if(!currenttime.contains(":")) return; ///no time slot  found
	////////////////////
    
	currentrow++;
    indirow=0; ///initialize individual row pointer

   // SetData(currenttime,table2,indirow,0); ///set first time slot in individual
    
    Matrix[indirow][0]=currenttime;
    
    //for(int col=1;col<7;col++) SetData("",table2,indirow,col);
    
    while(currentrow<ROWCOUNT-1)   	
	{  ///update time slot if next time slot starts
    	temp=GetData(table,currentrow,0); 
    	if(temp.contains(":"))
    	 { ////// if lecture found set new time slot otherwise overwrite time slot
    	   currenttime=temp; currentrow++; if(foundlecture) indirow++;  
    	   //SetData(currenttime,table2,indirow,0);
    	   Matrix[indirow][0]=currenttime;
    	  
    	 }
       
       for(int col=1;col<7;col++)
       {temp=GetData(table,currentrow,col);
        
        if(temp.contains(ind)) 
         {  foundlecture=true;
            originalclass=Matrix[indirow][col];
            sub=temp.split("\\(");
           // System.out.println(sub[0]);
            newclass=GetData(table,currentrow,0)+"-"+sub[0];
            if(originalclass.length()!=0)
            	{ Matrix[indirow][col]=originalclass+";"+newclass; CC++;}
            else
            	Matrix[indirow][col]=newclass;
            
            lecturecount++;
         }
       }
       currentrow++; 
		
     }
    	
   }

    
    private void CountGaps()
    { String temp;
      int firstnonempty=0,lastnonempty=0;
      for(int c=1;c<7;c++)
      { lastnonempty=indirow;
         for(lastnonempty=indirow;lastnonempty>0;lastnonempty--)
    	  {
    	       temp=Matrix[lastnonempty][c];
		       if(temp.length()!=0) break;
    	   }
    
         for(firstnonempty=0;firstnonempty<indirow;firstnonempty++)
             { temp=Matrix[firstnonempty][c];
	               if(temp.length()!=0) break;
             }
    
        while(firstnonempty<lastnonempty)
         {temp=Matrix[firstnonempty][c];
	          if(temp.length()==0) GC++;
    	   firstnonempty++;
         }
       }
    }
    
    private void CreatePerPerDivisionChart()
    {  String temp="";
    String[] Division = new String[100];
    int[] Count =new int[100];
    int items=0;
    boolean found=false;
    
    for(int r=0;r<=indirow;r++)
   	 for(int c=1;c<7;c++)
   	   { temp=Matrix[r][c];
   		 if(temp.length()==0) continue;
   	     //otherwise update count
   		 found=false;
   		 for(int i=0;i<items;i++)
   	    	 if(Division[i].contains(temp)) { Count[i]++;found=true; break;} 
   	    	 	 
   	    	if(!found)  { Division[items]=temp; Count[items]=1; items++;} 
   	   }

    	
    }
    
    
   private void CountDoubles()
   { String temp,temp2;     
	   for(int c=1;c<7;c++)
     {
       for(int r=0;r<indirow;r++)
        { temp=Matrix[r][c];
          if(temp.length()==0) continue;
          {
               for(int k=r+1;k<=indirow;k++)  
       
                 {  temp2=Matrix[k][c];
            	   if(temp2.length()==0) continue;
            	   if(temp.contains(temp2)) DC++;
                 }
           }
        }
    }
   }
    
   private void UpdateCounts(String ind)
   {
	    
	    JTableHeader th = table2.getTableHeader();
		TableColumnModel tcm = th.getColumnModel();
		TableColumn tc ;
		tc= tcm.getColumn(0);
		
	    String LectureCount=String.format("%s-%d",ind.substring(1,ind.length()-1),lecturecount);
	    tc.setHeaderValue(LectureCount);th.repaint();
	    
	    ClashCount=String.format("CC : %d  ",CC);
	    DoubleCount=String.format("DC : %d  ",DC);
	    GapCount=String.format("GC : %d",GC);
	    cc.setText(ClashCount); dc.setText(DoubleCount); gc.setText(GapCount);

	   
   }
    
    
    private void UpdateDisplay(String ind)
    {ClearIndividualTable();
    for (int i = 0; i < ROWCOUNT2; i++)
	      for(int j = 0; j < table2.getColumnCount(); j++)
	          table2.setValueAt(Matrix[i][j], i, j);
	      
        	
    }

        
    private void DeleteLastTimeSlot() ///Delete Extra time slot resulting from main while loop
    {   String temp;

    for (int i = indirow; i>1 ;i--)
	      { 
		      for(int j = 1; j < COLCOUNT; j++)
		      {temp=Matrix[i][j];
		       if(temp.length()!=0) return; ///return if time slot is not extra 
	          }
		    Matrix[i][0]="";   ////delete extra
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
    
    
    
    public class ColoringCellRenderer extends DefaultTableCellRenderer
    {
    	  public Component getTableCellRendererComponent(JTable table, 
    	Object obj, boolean isSelected, boolean hasFocus, int row, int column) 
    	  {
    	    Component cell = super.getTableCellRendererComponent(
    	   table, obj, isSelected, hasFocus, row, column);

    	    TableModel model = table.getModel();
    	    
    	    String colYearValue = (String) model.getValueAt(row, column);

    	    if (colYearValue.contains(";"))
    	    {
    	        cell.setBackground(Color.cyan);
    	    }
    	     else 
    	    {
    	        cell.setBackground(Color.WHITE);
    	    }
    	   return cell;

    	  }
    	  

    }

    
    
    
    class FreezeCellRenderer extends DefaultTableCellRenderer
    {
        private final Map<Point, Color> cellColors = new HashMap<Point, Color>();

        void setCellColor(int r, int c, Color color)
        {
            if (color == null)
            {
                cellColors.remove(new Point(r,c));
            }
            else
            {
                cellColors.put(new Point(r,c), color);
            }
        }

        private Color getCellColor(int r, int c)
        {
            Color color = cellColors.get(new Point(r,c));
            if (color == null)
            {
                return Color.WHITE;
            }
            return color;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column)
        {
            super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
            Color color = getCellColor(row, column);
            setBackground(color);
            return this;
        }

    
    }
    
    
    
    
    
    
    
    
    

}









	




