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
            c = Holders.config.plugin.livereload
        }

        return c
    }

    static boolean isEnabled() {
        //this method should be fast
        def localConfig = (c) ?: config
        return localConfig.css || localConfig.js
    }

    static void reset() {
        c = null
    }
}
