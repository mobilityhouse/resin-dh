DESCRIPTION = "github.com/hashicorp/go-syslog"

GO_IMPORT = "github.com/hashicorp/go-syslog"

inherit go

SRC_URI = "git://github.com/hashicorp/go-syslog;protocol=https;destsuffix=${PN}-${PV}/src/${GO_IMPORT}"
SRCREV = "${AUTOREV}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=cb04212e101fbbd028f325e04ad45778"

FILES_${PN} += "${GOBIN_FINAL}/*"
