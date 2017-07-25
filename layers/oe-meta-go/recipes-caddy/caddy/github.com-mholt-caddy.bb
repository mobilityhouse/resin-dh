DESCRIPTION = "github.com/mholt/caddy"

GO_IMPORT = "github.com/mholt/caddy"

inherit go

SRC_URI = "git://github.com/mholt/caddy;protocol=https;destsuffix=${PN}-${PV}/src/${GO_IMPORT}"
SRCREV = "${AUTOREV}"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://src/${GO_IMPORT}/LICENSE.txt;md5=e3fc50a88d0a364313df4b21ef20c29e"

FILES_${PN} += "${GOBIN_FINAL}/*"

DEPENDS += "\
	github.com-BurntSushi-toml \
	github.com-dustin-go-humanize \
	github.com-flynn-go-shlex \
	github.com-gorilla-websocket \
	github.com-hashicorp-go-syslog \
	github.com-jimstudt-http-authentication \
	github.com-russross-blackfriday \
	github.com-shurcooL-sanitized-anchor-name \
	github.com-square-go-jose \
	github.com-xenolf-lego \
	golang.org-x-crypto \
	golang.org-x-net \
	gopkg.in-natefinch-lumberjack.v2 \
	gopkg.in-yaml.v2 \
"
