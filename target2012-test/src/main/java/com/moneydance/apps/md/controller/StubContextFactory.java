// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2019 Florian J. Breunig
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
