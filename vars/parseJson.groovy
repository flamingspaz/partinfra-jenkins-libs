import groovy.json.JsonSlurperClassic

@NonCPS
def call(String text) {
    return new groovy.json.JsonSlurperClassic().parseText(text)
}
