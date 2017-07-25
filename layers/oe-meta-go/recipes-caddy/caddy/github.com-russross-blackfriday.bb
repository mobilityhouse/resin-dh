DESCRIPTION = "github.com/russross/blackfriday"

GO_IMPORT = "github.com/russross/blackfriday"

inherit go

SRC_URI = "git://github.com/russross/blackfriday;protocol=https;destsuffix=${PN}-${PV}/src/${GO_IMPORT}"
SRCREV = "${AUTOREV}"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE.txt;md5=ecf8a8a60560c35a862a4a545f2db1b3"

FILES_${PN} += "${GOBIN_FINAL}/*"
