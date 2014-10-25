(function (window) {
    console.log('Alba PrimeFaces override called');
    if (window.PrimeFaces) {
        /**
         * Builds a resource URL for given parameters.
         * Overriden as per https://www.liferay.com/community/forums/-/message_boards/message/43787391
         *
         * @param {string} name The name of the resource. For example: primefaces.js
         * @param {string} library The library of the resource. For example: primefaces
         * @param {string} version The version of the library. For example: 5.1
         * @returns {string} The resource URL.
         */
        window.PrimeFaces.getFacesResource = function (name, library, version) {
            var scriptURI = $('script[src*="javax.faces.resource=' + PrimeFaces.getCoreScriptName() + '"]').attr('src');

            scriptURI = scriptURI.replace(PrimeFaces.getCoreScriptName(), name);
            scriptURI = scriptURI.replace('ln=primefaces', 'ln=' + library);

            if (version) {
                var extractedVersion = new RegExp('[?&]v=([^&]*)').exec(scriptURI)[1];
                scriptURI = scriptURI.replace('v=' + extractedVersion, 'v=' + version);
            }
            var prefix = window.location.protocol + '//' + window.location.host;
            return scriptURI.indexOf(prefix) >= 0 ? scriptURI : prefix + scriptURI;
        };
    } else {
        console.error('Alba PrimeFaces override failed -- no window.PrimeFaces exists');
    }
})
(window);