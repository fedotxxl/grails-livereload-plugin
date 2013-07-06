package io.belov.grails.livereload

/*
 * LiveReloadTabLib
 * Copyright (c) 2012 Cybervision. All rights reserved.
 */
class LiveReloadTagLib {

    static namespace = "livereload"

    def js = {
        if (LiveReloadConfigHolder.config.enable) {
            def file = "js/livereload.js${(LiveReloadConfigHolder.config.verbose) ? '?LR-verbose=1' : ''}"
            out << "<script type='text/javascript' src='${g.resource(file: file, plugin: 'resources-livereload', absolute: true)}'></script>"
        }
    }

}
