package com.moneydance.modules.features.importlist.util;

import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.Validate;


/**
 * This configuration class accesses all values that are read
 * from a settings file in plain text. The settings file cannot be
 * modified at runtime, so the {@code Settings} class is effectively
 * immutable.
 *
 * @author Florian J. Breunig
 */
public final class Settings implements ISettings {

    /**
     * The list delimiter character.
     */
    private static final char DELIMITER = ',';

    private final Configuration config;
    private final Image iconImage;

    /**
     * Private constructor that takes already created config and image.
     * @param configuration The configuration
     * @param image The icon image
     */
    private Settings(final Configuration configuration, final Image image) {
        this.config = configuration;
        this.iconImage = image;
    }

    /**
     * Creates settings from a resource.
     * @param resource Path to settings resource
     * @param isFilePath True=file path, False=classpath
     * @throws NullPointerException If not found
     * @throws IOException For I/O errors
     * @throws ConfigurationException For config errors
     */
    public Settings(final String resource, final boolean isFilePath)
            throws IOException, ConfigurationException {
        this.config = createTempConfig(resource, isFilePath);
        this.iconImage = createTempImage(resource, isFilePath);
    }

    /**
     * Constructor using classpath resource.
     * @param resource The resource path
     * @throws NullPointerException If resource path is null/not found
     * @throws IllegalArgumentException If configuration or image cannot be loaded
     * @throws IOException If there is an IO error loading the resources
     * @throws ConfigurationException If there is a configuration error
     */
    public Settings(final String resource) throws IOException, ConfigurationException {
        this.config = createTempConfig(resource, false);
        this.iconImage = createTempImage(resource, false);
    }


