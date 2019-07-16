package ctt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class View {

	
	 public void show(String msg) 
	    {JOptionPane.showMessageDialog(null, msg);}
	
	final FreezeCellRenderer fRenderer = new FreezeCellRenderer();
	boolean modified=false;
	public JFrame frame;
    private JButton FileBT,FreezeBT,PRINTMENUbutton,SetPRN;
    private JButton GLOBALCOUNTbutton,DEMObutton,REMCLASHbutton,MULTIFRIZbutton;
    private JButton CLEARFRIZbutton,WIZARDbutton,PRINTCLASSbutton,FINDbutton;
    private JButton TIMESAMPLEbutton,INSERTROWbutton,SWAPbutton,multiselectbutton,DELETEROWbutton;
    private JButton FINDREPLACEbutton,REMGAPDbutton,SCHOOLbutton,HELPbutton;
    private DefaultTableModel model;
    private DefaultTableModel model2;
    private DefaultTableModel model3;
    public Toast toast;
    public String collegename="SCHOOL/COLLEGE";
    private String DragCellBuffer="";
    private boolean isClass=true; // 1=true, 0 false;
    
    
    Cursor dragCursor = new Cursor(Cursor.TEXT_CURSOR);
    String LectureCount;
    JTable table;
    JTable table2;
    JTable table3;
    int COLS=7;
    int ROWCOUNT2=25;         /////500;////For Master Print ,change to 25 later
    int ROWCOUNT=200; ///Main Table
    int ROWCOUNT3=2000; //for prepare print table 3
    int MROWS=25;         /// Individual rows in IndiMatrix;
    int ColorMatrix[][]= new int[ROWCOUNT][COLS];
    int COLCOUNT=7;
    JTextField tcField;
   
 
    
    JProgressBar jb=new JProgressBar(0,100);
    
    int CC,DC,GC,indirow,lecturecount;
    JLabel countLabel,spesLabel,msgLabel,dragnoteLabel,centerLabel; 
    String allcounts;
//    ListSelectionModel listSelectionModel;
  
    String[][] Matrix = new String[MROWS][COLS];
    
    public void SetTitle(String title) {frame.setTitle("College Time Table ( CTT ) : "+title);}
    public void SetData(Object obj, JTable table, int row_index, int col_index)    {  table.getModel().setValueAt(obj,row_index,col_index);  }
    public String GetData(JTable table, int row_index, int col_index) {  return (String) table.getModel().getValueAt(row_index, col_index); }    
    public void SetData2(Object obj, int row_index, int col_index)   
      {  table2.getModel().setValueAt(obj,row_index,col_index);  } 
   
    public void SetData3(Object obj, int row_index, int col_index)   
    {  table3.getModel().setValueAt(obj,row_index,col_index);  }
    
    class MyColListener implements ListSelectionListener
    {
        
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting())
            {  
              refresh();
            }
        }
    }

    class MyRowListener implements ListSelectionListener 
    {
      	 
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) 
            { 
            	refresh();
            	        
                }
        }
    }
    
    public void ClearMasterTable() /////same as class 
    {
    	
    	   for (int i = 0; i < ROWCOUNT-2; i++)
    	      for(int j = 0; j < table.getColumnCount(); j++)
    	      {
    	          table.setValueAt("", i, j);
    	      }
    	  
    }
    
    private void SetEmptyHeader()
    {JTableHeader th = table2.getTableHeader();
	TableColumnModel tcm = th.getColumnModel();
	TableColumn tc ;
	tc= tcm.getColumn(0);
    tc.setHeaderValue("");th.repaint();
    }
    
    
    private void refresh()
    {ClearIndividualTable();
     tcField.setText("");
     SetEmptyHeader();
    int row = table.getSelectedRow();
    int col = table.getSelectedColumn();
    
    if(row<0 || col<0) return;
    String str = (String)table.getValueAt(row, col);
    if(str==null) return;
    if(str.length()==0 ) return;
    if(str.contains(":")) return;
    
    if(col==0)
       { 
    	 CreateClass(str);
    	 DeleteLastTimeSlot();
    	 CreatePerPerDivisionChart();
    	 UpdateDisplay();
         UpdateCountsClass(str);
         isClass=true;
         return; 
        }
    if(str.length()<7) return;
    if(str.charAt(3)!='(') return;
    int left=str.indexOf("(");
    int rite=str.indexOf(")");
    if(left<0 || rite<0) return;
    final String teachercode = str.substring(str.indexOf("(")+1,str.indexOf(")"));
    
    tcField.setText(teachercode);
    isClass=false;
    }
    
    
    public View(String text)
    {
    	frame = new JFrame("College Time Table ( CTT )");                  
        frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        jb.setValue(0); jb.setStringPainted(true);jb.setVisible(false);
        
        
        ////// Create JTable ---------------------------
        
           WeekDays wd=new WeekDays();
           String columnNames[] =Arrays.copyOf(wd.weekdays, wd.weekdays.length); 

        
           //Object columnNames[]={"TIME","SAT","SUN","MON","TUE","WED","THU"};
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

         
           //////88888888888888888
            
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
           table.addMouseListener(new MouseAdapter() 
           {

            //    @Override
                public void mouseDragged(MouseEvent e) {
                    System.out.println("mouseDragged");
                }

               // @Override
                public void mousePressed(MouseEvent e) 
                { 
                Point p = e.getPoint();
                int row = table.rowAtPoint(p);
                int col = table.columnAtPoint(p);
                if(col<=0) return;
                if(row>=0) 
                DragCellBuffer=GetData(table,row,col);
               
            
                Toolkit t = Toolkit.getDefaultToolkit ();
                Image img= t.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("dragcursor.png")); 
                dragCursor = t.createCustomCursor (img, new Point (0,0), "cur"); 
                frame.setCursor(dragCursor);
                
                }

           //     @Override
                public void mouseReleased(MouseEvent e) 
                {Point p = e.getPoint();
                 int row = table.rowAtPoint(p);
                 int col = table.columnAtPoint(p);
                 if(col<=0) return;
                if(row>=0)
                SetData(DragCellBuffer,table,row,col);
                Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
                frame.setCursor(normalCursor);
                refresh();
                }

            });
            
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
            
        //   listSelectionModel = table.getSelectionModel();
           table.getSelectionModel()
                   .addListSelectionListener(new MyRowListener());
            table.getColumnModel().getSelectionModel()
                    .addListSelectionListener(new MyColListener());
           // table.setSelectionModel(listSelectionModel);
     //////////////////////////
            table.getModel().addTableModelListener(new TableModelListener() {

				@Override
				public void tableChanged(TableModelEvent arg0) 
				{
					modified=true;
					
				}
              });
