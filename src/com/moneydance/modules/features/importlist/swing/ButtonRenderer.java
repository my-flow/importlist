package com.moneydance.modules.features.importlist.swing;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * @author Florian J. Breunig, Florian.J.Breunig@my-flow.com
 *
 */
public class ButtonRenderer extends JButton implements TableCellRenderer {

  private static final long serialVersionUID = -2724848115953702911L;

  public final Component getTableCellRendererComponent(
    final JTable table,
    final Object value,
    final boolean isSelected,
    final boolean hasFocus,
    final int row,
    final int column) {

     this.setText("");
     if (value != null) {
         this.setText(value.toString());
     }
     return this;
  }
}
