// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2013 Florian J. Breunig
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

package com.moneydance.modules.features.importlist.util;

import com.dmurph.tracking.AnalyticsConfigData;
import com.dmurph.tracking.JGoogleAnalyticsTracker;
import com.dmurph.tracking.JGoogleAnalyticsTracker.GoogleAnalyticsVersion;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.SocketAddress;

/**
 * A facade for dispatching Google Analytics tracking information.
 *
 * @author Florian J. Breunig
 */
public final class Tracker {

    private final String fullVersion;
    private final String build;

    private final JGoogleAnalyticsTracker analyticsTracker;

    Tracker(final int argBuild,
            final String argExtensionName,
            final String argFullVersion,
            final String argTrackingCode) {
        this.fullVersion = String.format("Moneydance %s", argFullVersion);
        this.build       = String.format("%s v%d", argExtensionName, argBuild);

        if (!Proxy.NO_PROXY.equals(Tracker.getProxy())) {
            JGoogleAnalyticsTracker.setProxy(Tracker.getProxy());
        }
        AnalyticsConfigData config = new AnalyticsConfigData(argTrackingCode);
        this.analyticsTracker = new JGoogleAnalyticsTracker(
                config,
                GoogleAnalyticsVersion.V_4_7_2);
    }

    public void track(final EventName eventName) {
        this.analyticsTracker.trackEvent(
                this.fullVersion,
                eventName.toString(),
                this.build);
    }

    private static Proxy getProxy() {
        final Preferences prefs = Helper.INSTANCE.getPreferences();
        if (!prefs.hasProxy()) {
            return Proxy.NO_PROXY;
        }

        final SocketAddress socketAddress = new InetSocketAddress(
                prefs.getProxyHost(),
                prefs.getProxyPort());

        boolean authProxy = prefs.hasProxyAuthentication();

        Proxy.Type proxyType = Proxy.Type.HTTP;

        if (authProxy) {
            proxyType = Proxy.Type.SOCKS;
            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                            prefs.getProxyUsername(),
                            prefs.getProxyPassword().toCharArray());
                }
            });
        }
        return new Proxy(proxyType, socketAddress);
    }

    /**
     * @author Florian J. Breunig
     */
    public enum EventName {

        INSTALL(Helper.INSTANCE.getSettings().getEventActionInstall()),

        DISPLAY(Helper.INSTANCE.getSettings().getEventActionDisplay()),

        UNINSTALL(Helper.INSTANCE.getSettings().getEventActionUninstall());

        private final String eventString;

        private EventName(final String argEventString) {
            this.eventString = argEventString;
        }

        @Override
        public String toString() {
            return this.eventString;
        }
    }
}
