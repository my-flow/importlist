package com.moneydance.modules.features.importlist.presentation;

import com.moneydance.modules.features.importlist.bootstrap.Helper;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.table.ColorScheme;
import com.moneydance.modules.features.importlist.table.ColumnFactory;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;

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
            final Settings argSettings,
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
        final Settings settings = this.getSettings();

        table.setBackground(Helper.INSTANCE.getPreferences().getBackground());

        int bodyRowHeight = Helper.INSTANCE.getPreferences().getBodyRowHeight();
        int tableHeightOffset = settings.getTableHeightOffset();

        table.setRowHeight(
                bodyRowHeight
                + tableHeightOffset);
        table.setMinimumSize(
                new Dimension(
                        settings.getMinimumTableWidth(),
                        bodyRowHeight
                        + tableHeightOffset));
        table.setPreferredSize(
                new Dimension(
                        Helper.INSTANCE.getPreferences().getPreferredTableWidth(),
                        bodyRowHeight
                        + tableHeightOffset));
        table.setMaximumSize(
                new Dimension(
                        Helper.INSTANCE.getPreferences().getMaximumTableWidth(),
                        bodyRowHeight
                        + tableHeightOffset));

        return table;
    }
}
