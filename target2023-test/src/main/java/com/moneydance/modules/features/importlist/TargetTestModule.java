package com.moneydance.modules.features.importlist;

import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.apps.md.controller.StubContextFactory;
import com.moneydance.modules.features.importlist.controller.Context;
import com.moneydance.modules.features.importlist.controller.ContextMock;
import com.moneydance.modules.features.importlist.io.FileContainer;
import com.moneydance.modules.features.importlist.table.ColorScheme;
import com.moneydance.modules.features.importlist.table.ColorSchemeImpl;

import java.awt.Color;
import java.util.ArrayList;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

/**
 * Test module for providing test implementations of components.
 *
 * @author Florian J. Breunig
 */
public final class TargetTestModule {

    private final FeatureModuleContext context;

    /**
     * Creates a new TargetTestModule.
     */
    public TargetTestModule() {
        StubContextFactory factory = new StubContextFactory();
        this.context = factory.getContext();
    }

    /**
     * Provides the feature module context.
     *
     * @return feature module context for tests
     */
    public FeatureModuleContext getFeatureModuleContext() {
        return this.context;
    }

    /**
     * Provides a context implementation for tests.
     *
     * @return Context mock for tests
     */
    public Context provideContext() {
        return new ContextMock();
    }

    /**
     * Provides a file container for tests.
     *
     * @return FileContainer instance for tests
     */
    public FileContainer provideFileContainer() {
        IOFileFilter fileFilter = TrueFileFilter.TRUE;
        return new FileContainer(new ArrayList<>(), new ArrayList<>(), fileFilter);
    }

    /**
     * Provides an even color scheme for tests.
     *
     * @return ColorScheme instance for tests
     */
    public ColorScheme provideEvenColorScheme() {
        return new ColorSchemeImpl(Color.BLACK);
    }

    /**
     * Provides an odd color scheme for tests.
     *
     * @return ColorScheme instance for tests
     */
    public ColorScheme provideOddColorScheme() {
        return new ColorSchemeImpl(Color.BLACK);
    }
}