    /**
     * Factory method to create configuration.
     * @param resource The resource path
     * @param isFilePath If true, use file path, otherwise classpath
     * @return The configuration
     * @throws IOException if there is an error reading the resource
     * @throws ConfigurationException if there is an error in the configuration
     */
    private static Configuration createTempConfig(final String resource, final boolean isFilePath)
            throws IOException, ConfigurationException {
        PropertiesConfiguration propertiesConfig = new PropertiesConfiguration();
        propertiesConfig.setListDelimiterHandler(new DefaultListDelimiterHandler(DELIMITER));
        if (isFilePath) {
            // Load from a file path directly
            try (InputStream inputStream = java.nio.file.Files.newInputStream(java.nio.file.Paths.get(resource))) {
                propertiesConfig.read(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            }
        } else {
            // Load from classpath resource
            try (InputStream inputStream = getInputStreamFromResource(resource)) {
                propertiesConfig.read(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            }
        }
        return propertiesConfig;
    }

    /**
     * Factory method to create image.
     * @param resource The resource path
     * @param isFilePath If true, use file path, otherwise classpath
     * @return The image
     * @throws IOException if there is an error reading the resource or image
     */
    private static Image createTempImage(final String resource, final boolean isFilePath) throws IOException {
        try {
            return getImage(createTempConfig(resource, isFilePath).getString("icon_resource"));
        } catch (ConfigurationException e) {
            java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Settings.class.getName());
            if (logger.isLoggable(java.util.logging.Level.SEVERE)) {
                logger.severe("Error loading configuration for image: " + e.getMessage());
            }
            throw new IOException("Failed to load configuration for image", e);
        }
    }

    /**
     * @return The descriptive name of this extension.
     */
    @Override
    public String getExtensionName() {
        return this.config.getString("extension_name"); //$NON-NLS-1$
    }

    /**
     * @return The ID string for this extension.
     */
    @Override
    public String getExtensionIdentifier() {
        return this.config.getString("extension_identifier"); //$NON-NLS-1$
    }

    /**
     * @return The icon image that represents this extension.
     */
    @Override
    public Image getIconImage() {
        // Return a defensive copy if the image is mutable
        // Since java.awt.Image is usually immutable, this is a precaution
        return this.iconImage;
    }

    /**
     * @return The resource that contains the configuration of the logger.
     */
    @Override
    public InputStream getLoggingPropertiesResource() {
        String resourcePath = this.config.getString("logging_properties_resource"); //$NON-NLS-1$
        if (resourcePath != null) {
            return getInputStreamFromResource(resourcePath);
        }
        return null;
    }

    /**
     * @return The resource in the JAR file to read the language strings from.
     */
    @Override
    public String getLocalizableResource() {
        return this.config.getString("localizable_resource"); //$NON-NLS-1$
    }

    /**
     * @return The suffix of the application event that lets the user change the
     * base directory.
     */
    @Override
    public String getChooseBaseDirSuffix() {
        return this.config.getString("choose_base_dir_suffix"); //$NON-NLS-1$
    }

    /**
     * @return The scheme of the application event that imports a given file.
     */
    @Override
    public String getTransactionFileImportUriScheme() {
        return this.config.getString(
                "transaction_file_import_uri_scheme"); //$NON-NLS-1$
    }

    /**
     * @return The scheme of the application event that imports a given CSV file
     * using the text file importer plugin.
     */
    @Override
    public String getTextFileImportUriScheme() {
        return this.config.getString(
                "text_file_import_uri_scheme"); //$NON-NLS-1$
    }

    /**
     * @return The amount of milliseconds to wait between two runs of the file
     * scanner.
     */
    @Override
    public int getMonitorInterval() {
        return this.config.getInt("monitor_interval");
    }

    /**
     * @return Valid extensions of transaction files that can be imported
     * (case-insensitive).
     */
    @Override
    public List<String> getTransactionFileExtensions() {
        List<String> extensions = this.config.getList(String.class,
                "transaction_file_extensions"); //$NON-NLS-1$
        if (extensions != null) {
            return Collections.unmodifiableList(new ArrayList<>(extensions));
        }
        return Collections.emptyList();
    }

    /**
     * @return Maximum length of a filename displayed in an error message.
     */
    @Override
    public int getMaxFilenameLength() {
        return this.config.getInt("max_filename_length"); //$NON-NLS-1$
    }

    /**
     * @return Unique descriptor of the "name" column.
     */
    @Override
    public String getDescName() {
        return this.config.getString("desc_name"); //$NON-NLS-1$
    }

    /**
     * @return Unique descriptor of the "modified" column.
     */
    @Override
    public String getDescModified() {
        return this.config.getString("desc_modified"); //$NON-NLS-1$
    }

    /**
     * @return Unique descriptor of the "import" column.
     */
    @Override
    public String getDescImport() {
        return this.config.getString("desc_import"); //$NON-NLS-1$
    }

    /**
     * @return Unique descriptor of the "delete" column.
     */
    @Override
    public String getDescDelete() {
        return this.config.getString("desc_delete"); //$NON-NLS-1$
    }

    /**
     * @return Indentation prefix for table header and values.
     */
    @Override
    public String getIndentationPrefix() {
        return this.config.getString("indentation_prefix"); //$NON-NLS-1$
    }

    /**
     * @return Determines if the button columns can have different widths.
     */
    @Override
    public boolean isButtonResizable() {
        return this.config.getBoolean("button_resizable"); //$NON-NLS-1$
    }

    /**
     * @return Minimum width of all columns.
     */
    @Override
    public int getMinColumnWidth() {
        return this.config.getInt("min_column_width"); //$NON-NLS-1$
    }

    /**
     * @return Determines if reordering of the columns is allowed.
     */
    @Override
    public boolean isReorderingAllowed() {
        return this.config.getBoolean("reordering_allowed"); //$NON-NLS-1$
    }

    /**
     * @return Minimum width of the file table.
     */
    @Override
    public int getMinimumTableWidth() {
        return this.config.getInt("minimum_table_width"); //$NON-NLS-1$
    }

    /**
     * @return Minimum height of the file table.
     */
    @Override
    public int getMinimumTableHeight() {
        return this.config.getInt("minimum_table_height"); //$NON-NLS-1$
    }

    /**
     * @return Constant offset to determine the preferred table height.
     */
    @Override
    public int getTableHeightOffset() {
        return this.config.getInt("table_height_offset"); //$NON-NLS-1$
    }

    /**
     * @return The default width of the columns
     */
    @Override
    public int getColumnWidth() {
        return this.config.getInt("column_width"); //$NON-NLS-1$
    }

    /**
     * @return Preferred width of the message box when no displayable file is
     * found.
     */
    @Override
    public int getPreferredEmptyMessageWidth() {
        return this.config.getInt(
                "preferred_empty_message_width"); //$NON-NLS-1$
    }

    /**
     * @return Preferred height of the message box when no displayable file is
     * found.
     */
    @Override
    public int getPreferredEmptyMessageHeight() {
        return this.config.getInt(
                "preferred_empty_message_height"); //$NON-NLS-1$
    }

    /**
     * The filename in the confirmation message is split in several lines. This
     * value defines the maximum length of each line.
     * @return The maximum length of each line.
     */
    @Override
    public int getMessageFilenameLineMaxLength() {
        return this.config.getInt(
                "message_filename_line_max_length"); //$NON-NLS-1$
    }

    /**
     * @return The default foreground color.
     */
    @Override
    public int getColorValueFgDef() {
        return this.config.getInt("color_value_fg_def"); //$NON-NLS-1$
    }

    /**
     * @return The default background color.
     */
    @Override
    public int getColorValueBgDef() {
        return this.config.getInt("color_value_bg_def"); //$NON-NLS-1$
    }

    /**
     * @return Default alternative background color.
     */
    @Override
    public int getColorValueBgAltDef() {
        return this.config.getInt("color_value_bg_alt_def"); //$NON-NLS-1$
    }

    /**
     * @return The height of the header row by multiplying this value with
     * the font size.
     */
    @Override
    public double getFactorRowHeightHeader() {
        return this.config.getDouble("factor_row_height_header"); //$NON-NLS-1$
    }

    /**
     * @return The height of a table row by adding this value to the font size.
     */
    @Override
    public double getSummandRowHeightBody() {
        return this.config.getDouble("summand_row_height_body"); //$NON-NLS-1$
    }

    /**
     * @return Keyboard shortcut to import files.
     */
    @Override
    public String getKeyboardShortcutImport() {
        return this.config.getString("keyboard_shortcut_import"); //$NON-NLS-1$
    }

    /**
     * @return Keyboard shortcut to delete files.
     */
    @Override
    public String getKeyboardShortcutDelete() {
        return this.config.getString("keyboard_shortcut_delete"); //$NON-NLS-1$
    }

    private static InputStream getInputStreamFromResource(final String resource) {
        ClassLoader cloader = Settings.class.getClassLoader();
        InputStream inputStream = cloader.getResourceAsStream(resource);

        // Try various classloaders if the resource wasn't found
        if (inputStream == null) {
            // Try thread context classloader
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);

            // If still not found, try system classloader
            if (inputStream == null) {
                inputStream = ClassLoader.getSystemResourceAsStream(resource);
            }
        }
        Validate.notNull(inputStream, "Resource %s was not found.", resource);
        return inputStream;
    }

    private static Image getImage(final String resource) throws IOException {
        try {
            // First try to load as a file
            java.io.File file = new java.io.File(resource);
            if (file.exists() && file.canRead()) {
                return ImageIO.read(file);
            }
        } catch (Exception e) {
            // Log the exception to help with debugging
            java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Settings.class.getName());
            if (logger.isLoggable(java.util.logging.Level.WARNING)) {
                logger.warning("Error loading image as file: " + e.getMessage());
            }
        }

        // Then try as a resource
        try (InputStream inputStream = getInputStreamFromResource(resource)) {
            return ImageIO.read(inputStream);
        }
    }
}
