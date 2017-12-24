package ctt;

/*
public class RemoveCDG 
{
	View vu;
	
	public void setView(View vu)
	{
		this.vu=vu;
	//	vu.countLabel.setText("test");
		
	}
	

}
*/
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.beans.*;
import java.util.Random;

public class RemoveCDG extends JPanel implements ActionListener,PropertyChangeListener
   {

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

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

    
}
