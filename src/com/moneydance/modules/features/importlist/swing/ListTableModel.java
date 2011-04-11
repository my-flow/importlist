package com.moneydance.modules.features.importlist.swing;

import javax.swing.table.DefaultTableModel;

/**
 * @author Florian J. Breunig, http://www.my-flow.com
 */
public class ListTableModel extends DefaultTableModel {

    private static final long serialVersionUID = 3552703741263935211L;


    /**
    * @param columnNames descriptors of the columns in the model
    * @param rowCount    initial number of rows in the model
    */
   public ListTableModel(final Object[] columnNames, final int rowCount) {
       super(columnNames, rowCount);
    }


   /**
    * Only the last two columns contain buttons and are therefore editable.
    *
    * @param row    the row whose value is to be queried
    * @param column the column whose value is to be queried
    * @return true  if the column is the last or penultimate one in the model
    * @see javax.swing.table.DefaultTableModel#isCellEditable(int, int)
    */
   @Override
   public final boolean isCellEditable(final int row, final int column) {
        return column >= 2;
    }
}
