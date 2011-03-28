package com.moneydance.modules.features.importlist.swing;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @author Florian J. Breunig, Florian.J.Breunig@my-flow.com
 *
 */
public class BackgroundColorRenderer extends DefaultTableCellRenderer {

  private static final long serialVersionUID = -2724848115953702911L;
  private final             Color foregroundColor;
  private final             Color backgroundColor;
  private final             Color backgroundColorAlt;


  public BackgroundColorRenderer(
        final Color argForegroundColor,
        final Color argBackgroundColor,
        final Color argBackgroundColorAlt) {
     this.foregroundColor    = argForegroundColor;
     this.backgroundColor    = argBackgroundColor;
     this.backgroundColorAlt = argBackgroundColorAlt;
  }


  @Override
  public final Component getTableCellRendererComponent(
    final JTable table,
    final Object value,
    final boolean isSelected,
    final boolean hasFocus,
    final int row,
    final int column) {

     Component cell = super.getTableCellRendererComponent(table, value,
           isSelected, hasFocus, row, column);

     cell.setForeground(this.foregroundColor);
     cell.setBackground(this.backgroundColor);
     if (row % 2 != 0) {
        cell.setBackground(this.backgroundColorAlt);
     }

     return cell;
  }
}
