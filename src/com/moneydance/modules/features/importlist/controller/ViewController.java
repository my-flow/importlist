/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011-2012 Florian J. Breunig
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.moneydance.modules.features.importlist.controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JViewport;
import javax.swing.table.AbstractTableModel;

import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.apps.md.model.RootAccount;
import com.moneydance.apps.md.view.HomePageView;
import com.moneydance.apps.md.view.gui.MoneydanceLAF;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.table.AbstractEditor;
import com.moneydance.modules.features.importlist.table.ColumnFactory;
import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.modules.features.importlist.util.Localizable;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;
import com.moneydance.modules.features.importlist.view.AggregationTableFactory;
import com.moneydance.modules.features.importlist.view.BaseTableFactory;
import com.moneydance.modules.features.importlist.view.ComponentFactory;
import com.moneydance.modules.features.importlist.view.DirectoryChooserFactory;
import com.moneydance.modules.features.importlist.view.EmptyLabelFactory;
import com.moneydance.modules.features.importlist.view.SplitPaneFactory;

/**
 * The user interface that is displayed on the homepage.
 *
 * @author Florian J. Breunig
 */
public final class ViewController implements HomePageView, Observer {

    private final Preferences               prefs;
    private final Settings                  settings;
    private final Localizable               localizable;
    private final FileAdmin                 fileAdmin;
    private final Tracker                   tracker;
    private       boolean                   initialized;
    private       ColumnFactory             columnFactory;
    private       JViewport                 viewport;
    private       DirectoryChooserFactory   directoryChooserFactory;
    private       EmptyLabelFactory         emptyLabelFactory;
    private       ComponentFactory          splitPaneFactory;
    private       BaseTableFactory          baseTableFactory;
    private       AbstractTableModel        baseTableModel;
    private       AbstractTableModel        aggrTableModel;
    private       boolean                   dirty;


    public ViewController(
            final String baseDirectory,
            final FeatureModuleContext context,
            final int build) {
        this.prefs       = Helper.getPreferences();
        this.settings    = Helper.getSettings();
        this.localizable = Helper.getLocalizable();
        this.fileAdmin   = new FileAdmin(baseDirectory, context);
        this.fileAdmin.addObserver(this);
        this.tracker     = new Tracker(
                this.prefs.getFullVersion(),
                build,
                this.settings.getTrackingCode());
    }


    private void init() {
        this.viewport = new JViewport();
        this.viewport.setOpaque(false);

        ActionListener actionListener = new ChooseBaseDirectoryActionListener();
        this.directoryChooserFactory = new DirectoryChooserFactory(
                actionListener);
        this.emptyLabelFactory       = new EmptyLabelFactory();

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

        this.tracker.track("Display");
    }


    @Override
    public synchronized void refresh() {
        if (this.viewport == null || !this.viewport.isVisible()) {
            return;
        }

        this.prefs.reload();

        // display a label iff no base directory has been chosen
        if (this.fileAdmin.getBaseDirectory() == null) {
            JButton chooserButton = this.directoryChooserFactory.getComponent();
            this.viewport.setView(chooserButton);
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
            String emptyMessage = this.localizable.getEmptyMessage(
                    this.fileAdmin.getBaseDirectory().getAbsolutePath());
            JLabel emptyLabel = this.emptyLabelFactory.getComponent();
            emptyLabel.setText(emptyMessage);

            this.viewport.setView(emptyLabel);
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
    public void update(final Observable observable, final Object object) {
        this.setDirty(true);
        this.refresh();
    }


    @Override
    public JComponent getGUIView(final RootAccount rootAccount) {
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


    private class ChooseBaseDirectoryActionListener
        implements ActionListener {

        @Override
        public void actionPerformed(final ActionEvent event) {
            ViewController.this.chooseBaseDirectory();
        }
    }
}
