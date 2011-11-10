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

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.apps.md.model.Account;
import com.moneydance.apps.md.model.AccountUtil;
import com.moneydance.apps.md.model.AcctFilter;
import com.moneydance.apps.md.model.RootAccount;
import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.modules.features.importlist.util.Preferences;
import com.moneydance.modules.features.importlist.util.Settings;

/**
 * @author Florian J. Breunig
 */
final class FileImporter implements FileOperation {

    private final Preferences          prefs;
    private final Settings             settings;
    private final FeatureModuleContext context;
    private final FileFilter           transactionFileFilter;
    private final FileFilter           textFileFilter;

    /**
     * Static initialization of class-dependent logger.
     */
    private static final Logger LOG =
            LoggerFactory.getLogger(FileImporter.class);

    FileImporter(
            final FeatureModuleContext argContext,
            final FileFilter argTransactionFileFilter,
            final FileFilter argTextFileFilter) {
        this.prefs                  = Helper.getPreferences();
        this.settings               = Helper.getSettings();
        this.context                = argContext;
        this.transactionFileFilter  = argTransactionFileFilter;
        this.textFileFilter         = argTextFileFilter;
    }

    @Override
    public void perform(final File file) {
        String uriScheme = "";
        if (this.transactionFileFilter.accept(file)) {
            uriScheme = this.settings.getTransactionFileImportUriScheme();
        }
        if (this.textFileFilter.accept(file)) {
            uriScheme = this.settings.getTextFileImportUriScheme();
        }

        Map<String, String> valuesMap = new HashMap<String, String>();
        valuesMap.put("filename",  file.getAbsolutePath());
        valuesMap.put("accountno", this.getAccountNumberForFile(file));
        StrSubstitutor sub = new StrSubstitutor(valuesMap);

        final String resolvedUri = sub.replace(uriScheme);

        // Import the file by calling the URI
        this.context.showURL(resolvedUri);
    }

    private String getAccountNumberForFile(final File file) {
        RootAccount rootAccount = this.context.getRootAccount();
        if (rootAccount == null) {
            return "-1";
        }

        final String fileName = FilenameUtils.removeExtension(file.getName());

        Iterator<Account> iterator =
                AccountUtil.getAccountIterator(rootAccount);
        while (iterator.hasNext()) {
            final Account account = iterator.next();
            if (!AcctFilter.NON_CATEGORY_FILTER.matches(account)) {
                continue;
            }
            if (rootAccount.equals(account)) {
                continue;
            }

            final String accountName = account.getFullAccountName();
            if (this.isEqual(accountName, fileName)) {
                LOG.debug("Found matching account \""
                        + account.getFullAccountName()
                        + "\" for file " + file.getName());
                return String.valueOf(account.getAccountNum());
            }
        }

        return "-1";
    }

    private boolean isEqual(final String first, final String second) {
        final String string1 = this.simplifyComparableString(first);
        final String string2 = this.simplifyComparableString(second);
        return string1.contains(string2) || string2.contains(string1);
    }

    private String simplifyComparableString(final String string) {
        return string.toUpperCase(this.prefs.getLocale());
    }
}
