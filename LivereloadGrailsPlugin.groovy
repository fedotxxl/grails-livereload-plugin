import io.belov.grails.livereload.LiveReload
import io.belov.grails.livereload.LiveReloadConfigHolder
import org.apache.commons.io.FilenameUtils
import org.springframework.core.io.FileSystemResource

class LivereloadGrailsPlugin {
    // the plugin version
    def version = "0.2.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.0 > *"
    // the other plugins this plugin depends on
    def loadBefore = ['resources']

    def watchedResources = [
            "file:./web-app/**/*.js",
            "file:./web-app/**/*.css"
    ]

    //http://grails.1312388.n4.nabble.com/How-can-I-get-build-and-run-app-dependencies-but-excluded-from-war-td4645847.html
    def scopes = [excludes: 'war']

    def title = "Grails Livereload Plugin" // Headline display name of the plugin
    def author = "Fedor Belov"
    def authorEmail = "fedor.belov@mail.ru"
    def documentation = "http://grails.org/plugin/livereload"
    def description = '''\
This plugin automatically reloads you css/js files by using livereload-js
'''

    def doWithConfigOptions = {
        'css'(type: Boolean, defaultValue: false)
        'js'(type: Boolean, defaultValue: false)
        'verbose'(type: Boolean, defaultValue: true)
    }

    def doWithSpring = { ->
        LiveReload.instance.startServer()
    }

    def onChange = { event ->
        if (LiveReloadConfigHolder.enabled && event.source instanceof FileSystemResource) {
            File file = event.source.file
            def path = file.canonicalPath
            def extension = FilenameUtils.getExtension(file.name).toLowerCase()
            def doReload = false

            if (extension == 'js' && LiveReloadConfigHolder.config.js) {
                doReload = true
            } else if (extension == 'css' && LiveReloadConfigHolder.config.css) {
                doReload = true
            }

            if (doReload) {
                LiveReload.instance.fileReloaded(path)
            }
        }
    }

    def onConfigChange = { event ->
        //forget old config values
        LiveReloadConfigHolder.reset()

        //start server (enable from false to true)
        if (LiveReloadConfigHolder.enabled) {
            LiveReload.instance.startServer()
        }
    }

}
