package com.moneydance.modules.features.importlist.io;

import com.moneydance.modules.features.importlist.CoreTestComponent;
import com.moneydance.modules.features.importlist.CoreTestModule;
import com.moneydance.modules.features.importlist.DaggerCoreTestComponent;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Florian J. Breunig
 */
public final class ImportOneOperationTest {

    private final List<File> incomeFile;
    private FileOperation fileOperation;

    public ImportOneOperationTest() {
        this.incomeFile = Collections.singletonList(new File("mybank.csv"));
    }

    @Before
    public void setUp() {
        final CoreTestModule testModule = new CoreTestModule();
        final CoreTestComponent testComponent = DaggerCoreTestComponent.builder().coreTestModule(testModule).build();
        this.fileOperation = testComponent.importOneOperation();
    }

    @Test
    public void testShowWarningAndExecute() {
        this.fileOperation.showWarningAndExecute(this.incomeFile);
    }

    @Test
    public void testExecute() {
        this.fileOperation.execute(this.incomeFile);
    }
}
