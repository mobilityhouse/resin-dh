DESCRIPTION = "github.com/BurntSushi/toml"

GO_IMPORT = "github.com/BurntSushi/toml"

inherit go

SRC_URI = "git://github.com/BurntSushi/toml;protocol=https;destsuffix=${PN}-${PV}/src/${GO_IMPORT}"
SRCREV = "${AUTOREV}"
LICENSE = "DWTFYW"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/COPYING;md5=389a9e29629d1f05e115f8f05c283df5"

FILES_${PN} += "${GOBIN_FINAL}/*"
