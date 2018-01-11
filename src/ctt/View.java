package ctt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class View {
      
	final FreezeCellRenderer fRenderer = new FreezeCellRenderer();
	
	private JFrame frame;
    private JButton SaveBT,LoadBT,PRINTCURRENTbutton,SetPRN;
    private JButton GLOBALCOUNTbutton,DEMObutton,REMCLASHbutton,MULTIFRIZbutton;
    private JButton CLEARFRIZbutton,PRINTINDIbutton,PRINTCLASSbutton,PRINTMASTERbutton,b13,b14,b15;
    private DefaultTableModel model;
    private DefaultTableModel model2;
    JTable table;
    String LectureCount;    
    JTable table2;
    int COLS=7;
    int ROWCOUNT2=300;         /////500;////For Master Print ,change to 25 later
    int ROWCOUNT=200; ///Main Table
    int MROWS=25;         /// Individual rows in IndiMatrix;
    int ColorMatrix[][]= new int[ROWCOUNT][COLS];
    int COLCOUNT=7;
    
    JProgressBar jb=new JProgressBar(0,100);
    
    int CC,DC,GC,indirow,lecturecount;
    JLabel countLabel,spesLabel,msgLabel; 
    String allcounts;
    ListSelectionModel listSelectionModel;
  
    String[][] Matrix = new String[MROWS][COLS];
    
    
    
    public void SetData(Object obj, JTable table, int row_index, int col_index)    {  table.getModel().setValueAt(obj,row_index,col_index);  }
    public String GetData(JTable table, int row_index, int col_index) {  return (String) table.getModel().getValueAt(row_index, col_index); }    
    public void SetData2(Object obj, int row_index, int col_index)   
      {  table2.getModel().setValueAt(obj,row_index,col_index);  } 
   
    
    class MyColListener implements ListSelectionListener
    {
        
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting())
            {  
               refresh();
            }
        }
    }

    class MyRowListener implements ListSelectionListener 
    {
      	 
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) 
            { 
            	refresh();
            	        
                }
        }
    }

    
    
    private void refresh()
    {ClearIndividualTable();
    int row = table.getSelectedRow();
    int col = table.getSelectedColumn();
    String str = (String)table.getValueAt(row, col);
    if(str.length()==0) return;
    if(col==0)
       { //ClearIndividualTable(); 
         //DisplayClass(str);
    	 CreateClass(str);
    	 DeleteLastTimeSlot();
    	 CreatePerPerDivisionChart();
    	 UpdateDisplay();
          
         return; 
        }
    int left=str.indexOf("(");
    int rite=str.indexOf(")");
    if(left<0 || rite<0) return;
    final String teachercode = str.substring(str.indexOf("("),str.indexOf(")")+1);
    //DisplayIndividual(teachercode);
    CreateIndi(teachercode);
    CountGaps();
    CountDoubles();
    DeleteLastTimeSlot();
    CreatePerPerDivisionChart();
    UpdateDisplay();
    UpdateCounts(teachercode);
    }
    
    
    public View(String text){
        frame = new JFrame("View");                  
        frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
   
        
        jb.setValue(0); jb.setStringPainted(true);jb.setVisible(false);
        
        
        ////// Create JTable ---------------------------
        
           Object columnNames[] = { "Time", "MON", "TUE" ,"WED","THU","FRI","SAT"};
            
          //  JTable table = new JTable(rowData, columnNames);
           table = new JTable(new DefaultTableModel(columnNames, 0))
            { ////added tooltip

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
           
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.getViewport().putClientProperty
            ("EnableWindowBlit", Boolean.TRUE);

            scrollPane.putClientProperty("minimumPages","1");
          //  scrollPane.getVerticalScrollBar().setUnitIncrement(100);
            /////////////////
            
            TableColumnModel colModel = table.getColumnModel();
            int ccc = colModel.getColumnCount();
            for (int c=0; c<ccc; c++)
            {
                TableColumn column = colModel.getColumn(c);
                column. setCellRenderer(fRenderer);
                
            }
            
            
            
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

  ///--------CTRL-D KEY TO FREEZE CURRENT CELL CONTENT--------------------------------------
            
            InputMap inputMap2 = table.getInputMap(JComponent.WHEN_FOCUSED);
            ActionMap actionMap2 = table.getActionMap();

            inputMap2.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK), "Freeze");
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
                      if(col!=0)
                      {
                    	  Color color=fRenderer.getCellColor(row,col);  
                    	  if(color==Color.WHITE)
                    	  fRenderer.setCellColor(row,col,Color.YELLOW);
                    	  else
                    		  fRenderer.setCellColor(row,col,Color.WHITE);
                      
                    	  table.repaint();
                      }
                      
                   }
                }
            });            
     /////////---------------END OF FREEZE-------------------------------------
            
            
            
      ExcelAdapter myAd = new ExcelAdapter(table);        
            
            
            
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
        PRINTCURRENTbutton = new JButton("Print Current Table");
        SetPRN = new JButton("Set Printer");
        GLOBALCOUNTbutton = new JButton("Global Counts");
        
        DEMObutton = new JButton("Demo Time Table");
        REMCLASHbutton = new JButton("Remove Clash");
        MULTIFRIZbutton = new JButton("Multi Freeze");
        CLEARFRIZbutton = new JButton("Clear Freez");
       
        PRINTINDIbutton = new JButton("Print All Indi's");
        PRINTCLASSbutton = new JButton("Print All Classes");
        PRINTMASTERbutton = new JButton("Print Master");

        b13= new JButton("Button13");
        b14= new JButton("Button14");
        b15= new JButton("Button15");
        
        
        JPanel buttonPanel=new JPanel(); buttonPanel.setLayout(new GridLayout(5,3,2,2));
        buttonPanel.add(SaveBT);
        buttonPanel.add(LoadBT);
        buttonPanel.add(PRINTCURRENTbutton);
        buttonPanel.add(SetPRN);
        
        buttonPanel.add(GLOBALCOUNTbutton);
        buttonPanel.add(DEMObutton);
        buttonPanel.add(REMCLASHbutton);
        buttonPanel.add(MULTIFRIZbutton);
        buttonPanel.add(CLEARFRIZbutton);
        
        
        buttonPanel.add(PRINTINDIbutton);
        buttonPanel.add(PRINTCLASSbutton);
        buttonPanel.add(PRINTMASTERbutton);
        
        buttonPanel.add(b13);
        buttonPanel.add(b14);
        buttonPanel.add(b15);
        
        
        countLabel = new JLabel("CC : 0  DC : 0  GC : 0");
        spesLabel = new JLabel("        ");
        msgLabel=new JLabel(" Enter To Stop");
        msgLabel.setVisible(false);
        JPanel countpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
      //  countpanel.setLayout(new FlowLayout());
        countpanel.setPreferredSize(new Dimension(200, 25));
        countpanel.add(countLabel);
        countpanel.add(spesLabel);
        countpanel.add(jb); 
        countpanel.add(msgLabel);
        //countpanel.add(gc);
       
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
   
    public JButton getPRINTCURRENTbutton()
    { return PRINTCURRENTbutton;
    }
    

    public JButton getSetPRN()
    { return SetPRN;
    }
    
    public JButton getGLOBALCOUNTbutton()
    {return GLOBALCOUNTbutton;
    	
    }
    
    public JButton getREMCLASHbutton()
    {
    	return REMCLASHbutton;
    }
    
    public JButton getDEMObutton()
    { return DEMObutton;
    }
    
    public JButton getMULTIFRIZbutton()
    { return MULTIFRIZbutton;
    }
    
    public JButton getCLEARFRIZbutton()
    { return CLEARFRIZbutton;
    }
    

    public JButton getPRINTINDIbutton()
    { return PRINTINDIbutton;
    }

    public JButton getPRINTCLASSbutton()
    { return PRINTCLASSbutton;
    }

    public JButton getPRINTMASTERbutton()
    { return PRINTMASTERbutton;
    }
    
    
    public void CreateClass(String clas)
    {
/////////////Must wait till Matrix is Clean    	
    	ThreadB b = new ThreadB();
        b.start();
 
        synchronized(b){
            try{  b.wait();
                }catch(InterruptedException e){
                e.printStackTrace();
                }
        }
    	
   ////////////////////////////////////////// 	
    String temp="",currenttime="";
	if(clas.contains(":")) return;
	if(clas.length()==0) return;
    int currentrow=0;
    indirow=0;    	
    
    while(currentrow<ROWCOUNT-1)   	
	{  ///update time slot if next time slot starts
    	temp=GetData(table,currentrow,0); 
    	if(temp.contains(":"))
    	 { currenttime=temp;
    	   Matrix[indirow][0]=currenttime;
    	  // SetData(currenttime,table2,clasrow,0); ///set new time slot in individual
    	 }
    	
        if(temp.contains(clas)) 
         {  
        	for(int col=1;col<7;col++)
        	//SetData(GetData(table,currentrow,col),table2,clasrow,col);
        	Matrix[indirow][col]=GetData(table,currentrow,col);
        	indirow++;
        	
         }
       currentrow++;
	}
    	
    }
    
        
    
    class ThreadB extends Thread 
    {
        @Override
        public void run()
        {
            synchronized(this)
            {
            	for(int i=0;i<MROWS;i++)
           		 for(int j=0;j<COLS;j++) Matrix[i][j]="";  ////clean matrix               
                
                notify();
            }
        }
    }
    
    public void CreateIndi(String ind)
    {
    	/////////////////////////////
    	
    	ThreadB b = new ThreadB();
        b.start();
 
        synchronized(b){
            try{
    //            System.out.println("Waiting for b to complete...");
                b.wait();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
 
         //   System.out.println("Total is: " + b.total);
        }
    
    	
    	
    	//////////////////////////////  	
     String sub[];
    CC=0;DC=0;GC=0;  ///reset all counts
    lecturecount=0;

    String originalclass,newclass;
	String temp="",currenttime="";    int currentrow=0; boolean foundlecture=false;    	
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

    
    public void CountGaps()
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
    
    public void CreatePerPerDivisionChart()
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
    int rowno=indirow+2;
    int colno=1;
    for(int i=0;i<items;i++)
    {    
    	 Matrix[rowno][colno]=Division[i];
         temp=String.format("%d", Count[i]);
         colno++;
         Matrix[rowno][colno]=temp;
         colno++;
         
         if(colno>6) {colno=1;rowno++;}
         
    }
    	
    }
    
    
   public void CountDoubles()
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
    
   public void UpdateCounts(String ind)
   {
	    
	    JTableHeader th = table2.getTableHeader();
		TableColumnModel tcm = th.getColumnModel();
		TableColumn tc ;
		tc= tcm.getColumn(0);
		
	    LectureCount=String.format("%s-%d",ind.substring(1,ind.length()-1),lecturecount);
	    tc.setHeaderValue(LectureCount);th.repaint();
	    
	    allcounts=String.format("CC : %d  DC : %d  GC : %d",CC,DC,GC);
	    countLabel.setText(allcounts);

	   
   }
   
   public void ShowGlobalCounts(String label)
   {
   countLabel.setText(label);
	   
   }

    
    public void UpdateDisplay()
    {//ClearIndividualTable();
    for (int i = 0; i < MROWS; i++)
	      for(int j = 0; j < table2.getColumnCount(); j++)
	          table2.setValueAt(Matrix[i][j], i, j);
	      
        	
    }

        
    public void DeleteLastTimeSlot() ///Delete Extra time slot resulting from main while loop
    {   String temp;

    for (int i = indirow; i>1 ;i--)
	      { 
		      for(int j = 1; j < COLCOUNT; j++)
		      {temp=Matrix[i][j];
		       if(temp.length()!=0) return; ///return if time slot is not extra 
	          }
		    Matrix[i][0]="";   ////delete extra
		    indirow=i-1;
	      }
	

    	
    }
    
    
    public void ClearIndividualTable() /////same as class 
    {
    	
    	   for (int i = 0; i < MROWS; i++)
    	      for(int j = 0; j < table2.getColumnCount(); j++)
    	      {
    	          table2.setValueAt("", i, j);
    	      }
    	  
    }	
    
    
    void ClearColorMatrix()
    {for(int r=0;r<ROWCOUNT;r++)
		for(int c=1;c<7;c++) ColorMatrix[r][c]=0;
    	table.repaint();
    }
    
    void MultiFrizCellsContaing(String str)
    {for(int r=0;r<ROWCOUNT;r++)
		for(int c=1;c<7;c++) 
			{String temp=GetData(table,r,c);
			 if(temp.contains(str)) ColorMatrix[r][c]=1;
			}
    	table.repaint();
    }
    
    
    public class ColoringCellRenderer extends DefaultTableCellRenderer
    {
		private static final long serialVersionUID = 1L;
	
		
		
		Color clashred=new Color(255,200,200);
    	
    	
    	  public Component getTableCellRendererComponent(JTable table, 
    	Object obj, boolean isSelected, boolean hasFocus, int row, int column) 
    	  {
    	    Component cell = super.getTableCellRendererComponent(
    	   table, obj, isSelected, hasFocus, row, column);

    	    TableModel model = table.getModel();
    	    
    	    String colYearValue = (String) model.getValueAt(row, column);

    	    if (colYearValue.contains(";"))
    	    {
    	        cell.setBackground(clashred);
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
    	/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Color focusblue=new Color(51,249,255);
    	FreezeCellRenderer() 
    	{for(int r=0;r<ROWCOUNT;r++)
    		for(int c=0;c<7;c++) ColorMatrix[r][c]=0;
    		
    	}

        void setCellColor(int r, int c, Color color)
        {  
            if (color ==Color.WHITE)
            {
                ColorMatrix[r][c]=0;
            }
            else
            {
                ColorMatrix[r][c]=1;
            }
            
            
        }

        private Color getCellColor(int r, int c)
        {
            //Color color = cellColors.get(new Point(r,c));
        	
        	
        	if(ColorMatrix[r][c]==0)
     
            {
                return Color.WHITE;
            }
            return Color.YELLOW;
        }

       // @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column)
        {
            super.getTableCellRendererComponent(
                table, value, isSelected, hasFocus, row, column);
            Color color = getCellColor(row, column);
            setBackground(color);

            if(isSelected) setBackground(focusblue);
            
    	    TableModel model = table.getModel();
    	    
    	    String colYearValue = (String) model.getValueAt(row, column);

    	    
    	    if (colYearValue.contains(":"))
    	    {
    	        setBackground(Color.GREEN);
    	    }
//    	     else 
  //  	    {
    //	        setBackground(Color.WHITE);
    	//    }

            
            return this;
        }

    
    }
    
    

}









	




