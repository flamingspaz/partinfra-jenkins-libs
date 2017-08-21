import org.mozilla.partinfra.observatoryScan

def call(String url) {
    obs = new observatoryScan(url)
    initialResult = obs.getScanObject()
    println url + " has score " + initialResult.score + " as of " + initialResult.end_time
    newScan = null
    while (newScan == null) {
        newScan = obs.scan()
        sleep 10
    }
    println "Started scan " + newScan
    sleep 10
    newResult = obs.getScanObject()
    println "New score of " + newResult.score + " as of " + newResult.end_time
    println "Results summary for " + url + ":"
    scanDetails = obs.getScanResults(newScan.toString())
    println obs.getResultsSummary(scanDetails)
}
