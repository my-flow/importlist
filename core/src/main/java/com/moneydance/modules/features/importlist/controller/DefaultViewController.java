package com.moneydance.modules.features.importlist.controller;

import com.moneydance.modules.features.importlist.bootstrap.Helper;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.presentation.AggregationTableFactory;
import com.moneydance.modules.features.importlist.presentation.BaseTableFactory;
import com.moneydance.modules.features.importlist.presentation.ComponentFactory;
import com.moneydance.modules.features.importlist.presentation.DirectoryChooserButtonFactory;
import com.moneydance.modules.features.importlist.presentation.EmptyLabelFactory;
import com.moneydance.modules.features.importlist.presentation.SplitPaneFactory;
import com.moneydance.modules.features.importlist.table.AbstractEditor;
import com.moneydance.modules.features.importlist.table.ColorScheme;
import com.moneydance.modules.features.importlist.table.ColumnFactory;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JViewport;
import javax.swing.table.AbstractTableModel;

/**
 * The user interface that is displayed on the homepage.
 *
 * @author Florian J. Breunig
 */
abstract class DefaultViewController implements ViewController {

    private final FileAdmin fileAdmin;
    private boolean initialized;
    private final ColumnFactory columnFactory;
    private final JViewport viewport;
    private final ComponentFactory splitPaneFactory;
    private final BaseTableFactory baseTableFactory;
    private final AbstractTableModel baseTableModel;
    private final AbstractTableModel aggrTableModel;
    private final Settings settings;
    private final Preferences prefs;
    private boolean dirty;

    DefaultViewController(
            final FileAdmin argFileAdmin,
            final AbstractTableModel argBaseTableModel,
            final AbstractTableModel argAggrTableModel,
            final ColorScheme evenColorScheme,
            final ColorScheme oddColorScheme,
            final Settings argSettings,
            final Preferences argPrefs) {

        this.fileAdmin = argFileAdmin;
        this.fileAdmin.addObserver(new ViewControllerObserver());
        this.settings = argSettings;
        this.prefs = argPrefs;

        this.baseTableModel = argBaseTableModel;
        this.baseTableFactory = new BaseTableFactory(
                this.baseTableModel,
                this.fileAdmin,
                evenColorScheme,
                oddColorScheme,
                this.settings,
                this.prefs);

        this.aggrTableModel = argAggrTableModel;
        AggregationTableFactory aggrTableFactory = new AggregationTableFactory(
                this.aggrTableModel,
                this.fileAdmin,
                evenColorScheme,
                oddColorScheme,
                this.settings,
                this.prefs);
        this.columnFactory = this.baseTableFactory.getColumnFactory();

        this.splitPaneFactory = new SplitPaneFactory(
                this.baseTableFactory,
                aggrTableFactory);

        this.viewport = new JViewport();
        this.viewport.setOpaque(false);
    }


    private void init() {
        this.setDirty(true);
        this.refresh();
        this.fileAdmin.startMonitor();
    }


    synchronized void refresh() {
        if (this.viewport == null || !this.viewport.isVisible()) {
            return;
        }

        Helper.INSTANCE.setChanged();
        Helper.INSTANCE.notifyObservers(Boolean.TRUE);

        this.fileAdmin.checkValidBaseDirectory();

        // display a label iff no base directory has been chosen
        if (!this.fileAdmin.getBaseDirectory().isPresent()) {
            this.showLabelMissingBaseDirectory();
            return;
        }

        if (this.isDirty()) {
            this.setDirty(false);
            this.fileAdmin.reloadFiles();
            this.baseTableModel.fireTableDataChanged();
            this.aggrTableModel.fireTableDataChanged();
        }

        // display a label iff there are no files in the list
        if (this.baseTableModel.getRowCount() == 0) {
            this.showLabelEmptyList();
            return;
        }

        // display only scroll pane iff there is exactly one file in the list
        if (this.baseTableModel.getRowCount() == 1) {
            JComponent component = this.baseTableFactory.getComponent();
            component.setBorder(this.prefs.getHomePageBorder());

            this.viewport.setView(component);
            this.viewport.setMinimumSize(
                    new Dimension(
                            this.settings.getMinimumTableWidth(),
                            this.settings.getMinimumTableHeight()));
            this.viewport.setPreferredSize(
                    new Dimension(
                            this.prefs.getPreferredTableWidth(),
                            this.prefs.getPreferredTableHeight(
                                    this.baseTableModel.getRowCount())));
            this.viewport.setMaximumSize(
                    new Dimension(
                            this.prefs.getMaximumTableWidth(),
                            this.prefs.getMaximumTableHeight()));
            this.registerKeyboardShortcuts4One();
            return;
        }

        // display scroll pane and an additional import all/delete all table
        // iff there is more than one file in the list
        if (this.baseTableModel.getRowCount() > 1) {
            JComponent component = this.splitPaneFactory.getComponent();
            component.setBorder(this.prefs.getHomePageBorder());

            this.viewport.setView(component);
            this.viewport.setMinimumSize(null);
            this.viewport.setPreferredSize(null);
            this.viewport.setMaximumSize(null);
            this.registerKeyboardShortcuts4All();
        }
    }


