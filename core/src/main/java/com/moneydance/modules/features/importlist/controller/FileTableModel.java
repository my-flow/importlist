package com.moneydance.modules.features.importlist.controller;

import com.moneydance.modules.features.importlist.bootstrap.Helper;
import com.moneydance.modules.features.importlist.io.FileContainer;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;

import java.io.Serializable;

import javax.swing.table.AbstractTableModel;


/**
 * This class provides a <code>TableModel</code> implementation for a given
 * <code>List</code> of <code>File</code>s. It takes care of the formatting and
 * caching of its table values. The first two columns represent the name of the
 * file and the date of its last modification, the last two columns represent
 * the action buttons to import and delete the file, respectively.
 * @author Florian J. Breunig
 */
public final class FileTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 1L;

    private final FileContainer fileContainer;
    private final Settings settings;
    private final Preferences prefs;

    public FileTableModel(
            final FileContainer argFileContainer,
            final Settings argSettings,
            final Preferences argPrefs) {
        super();
        this.fileContainer = argFileContainer;
        this.settings = argSettings;
        this.prefs = argPrefs;
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        final String columnName = this.getColumnName(columnIndex);

        if (this.settings.getDescName().equals(columnName)) {
            return String.class;
        }
        if (this.settings.getDescModified().equals(columnName)) {
            return Long.class;
        }
        if (this.settings.getDescImport().equals(columnName)) {
            return String.class;
        }
        if (this.settings.getDescDelete().equals(columnName)) {
            return String.class;
        }
        throw new IllegalArgumentException(String.format(
                "Could not find column name for index %d", columnIndex));
    }

    @Override
    public Comparable<? extends Serializable> getValueAt(final int row, final int column) {
        if (row >= this.fileContainer.size()) {
            this.fireTableDataChanged();
            throw new IllegalArgumentException(String.format(
                    "Could not find value for row %d, column %d", row, column));
        }
        String columnName = this.getColumnName(column);

        if (this.settings.getDescName().equals(columnName)) {
            return this.fileContainer.getFileName(row);
        }
        if (this.settings.getDescModified().equals(columnName)) {
            return this.fileContainer.getLastModifiedTime(row);
        }
        if (this.settings.getDescImport().equals(columnName)) {
            return Helper.INSTANCE.getLocalizable().getLabelImportOneButton();
        }
        if (this.settings.getDescDelete().equals(columnName)) {
            return Helper.INSTANCE.getLocalizable().getLabelDeleteOneButton();
        }
        throw new IllegalArgumentException(String.format(
                "Could not find value for row %d, column %d", row, column));
    }

    /**
     * Only the last two columns contain buttons and are therefore editable.
     *
     * @param row    the row whose value is to be queried
     * @param column the column whose value is to be queried
     * @return true  if the column is the last or penultimate one in the model
     */
    @Override
    public boolean isCellEditable(final int row, final int column) {
        String columnName = this.getColumnName(column);
        return this.settings.getDescImport().equals(columnName)
                || this.settings.getDescDelete().equals(columnName);
    }

    @Override
    public int getColumnCount() {
        return this.prefs.getColumnCount();
    }

    @Override
    public String getColumnName(final int column) {
        return this.prefs.getColumnName(column);
    }

    @Override
    public int getRowCount() {
        return this.fileContainer.size();
    }
}
