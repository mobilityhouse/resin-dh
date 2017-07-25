LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = "u-boot-mkimage-native"
DESCRIPTION = "Install an u-boot script in the final .sdcard image"

S = "${WORKDIR}/"

SRC_URI = "file://DHupdate.ini \
           file://COPYING "

inherit deploy

#do_mkimage () {
#}
#addtask mkimage after do_compile before do_install

do_deploy () {
    install -d ${DEPLOYDIR}
    rm -f ${DEPLOYDIR}/DHupdate.ini
    install ${S}/DHupdate.ini ${DEPLOYDIR}/DHupdate.ini
}

addtask deploy after do_install before do_build

do_compile[noexec] = "1"
do_install[noexec] = "1"
do_populate_sysroot[noexec] = "1"

PROVIDES += "DHupdateini"

PACKAGE_ARCH = "${MACHINE_ARCH}"
