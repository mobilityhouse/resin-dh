DESCRIPTION = "github.com/xenolf/lego"

GO_IMPORT = "github.com/xenolf/lego"

inherit go

SRC_URI = "git://github.com/xenolf/lego;protocol=https;destsuffix=${PN}-${PV}/src/${GO_IMPORT}"
SRCREV = "${AUTOREV}"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=bc6cc6b9955a86b318178c38ac271bba"

FILES_${PN} += "${GOBIN_FINAL}/*"
