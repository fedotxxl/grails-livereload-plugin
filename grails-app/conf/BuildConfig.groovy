grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"
grails.project.target.level = 1.6

grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn"
    repositories {
        grailsCentral()
        mavenCentral()
    }

    dependencies {
        //websocket
        ["tyrus-server", "tyrus-client", "tyrus-container-grizzly"].each {
            compile "org.glassfish.tyrus:$it:1.1"
        }
    }

    plugins {
        build(":tomcat:$grailsVersion", ":release:2.0.3", ":rest-client-builder:1.0.2",) {
            export = false
        }

        compile ":platform-core:1.0.RC5"
    }
}
