package org.mozilla.partinfra

class observatoryScan implements Serializable {
    def url
    observatoryScan(String url) {
        this.url = url
    }

    static String scan() {
        return "test"
    }

}