    JComponent getGUIView() {
        if (!this.initialized) {
            this.initialized = true;
            this.init();
        }
        return this.viewport;
    }


    @Override
    public void setActive(final boolean active) {
        if (this.viewport == null) {
            return;
        }
        this.viewport.setVisible(active);
        this.refresh();
    }


    public String getID() {
        return this.settings.getExtensionIdentifier();
    }

    @Override
    public String toString() {
        return this.settings.getExtensionName();
    }

    @Override
    public void chooseBaseDirectory() {
        this.fileAdmin.chooseBaseDirectory();
    }

    @Override
    public void cleanup() {
        if (!this.initialized) {
            return;
        }
        this.viewport.setEnabled(false);
        this.viewport.removeAll();
        this.fileAdmin.stopMonitor();
    }


    void setDirty(final boolean argDirty) {
        this.dirty = argDirty;
    }


    boolean isDirty() {
        return this.dirty;
    }


    private void showLabelMissingBaseDirectory() {
        DirectoryChooserButtonFactory directoryChooserFactory =
                new DirectoryChooserButtonFactory(
                        String.format(
                                "<html><u>%s</u></html>",
                                Helper.INSTANCE.getLocalizable().getDirectoryChooserTitle()
                        ),
                        new ChooseBaseDirectoryActionListener(),
                        this.prefs.getHomePageBorder(),
                        this.prefs.getBackground(),
                        this.prefs.getBodyFont());

        this.viewport.setView(directoryChooserFactory.getComponent());
        this.viewport.setMinimumSize(
                new Dimension(
                        this.settings.getPreferredEmptyMessageWidth(),
                        this.settings.getPreferredEmptyMessageHeight()));
        this.viewport.setPreferredSize(
                new Dimension(
                        this.settings.getPreferredEmptyMessageWidth(),
                        this.settings.getPreferredEmptyMessageHeight()));
        this.viewport.setMaximumSize(
                new Dimension(
                        this.settings.getPreferredEmptyMessageWidth(),
                        this.settings.getPreferredEmptyMessageHeight()));
    }


    private void showLabelEmptyList() {
        EmptyLabelFactory emptyLabelFactory = new EmptyLabelFactory(
                Helper.INSTANCE.getLocalizable().getEmptyMessage(
                        this.fileAdmin.getBaseDirectory().orElseThrow(AssertionError::new).getAbsolutePath()
                ),
                this.prefs.getHomePageBorder(),
                this.prefs.getBackground(),
                this.prefs.getBodyFont());

        this.viewport.setView(emptyLabelFactory.getComponent());
        this.viewport.setMinimumSize(
                new Dimension(
                        this.settings.getPreferredEmptyMessageWidth(),
                        this.settings.getPreferredEmptyMessageHeight()));
        this.viewport.setPreferredSize(
                new Dimension(
                        this.settings.getPreferredEmptyMessageWidth(),
                        this.settings.getPreferredEmptyMessageHeight()));
        this.viewport.setMaximumSize(
                new Dimension(
                        this.settings.getPreferredEmptyMessageWidth(),
                        this.settings.getPreferredEmptyMessageHeight()));
    }


    private void registerKeyboardShortcuts4One() {
        AbstractEditor importOneEditor =
                this.columnFactory.getImportOneEditor();
        importOneEditor.registerKeyboardShortcut(this.viewport);

        AbstractEditor deleteOneEditor =
                this.columnFactory.getDeleteOneEditor();
        deleteOneEditor.registerKeyboardShortcut(this.viewport);
    }


    private void registerKeyboardShortcuts4All() {
        AbstractEditor importAllEditor =
                this.columnFactory.getImportAllEditor();
        importAllEditor.registerKeyboardShortcut(this.viewport);

        AbstractEditor deleteAllEditor =
                this.columnFactory.getDeleteAllEditor();
        deleteAllEditor.registerKeyboardShortcut(this.viewport);
    }

    /**
     * @author Florian J. Breunig
     */
    private final class ViewControllerObserver implements Observer {
        @Override
        public void update(final Observable observable, final Object object) {
            DefaultViewController.this.setDirty(true);
            DefaultViewController.this.refresh();
        }
    }

    /**
     * @author Florian J. Breunig
     */
    private final class ChooseBaseDirectoryActionListener implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent event) {
            DefaultViewController.this.chooseBaseDirectory();
        }
    }
}
