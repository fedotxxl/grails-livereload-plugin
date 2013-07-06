/*
 * LiveReloadServerConfig
 * Copyright (c) 2012 Cybervision. All rights reserved.
 */
package io.belov.grails.livereload

import javax.websocket.Endpoint
import javax.websocket.server.ServerApplicationConfig
import javax.websocket.server.ServerEndpointConfig

class LiveReloadServerConfig implements ServerApplicationConfig {
    @Override
    Set<ServerEndpointConfig> getEndpointConfigs(Set<Class<? extends Endpoint>> classes) {
        return null;
    }

    @Override
    Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> classes) {
        return [LiveReloadEndpoint]
    }
}
