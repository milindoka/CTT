package ctt;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.beans.*;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class RemoveCDG extends JPanel implements ActionListener,PropertyChangeListener
   {

	
    private View view;
    private int globalCC,globalDC,globalGC;
    String o1,o2;
    int occ,odc,ogc;
    int sCCbefore,sDCbefore,sGCbefore;
    int sCCafter,sDCafter,sGCafter;
    int tCCbefore,tDCbefore,tGCbefore;
    int tCCafter,tDCafter,tGCafter;

    
    
    
	public void setView(View vu)
	{
		this.view=vu;
	//	vu.countLabel.setText("test");
		
	}
	
	
	
    private ProgressMonitor progressMonitor;
   // private JButton startButton;
   // private JTextArea taskOutput;
    private Task task;

    class Task extends SwingWorker<Void, Void> {
        @Override
        public Void doInBackground() {
            Random random = new Random();
            int progress = 0;
            setProgress(0);
            try {
                Thread.sleep(1000);
                while (progress < 100 && !isCancelled()) 
                {
                    //Sleep for up to one second.
                    Thread.sleep(random.nextInt(1000));
                    //Make random progress.
                    progress += random.nextInt(10);
                    setProgress(Math.min(progress, 100));
                }
            } catch (InterruptedException ignore) {}
            return null;
        }

        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
         //   startButton.setEnabled(true);
            progressMonitor.setProgress(0);
        }
    }

    public RemoveCDG() {
        //super(new BorderLayout());

        //Create the demo's UI.
  //      startButton = new JButton("Start");
    //    startButton.setActionCommand("start");
     //   startButton.addActionListener(this);


       // add(startButton, BorderLayout.PAGE_START);
      //  setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    }

    /**
     * Invoked when the user presses the start button.
     */
    public void startnow()
    {
        progressMonitor = new ProgressMonitor(RemoveCDG.this,
                                  "Running a Long Task",
                                  "", 0, 100);
        progressMonitor.setProgress(0);
        task = new Task();
        task.addPropertyChangeListener(this);
        task.execute();

         }

    /**
     * Invoked when task's progress property changes.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName() ) {
            int progress = (Integer) evt.getNewValue();
            progressMonitor.setProgress(progress);
            String message = String.format("Completed %d%%.\n", progress);
            progressMonitor.setNote(message);
       //     taskOutput.append(message);
            if (progressMonitor.isCanceled() || task.isDone()) {
                Toolkit.getDefaultToolkit().beep();
                if (progressMonitor.isCanceled()) 
                {
                    task.cancel(true);
         //           taskOutput.append("Task canceled.\n");
                } else {
           //         taskOutput.append("Task completed.\n");
                }
            //    startButton.setEnabled(true);
            }
        }

    }

	@Override     ////unimplemented methods
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		
	}
/////////////////////////////////////////// END OF Progress Monitor Code ////////////
	
//////////// Remove class Routine and dependent functions start here /////////////////	
	

    public void CalculateGlobalCounts()
    { 	 Set<String> names = new LinkedHashSet<>();
         String temp,tcode;
    	 for(int r=0;r<view.ROWCOUNT-1;r++)	
    		 for(int c=1;c<7;c++)
    			 { 
    			   temp=view.GetData(view.table, r, c);
    			   if(temp.length()==0) continue;
    			   if(temp.contains(",")) 
    			   		{ String parts[]=temp.split(temp);
    			   		  for(int i=0;i<parts.length;i++)
    			   			  { tcode=parts[i].substring(parts[i].indexOf("("),parts[i].indexOf(")")+1);
    			   			    names.add(tcode); 			  
    			   			  }
    			   		  continue;
    			   		}
    			   
    			   
    			   
    			   tcode = temp.substring(temp.indexOf("("),temp.indexOf(")")+1);
    			   names.add(tcode); 
    			 }
        
    	 globalCC=0;globalDC=0;globalGC=0;
    	 for (String name : names) 
    	 { view.CreateIndi(name);
    	   view.CountDoubles();
    	   view.CountGaps();
    	   globalCC+=view.CC;globalDC+=view.DC;globalGC+=view.GC;
    		 
    	}
    	 
    	 
    
       	 
    }
	
	
	 
    public void DisplayAllCounts()
    {String countLabel=String.format("Global CC : %d  Global DC : %d  Global GC : %d",  globalCC,globalDC,globalGC);   
    view.ShowGlobalCounts(countLabel);
    	
    }
    
    
    private void getIndividualCounts(String cell) ///nonempty cell string, including , separated lectures
    { occ=0; odc=0; ogc=0; /////initialize original counts
     String tcode;
     if(cell.contains(",")) 
 		{ String parts[]=cell.split(",");
 		  for(int i=0;i<parts.length;i++)
 			  { tcode=parts[i].substring(parts[i].indexOf("("),parts[i].indexOf(")")+1);
 			    view.CreateIndi(tcode); 	
 			    view.CountGaps();
 			    view.CountDoubles();
 			 
 			    occ+=view.CC;odc+=view.DC;ogc+=view.GC;
 			  }
 		  
 		}
    else ///single lecture
    { tcode = cell.substring(cell.indexOf("("),cell.indexOf(")")+1);
      view.CreateIndi(tcode);
 	    view.CountGaps();
 	    view.CountDoubles();
      
       occ=view.CC;odc=view.DC;ogc=view.GC;
    }
 	   
 	   
    }
	
    public void ExchangeCells(int r1,int c1,int r2,int c2)
    { //String tcode;
      o1=view.GetData(view.table, r1,c1);
      if(o1.length()==0) return;    
      o2=view.GetData(view.table, r2,c2);
      if(o2.length()==0) return;
     // CalculateGlobalCounts();
      getIndividualCounts(o1);
      sCCbefore=occ;sDCbefore=odc;sGCbefore=ogc;
      getIndividualCounts(o2);
      tCCbefore=occ;tDCbefore=odc;tGCbefore=ogc;
      ////// Now exchange CELLS
      view.SetData(o2, view.table,r1,c1); 
      view.SetData(o1, view.table,r2,c2);
      
      ///count again .....////////////
      
      
      getIndividualCounts(o1);
      sCCafter=occ;sDCafter=odc;sGCafter=ogc;
      getIndividualCounts(o2);
      tCCafter=occ;tDCafter=odc;tGCafter=ogc;
      
      /////// Evaluate EXHANGE carefully      
      
      if(sCCafter+tCCafter<sCCbefore+tCCbefore) return; ///clash decreased then return
      if(sCCafter+tCCafter>sCCbefore+tCCbefore) //clash increased so restore cells
    	  {view.SetData(o1, view.table,r1,c1);  // and then return;
           view.SetData(o2, view.table,r2,c2); 
           return;
          } 
      
      ///now clash counts are unchanged by EXHANGE so look for additional optimization if possible
      if(sDCafter+tDCafter<sDCbefore+tDCbefore) return; ///doubles decreased so return
      if(sDCafter+tDCafter>sDCbefore+tDCbefore) // doubles increased so restore cells
	  {view.SetData(o1, view.table,r1,c1);  // and then return;
       view.SetData(o2, view.table,r2,c2); 
       return;
      } 

       ////  Now doubles and clashes are unchanged so look for gaps reduction if possible
      if(sGCafter+tGCafter>sGCbefore+tGCbefore) // gaps increased so restore cells
	  {view.SetData(o1, view.table,r1,c1);  // and then return;
       view.SetData(o2, view.table,r2,c2); 
       return;
      }
      // now gaps are equal or less
      //  other counts are unchanged so no need to reset cells 
      
    }

    
    
    public void RemoveClashGapDoubles()
    {int sourcerow=1;int sourcecol=1;
     CalculateGlobalCounts();
    String class1,class2;
    for(sourcerow=1;sourcerow<30;sourcerow++)
    { for(sourcecol=1;sourcecol<7;sourcecol++)
       {
    	if(view.ColorMatrix[sourcerow][sourcecol]==1) continue;
    	for(int r=0;r<30;r++)
    	
    	  { for(int c=1;c<7;c++)
    		  {if(view.ColorMatrix[r][c]==1) continue;
    		  class1=view.GetData(view.table,sourcerow,0);
    		  class2=view.GetData(view.table,r,0);
    		  if(!class1.contains(class2)) continue;
              //JOptionPane.showMessageDialog(null, class1+"="+class2)    		  
    		   ExchangeCells(sourcerow,sourcecol,r,c);
    		  }
    	   }
        }
    CalculateGlobalCounts();
    DisplayAllCounts();
    view.jb.setValue(sourcerow);
    //  String plate=String.format("%d %d %d", improved)
    createCloseTimer(1).start();  
    JOptionPane.showMessageDialog(null,"continue");
    }
     
    createCloseTimer(1).start();
    //JOptionPane.showMessageDialog((Component) e.getSource(), "nothing to do!");
    JOptionPane.showMessageDialog(null,"Over");    
    }
   
    
    
    private Timer createCloseTimer(int miliseconds) 
    {
        ActionListener close = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Window[] windows = Window.getWindows();
                for (Window window : windows) {
                    if (window instanceof JDialog) {
                        JDialog dialog = (JDialog) window;
                        if (dialog.getContentPane().getComponentCount() == 1
                            && dialog.getContentPane().getComponent(0) instanceof JOptionPane){
                            dialog.dispose();
                        }
                    }
                }

            }

        };
        Timer t = new Timer(100, close);
        t.setRepeats(false);
        return t;
    }

    
    
    
}
