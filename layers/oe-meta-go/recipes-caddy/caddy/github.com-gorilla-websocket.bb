DESCRIPTION = "github.com/gorilla/websocket"

GO_IMPORT = "github.com/gorilla/websocket"

inherit go

SRC_URI = "git://github.com/gorilla/websocket;protocol=https;destsuffix=${PN}-${PV}/src/${GO_IMPORT}"
SRCREV = "${AUTOREV}"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=c007b54a1743d596f46b2748d9f8c044"

FILES_${PN} += "${GOBIN_FINAL}/*"
