package com.moneydance.apps.md.controller;

import com.moneydance.apps.md.model.Account;
import com.moneydance.apps.md.model.AccountBook;
import com.moneydance.apps.md.model.CurrencyTable;
import com.moneydance.apps.md.model.CurrencyType;
import com.moneydance.apps.md.model.RootAccount;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public StubContextFactory() {
        this.context = new StubContext();
        try {
            this.initContext();
        } catch (IOException e) {
            final String message = e.getMessage();
            if (message != null) {
                LOG.log(Level.WARNING, message, e);
            }
        }
    }

    private void initContext() throws IOException {
        CurrencyType currencyType = new CurrencyType(
                -1,
                "USD",
                "Test Currency",
                1.0D,
                0,
                "$",
                "",
                "USD",
                CurrencyType.CURRTYPE_CURRENCY,
                0,
                null);

        AccountBook accountBook = AccountBook.fakeAccountBook();
        RootAccount rootAccount = new RootAccount(accountBook, currencyType,
                new CurrencyTable());
        try {
            rootAccount.addSubAccount(Account.makeAccount(
                    Account.ACCOUNT_TYPE_BANK,
                    "stub account",
                    currencyType,
                    rootAccount));
        } catch (Exception e) {
            final String message = e.getMessage();
            if (message != null) {
                LOG.log(Level.WARNING, message, e);
            }
        }
        this.context.setRootAccount(rootAccount);
    }

    public StubContext getContext() {
        return this.context;
    }
}
