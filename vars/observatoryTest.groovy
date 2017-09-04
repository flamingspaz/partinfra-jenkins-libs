import org.mozilla.partinfra.observatoryScan

def call(String url) {
    obs = new observatoryScan(url)
    initialResult = obs.getScanObject()
    println url + " has score " + initialResult.score + " as of " + initialResult.end_time
    newScan = null
    while (newScan == null) {
        newScan = obs.scan()
        sleep 30
    }
    println "Started scan " + newScan
    sleep 20
    newResult = obs.getScanObject()
    println "New score of " + newResult.score + " as of " + newResult.end_time
    println "Results summary for " + url + ":"
    scanDetails = obs.getScanResults(newScan.toString())
    println obs.getResultsSummary(scanDetails)
    if (initialResult.score != null && newResult.score < initialResult.score) {
        try {
            slackSend color: 'bad', message: "http-obs regression for ${url}: Was ${initialResult.score} (${initialResult.grade}), now ${newResult.score} (${newResult.grade})  | <https://observatory.mozilla.org/analyze.html?host=${url} | View>"
        }
        catch (e) {
            println "Slack plugin is not installed, not sending notification"
        }
    }
}
