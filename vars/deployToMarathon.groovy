def call(String marathon_id) {
    marathon = new org.mozilla.partinfra.marathonInstance()
    marathon.restartApp(marathon_id)
}
