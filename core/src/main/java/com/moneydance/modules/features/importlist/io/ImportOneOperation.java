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

import com.moneydance.apps.md.controller.FeatureModuleContext;
import com.moneydance.modules.features.importlist.util.Helper;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.text.StrSubstitutor;

/**
 * @author Florian J. Breunig
 */
final class ImportOneOperation implements FileOperation {

    private final FeatureModuleContext context;
    private final FileFilter           transactionFileFilter;

    ImportOneOperation(
            final FeatureModuleContext argContext,
            final FileFilter argTransactionFileFilter) {
        this.context                = argContext;
        this.transactionFileFilter  = argTransactionFileFilter;
    }

    @Override
    public void showWarningAndExecute(final List<File> files) {
        this.execute(files);
    }

    @Override
    public void execute(final List<File> files) {
        final File file = files.iterator().next();
        Map<String, String> valuesMap = new HashMap<String, String>(1);
        valuesMap.put("filename",  file.getAbsolutePath());

        String uriScheme = "";
        if (this.transactionFileFilter.accept(file)) {
            uriScheme = Helper.INSTANCE.getSettings()
                    .getTransactionFileImportUriScheme();
        }

        final StrSubstitutor sub = new StrSubstitutor(valuesMap);
        final String resolvedUri = sub.replace(uriScheme);

        // Import the file by calling the URI
        this.context.showURL(resolvedUri);
    }
}
