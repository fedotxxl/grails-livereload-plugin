package io.belov.grails.livereload

/*
 * LiveReloadTabLib
 * Copyright (c) 2012 Cybervision. All rights reserved.
 */
class LiveReloadTagLib {

    static namespace = "livereload"

    def js = {
        if (LiveReloadConfigHolder.enabled) {
            def params = []
            if (LiveReloadConfigHolder.config.verbose) params << 'LR-verbose=1'
            if (LiveReloadConfigHolder.config.flashOnCssReload) params << 'LR-flashOnCssReload=1'

            def file = "livereload.js${(params.size()) ? '?' + params.join('&') : ''}"
            out << "<script type='text/javascript' src='${g.resource(file: file, dir: 'js', plugin: 'livereload', absolute: true)}'></script>"
        }
    }

}
