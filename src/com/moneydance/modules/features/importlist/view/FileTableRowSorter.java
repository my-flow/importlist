package com.moneydance.modules.features.importlist.view;

import javax.swing.DefaultRowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.commons.lang.Validate;

import com.moneydance.modules.features.importlist.Constants;

/**
 * An implementation of <code>RowSorter</code> that provides sorting for a
 * <code>ListTableModel</code>.
 *
 * @author <a href="mailto:&#102;&#108;&#111;&#114;&#105;&#97;&#110;&#46;&#106;
 *&#46;&#98;&#114;&#101;&#117;&#110;&#105;&#103;&#64;&#109;&#121;&#45;&#102;
 *&#108;&#111;&#119;&#46;&#99;&#111;&#109;">Florian J. Breunig</a>
 */
public class FileTableRowSorter extends TableRowSorter<TableModel> {

    private final ListTableModel listTableModel;

    /**
     * @param argListTableModel The table model that is to be sorted
     */
    FileTableRowSorter(final ListTableModel argListTableModel) {
        super(argListTableModel);
        Validate.notNull(argListTableModel, "argListTableModel can't be null");
        this.listTableModel = argListTableModel;
        this.setModelWrapper(new FileModelWrapper());

    }

    @Override
    public final boolean isSortable(final int column) {
        String columnName = this.listTableModel.getColumnName(column);
        return Constants.DESC_NAME.equals(columnName)
        || Constants.DESC_MODIFIED.equals(columnName);
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
            if (column == 1) {
                return model.getFileAt(row).lastModified();
            } else {
                return model.getValueAt(row, column);
            }
        }
    }
}
