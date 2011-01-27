package com.moneydance.modules.features.importlist;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author Florian J. Breunig, Florian.J.Breunig@my-flow.com
 *
 */
class BackgroundColorRenderer extends DefaultTableCellRenderer {

  private static final long serialVersionUID = -2724848115953702911L;

  public Component getTableCellRendererComponent(
    final JTable table,
    final Object value,
    final boolean isSelected,
    final boolean hasFocus,
    final int row,
    final int column) {

     Component cell = super.getTableCellRendererComponent(table, value,
           isSelected, hasFocus, row, column);

     cell.setForeground(Constants.FOREGROUND_COLOR);
     cell.setBackground(Constants.BACKGROUND_COLOR);
     if (row % 2 == 0) {
        cell.setBackground(Constants.BACKGROUND_COLOR_ALTERNATIVE);
     }

     return cell;
  }
}
