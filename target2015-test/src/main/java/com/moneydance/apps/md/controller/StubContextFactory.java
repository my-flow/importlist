package com.moneydance.apps.md.controller;

import com.infinitekind.moneydance.model.Account;
import com.infinitekind.moneydance.model.AccountBook;
import com.infinitekind.moneydance.model.AccountHelper;
import com.infinitekind.util.StreamTable;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Nullable;

import org.apache.commons.lang3.Validate;

/**
 * @author Florian J. Breunig
 */
public final class StubContextFactory {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG =
            Logger.getLogger(StubContextFactory.class.getName());

    private final StubContext context;
    private FeatureModule featureModule;

    public StubContextFactory() {
        this.context = new StubContext();
        initContext(this.context);
    }

    public StubContextFactory(final FeatureModule argFeatureModule) {
        Validate.notNull(argFeatureModule, "featureModule must not be null");
        this.featureModule = argFeatureModule;
        this.context = new StubContext(this.featureModule);
        initContext(this.context);
    }

    private static void initContext(final StubContext context) {
        AccountBook accountBook = AccountBook.fakeAccountBook();

        try {
            AccountHelper.addSubAccount(
                    accountBook.getRootAccount(),
                    Account.makeAccount(
                            accountBook,
                            Account.AccountType.BANK,
                            accountBook.getRootAccount()));
        // Catch broad exception in test setup to ensure robust error handling
        } catch (RuntimeException e) { // NOPMD - AvoidCatchingGenericException - test infrastructure
            final String message = e.getMessage();
            if (message != null) {
                LOG.log(Level.WARNING, message, e);
            }
        }

        context.setAccountBook(accountBook);
    }

    @SuppressWarnings("nullness")
    public void init() {
        this.init(null);
    }

    /**
     * Sets up the feature module in the stub context.
     *
     * @param moduleId The ID the feature module is set up with.
     */
    @SuppressWarnings("nullness")
    public void init(@Nullable final String moduleId) {
        LOG.info("Setting up stub context");
        this.featureModule.setup(
                this.context,
                moduleId,
                new StreamTable(),
                null,
                null,
                true);
    }

    @Nullable public StubContext getContext() {
        return this.context;
    }
}
