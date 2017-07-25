DESCRIPTION = "gopkg.in/yaml.v2"

GO_IMPORT = "gopkg.in/yaml.v2"

inherit go

SRC_URI = "git://gopkg.in/yaml.v2;protocol=https;destsuffix=${PN}-${PV}/src/${GO_IMPORT}"
SRCREV = "${AUTOREV}"
LICENSE = "LGPL-3"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE;md5=3493bb7e185df64f59d28a975a1f91a7"

FILES_${PN} += "${GOBIN_FINAL}/*"
