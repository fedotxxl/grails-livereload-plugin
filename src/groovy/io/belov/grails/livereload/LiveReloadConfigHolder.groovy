/*
 * LiveReloadConfigHolder
 * Copyright (c) 2012 Cybervision. All rights reserved.
 */
package io.belov.grails.livereload

import grails.util.Holders

class LiveReloadConfigHolder {
    private static ConfigObject c

    static ConfigObject getConfig() {
        if (!c) {
            c = Holders.config.plugin.resourcesLivereload
        }

        return c
    }
}
