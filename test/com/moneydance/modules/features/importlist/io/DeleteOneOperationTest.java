/*
 * Import List - http://my-flow.github.io/importlist/
 * Copyright (C) 2011-2013 Florian J. Breunig
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
import java.util.Collections;

import org.junit.Test;

/**
 * @author Florian J. Breunig
 */
public final class DeleteOneOperationTest {

    private final File incomeFile;
    private final File deleteFile;

    public DeleteOneOperationTest() {
        this.incomeFile = new File("mybank.csv");
        this.deleteFile = new File("deleteme.csv");
    }

    @Test
    public void testExecute() {
        try {
            this.deleteFile.createNewFile();
        } catch (IOException e) {
            fail(e.getMessage());
        }

        FileOperation fileOperation = new DeleteOneOperation();
        fileOperation.execute(
                Collections.singletonList(this.deleteFile));

        fileOperation = new DeleteOneOperation();
        fileOperation.execute(
                Collections.singletonList(this.incomeFile));
    }

    @Test
    public void testShowWarningAndExecute() {
        try {
            this.deleteFile.createNewFile();
        } catch (IOException e) {
            fail(e.getMessage());
        }

        FileOperation fileOperation = new DeleteOneOperation();
        fileOperation.showWarningAndExecute(
                Collections.singletonList(this.deleteFile));

        fileOperation = new DeleteOneOperation();
        fileOperation.showWarningAndExecute(
                Collections.singletonList(this.incomeFile));
    }
}
