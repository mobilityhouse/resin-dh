DESCRIPTION = "golang.org/x/net"

GO_IMPORT = "golang.org/x/net"

inherit go

SRC_URI = "git://github.com/golang/net/;protocol=https;destsuffix=${PN}-${PV}/src/${GO_IMPORT}"
SRCREV = "${AUTOREV}"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=5d4950ecb7b26d2c5e4e7b4e0dd74707"

FILES_${PN} += "${GOBIN_FINAL}/*"

DEPENDS += "golang.org-x-text"
