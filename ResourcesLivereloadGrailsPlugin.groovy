import io.belov.grails.livereload.LiveReload
import io.belov.grails.livereload.LiveReloadConfigHolder

class ResourcesLivereloadGrailsPlugin {
    // the plugin version
    def version = "0.1.9"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.0 > *"
    // the other plugins this plugin depends on
    def dependsOn = [resources: '1.2.RC2 > *']
    def loadAfter = ['resources']

    //http://grails.1312388.n4.nabble.com/How-can-I-get-build-and-run-app-dependencies-but-excluded-from-war-td4645847.html
    def scopes = [excludes: 'war']

    // TODO Fill in these fields
    def title = "Grails Plugin Resources Livereload Plugin" // Headline display name of the plugin
    def author = "Your name"
    def authorEmail = ""
    def description = '''\
Brief summary/description of the plugin.
'''
    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/grails-plugin-resources-livereload"

    def doWithConfigOptions = {
        'enable'(type: Boolean, defaultValue: true)
        'verbose'(type: Boolean, defaultValue: true)
        'reload'(type: String, defaultValue: 'byName') //or all
    }

    def onChange = { event ->
        // TODO Implement code that is executed when any artefact that this plugin is
        // watching is modified and reloaded. The event contains: event.source,
        // event.application, event.manager, event.ctx, and event.plugin.
    }

    def onConfigChange = { event ->
        //forget old config values
        LiveReloadConfigHolder.reset()

        //start server (enable from false to true)
        if (LiveReloadConfigHolder.config.enable) {
            LiveReload.instance.startServer()
        }
    }

}
