package io.belov.grails.livereload

/*
 * LiveReloadTabLib
 * Copyright (c) 2012 Cybervision. All rights reserved.
 */
class LiveReloadTagLib {

    static namespace = "livereload"

    def js = {
        if (LiveReloadConfigHolder.enabled) {
            def file = "livereload.js${(LiveReloadConfigHolder.config.verbose) ? '?LR-verbose=1' : ''}"
            out << "<script type='text/javascript' src='${g.resource(file: file, dir: 'js', plugin: 'livereload', absolute: true)}'></script>"
        }
    }

}
