DESCRIPTION = "github.com/codegangsta/cli"

GO_IMPORT = "github.com/codegangsta/cli"

inherit go

SRC_URI = "git://github.com/codegangsta/cli;protocol=https;destsuffix=${PN}-${PV}/src/${GO_IMPORT}"
SRCREV = "${AUTOREV}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=ed9b539ed65d73926f30ff1f1587dc44"

FILES_${PN} += "${GOBIN_FINAL}/*"
