DESCRIPTION = "github.com/square/go-jose"

GO_IMPORT = "github.com/square/go-jose"

inherit go

SRC_URI = "git://github.com/square/go-jose;protocol=https;destsuffix=${PN}-${PV}/src/${GO_IMPORT}"
SRCREV = "${AUTOREV}"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=3b83ef96387f14655fc854ddc3c6bd57"

FILES_${PN} += "${GOBIN_FINAL}/*"

DEPENDS += "github.com-codegangsta-cli"
