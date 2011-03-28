package com.moneydance.modules.features.importlist.swing;

import javax.swing.table.DefaultTableModel;

/**
 * @author Florian J. Breunig, Florian.J.Breunig@my-flow.com
 *
 */
public class ListTableModel extends DefaultTableModel {

    private static final long serialVersionUID = 3552703741263935211L;


    public ListTableModel(final Object[][] data, final Object[] columnNames) {
        super(data, columnNames);
    }


    @Override
    public final boolean isCellEditable(final int row, final int column) {
        return column >= 2;
    }
}
