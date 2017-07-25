LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

#DEPENDS = "u-boot-mkimage-native"
DESCRIPTION = "Install an splash image in the final .sdcard image"

S = "${WORKDIR}/"

SRC_URI = "file://800x480_done.bmp \
           file://800x480_error.bmp \
           file://800x480_progress.bmp \
           file://800x480_splash_DHCOM_iMX6.bmp \
           file://COPYING "



inherit deploy

#do_mkimage () {
#    uboot-mkimage  -A arm -O linux -T script -C none -a 0 -e 0 \
#                   -n "boot script" -d bootscript.source \
#                   bootscript
#}

#addtask mkimage after do_compile before do_install

do_deploy () {
    install -d ${DEPLOYDIR}
    rm -f ${DEPLOYDIR}/800x480_done.bmp
    rm -f ${DEPLOYDIR}/800x480_error.bmp
    rm -f ${DEPLOYDIR}/800x480_progress.bmp
    rm -f ${DEPLOYDIR}/splash.bmp
    install ${S}/800x480_done.bmp ${DEPLOYDIR}/800x480_done.bmp
    install ${S}/800x480_error.bmp ${DEPLOYDIR}/800x480_error.bmp
    install ${S}/800x480_progress.bmp ${DEPLOYDIR}/800x480_progress.bmp
    install ${S}/800x480_splash_DHCOM_iMX6.bmp ${DEPLOYDIR}/splash.bmp
}

addtask deploy after do_install before do_build

do_compile[noexec] = "1"
do_install[noexec] = "1"
do_populate_sysroot[noexec] = "1"

PROVIDES += "splash"

PACKAGE_ARCH = "${MACHINE_ARCH}"
