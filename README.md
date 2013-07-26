Description
-----------
This plugin integrates [livereload-js](https://github.com/livereload/livereload-js) with Grails:
1. It starts WebSocket server when you start your application
2. It sends message to the client when you change `css` or `js` file
3. [livereload-js](https://github.com/livereload/livereload-js) updates css files (without page refresh) or refreshes pages to reload js file

>livereload-js uses WebSocket to communicate with server. So this plugin doesn't work with browsers that [doesn't support WebSocket](http://caniuse.com/#feat=websockets)

Installation
-----------
Plugin installation requires 3 steps to be done:
1. Add plugin to your `BuildConfig.groovy`: `compile ":livereload:0.2"`
2. Configure plugin (check section "Configuration")
3. Add js file to you layout (check section "Adding livereload.js")

Configuration
-----------
By default plugin is disabled. To enable `css` reloading add `plugin.livereload.css = true` in your `Config.groovy`.
To enable `js` reloading add `plugin.livereload.js = true`. To enable `css` and `js` reloading you can use following syntax:
```groovy
plugin {
    livereload {
        css = true
        js = true
    }
}
```

Adding livereload.js
-----------
Add `<livereload:js/>` right before `</body>`. `js` method of `livereload` TagLib will add `livereload.js` file into response HTML only when livereload is enabled.
If livereload is disabled (`plugin.livereload.css == false && plugin.livereload.js == false`) it does nothing.

If you have any problems
-------------------------
If you have any problems or suggestions pls feel free [to ask me](https://github.com/fedotxxl/grails-livereload-plugin/issues) - I'll be glad to answer you.
