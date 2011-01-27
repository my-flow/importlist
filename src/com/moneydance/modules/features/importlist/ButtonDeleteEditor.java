package com.moneydance.modules.features.importlist;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

/**
 * @author Florian J. Breunig, Florian.J.Breunig@my-flow.com
 *
 */
class ButtonDeleteEditor extends DefaultCellEditor {

  private static final long    serialVersionUID = 6773713824539296048L;
  private final        Main    main;
  private              String  label;


  public ButtonDeleteEditor(final Main paraMain, final JCheckBox checkBox) {
    super(checkBox);
    this.main = paraMain;
  }

  public Component getTableCellEditorComponent(
     final JTable jTable,
     final Object value,
     final boolean isSelected,
     final int row,
     final int column) {

     this.main.deleteFile(row);

     this.label = "";
     if (value != null) {
         this.label = value.toString();
     }
     return new JButton(this.label);
  }
}
