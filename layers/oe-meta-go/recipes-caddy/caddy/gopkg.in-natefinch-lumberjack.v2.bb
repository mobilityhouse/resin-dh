DESCRIPTION = "gopkg.in/natefinch/lumberjack.v2"

GO_IMPORT = "gopkg.in/natefinch/lumberjack.v2"

inherit go

SRC_URI = "git://gopkg.in/natefinch/lumberjack.v2;protocol=https;destsuffix=${PN}-${PV}/src/${GO_IMPORT}"
SRCREV = "${AUTOREV}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=574cdb55b81249478f5af5f789e9e29f"

FILES_${PN} += "${GOBIN_FINAL}/*"
