package com.infinitekind.moneydance.model;

import com.moneydance.apps.md.model.Account;

/**
 * @author Florian J. Breunig
 */
public final class AccountHelper {

    /**
     * Restrictive constructor.
     */
    private AccountHelper() {
        // Prevents this class from being instantiated from the outside.
    }

    public static void addSubAccount(final Account parent, final Account subaccount) {
        parent.addSubAccount(subaccount);
    }
}
