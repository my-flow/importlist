package com.moneydance.modules.features.importlist.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Mock implementation of Settings for testing.
 *
 * @author Florian J. Breunig
 */
public final class SettingsMock implements ISettings {

    private static final Image MOCK_IMAGE = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);


    @Override
    public String toString() {
        return "SettingsMock@" + Integer.toHexString(System.identityHashCode(this));
    }

    @Override
    public String getExtensionName() {
        return "Import List Extension";
    }

    @Override
    public String getExtensionIdentifier() {
        return "importlist";
    }

    @Override
    public Image getIconImage() {
        return MOCK_IMAGE;
    }

    @Override
    public InputStream getLoggingPropertiesResource() {
        return new ByteArrayInputStream("".getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }

    @Override
    public String getLocalizableResource() {
        return "localizable";
    }

    @Override
    public String getChooseBaseDirSuffix() {
        return "choose_base_dir";
    }

    @Override
    public String getTransactionFileImportUriScheme() {
        return "moneydance:importfile:";
    }

    @Override
    public String getTextFileImportUriScheme() {
        return "moneydance:importcsv:";
    }

    @Override
    public int getMonitorInterval() {
        return 5000;
    }

    @Override
    public List<String> getTransactionFileExtensions() {
        return Collections.unmodifiableList(Arrays.asList("qif", "ofx", "qfx", "ofc", "csv"));
    }

    @Override
    public int getMaxFilenameLength() {
        return 25;
    }

    @Override
    public String getDescName() {
        return "Name";
    }

    @Override
    public String getDescModified() {
        return "Date Modified";
    }

    @Override
    public String getDescImport() {
        return "Import";
    }

    @Override
    public String getDescDelete() {
        return "Delete";
    }

    @Override
    public String getIndentationPrefix() {
        return "  ";
    }

    @Override
    public boolean isButtonResizable() {
        return false;
    }

    @Override
    public int getMinColumnWidth() {
        return 50;
    }

    @Override
    public boolean isReorderingAllowed() {
        return false;
    }

    @Override
    public int getMinimumTableWidth() {
        return 400;
    }

    @Override
    public int getMinimumTableHeight() {
        return 200;
    }

    @Override
    public int getTableHeightOffset() {
        return 30;
    }

    @Override
    public int getColumnWidth() {
        return 120;
    }

    @Override
    public int getPreferredEmptyMessageWidth() {
        return 300;
    }

    @Override
    public int getPreferredEmptyMessageHeight() {
        return 150;
    }

    @Override
    public int getMessageFilenameLineMaxLength() {
        return 40;
    }

    @Override
    public int getColorValueFgDef() {
        return 0;
    }

    @Override
    public int getColorValueBgDef() {
        return 0;
    }

    @Override
    public int getColorValueBgAltDef() {
        return 0;
    }

    @Override
    public double getFactorRowHeightHeader() {
        return 1.5;
    }

    @Override
    public double getSummandRowHeightBody() {
        return 4.0;
    }

    @Override
    public String getKeyboardShortcutImport() {
        return "alt I";
    }

    @Override
    public String getKeyboardShortcutDelete() {
        return "alt D";
    }
}
