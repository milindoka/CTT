package ctt;


import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import javax.swing.table.DefaultTableModel;

public class MyTransferHandlerT extends TransferHandler {

 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
private JTable table;
 private DefaultTableModel model;
 private int rowIndex;
 private int colIndex;

@Override
public int getSourceActions(JComponent c) {
    return COPY_OR_MOVE;
}

@Override
protected Transferable createTransferable(JComponent source) {

    table= (JTable)source;
    model = (DefaultTableModel) table.getModel();
    rowIndex = table.getSelectedRow();
    colIndex = table.getSelectedColumn();


    model.getValueAt(rowIndex, colIndex);

    String value = (String)model.getValueAt(rowIndex, colIndex);
    Transferable t = new StringSelection(value);
    return t;
}

@Override
protected void exportDone(JComponent source, Transferable data, int action) {

    table= (JTable)source;
    model = (DefaultTableModel) table.getModel();
    rowIndex = table.getSelectedRow();
    colIndex = table.getSelectedColumn();
   if(action==MOVE) 
      model.setValueAt("", rowIndex, colIndex);
   System.out.println(action);
}


@Override
public boolean canImport(TransferSupport support) {
    if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
        return false;
    }
    return true;
}

@Override
public boolean importData(TransferSupport support) {

    table = (JTable) support.getComponent();
    Object data= null;
    int row = table.getSelectedRow();
    int col = table.getSelectedColumn();



    try {
        data = (Object) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
    } catch (UnsupportedFlavorException e) {
        System.out.println("unsupported Flavor Exception");
        e.printStackTrace();
    } catch (IOException e) {
        System.out.println("IO Exception");
        e.printStackTrace();
    }

    model.setValueAt(data, row, col);
    model.fireTableStructureChanged();
    return false;
}


}
