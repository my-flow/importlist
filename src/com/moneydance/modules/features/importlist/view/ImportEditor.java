package com.moneydance.modules.features.importlist.view;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import com.moneydance.modules.features.importlist.io.FileAdministration;

/**
 * @author <a href="mailto:&#102;&#108;&#111;&#114;&#105;&#97;&#110;&#46;&#106;
 *&#46;&#98;&#114;&#101;&#117;&#110;&#105;&#103;&#64;&#109;&#121;&#45;&#102;
 *&#108;&#111;&#119;&#46;&#99;&#111;&#109;">Florian J. Breunig</a>
 */
class ImportEditor extends DefaultCellEditor {

    private static final long serialVersionUID = 4286061728240721119L;
    private final FileAdministration    fileAdministration;
    private final ButtonRenderer        buttonRenderer;
    private String label;

    ImportEditor(final FileAdministration argFileAdministration,
            final ButtonRenderer argButtonRenderer) {
        super(new JCheckBox());
        this.fileAdministration = argFileAdministration;
        this.buttonRenderer     = argButtonRenderer;
    }

    @Override
    public final Object getCellEditorValue() {
        return this.label;
    }

    @Override
    public final Component getTableCellEditorComponent(
            final JTable table,
            final Object value,
            final boolean isSelected,
            final int row,
            final int column) {
        if (value != null) {
            this.label = value.toString();
        }

        AbstractButton button = this.buttonRenderer.getTableCellRendererButton(
                table, value, isSelected, isSelected, row, column);
        ActionListener actionListener =
            this.fileAdministration.getImportActionListener(
                    table.convertRowIndexToModel(row));
        button.addActionListener(actionListener);

        return button;
    }
}
