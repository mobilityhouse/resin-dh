LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"
DESCRIPTION = "Script to resize the last partition to fill the disk"

RDEPENDS_${PN} += " e2fsprogs parted update-rc.d util-linux"

SRC_URI = "file://dhcom_resize_partition \
           file://COPYING "

S = "${WORKDIR}/"

do_install () {
    install -d ${D}${bindir}
    install -m 0755 dhcom_resize_partition ${D}${bindir}
}

PACKAGE_ARCH = "${MACHINE_ARCH}"
