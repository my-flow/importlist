package com.moneydance.modules.features.importlist.io;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.io.monitor.FileAlterationObserver;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Florian J. Breunig
 */
public final class TransactionFileListenerTest implements Observer {

    private TransactionFileListener listener;
    private File file;
    private boolean observerWasNotified;
    private FileAlterationObserver fileAlterationObserver;

    @Before
    public void setUp() {
        this.file = new File("");
        this.listener = new TransactionFileListener();
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
