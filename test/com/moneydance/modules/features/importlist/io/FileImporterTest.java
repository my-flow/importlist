/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011 Florian J. Breunig
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.moneydance.modules.features.importlist.io;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.junit.Test;

import com.moneydance.apps.md.controller.StubContext;
import com.moneydance.apps.md.controller.StubContextFactory;
import com.moneydance.apps.md.model.Account;
import com.moneydance.apps.md.model.BankAccount;
import com.moneydance.apps.md.model.CreditCardAccount;
import com.moneydance.apps.md.model.CurrencyTable;
import com.moneydance.apps.md.model.CurrencyType;
import com.moneydance.apps.md.model.IncomeAccount;
import com.moneydance.apps.md.model.RootAccount;

/**
 * @author Florian J. Breunig
 */
public final class FileImporterTest {

    private final File incomeFile;
    private final File creditcardFile;
    private final File noCategoryFile;

    private final CurrencyTable currencyTable;
    private final CurrencyType  currencyType;

    public FileImporterTest() {
        this.incomeFile     = new File("mybank.csv");
        this.creditcardFile = new File("credit.csv");
        this.noCategoryFile = new File("nocategory.csv");

        this.currencyTable  = new CurrencyTable();
        this.currencyType   = new CurrencyType(0, "", "", 0, 0, "", "", "", 0,
                0, this.currencyTable);
    }

    @Test
    public void testPerform() {
        RootAccount fullRootAccount = new RootAccount(
                this.currencyType,
                this.currencyTable);
        Account incomeAccount = new IncomeAccount(
                "Income",
                1,
                this.currencyType,
                null,
                null,
                fullRootAccount);
        fullRootAccount.addSubAccount(incomeAccount);
        Account bankAccount = new BankAccount(
                "Bank",
                1,
                this.currencyType,
                null,
                null,
                fullRootAccount,
                0);
        fullRootAccount.addSubAccount(bankAccount);

        Account creditcardAccount = new CreditCardAccount(
                "Credit Card",
                1,
                this.currencyType,
                null,
                null,
                fullRootAccount,
                0);
        fullRootAccount.addSubAccount(creditcardAccount);

        final StubContextFactory factory = new StubContextFactory();
        StubContext context = factory.getContext();

        FileOperation fileOperation = null;

        try {
            context.setRootAccount(fullRootAccount);
            fileOperation = new FileImporter(
                    factory.getContext(),
                    TrueFileFilter.TRUE,
                    FalseFileFilter.FALSE);
            fileOperation.perform(this.incomeFile);
            fileOperation.perform(this.creditcardFile);
            fileOperation.perform(this.noCategoryFile);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        try {
            context.setRootAccount(fullRootAccount);
            fileOperation = new FileImporter(
                    factory.getContext(),
                    FalseFileFilter.FALSE,
                    TrueFileFilter.TRUE);

            fileOperation.perform(this.incomeFile);
            fileOperation.perform(this.creditcardFile);
            fileOperation.perform(this.noCategoryFile);
        } catch (IOException e) {
            fail(e.getMessage());
        }

        try {
            context.setRootAccount(fullRootAccount);
            fileOperation = new FileImporter(
                    factory.getContext(),
                    FalseFileFilter.FALSE,
                    FalseFileFilter.FALSE);

            fileOperation.perform(this.incomeFile);
            fileOperation.perform(this.creditcardFile);
            fileOperation.perform(this.noCategoryFile);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }
}
