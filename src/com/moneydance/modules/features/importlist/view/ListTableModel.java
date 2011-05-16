package com.moneydance.modules.features.importlist.view;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import org.apache.commons.lang.Validate;

import com.moneydance.modules.features.importlist.Constants;
import com.moneydance.modules.features.importlist.Preferences;
import com.moneydance.util.CustomDateFormat;

/**
 * This class provides a <code>TableModel</code> implementation for a given
 * <code>List</code> of <code>File</code>s. It takes care of the formatting and
 * caching of its table values. The first two columns represent the name of the
 * file and the date of its last modification, the last two columns represent
 * the action buttons to import and delete the file, respectively.
 *
 * @author <a href="mailto:&#102;&#108;&#111;&#114;&#105;&#97;&#110;&#46;&#106;
 *&#46;&#98;&#114;&#101;&#117;&#110;&#105;&#103;&#64;&#109;&#121;&#45;&#102;
 *&#108;&#111;&#119;&#46;&#99;&#111;&#109;">Florian J. Breunig</a>
 */
class ListTableModel extends AbstractTableModel {

    private static final long serialVersionUID = 3552703741263935211L;
    private final List<File>             files;
    private final Map<Integer, String>   fileDateStringCache;
    private CustomDateFormat             dateFormatter;
    private DateFormat                   timeFormatter;

    ListTableModel(final List<File> argFiles) {
        Validate.notNull(argFiles, "argFiles can't be null");
        this.files               = argFiles;
        this.fileDateStringCache = new Hashtable<Integer, String>();
    }

    @Override
    public final String getValueAt(final int row, final int column) {
        String columnName = this.getColumnName(column);

        if (Constants.DESC_NAME.equals(columnName)) {
            File file = this.getFileAt(row);
            return Constants.INDENTATION_PREFIX + file.getName();
        }

        if (Constants.DESC_MODIFIED.equals(columnName)) {
            if (this.fileDateStringCache.get(row) == null) {
                File file             = this.getFileAt(row);
                Date fileDate         = new Date(file.lastModified());
                String fileDateString = Constants.INDENTATION_PREFIX
                + this.dateFormatter.format(fileDate)
                + " " + this.timeFormatter.format(fileDate);

                this.fileDateStringCache.put(row, fileDateString);
            }
            return this.fileDateStringCache.get(row);
        }

        if (Constants.DESC_IMPORT.equals(columnName)) {
            return Constants.LABEL_IMPORT_BUTTON;
        }

        if (Constants.DESC_DELETE.equals(columnName)) {
            return Constants.LABEL_DELETE_BUTTON;
        }

        return null;
    }


    @Override
    public final void fireTableDataChanged() {
        super.fireTableDataChanged();
        this.fileDateStringCache.clear();
    }

    /**
     * Only the last two columns contain buttons and are therefore editable.
     *
     * @param row    the row whose value is to be queried
     * @param column the column whose value is to be queried
     * @return true  if the column is the last or penultimate one in the model
     */
    @Override
    public final boolean isCellEditable(final int row, final int column) {
        String columnName = this.getColumnName(column);
        return Constants.DESC_IMPORT.equals(columnName)
        || Constants.DESC_DELETE.equals(columnName);
    }

    @Override
    public final int getColumnCount() {
        return Preferences.getInstance().getColumnCount();
    }

    @Override
    public final String getColumnName(final int column) {
        return Preferences.getInstance().getColumnName(column);
    }

    @Override
    public final int getRowCount() {
        if (this.files == null) {
            return 0;
        }
        return this.files.size();
    }

    final File getFileAt(final int row) {
        return this.files.get(row);
    }

    final void setDateFormatter(
            final CustomDateFormat argDateFormatter) {
        Validate.notNull(argDateFormatter, "argDateFormatter can't be null");
        if (this.dateFormatter != null
                && !argDateFormatter.equals(this.dateFormatter)) {
            this.fileDateStringCache.clear();
        }
        this.dateFormatter = argDateFormatter;
    }

    final void setTimeFormatter(final DateFormat argTimeFormatter) {
        Validate.notNull(argTimeFormatter, "argTimeFormatter can't be null");
        if (this.timeFormatter != null
                && !argTimeFormatter.equals(this.timeFormatter)) {
            this.fileDateStringCache.clear();
        }
        this.timeFormatter = argTimeFormatter;
    }
}