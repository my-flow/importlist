// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2016 Florian J. Breunig
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <http://www.gnu.org/licenses/>.

package com.moneydance.modules.features.importlist.controller;

import com.infinitekind.moneydance.model.AccountBook;
import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.apps.md.view.gui.MoneydanceLAF;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.presentation.AggregationTableFactory;
import com.moneydance.modules.features.importlist.presentation.BaseTableFactory;
import com.moneydance.modules.features.importlist.presentation.ComponentFactory;
import com.moneydance.modules.features.importlist.presentation.DirectoryChooserButtonFactory;
import com.moneydance.modules.features.importlist.presentation.EmptyLabelFactory;
import com.moneydance.modules.features.importlist.presentation.SplitPaneFactory;
import com.moneydance.modules.features.importlist.table.AbstractEditor;
import com.moneydance.modules.features.importlist.table.ColumnFactory;
import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.modules.features.importlist.util.Localizable;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;
import com.moneydance.modules.features.importlist.util.Tracker;

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
public final class ViewController implements HomePageView {

    private final Settings              settings;
    private final Localizable           localizable;
    private final FileAdmin             fileAdmin;
    private final Tracker               tracker;
    private       boolean               initialized;
    private       ColumnFactory         columnFactory;
    private       JViewport             viewport;
    private       ComponentFactory      splitPaneFactory;
    private       BaseTableFactory      baseTableFactory;
    private       AbstractTableModel    baseTableModel;
    private       AbstractTableModel    aggrTableModel;
    private       boolean               dirty;


    public ViewController(
            final String baseDirectory,
            final FeatureModuleContext argContext,
            final Tracker argTracker) {
        this.settings    = Helper.INSTANCE.getSettings();
        this.localizable = Helper.INSTANCE.getLocalizable();
        this.fileAdmin   = new FileAdmin(baseDirectory, argContext);
        this.fileAdmin.addObserver(new ViewControllerObserver());
        this.tracker     = argTracker;
    }


    private void init() {
        this.viewport = new JViewport();
        this.viewport.setOpaque(false);

        this.baseTableModel   = new FileTableModel(this.fileAdmin.getFiles());
        this.baseTableFactory = new BaseTableFactory(
                this.baseTableModel,
                this.fileAdmin);

        this.aggrTableModel   = new AggregationTableModel();
        AggregationTableFactory aggrTableFactory = new AggregationTableFactory(
                this.aggrTableModel,
                this.fileAdmin);
        this.columnFactory    = this.baseTableFactory.getColumnFactory();

        this.splitPaneFactory = new SplitPaneFactory(
                this.baseTableFactory,
                aggrTableFactory);

        this.setDirty(true);
        this.refresh();
        this.fileAdmin.startMonitor();

        this.tracker.track(Tracker.EventName.DISPLAY);
    }


    @Override
    public synchronized void refresh() {
        if (this.viewport == null || !this.viewport.isVisible()) {
            return;
        }

        Helper.INSTANCE.setChanged();
        Helper.INSTANCE.notifyObservers(Boolean.TRUE);

        this.fileAdmin.checkValidBaseDirectory();

        // display a label iff no base directory has been chosen
        if (this.fileAdmin.getBaseDirectory() == null) {
            DirectoryChooserButtonFactory directoryChooserFactory =
                    new DirectoryChooserButtonFactory(
                            String.format(
                                    "<html><u>%s</u></html>",
                                    this.localizable.getDirectoryChooserTitle()
                                    ),
                                    new ChooseBaseDirectoryActionListener()
                            );

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
            EmptyLabelFactory emptyLabelFactory = new EmptyLabelFactory(
                    this.localizable.getEmptyMessage(
                            this.fileAdmin.getBaseDirectory().getAbsolutePath()
                            )
                    );

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
            return;
        }

        // display only scroll pane iff there is exactly one file in the list
        if (this.baseTableModel.getRowCount() == 1) {
            JComponent component = this.baseTableFactory.getComponent();
            component.setBorder(MoneydanceLAF.homePageBorder);

            this.viewport.setView(component);
            this.viewport.setMinimumSize(
                    new Dimension(
                            this.settings.getMinimumTableWidth(),
                            this.settings.getMinimumTableHeight()));
            this.viewport.setPreferredSize(
                    new Dimension(
                            Helper.INSTANCE.getPreferences()
                            .getPreferredTableWidth(),
                            Helper.INSTANCE.getPreferences()
                            .getPreferredTableHeight(
                                    this.baseTableModel.getRowCount())));
            this.viewport.setMaximumSize(
                    new Dimension(
                            Preferences.getMaximumTableWidth(),
                            Preferences.getMaximumTableHeight()));
            this.registerKeyboardShortcuts4One();
            return;
        }

        // display scroll pane and an additional import all/delete all table
        // iff there is more than one file in the list
        if (this.baseTableModel.getRowCount() > 1) {
            JComponent component = this.splitPaneFactory.getComponent();
            component.setBorder(MoneydanceLAF.homePageBorder);

            this.viewport.setView(component);
            this.viewport.setMinimumSize(null);
            this.viewport.setPreferredSize(null);
            this.viewport.setMaximumSize(null);
            this.registerKeyboardShortcuts4All();
            return;
        }
    }


    @Override
    public JComponent getGUIView(final AccountBook accountBook) {
        if (!this.initialized) {
            this.initialized = true;
            this.init();
        }
        return this.viewport;
    }


    @Override
    public void reset() {
        // ignore
    }


    @Override
    public void setActive(final boolean active) {
        if (this.viewport == null) {
            return;
        }
        this.viewport.setVisible(active);
        this.refresh();
    }


    @Override
    public String getID() {
        return this.settings.getExtensionIdentifier();
    }


    @Override
    public String toString() {
        return this.settings.getExtensionName();
    }


    public void chooseBaseDirectory() {
        this.fileAdmin.chooseBaseDirectory();
    }


    public void cleanup() {
        if (!this.initialized) {
            return;
        }
        this.viewport.setEnabled(false);
        this.viewport.removeAll();
        this.fileAdmin.stopMonitor();
    }


    public void setDirty(final boolean argDirty) {
        this.dirty = argDirty;
    }


    public boolean isDirty() {
        return this.dirty;
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
    private final class ViewControllerObserver
    implements Observer {
        @Override
        public void update(final Observable observable, final Object object) {
            ViewController.this.setDirty(true);
            ViewController.this.refresh();
        }
    }

    /**
     * @author Florian J. Breunig
     */
    private final class ChooseBaseDirectoryActionListener
    implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent event) {
            ViewController.this.chooseBaseDirectory();
        }
    }
}
