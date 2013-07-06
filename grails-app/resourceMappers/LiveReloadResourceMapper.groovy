/*
 * LiveReloadResourceMapper
 * Copyright (c) 2012 Cybervision. All rights reserved.
 */

import groovy.util.logging.Slf4j
import io.belov.grails.livereload.LiveReload
import grails.util.Holders
import org.apache.commons.lang.RandomStringUtils
import org.apache.commons.lang.StringUtils
import org.grails.plugin.resource.AggregatedResourceMeta
import org.grails.plugin.resource.ResourceMeta
import org.grails.plugin.resource.mapper.MapperPhase

@Slf4j
class LiveReloadResourceMapper {

    def phase = MapperPhase.NOTIFICATION

    static defaultIncludes = [ '**/*.css', '**/*.js', ]

    def map(ResourceMeta resource, config) {
        try {
            if (Holders.config.grails.resources.debug) {
                //if debug mode enable it makes sence to reload single resource
                if (resource instanceof AggregatedResourceMeta) {
                    def aggregated = (AggregatedResourceMeta) resource
                    aggregated.resources.each { r ->
                        processResource(r)
                    }
                } else {
                    processResource(resource)
                }
            } else {
                //do full reload
                reloadAllResourcesOfType(resource.processedFileExtension)
            }

        } catch (e) {
            log.error("LiveReload: unable to reload resource ${resource}", e)
        }
    }

    private processResource(ResourceMeta meta) {
        LiveReload.instance.fileReloaded(meta.@actualUrl)
    }

    private reloadAllResourcesOfType(String type) {
        if (type) {
            //reload random named file of type ${type} to init full reload
            LiveReload.instance.fileReloaded("${RandomStringUtils.random(12)}.${type}")
        }
    }
}
