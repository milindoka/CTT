package ctt;



import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.prefs.Preferences;

import javax.print.PrintService;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

public class SetPrinter 
{
   
	
	String PrinterName;
	
   
	public void SavePreferences()
	{Preferences prefs = Preferences.userNodeForPackage(ctt.SetPrinter.class);

	// Preference key name
	final String PREF_NAME = "ResultViewPref";  ///share printer with result software preferences
	// Set the value of the preference
	prefs.put(PREF_NAME, PrinterName);
		
	}


	public  String LoadPreferences()
	{Preferences prefs = Preferences.userNodeForPackage(ctt.SetPrinter.class);

	// Preference key name
	final String PREF_NAME = "ResultViewPref";   ///share printer with result software preferences
	PrinterName= prefs.get(PREF_NAME,PrinterName); // "a string"
	return PrinterName;
	
	}

	
	
	
	public String SelectPrinter()
    {   LoadPreferences();
    	//show(PrinterName);
    	ButtonGroup group = new ButtonGroup();
        ArrayList<String> PrinterNames = new ArrayList<String>(); 
    	
    	for (PrintService service : PrinterJob.lookupPrintServices())
        {
            PrinterNames.add(service.getName());
               
        }	    	
    	JRadioButton buttons[] = new JRadioButton[PrinterNames.size()];
    	
    	for (int i = 0; i < buttons.length; ++i)
    	{
    		buttons[i] = new JRadioButton(PrinterNames.get(i));
    	 //   btn.addActionListener(this);
    	    group.add(buttons[i]);
    	    //buttons[i] = btn;
    	}
    	
        int res = JOptionPane.showConfirmDialog(null, buttons, "Select Default Printer", 
                JOptionPane.OK_CANCEL_OPTION);

        if (res == JOptionPane.OK_OPTION)
              { for(int i=0;i<PrinterNames.size();i++) 
            	  if(buttons[i].isSelected()) { PrinterName=PrinterNames.get(i);
            	                                SavePreferences();}
              }

        return PrinterName;
    }


}