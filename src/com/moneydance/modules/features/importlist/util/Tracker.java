/*
 * Import List - http://my-flow.github.com/importlist/
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

package com.moneydance.modules.features.importlist.util;

import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.SocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dmurph.tracking.AnalyticsConfigData;
import com.dmurph.tracking.JGoogleAnalyticsTracker;
import com.dmurph.tracking.JGoogleAnalyticsTracker.GoogleAnalyticsVersion;

/**
 * @author Florian J. Breunig
 */
public final class Tracker {

    private final Preferences prefs;
    private final String fullVersion;
    private final String build;

    private final JGoogleAnalyticsTracker analyticsTracker;
    private static final Logger LOG = LoggerFactory.getLogger(Tracker.class);

    Tracker(final int argBuild) {
        this.prefs          = Helper.INSTANCE.getPreferences();
        this.fullVersion    = "Moneydance " + this.prefs.getFullVersion();
        this.build          = "Import List v" + String.valueOf(argBuild);

        JGoogleAnalyticsTracker.setProxy(this.getProxy());
        Settings settings = Helper.INSTANCE.getSettings();
        AnalyticsConfigData config = new AnalyticsConfigData(
                settings.getTrackingCode());
        this.analyticsTracker = new JGoogleAnalyticsTracker(
                config,
                GoogleAnalyticsVersion.V_4_7_2);
    }

    public void track(final EventName eventName) {
        LOG.debug("trackEvent");
        this.analyticsTracker.trackEvent(
                this.fullVersion,
                eventName.toString(),
                this.build);
    }

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
