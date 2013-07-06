/*
 * LiveReload
 * Copyright (c) 2012 Cybervision. All rights reserved.
 */
package io.belov.grails.livereload

import groovy.json.JsonOutput
import groovy.util.logging.Slf4j
import org.glassfish.tyrus.server.Server

import javax.websocket.Session

@Slf4j
@Singleton
class LiveReload {

    private List<Session> sessions = []
    private boolean started = false

    public LiveReload() {
        startServer()
    }

    void startServer() {
        if (LiveReloadConfigHolder.config.enable && !started) {
            def server = "localhost"
            def port = 35729
            def root = "livereload"

            try {
                def webSocketServer = new Server(server, port, "/$root", LiveReloadServerConfig);
                webSocketServer.start();
                started = true
            } catch (e) {
                log.error("LiveReload: unable to start WebSocket server ${server}:$port/$root", e)
            }
        }
    }

    Boolean getStarted() {
        return this.@started
    }

    synchronized void addSession(Session session) {
        sessions << session
    }

    synchronized void fileReloaded(String path) {
        log.info "LiveReload: reloading file ${path}"

        removeClosedSessions();

        def response = JsonOutput.toJson([command: 'reload', liveCSS: true, path: path])
        sessions.each { session ->
            try {
                session.basicRemote.sendText(response);
            } catch (e) {
                log.warn "LiveReload: unable to send file ${path} reload response to session ${session.id}"
            }
        }
    }

    synchronized void removeClosedSessions() {
        sessions = sessions.findAll { it.isOpen() }
    }
}
