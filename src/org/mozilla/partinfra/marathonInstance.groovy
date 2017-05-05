package org.mozilla.partinfra


String marathon_url
marathonInstance(String url) {
    this.marathon_url = url
}

marathonInstance(){}

def restartApp(String appid) {
    runScript("curl -s -X POST " + this.url + "/v2/apps/" + appid + "/restart -H \'Content-type: application/json\'")
}


def runScript(String script) {
    sh script: script
}
