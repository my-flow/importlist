/*
 * Import List - http://my-flow.github.com/importlist/
 * Copyright (C) 2011 Florian J. Breunig
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

import java.net.Proxy;

import com.dmurph.tracking.AnalyticsConfigData;
import com.dmurph.tracking.JGoogleAnalyticsTracker;
import com.dmurph.tracking.JGoogleAnalyticsTracker.GoogleAnalyticsVersion;

final class Tracker {

    private final String fullVersion;
    private final String build;

    private final JGoogleAnalyticsTracker tracker;

    Tracker(final String argFullVersion,
            final int argBuild,
            final Proxy proxy,
            final String trackingCode) {
        this.fullVersion = argFullVersion;
        this.build       = String.valueOf(argBuild);

        JGoogleAnalyticsTracker.setProxy(proxy);
        AnalyticsConfigData config = new AnalyticsConfigData(trackingCode);
        this.tracker = new JGoogleAnalyticsTracker(
                config,
                GoogleAnalyticsVersion.V_4_7_2);
    }


    void track(final String eventName) {
        final String trackVersion = "Moneydance " + this.fullVersion;
        final String trackBuild   = "Import List v" + this.build;

        this.tracker.trackEvent(trackVersion, eventName, trackBuild);
    }
}
