package com.moneydance.modules.features.importlist.table;

import com.moneydance.modules.features.importlist.io.FileAdmin;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.Nullable;
import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.KeyStroke;

/**
 * @author Florian J. Breunig
 */
public abstract class AbstractEditor extends DefaultCellEditor {

    private static final long serialVersionUID = 1L;
    private final FileAdmin fileAdmin;
    private final ButtonRenderer buttonRenderer;
    private final String keyboardShortcut;
    @Nullable private Object label;

    protected AbstractEditor(
            final FileAdmin argFileAdmin,
            final ButtonRenderer argButtonRenderer,
            final String argKeyboardShortcut) {
        super(new JCheckBox());
        this.fileAdmin = argFileAdmin;
        this.buttonRenderer = argButtonRenderer;
        this.keyboardShortcut = argKeyboardShortcut;
    }

    public abstract ActionListener getActionListener(int rowNumber);

    final FileAdmin getFileAdmin() {
        return this.fileAdmin;
    }

    @Override
    @SuppressWarnings("nullness")
    public final Object getCellEditorValue() {
        if (this.label == null) {
            return null;
        }
        return this.label.toString();
    }

    @Override
    public final Component getTableCellEditorComponent(
            final JTable table,
            final Object value,
            final boolean isSelected,
            final int row,
            final int column) {

        this.label = value;

        AbstractButton button = this.buttonRenderer.getTableCellRendererButton(
                value, row);
        ActionListener actionListener = this.getActionListener(
                table.convertRowIndexToModel(row));
        button.addActionListener(actionListener);

        return button;
    }


    public final void registerKeyboardShortcut(final JComponent jComponent) {
        final KeyStroke keyStroke = KeyStroke.getKeyStroke(this.keyboardShortcut);

        if (keyStroke == null) {
            return;
        }

        final Action action = new AbstractAction() {
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                ActionListener actionListener =
                        AbstractEditor.this.getActionListener(0);
                actionListener.actionPerformed(actionEvent);
            }
        };

        final String actionMapKey = this.getClass().getName(); // unique
        jComponent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                keyStroke,
                actionMapKey);
        jComponent.getActionMap().put(actionMapKey, action);
    }
}
