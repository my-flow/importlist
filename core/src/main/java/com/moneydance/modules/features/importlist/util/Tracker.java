// Import List - http://my-flow.github.io/importlist/
// Copyright (C) 2011-2016 Florian J. Breunig
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

import com.brsanthu.googleanalytics.EventHit;
import com.brsanthu.googleanalytics.GoogleAnalytics;
import com.brsanthu.googleanalytics.GoogleAnalyticsConfig;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

import org.apache.commons.lang3.StringUtils;

/**
 * A facade for dispatching Google Analytics tracking information.
 *
 * @author Florian J. Breunig
 */
public final class Tracker {

    private final String fullVersion;
    private final String build;

    private final GoogleAnalytics analyticsTracker;

    Tracker(final int argBuild,
            final String argExtensionName,
            final String argFullVersion,
            final String argTrackingCode) {
        this.fullVersion = String.format("Moneydance %s", argFullVersion);
        this.build       = String.format("%s v%d", argExtensionName, argBuild);

        Settings settings = Helper.INSTANCE.getSettings();
        boolean enabled = StringUtils.isNotEmpty(settings.getTrackingCode());

        GoogleAnalyticsConfig config = new GoogleAnalyticsConfig();
        config.setEnabled(enabled);

        final Preferences prefs = Helper.INSTANCE.getPreferences();
        if (prefs.hasProxy()) {
            config.setProxyHost(prefs.getProxyHost());
            config.setProxyPort(prefs.getProxyPort());

            if (prefs.hasProxyAuthentication()) {
                config.setProxyUserName(prefs.getProxyUsername());
                config.setProxyPassword(prefs.getProxyPassword());
                Authenticator.setDefault(new Authenticator() {
                    @Override
                    protected PasswordAuthentication
                    getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                prefs.getProxyUsername(),
                                prefs.getProxyPassword().toCharArray());
                    }
                });
            }
        }
        this.analyticsTracker = new GoogleAnalytics(config, argTrackingCode);
    }

    @SuppressWarnings("nullness")
    public void track(final EventName eventName) {
        this.analyticsTracker.postAsync(new EventHit(
                this.fullVersion,
                eventName.toString(),
                this.build,
                null));
    }

    /**
     * @author Florian J. Breunig
     */
    public enum EventName {

        INSTALL(Helper.INSTANCE.getSettings().getEventActionInstall()),

        DISPLAY(Helper.INSTANCE.getSettings().getEventActionDisplay()),

        UNINSTALL(Helper.INSTANCE.getSettings().getEventActionUninstall());

        private final String eventString;

        EventName(final String argEventString) {
            this.eventString = argEventString;
        }

        @Override
        public String toString() {
            return this.eventString;
        }
    }
}
