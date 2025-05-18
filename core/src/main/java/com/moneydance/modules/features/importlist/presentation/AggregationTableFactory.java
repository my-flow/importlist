package com.moneydance.modules.features.importlist.presentation;

import com.moneydance.modules.features.importlist.bootstrap.Helper;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.table.ColorScheme;
import com.moneydance.modules.features.importlist.table.ColumnFactory;
import com.moneydance.modules.features.importlist.util.ISettings;
import com.moneydance.modules.features.importlist.util.Preferences;

import java.awt.Dimension;

import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

/**
 * @author Florian J. Breunig
 */
public final class AggregationTableFactory extends AbstractTableFactory {

    public AggregationTableFactory(
            final TableModel argTableModel,
            final FileAdmin argFileAdmin,
            final ColorScheme evenColorScheme,
            final ColorScheme oddColorScheme,
            final ISettings argSettings,
            final Preferences argPrefs) {
        super(argTableModel, argSettings);

        final JTable table = this.getTable();

        table.setIntercellSpacing(
                new Dimension(
                        0,
                        argSettings.getTableHeightOffset()));

        ColumnFactory columnFactory = new ColumnFactory(
                argFileAdmin,
                argPrefs.getDateFormatter(),
                argPrefs.getTimeFormatter(),
                evenColorScheme,
                oddColorScheme,
                argSettings);

        // name column
        final String descName = argSettings.getDescName();
        final TableColumn nameCol = buildColumn(descName);
        nameCol.setCellRenderer(columnFactory.getLabelNameAllRenderer());

        // modified column
        final String descModified = argSettings.getDescModified();
        final TableColumn modifiedCol = buildColumn(descModified);
        modifiedCol.setCellRenderer(columnFactory.getLabelModifiedAllRenderer());

        // import column
        final String descImport = argSettings.getDescImport();
        final TableColumn importCol = buildColumn(descImport);
        importCol.setCellRenderer(columnFactory.getButtonAllRenderer());
        importCol.setCellEditor(columnFactory.getImportAllEditor());
        importCol.setResizable(argSettings.isButtonResizable());

        // delete column
        final String descDelete = argSettings.getDescDelete();
        final TableColumn deleteCol = buildColumn(descDelete);
        deleteCol.setCellRenderer(columnFactory.getButtonAllRenderer());
        deleteCol.setCellEditor(columnFactory.getDeleteAllEditor());
        deleteCol.setResizable(argSettings.isButtonResizable());
    }

    @Override
    public JTable getComponent() {
        final JTable table = this.getTable();
        final ISettings settings = this.getSettings();
        final Preferences preferences = Helper.INSTANCE.getPreferences();

        table.setBackground(preferences.getBackground());

        int bodyRowHeight = preferences.getBodyRowHeight();
        int tableHeightOffset = settings.getTableHeightOffset();
        int rowHeightTotal = bodyRowHeight + tableHeightOffset;

        table.setRowHeight(rowHeightTotal);
        table.setMinimumSize(
                new Dimension(
                        settings.getMinimumTableWidth(),
                        rowHeightTotal));
        table.setPreferredSize(
                new Dimension(
                        preferences.getPreferredTableWidth(),
                        rowHeightTotal));
        table.setMaximumSize(
                new Dimension(
                        preferences.getMaximumTableWidth(),
                        rowHeightTotal));

        return table;
    }
}
