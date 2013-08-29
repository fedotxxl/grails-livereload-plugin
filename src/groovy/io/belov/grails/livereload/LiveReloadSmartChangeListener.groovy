/*
 * LiveReloadSmartChangeListener
 * Copyright (c) 2012 Cybervision. All rights reserved.
 */
package io.belov.grails.livereload
import grails.util.Holders
import groovy.util.logging.Slf4j
import org.apache.commons.io.FilenameUtils
import org.codehaus.groovy.grails.web.mapping.LinkGenerator

import java.util.concurrent.*

@Singleton
@Slf4j
/**
 * This class tries to fix strange Grails processing of static resources in dev mode
 * Check this ticket for more info - http://jira.grails.org/browse/GPTOMCAT-7
 */
class LiveReloadSmartChangeListener {

    private ExecutorService executorService = Executors.newCachedThreadPool()
    private ConcurrentMap<String, Future> tasksByFiles = new ConcurrentHashMap()
    private ConcurrentHashMap<String, String> urlsByContent = new ConcurrentHashMap()
    private LinkGenerator linkGenerator = null
    private static final CHECK_DELAY = 500
    private static final CHECK_TIME_LIMIT = 5000

    /**
     * Loop 5 seconds and detect that files was changed by sending GET requests to server
     * @param path to file that was locally changed
     */
    void fileReloaded(String path) {
        path = FilenameUtils.normalize(path, true)
        tasksByFiles[path]?.cancel(false)
        tasksByFiles[path] = executorService.submit({
            try {
                doFileReload(path)
            } catch (e) {
                log.error("Livereload: exception on reloading file ${path}", e)
            }
        })
    }

    private doFileReload(String path) {
        def relativePath = getRelativeFilePath(path)
        def url = (relativePath) ? getLinkGenerator().resource(file: relativePath, absolute: true) : null

        def doLoop = true
        def triggerClient = false
        def lastRun = false
        def started = System.currentTimeMillis()

        while (doLoop) {
            def oldContent = (url) ? urlsByContent[url] : null
            def newContent = (url) ? getUrlContent(url) : null

            if (url == null || !urlsByContent.containsKey(url)) {
                triggerClient = true
            } else if (oldContent != newContent) {
                triggerClient = true
                doLoop = false
            }

            if (triggerClient) {
                LiveReload.instance.fileReloaded(path)
                if (url) urlsByContent[url] = newContent
                triggerClient = false
            }

            if (lastRun) {
                doLoop = false
            } else if ((System.currentTimeMillis() - started) > CHECK_TIME_LIMIT) {
                lastRun = true
            }

            if (doLoop) {
                sleep(CHECK_DELAY)
            }
        }
    }

    private synchronized getLinkGenerator() {
        if (!linkGenerator) {
            linkGenerator = Holders.applicationContext.getBean("grailsLinkGenerator")
        }

        return linkGenerator
    }

    private getRelativeFilePath(String path) {
        def position = path.toLowerCase().indexOf("/web-app/")
        if (position == -1) {
            log.warn("Livereload: unsupported file path: $path")
            return null
        } else {
            return path.substring(position + "/web-app/".length() - 1)
        }
    }

    private getUrlContent(String url) {
        try {
            return new URL(url).text
        } catch (e) {
            log.info("Livereload: unable to get content by URL ${url}")
            return null
        }
    }
}
