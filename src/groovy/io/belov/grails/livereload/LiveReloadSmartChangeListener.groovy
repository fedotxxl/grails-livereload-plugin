/*
 * LiveReloadSmartChangeListener
 * Copyright (c) 2012 Cybervision. All rights reserved.
 */
package io.belov.grails.livereload
import groovy.util.logging.Slf4j
import io.belov.underscore._grails
import org.apache.commons.io.FilenameUtils

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

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
            def dir = path.substring(path.indexOf("/web-app/"))
            def file = FilenameUtils.getName(path)
            def url = _grails.g.resource(dir: dir, file: file, absolute: true)

            def doLoop = true
            def triggerClient = false
            def lastRun = false
            def started = System.currentTimeMillis()

            while (doLoop) {
                def oldContent = urlsByContent[url]
                def newContent = new URL(url).text

                if (!urlsByContent.containsKey(url)) {
                    triggerClient = true
                } else if (oldContent != newContent) {
                    triggerClient = true
                    doLoop = false
                }

                if (triggerClient) {
                    LiveReload.instance.fileReloaded(path)
                    urlsByContent[url] = newContent
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
        })
    }
}
