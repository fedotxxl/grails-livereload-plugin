/*
 * LiveReloadEndpoint
 * Copyright (c) 2012 Cybervision. All rights reserved.
 */
package io.belov.grails.livereload

import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j

import javax.websocket.OnMessage
import javax.websocket.OnOpen
import javax.websocket.Session
import javax.websocket.server.ServerEndpoint

@Slf4j
@ServerEndpoint("/reload")
class LiveReloadEndpoint {

    @OnOpen
    public void onOpen(Session session) throws IOException {
        log.debug "LiveReload: connection: ${session}"

        LiveReload.instance.addSession(session)
    }

    @OnMessage
    public String reverse(String message) {
        log.debug "LiveReload: message: ${message}"

        def request = new JsonSlurper().parseText(message)
        if (request.command == 'hello') {
            def answer = [
                    command: 'hello',
                    protocols: ['http://livereload.com/protocols/official-7'],
                    serverName: 'grails-live-reload'
            ]

            return JsonOutput.toJson(answer)
        } else {
            return null
        }

    }

}
