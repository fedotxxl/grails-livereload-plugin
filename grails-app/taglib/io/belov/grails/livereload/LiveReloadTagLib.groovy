package io.belov.grails.livereload

/*
 * LiveReloadTabLib
 * Copyright (c) 2012 Cybervision. All rights reserved.
 */
class LiveReloadTagLib {

    static namespace = "livereload"

    def js = {
        out << "<script type='text/javascript' src='${g.resource(file: 'js/livereload.js', plugin: 'resources-livereload', absolute: true)}'></script>"
    }

}
