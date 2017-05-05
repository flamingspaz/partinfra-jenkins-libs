def call(String message, ArrayList channels = ["#communityops", "#partinfra"],
         String server = 'irc.mozilla.org',
         String user = "morpho") {

    String messages = ""

    for (String it : channels) {
        messages += "echo \"JOIN ${it}\" &&
                     echo \"PRIVMSG ${it} :${message}\" &&
                     sleep 1; "
    }

    sh """
    (
    echo NICK ${user}
    echo USER ${user} 8 * : ${user}
    sleep 5
    """ + messages +
    """
    echo QUIT
    ) | nc ${server} 6667
    """
}
