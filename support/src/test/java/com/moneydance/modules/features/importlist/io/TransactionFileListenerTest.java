// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2018 Florian J. Breunig
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

package com.moneydance.modules.features.importlist.io;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.io.monitor.FileAlterationObserver;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Florian J. Breunig
 */
public final class TransactionFileListenerTest implements Observer {

    private TransactionFileListener listener;
    private File file;
    private boolean observerWasNotified = false;
    private FileAlterationObserver fileAlterationObserver;

    @Before
    public void setUp() {
        this.file                   = new File("");
        this.listener               = new TransactionFileListener();
        this.fileAlterationObserver = new FileAlterationObserver(this.file);
        this.listener.addObserver(this);
        this.listener.onStart(this.fileAlterationObserver);
    }

    @Test
    public void testOnStart() {
        assertThat(this.listener.hasChanged(), is(false));
    }

    @Test
    public void testOnFileCreate() {
        this.listener.onFileCreate(this.file);
        assertThat(this.listener.hasChanged(), is(true));
    }

    @Test
    public void testOnFileChange() {
        this.listener.onFileChange(this.file);
        assertThat(this.listener.hasChanged(), is(true));
    }

    @Test
    public void testOnFileDelete() {
        this.listener.onFileDelete(this.file);
        assertThat(this.listener.hasChanged(), is(true));
    }

    @Test
    public void testOnStopAfterIdle() {
        this.listener.onStop(this.fileAlterationObserver);
        assertThat(this.observerWasNotified, is(false));
    }

    @Test
    public void testOnStopAfterChange() {
        this.listener.onFileDelete(this.file);
        this.listener.onStop(this.fileAlterationObserver);
        assertThat(this.observerWasNotified, is(true));
    }

    @Test
    public void testOnDirectoryChange() {
        this.listener.onDirectoryChange(this.file);
        assertThat(this.listener.hasChanged(), is(false));
    }

    @Test
    public void testOnDirectoryCreate() {
        this.listener.onDirectoryCreate(this.file);
        assertThat(this.listener.hasChanged(), is(false));
    }

    @Test
    public void testOnDirectoryDelete() {
        this.listener.onDirectoryDelete(this.file);
        assertThat(this.listener.hasChanged(), is(false));
    }

    @Override
    public void update(final Observable observable, final Object object) {
        this.observerWasNotified = true;
    }
}
