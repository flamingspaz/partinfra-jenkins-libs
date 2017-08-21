package org.mozilla.partinfra

@Grapes(
    @Grab(group='org.codehaus.groovy.modules.http-builder', module='http-builder', version='0.7.1')
)

import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.*
import static groovyx.net.http.Method.*
import groovy.json.JsonSlurperClassic

class observatoryScan implements Serializable {
    String url
    String observatory_endpoint = "https://http-observatory.security.mozilla.org"
    observatoryScan(String url) {
        this.url = url
    }

    void setEndpoint(String endpoint) {
        observatory_endpoint = endpoint
    }

    private parseJson(text) {
        return new groovy.json.JsonSlurperClassic().parse(text)
    }

    def getScanObject() {
        HTTPBuilder http = new HTTPBuilder(observatory_endpoint)
        def res = http.get( path : "/api/v1/analyze", query : ["host": this.url], contentType: TEXT)
        return parseJson(res)
    }

    def scan() {
        HTTPBuilder http = new HTTPBuilder(observatory_endpoint)
        def res = http.post( path : "/api/v1/analyze", query : ["host": this.url], body: "rescan=true", contentType: TEXT)
        return parseJson(res).scan_id
    }

    def getScanResults(String scan_id) {
        HTTPBuilder http = new HTTPBuilder(observatory_endpoint)
        def res = http.get( path : "/api/v1/getScanResults", query : ["scan": scan_id], contentType: TEXT)
        return parseJson(res)
    }

    def getResultsSummary(HashMap results) {
        String text = ''
        for(String key: results.keySet()){
            for(String value: results.get(key)["pass"]) {
                text += key + ": " + value + "\n"
            }
        }
        return text
    }        
}
