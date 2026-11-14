package com.moneydance.modules.features.importlist;

import com.moneydance.modules.features.importlist.io.FileContainer;

/**
 * Target test component interface that extends TargetComponent
 * to provide additional testing functionality.
 *
 * @author Florian J. Breunig
 */
public interface TargetTestComponent extends TargetComponent {

    @Override
    FileContainer fileContainer();
}
