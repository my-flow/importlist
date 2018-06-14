// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2018 Florian J. Breunig
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
import com.infinitekind.moneydance.model.AccountUtil;
import com.infinitekind.moneydance.model.AcctFilter;
import com.infinitekind.util.StringUtils;
import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.modules.features.importlist.util.Preferences;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.FilenameUtils;

/**
 * @author Florian J. Breunig
 */
final class ImportOneOperation implements FileOperation {

    private final Preferences          prefs;
    private final FeatureModuleContext context;
    private final FileFilter           transactionFileFilter;
    private final FileFilter           textFileFilter;

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG =
            Logger.getLogger(ImportOneOperation.class.getName());

    ImportOneOperation(
            final FeatureModuleContext argContext,
            final FileFilter argTransactionFileFilter,
            final FileFilter argTextFileFilter) {
        this.prefs                  = Helper.INSTANCE.getPreferences();
        this.context                = argContext;
        this.transactionFileFilter  = argTransactionFileFilter;
        this.textFileFilter         = argTextFileFilter;
    }

    @Override
    public void showWarningAndExecute(final List<File> files) {
        this.execute(files);
    }

    @Override
    public void execute(final List<File> files) {
        final File file = files.iterator().next();
        
        String uriScheme = "";
        if (this.transactionFileFilter.accept(file)) {
          uriScheme = Helper.INSTANCE.getSettings().getTransactionFileImportUriScheme();
        } else if (this.textFileFilter.accept(file)) {
          uriScheme = Helper.INSTANCE.getSettings().getTextFileImportUriScheme();
          uriScheme = StringUtils.replaceAll(uriScheme, "${accountno}", 
                                             this.getAccountNumberForFile(file));
        }
        uriScheme = StringUtils.replaceAll(uriScheme, "${filename}", 
                                           file.getAbsolutePath());
        
        // Import the file by calling the URI
        this.context.showURL(uriScheme);
    }

    private String getAccountNumberForFile(final File argFile) {
        final String fileName = FilenameUtils.removeExtension(
                argFile.getName());

        Account rootAccount =
                this.context.getCurrentAccountBook().getRootAccount();

        String accountNumber = "-1";

        for (Iterator<Account> iterator =
                AccountUtil.getAccountIterator(rootAccount);
                iterator.hasNext();) {
            final Account account = iterator.next();
            if (!AcctFilter.NON_CATEGORY_FILTER.matches(account)
                    || rootAccount.equals(account)) {
                continue;
            }

            final String accountName = account.getFullAccountName();
            if (this.isEqual(accountName, fileName)) {
                LOG.config(String.format(
                        "Found matching account \"%s\" for file %s",
                        account.getFullAccountName(),
                        argFile.getName()));
                accountNumber = String.valueOf(account.getAccountNum());
            }
        }

        return accountNumber;
    }

    private boolean isEqual(final String first, final String second) {
        final String string1 =  first.toUpperCase(this.prefs.getLocale());
        final String string2 = second.toUpperCase(this.prefs.getLocale());
        return string1.contains(string2) || string2.contains(string1);
    }
}
