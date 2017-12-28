// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2017 Florian J. Breunig
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

package com.moneydance.apps.md.controller;

import com.infinitekind.moneydance.model.Account;
import com.infinitekind.moneydance.model.AccountBook;
import com.infinitekind.moneydance.model.AccountHelper;
import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.util.StreamTable;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Nullable;

/**
 * @author Florian J. Breunig
 */
public final class StubContextFactory {

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG =
            Logger.getLogger(StubContextFactory.class.getName());

    @Nullable private final FeatureModule featureModule;
    private final StubContext context;

    public StubContextFactory() {
        this.featureModule = null;
        this.context = new StubContext(this.featureModule);
        initContext(this.context);
    }

    public StubContextFactory(final FeatureModule argFeatureModule) {
        if(argFeatureModule==null) throw new NullPointerException("featureModule must not be null");
        this.featureModule  = argFeatureModule;
        this.context = new StubContext(this.featureModule);
        initContext(this.context);
    }

    private static void initContext(final StubContext context) {
        AccountBook accountBook = AccountBook.fakeAccountBook();
        accountBook.initializeAccounts(new Account(accountBook));

        try {
            AccountHelper.addSubAccount(
                    accountBook.getRootAccount(),
                    Account.makeAccount(
                            accountBook,
                            Account.AccountType.BANK,
                            accountBook.getRootAccount()));
        } catch (Exception e) {
            final String message = e.getMessage();
            if (message != null) {
                LOG.log(Level.WARNING, message, e);
            }
        }

        context.setAccountBook(accountBook);
        Helper.INSTANCE.setContext(context);
    }

    @SuppressWarnings("nullness")
    public void init() {
        LOG.info("Setting up stub context");
        this.featureModule.setup(
                this.context,
                null,
                new StreamTable(),
                null,
                null);
    }

    @Nullable public StubContext getContext() {
        return this.context;
    }
}
