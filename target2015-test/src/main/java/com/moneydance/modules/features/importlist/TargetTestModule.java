package com.moneydance.modules.features.importlist;

import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.apps.md.controller.StubContextFactory;
import com.moneydance.modules.features.importlist.controller.Context;
import com.moneydance.modules.features.importlist.controller.ContextMock;
import com.moneydance.modules.features.importlist.controller.ViewController;
import com.moneydance.modules.features.importlist.controller.ViewControllerImpl;
import com.moneydance.modules.features.importlist.io.FileAdmin;
import com.moneydance.modules.features.importlist.io.FileContainer;
import com.moneydance.modules.features.importlist.table.ColorScheme;
import com.moneydance.modules.features.importlist.table.ColorSchemeImpl;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.PreferencesImpl;
import com.moneydance.modules.features.importlist.util.Settings;

import java.awt.Color;
import java.util.ArrayList;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.swing.table.AbstractTableModel;

import dagger.Module;
import dagger.Provides;
import org.apache.commons.io.filefilter.IOFileFilter;

/**
 * @author Florian J. Breunig
 */
@Module
public final class TargetTestModule {

    private final FeatureModuleContext context;

    public TargetTestModule() {
        StubContextFactory factory = new StubContextFactory();
        this.context = factory.getContext();
    }

    @Provides
    FeatureModuleContext provideFeatureModuleContext() {
        return this.context;
    }

    @Provides
    Context provideContext() {
        return new ContextMock();
    }

    @Provides
    Preferences providePreferences(final Settings argSettings, final FeatureModuleContext argContext) {
        final com.moneydance.apps.md.controller.Main main =
                (com.moneydance.apps.md.controller.Main) argContext;
        return new PreferencesImpl(argSettings, main);
    }

    @Provides
    @Singleton
    ViewController provideViewController(
            @Named("base") final AbstractTableModel baseTableModel,
            @Named("aggregation") final AbstractTableModel aggrTableModel,
            final FileAdmin fileAdmin,
            @Named("even color scheme") final ColorScheme evenColorScheme,
            @Named("odd color scheme") final ColorScheme oddColorScheme,
            final Settings settings,
            final Preferences prefs) {
        return new ViewControllerImpl(
                fileAdmin,
                baseTableModel,
                aggrTableModel,
                evenColorScheme,
                oddColorScheme,
                settings,
                prefs);
    }

    @Provides
    @Singleton
    FileContainer provideFileContainer(@Named("readable files") final IOFileFilter argFileFilter) {
        return new FileContainer(new ArrayList<>(), new ArrayList<>(), argFileFilter);
    }

    @Provides
    @Singleton
    @Named("even color scheme")
    ColorScheme provideEvenColorSchemeImpl() {
        return new ColorSchemeImpl(Color.BLACK);
    }

    @Provides
    @Singleton
    @Named("odd color scheme")
    ColorScheme provideOddColorSchemeImpl() {
        return new ColorSchemeImpl(Color.BLACK);
    }
}
