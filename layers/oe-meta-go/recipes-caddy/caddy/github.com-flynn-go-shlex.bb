DESCRIPTION = "github.com/flynn/go-shlex"

GO_IMPORT = "github.com/flynn/go-shlex"

inherit go

SRC_URI = "git://github.com/flynn/go-shlex;protocol=https;destsuffix=${PN}-${PV}/src/${GO_IMPORT}"
SRCREV = "${AUTOREV}"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/COPYING;md5=3b83ef96387f14655fc854ddc3c6bd57"

FILES_${PN} += "${GOBIN_FINAL}/*"
