DESCRIPTION = "github.com/jimstudt/http-authentication"

GO_IMPORT = "github.com/jimstudt/http-authentication"

inherit go

SRC_URI = "git://github.com/jimstudt/http-authentication;protocol=https;destsuffix=${PN}-${PV}/src/${GO_IMPORT}"
SRCREV = "${AUTOREV}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=61e7f1bfbf294711fc438f57de71ccdd"

FILES_${PN} += "${GOBIN_FINAL}/*"