////////////////////////////////////////     
            
            
 
         /*  
             table.addMouseMotionListener(new MouseAdapter() {
       	      @Override public void mouseDragged(MouseEvent e) {
       	        JComponent c = (JComponent) e.getComponent();
       	        Optional.ofNullable(c.getTransferHandler())
       	                .ifPresent(th -> th.exportAsDrag(c, e, TransferHandler.COPY));
       	      }
       	    });

           */  

            
            
              ///--------DELETE KEY TO CLEART CELL or RANGE OF CELLS-------------------------------------
            
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
					 int[] rowIndices = table.getSelectedRows();
					 int[] columnIndices = table.getSelectedColumns();
                     
					 for(int r=0;r<rowIndices.length;r++)
						 for(int c=0;c<columnIndices.length;c++)
					  {                	   
                	   int row = table.convertRowIndexToModel(rowIndices[r]);
                       int col = table.convertColumnIndexToModel(columnIndices[c]);
                       table.getModel().setValueAt("", row, col);
                      }
                }
            });            
     /////////------------------------------------------------------------------------
      ExcelAdapter myAd = new ExcelAdapter(table);        
            
            
            
     ////// Create Table2  ---------------------------
            
             String columnNames2[] =Arrays.copyOf(wd.weekdays, wd.weekdays.length);
             //String columnNames2[] = { "TIME", "MON", "TUE" ,"WED","THU","FRI","SAT"};
             
            
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
             
             table2.setEnabled(false);
             
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
             
             //////99999999999999999
             
             table2.addMouseListener(new MouseAdapter() 
             { 
                 //    @Override
                     public void mouseDragged(MouseEvent e) {
                      //   System.out.println("mouseDragged");
                     }

                     @Override
                     public void mousePressed(MouseEvent e) 
                     {  
                      	   Point p = e.getPoint();
                           int row = table2.rowAtPoint(p);
                           int col = table2.columnAtPoint(p);
                           if(row>=0 && col>=1)
                           { ClearTable2AfterLastTime();
                        	 DragCellBuffer=GetData(table2,row,col);
                  	         Toolkit t = Toolkit.getDefaultToolkit ();
                             Image img= t.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("dragcursor.png")); 
                             dragCursor = t.createCustomCursor (img, new Point (0,0), "cur"); 
                             frame.setCursor(dragCursor);                   
                           }
                     
                           if(isClass) 
                          {    int len=DragCellBuffer.length();
                               if(len<4) return;
                               if(!DragCellBuffer.contains("(")) return;
                               if(!DragCellBuffer.contains(")")) return; 
                        	   String teachercode = DragCellBuffer.substring(DragCellBuffer.indexOf("(")+1,DragCellBuffer.indexOf(")"));
                        	   CreateIndi("("+teachercode+")");
                        	   UpdatePinkDisplay();
                           }
                           else
                           {
                        	   int len=DragCellBuffer.length();
                        	   String sourceclass="";
                               if(len>4)
                        	   {   
                            	   if(DragCellBuffer.contains(";"))
                                   { String temp[]=DragCellBuffer.split(";");
                                     int len2=temp[0].length();
                                     if(len2<4) return;
                                     sourceclass=temp[0].substring(0,len2-4);
                           	         
                                    }
                            	   else
                            		   sourceclass=DragCellBuffer.substring(0,len-4);
                            	       
                            	   CreateClass(sourceclass);
                         	         UpdatePinkDisplay();
   
                        	   }
                               
                           //   show(sourceclass);
                               
                           } 	   
                        	   
                     }

                //     @Override
                     public void mouseReleased(MouseEvent e) 
                     {
                       if(isClass)
                       {
                    	 
                    	   Point p = e.getPoint();
                     	   int row = table2.rowAtPoint(p);
                           int col = table2.columnAtPoint(p);
                        
                           Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
                           frame.setCursor(normalCursor);
                        
                            if(row<0 || col<1) return;
                        
                            int i=0,j=0;
                    	   
                    	   String timestr=GetData(table2,row,0);
                            
                            boolean timefound=false;
                            for(i=0;i<ROWCOUNT;i++)
                            {  String temp=GetData(table,i,0);
                        	   if(temp.equalsIgnoreCase(timestr)) 
                                 { timefound=true; break;}
                                                           
                            }

                             if(timefound)  ///if time found then locate class 
                                { 
                        	       for(j=i+1;j<ROWCOUNT;j++)
                        	        { String tempclas=GetData(table,j,0);
                        	          if(tempclas.length()==0) continue;
                        		      if(tempclas.contains(LectureCount))
                        		       { SetData(DragCellBuffer,table,j,col);
                                         
                        		         break;
                        	         	}
                        			 
                        	             if(tempclas.contains(":")) break;	
                        	        }
                                 }
                         refresh();
                        }
                     
                       else
                       {
                    	   
                    	   Point p = e.getPoint();
                     	   int row = table2.rowAtPoint(p);
                           int col = table2.columnAtPoint(p);
                        
                           Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
                           frame.setCursor(normalCursor);
                        
                            if(row<0 || col<1) return;
                        
                            int i=0,j=0;
                    	   
                            String timestr=GetData(table2,row,0);
                            boolean timefound=false;
                            for(i=0;i<ROWCOUNT;i++)
                            {  String temp=GetData(table,i,0);
                        	   if(temp.equalsIgnoreCase(timestr)) 
                                 { timefound=true; break;}
                                                           
                            }
                            
                            
                            
                             if(timefound)  ///if time found then locate class 
                                {     if(DragCellBuffer.contains(";"))
                                        { String temp[]=DragCellBuffer.split(";");
                                          DragCellBuffer=temp[0];
                                	
                                         }
                            	 
                            	   int len=DragCellBuffer.length();
                            	   String sourceclass="";
                                   if(len>4)
                            	   {
                                    sourceclass=DragCellBuffer.substring(0,len-4);
                            	 	String sub=DragCellBuffer.substring(len-3);
                            	 	DragCellBuffer=sub+"("+LectureCount.substring(0,2)+")";
                            	   }
                                   else
                                   {String targetcell=GetData(table2,row,col);
                                   if(targetcell.contains(";"))
                                   { String temp[]=targetcell.split(";");
                                     targetcell=temp[0];
                           	
                                    }
                                   
                                    len=targetcell.length();
                                    if(len<4) return;
                                    sourceclass=targetcell.substring(0,len-4);
                                    DragCellBuffer="";
                                   }
                            	 	
                            	 	for(j=i+1;j<ROWCOUNT;j++)
                        	        { String tempclas=GetData(table,j,0);
                        		      if(tempclas.contains(sourceclass))
                        		       { SetData(DragCellBuffer,table,j,col);
                                         
                        		         break;
                        	         	}
                        			 
                        	             if(tempclas.contains(":")) break;	
                        	        }
                                 }
                         refresh();
                    	   
                       }
                     /////////// else ends here                     
                     }
                     
                 });
                 
                 
                  //////9999999999999999

             

             ////************IMPORTANT********************************************
             ////table3 largr & is not visible, only used for pagination in printing;
             //// Columnnames2 are not used anywhere in table3
             ////******************************************************************
             table3 = new JTable(new DefaultTableModel(columnNames2, 0)); 
             
             
             
             model3 =  (DefaultTableModel) table3.getModel();
             for(int i=0;i<ROWCOUNT3;i++) model3.addRow(new Object[]{"", "", "","","","",""});
             
             //table2.getColumnModel().getColumn(0).setMinWidth(100);

             
             
             
             
        JScrollPane scrollPane2 = new JScrollPane(table2);
        frame.getContentPane().setLayout(new GridLayout(1,2));                                          
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);           
        frame.setSize(400,400);
        frame.setLocationRelativeTo(null);
        
        
        FreezeBT = new JButton("Freeze Cell (Ctrl-D)");        
        FileBT = new JButton("File Menu");
        PRINTMENUbutton = new JButton("Print Menu");
        SetPRN = new JButton("Set Printer");
        GLOBALCOUNTbutton = new JButton("Global Counts");
        
        DEMObutton = new JButton("Demo Time Table");
        REMGAPDbutton = new JButton("Remove Gaps-Doubles");
        MULTIFRIZbutton = new JButton("Multi Freeze");
        CLEARFRIZbutton = new JButton("Clear Freez");
       
        WIZARDbutton = new JButton("Input Wizard");
        PRINTCLASSbutton = new JButton("Print All Classes");
        FINDbutton = new JButton("Find  (Ctrl-F)");

        FINDREPLACEbutton = new JButton("Find/Replace");
        TIMESAMPLEbutton= new JButton("Time Slot Sample");
        INSERTROWbutton= new JButton("Insert Row");
        SWAPbutton= new JButton("Swap Time Tables");
        multiselectbutton= new JButton("Multi Select OFF");
        DELETEROWbutton= new JButton("Delete Row");
        REMCLASHbutton=new JButton("Remove Clashes");
        SCHOOLbutton=new JButton("Button17");
        HELPbutton=new JButton("Help");
        
        
        JPanel buttonPanel=new JPanel(); buttonPanel.setLayout(new GridLayout(7,3,2,2));
        
        buttonPanel.add(FileBT);
        buttonPanel.add(PRINTMENUbutton);
        buttonPanel.add(SetPRN);
       

        buttonPanel.add(DEMObutton);
        buttonPanel.add(GLOBALCOUNTbutton);
        buttonPanel.add(REMGAPDbutton);
        
        buttonPanel.add(FreezeBT);
        buttonPanel.add(MULTIFRIZbutton);
        buttonPanel.add(CLEARFRIZbutton);
        
        
        buttonPanel.add(FINDbutton);
        buttonPanel.add(WIZARDbutton);
        buttonPanel.add(PRINTCLASSbutton);
        
        
        buttonPanel.add(FINDREPLACEbutton);
        buttonPanel.add(TIMESAMPLEbutton);
        buttonPanel.add(INSERTROWbutton);
        
        buttonPanel.add(SWAPbutton);
        buttonPanel.add(multiselectbutton);
        buttonPanel.add(DELETEROWbutton);
        buttonPanel.add(REMCLASHbutton);
       
        buttonPanel.add(SCHOOLbutton);
        buttonPanel.add(HELPbutton);
        
        
        countLabel = new JLabel("CC : 0  DC : 0  GC : 0");
        spesLabel = new JLabel("       ");
        msgLabel=new JLabel("Teacher code : ");
        tcField=new JTextField(2);
        tcField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                tcField.setText("");
            }
        });
        
        
        tcField.setDocument(new JTextFieldLimit(2));
    	tcField.getDocument().addDocumentListener(onTeacherCode); 
        msgLabel.setVisible(true);
        JPanel countpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        countpanel.setPreferredSize(new Dimension(300, 45));
        
       // countpanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        
        dragnoteLabel=new JLabel(" Use Drag and Drop to Copy Cells");
        dragnoteLabel.setForeground(Color.red);
        
        countpanel.add(countLabel);
        countpanel.add(spesLabel);
        countpanel.add(msgLabel);
        countpanel.add(tcField);
        countpanel.add(dragnoteLabel);
        countpanel.add(jb); 
        
      // JPanel centerpanel=new JPanel();
       
       centerLabel = new JLabel(" ");
      // centerpanel.add(centerLabel)
       
        JPanel southpanel=new JPanel();
        southpanel.setLayout(new BorderLayout());
        southpanel.add(scrollPane2, BorderLayout.SOUTH);
        southpanel.add(countpanel,BorderLayout.NORTH);
        
        JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
        panel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(centerLabel,BorderLayout.CENTER);
        panel.add(southpanel, BorderLayout.NORTH);
        
        
        
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0,0,30,0));


        
        
        
        frame.add(scrollPane);
        frame.add(panel);
        frame.setVisible(true);
     /*  
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	 toast=new Toast();
            }
        });
       */ 
        toast=new Toast();
       // TH.SetVP(this);
        
    //   SetFocusToCell(5,5);
        
    }

    
    public JButton getINSERTROWbutton()
    { return INSERTROWbutton;
    	
    }
    
    public JButton getSWAPbutton()
    { return SWAPbutton;
    	
    }
   
    public JButton getMULTISELECTbutton()
    { return multiselectbutton;
    	
    }
   
    public JButton getDELETEROWbutton()
    { return DELETEROWbutton;
    	
    }
   
    
    
    public JButton getWizard01BT()
    {
    	return TIMESAMPLEbutton;
    }
    
    
    public JButton getFindReplaceBT()
    {
    	return FINDREPLACEbutton;
    }
    
    public JButton getFileBT()
    {
        return FileBT;
    }
    
    public JButton getLoadBT()
    {
        return FreezeBT;
    }
   
    public JButton getPRINTMENUbutton()
    { return PRINTMENUbutton;
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
    

    public JButton getREMGAPDbutton()
    {
    	return REMGAPDbutton;
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
    

    public JButton getWIZARDbutton()
    { return WIZARDbutton;
    }

    public JButton getPRINTCLASSbutton()
    { return PRINTCLASSbutton;
    }

    public JButton getFINDbutton()
    { return FINDbutton;
    }
    

    public JButton getSCHOOLbutton()
    { return SCHOOLbutton;
    }
    
    public JButton getHELPbutton()
    { return HELPbutton;
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
          String subject="";
          int n=temp.indexOf(ind);
          if(n<7) subject=temp.substring(0,n);
          else 
        	  try {
        	  subject=temp.substring(n-3,n); 
        	  } catch (IndexOutOfBoundsException e) 
        	  {
        		  subject="---";
        	      System.out.println(temp);
        	  }
          
          
  
        // 
          newclass=GetData(table,currentrow,0)+"-"+subject;
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
    
    void CreatePerPerDivisionChart()
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

   
   void UpdateCountsClass(String clasname)
   {
	    
	    JTableHeader th = table2.getTableHeader();
		TableColumnModel tcm = th.getColumnModel();
		TableColumn tc ;
		tc= tcm.getColumn(0);
		LectureCount=clasname;
	    tc.setHeaderValue(clasname);th.repaint();
	    countLabel.setText(" ");
   }
   
   
    void UpdateCounts(String ind)
   {
	    
	    JTableHeader th = table2.getTableHeader();
		TableColumnModel tcm = th.getColumnModel();
		TableColumn tc ;
		tc= tcm.getColumn(0);
		
	    LectureCount=String.format("%s-%d",ind.substring(1,ind.length()-1),lecturecount);
	    tc.setHeaderValue(LectureCount);th.repaint();
	    
	    allcounts=String.format("CC : %d  DC : %d  GC : %d",CC,DC,GC);
	    countLabel.setText(allcounts);
	    dragnoteLabel.setVisible(true);
        msgLabel.setVisible(true);
        tcField.setVisible(true);
	    

	   
   }
   
   public void ShowGlobalCounts(String label)
   { dragnoteLabel.setVisible(false);
     msgLabel.setVisible(false);
     tcField.setVisible(false);
   countLabel.setText(label);
	   
   }

    
    private void UpdateDisplay()
    {//ClearIndividualTable();
    for (int i = 0; i < MROWS; i++)
	      for(int j = 0; j < table2.getColumnCount(); j++)
	          table2.setValueAt(Matrix[i][j], i, j);
	      
        	
    }

    private void UpdatePinkDisplay()
    {//ClearIndividualTable();
    for (int i = 0; i < MROWS; i++)
	      for(int j = 0; j < table2.getColumnCount(); j++)
	          { String str=GetData(table2,i,j);
	    	     if(str.length()!=0) continue; 
	             //if(Matrix[i][j].length()!=0)
	    	    table2.setValueAt(Matrix[i][j], i, j);
	          
	          }
	      
        	
    }
        
    void DeleteLastTimeSlot() ///Delete Extra time slot resulting from main while loop
    {   String temp;
    	for (int i = indirow; i>0 ;i--)
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
    
    public void ClearTable2AfterLastTime() /////same as class 
    {
    	   for (int i = MROWS-1; i>=0;i--)
    	      {  String str=GetData(table2,i,0);
    	         if(str.length()==0)
    		     for(int j = 0; j < table2.getColumnCount(); j++)
    	            {
    	             table2.setValueAt("", i, j);
    	            }
    	      }
    }	
    
    
    public void ClearTable3() /////same as class 
    {
    	
    	   for (int i = 0; i < MROWS; i++)
    	      for(int j = 0; j < table3.getColumnCount(); j++)
    	      {
    	          table3.setValueAt("", i, j);
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

        @Override
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
    
    DocumentListener onTeacherCode=new DocumentListener()
    {

		@Override
		public void changedUpdate(DocumentEvent arg0) 
		{//tcFieldAction();
			
			
		}

		@Override
		public void insertUpdate(DocumentEvent arg0) {
			// TODO Auto-generated method stub
			tcFieldAction();	
		}

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			// TODO Auto-generated method stub
			
		}
    	
    };
    

   void tcFieldAction()
   {
	    
	    String str = tcField.getText();
	    String trimmed=str.trim();
	    if(trimmed.length()<2) return;
	    String tc="("+trimmed+")";
	    CreateIndi(tc);
	    CountGaps();
	    CountDoubles();
	    DeleteLastTimeSlot();
	    CreatePerPerDivisionChart();
	    UpdateDisplay();
	    UpdateCounts(tc);
}

   

   void SetFocusToCell(int row,int col)
   {
	   table.setRowSelectionInterval(row,row);
	    table.setColumnSelectionInterval(col,col);

   }
   
   
   

}




