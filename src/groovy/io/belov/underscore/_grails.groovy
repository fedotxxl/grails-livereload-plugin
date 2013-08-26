/*
 * _grails
 * Copyright (c) 2012 Cybervision. All rights reserved.
 */
package io.belov.underscore

import grails.util.Holders
import org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib

class _grails {

    private static grailsApplication
    private static g

    public static getBean(String beanName) {
        return getGrailsApplication().mainContext.getBean(beanName);
    }

    public static getBean(Class clazz) {
        return getGrailsApplication().mainContext.getBean(clazz);
    }

    static getGrailsApplication() {
        //todo implement synchronized signleton
        if (!this.@grailsApplication) {
            grailsApplication = Holders.grailsApplication
        }

        return this.@grailsApplication
    }

    static ApplicationTagLib getG() {
        //todo implement synchronized signleton
        if (!this.@g) {
            g = getBean('org.codehaus.groovy.grails.plugins.web.taglib.ApplicationTagLib')
        }

        return this.@g
    }

}
