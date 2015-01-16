// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2015 Florian J. Breunig
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

import com.infinitekind.moneydance.model.Account;
import com.infinitekind.moneydance.model.AccountBook;
import com.infinitekind.moneydance.model.AccountHelper;
import com.moneydance.apps.md.controller.StubContext;
import com.moneydance.apps.md.controller.StubContextFactory;
import com.moneydance.modules.features.importlist.util.Helper;

import java.io.File;
import java.util.Collections;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.Test;

/**
 * @author Florian J. Breunig
 */
public final class ImportOneOperationTest {

    private final File incomeFile;
    private final File creditcardFile;
    private final File noCategoryFile;

    public ImportOneOperationTest() {
        Helper.INSTANCE.getPreferences();
        this.incomeFile     = new File("mybank.csv");
        this.creditcardFile = new File("credit.csv");
        this.noCategoryFile = new File("nocategory.csv");
    }

    @Test
    public void testExecute() {
        AccountBook accountBook = AccountBook.fakeAccountBook();

        Account fullRootAccount = new Account(accountBook);
        Account account = new Account(accountBook);
        AccountHelper.addSubAccount(fullRootAccount, account);
        accountBook.initializeAccounts(fullRootAccount);

        final StubContextFactory factory = new StubContextFactory();
        StubContext context = factory.getContext();

        FileOperation fileOperation = null;

        context.setAccountBook(accountBook);
        fileOperation = new ImportOneOperation(
                context,
                TrueFileFilter.TRUE,
                FalseFileFilter.FALSE);
        fileOperation.execute(Collections.singletonList(this.incomeFile));

        fileOperation = new ImportOneOperation(
                context,
                TrueFileFilter.TRUE,
                FalseFileFilter.FALSE);
        fileOperation.execute(Collections.singletonList(this.creditcardFile));

        fileOperation = new ImportOneOperation(
                context,
                TrueFileFilter.TRUE,
                FalseFileFilter.FALSE);
        fileOperation.execute(Collections.singletonList(this.noCategoryFile));

        fileOperation = new ImportOneOperation(
                context,
                FalseFileFilter.FALSE,
                TrueFileFilter.TRUE);
        fileOperation.execute(Collections.singletonList(this.incomeFile));

        fileOperation = new ImportOneOperation(
                context,
                FalseFileFilter.FALSE,
                TrueFileFilter.TRUE);
        fileOperation.execute(Collections.singletonList(this.creditcardFile));

        fileOperation = new ImportOneOperation(
                context,
                FalseFileFilter.FALSE,
                TrueFileFilter.TRUE);
        fileOperation.execute(Collections.singletonList(this.noCategoryFile));

        fileOperation = new ImportOneOperation(
                context,
                FalseFileFilter.FALSE,
                FalseFileFilter.FALSE);
        fileOperation.execute(Collections.singletonList(this.incomeFile));

        fileOperation = new ImportOneOperation(
                context,
                FalseFileFilter.FALSE,
                FalseFileFilter.FALSE);
        fileOperation.execute(Collections.singletonList(this.creditcardFile));

        fileOperation = new ImportOneOperation(
                context,
                FalseFileFilter.FALSE,
                FalseFileFilter.FALSE);
        fileOperation.execute(Collections.singletonList(this.noCategoryFile));
    }
}
