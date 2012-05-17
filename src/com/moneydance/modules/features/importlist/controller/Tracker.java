/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011-2012 Florian J. Breunig
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

package com.moneydance.modules.features.importlist.controller;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.SocketAddress;

import com.dmurph.tracking.AnalyticsConfigData;
import com.dmurph.tracking.JGoogleAnalyticsTracker;
import com.dmurph.tracking.JGoogleAnalyticsTracker.GoogleAnalyticsVersion;
import com.moneydance.modules.features.importlist.util.Helper;
import com.moneydance.modules.features.importlist.util.Preferences;

/**
 * @author Florian J. Breunig
 */
final class Tracker {

    private final Preferences prefs;
    private final String fullVersion;
    private final String build;

    private final JGoogleAnalyticsTracker analyticsTracker;

    Tracker(final String argFullVersion,
            final int argBuild,
            final String trackingCode) {
        this.prefs       = Helper.INSTANCE.getPreferences();
        this.fullVersion = argFullVersion;
        this.build       = String.valueOf(argBuild);

        JGoogleAnalyticsTracker.setProxy(this.getProxy());
        AnalyticsConfigData config = new AnalyticsConfigData(trackingCode);
        this.analyticsTracker = new JGoogleAnalyticsTracker(
                config,
                GoogleAnalyticsVersion.V_4_7_2);
    }

    void track(final String eventName) {
        final String trackVersion = "Moneydance " + this.fullVersion;
        final String trackBuild   = "Import List v" + this.build;

        this.analyticsTracker.trackEvent(trackVersion, eventName, trackBuild);
    }

    /**
     * @return The proxy server that the extension use to establish
     * a connection.
     */
    private Proxy getProxy() {
        if (!this.prefs.useProxy()) {
            return Proxy.NO_PROXY;
        }

        final SocketAddress socketAddress = new InetSocketAddress(
                this.prefs.getProxyHost(),
                this.prefs.getProxyPort());

        boolean authProxy = this.prefs.needProxyAuthentication();

        Proxy.Type proxyType = Proxy.Type.HTTP;

        if (authProxy) {
            proxyType = Proxy.Type.SOCKS;
            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                           Tracker.this.prefs.getProxyUsername(),
                           Tracker.this.prefs.getProxyPassword().toCharArray());
                }
            });
        }
        return new Proxy(proxyType, socketAddress);
    }
}
