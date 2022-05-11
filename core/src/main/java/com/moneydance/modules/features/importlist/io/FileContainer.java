package com.moneydance.modules.features.importlist.io;

import java.io.File;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * A business object that decorates a list of <code>File</code>s.
 *
 * @author Florian J. Breunig
 */
public final class FileContainer extends AbstractList<File> {

    private final List<File> files;
    private final List<Long> lastModifiedTimes;
    private final IOFileFilter fileFilter;

    @Inject
    FileContainer(@Named("readable files") final IOFileFilter argFileFilter) {
        this(
            new ArrayList<>(),
            new ArrayList<>(),
            argFileFilter);
    }

    public FileContainer(
            final List<File> argFiles,
            final List<Long> argLastModifiedTimes) {
        this(
            argFiles,
            argLastModifiedTimes,
            TrueFileFilter.TRUE);
    }

    public FileContainer(
            final List<File> argFiles,
            final List<Long> argLastModifiedTimes,
            final IOFileFilter argFileFilter) {
        super();
        this.files = Collections.synchronizedList(Validate.notNull(argFiles));
        this.lastModifiedTimes = Collections.synchronizedList(Validate.notNull(argLastModifiedTimes));
        this.fileFilter = Validate.notNull(argFileFilter);
    }

    void reloadFiles(final File baseDirectory) {
        synchronized (FileContainer.class) {
            this.files.clear();
            this.lastModifiedTimes.clear();
            final Collection<File> fileCollection = FileUtils.listFiles(
                    baseDirectory,
                    this.fileFilter,
                    null); // ignore subdirectories
            final Collection<Long> lastModifiedTimeCollection = StreamSupport.stream(fileCollection).
                    map(File::lastModified).
                    collect(Collectors.toList());
            this.lastModifiedTimes.addAll(lastModifiedTimeCollection);
            this.files.addAll(fileCollection);
        }
    }

    @Override
    public File get(final int index) {
        return this.files.get(index);
    }

    @Override
    public int size() {
        return this.files.size();
    }

    @Override
    public boolean isEmpty() {
        return this.files.isEmpty();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31).
                append(this.files.hashCode()).
                append(this.lastModifiedTimes.hashCode()).
                toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof FileContainer)) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        final FileContainer rhs = (FileContainer) obj;
        return new EqualsBuilder().
                append(this.hashCode(), rhs.hashCode()).
                isEquals();
    }

    public String getFileName(final int index) {
        final File file = this.files.get(index);
        return file.getName();
    }

    public Long getLastModifiedTime(final int index) {
        return this.lastModifiedTimes.get(index);
    }
}
