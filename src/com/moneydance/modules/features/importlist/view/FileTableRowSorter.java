package com.moneydance.modules.features.importlist.view;

import java.util.Comparator;

import javax.swing.DefaultRowSorter;
import javax.swing.table.TableModel;

import org.apache.commons.lang.Validate;

import com.moneydance.modules.features.importlist.util.AlphanumComparator;
import com.moneydance.modules.features.importlist.util.Preferences;

/**
 * An implementation of <code>RowSorter</code> that provides sorting for a
 * <code>ListTableModel</code>.
 *
 * @author <a href="mailto:&#102;&#108;&#111;&#114;&#105;&#97;&#110;&#46;&#106;
 *&#46;&#98;&#114;&#101;&#117;&#110;&#105;&#103;&#64;&#109;&#121;&#45;&#102;
 *&#108;&#111;&#119;&#46;&#99;&#111;&#109;">Florian J. Breunig</a>
 */
class FileTableRowSorter extends DefaultRowSorter<TableModel, Integer> {

    private final ListTableModel listTableModel;
    private final Comparator<?>  comparator;
    private final Preferences    prefs;

    /**
     * @param argListTableModel The table model that is to be sorted
     */
    FileTableRowSorter(final ListTableModel argListTableModel) {
        super();
        Validate.notNull(argListTableModel, "argListTableModel can't be null");
        this.listTableModel = argListTableModel;
        this.comparator     = new AlphanumComparator();
        this.prefs          = Preferences.getInstance();
        this.setModelWrapper(new FileModelWrapper());
    }

    @Override
    public final boolean isSortable(final int column) {
        String columnName = this.listTableModel.getColumnName(column);
        return this.prefs.getDescName().equals(columnName)
        || this.prefs.getDescModified().equals(columnName);
    }

    @Override
    public Comparator<?> getComparator(final int column) {
        return this.comparator;
    }

    private class FileModelWrapper
    extends DefaultRowSorter.ModelWrapper<TableModel, Integer> {

        @Override
        public final int getColumnCount() {
            return FileTableRowSorter.this.listTableModel.getColumnCount();
        }

        @Override
        public final Integer getIdentifier(final int columnIndex) {
            return columnIndex;
        }

        @Override
        public final TableModel getModel() {
            return FileTableRowSorter.this.listTableModel;
        }

        @Override
        public final int getRowCount() {
            return FileTableRowSorter.this.listTableModel.getRowCount();
        }

        @Override
        public final Object getValueAt(final int row, final int column) {
            ListTableModel model = FileTableRowSorter.this.listTableModel;
            String columnName    = model.getColumnName(column);

            if (FileTableRowSorter.this.prefs.getDescModified().equals(
                    columnName)) {
                return model.getFileAt(row).lastModified() + "";
            } else {
                return model.getValueAt(row, column);
            }
        }
    }
}
