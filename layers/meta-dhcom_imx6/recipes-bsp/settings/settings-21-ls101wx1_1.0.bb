LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=751419260aa954499f7abaabaa882bbe"

DEPENDS = "u-boot-mkimage-native"
DESCRIPTION = "Install an u-boot script in the final .sdcard image"

S = "${WORKDIR}/"
SRC_URI = "file://21_LG_10_1inch_LD101WX1.bin \
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
    rm -f ${DEPLOYDIR}/settings.bin
    install ${S}/21_LG_10_1inch_LD101WX1.bin ${DEPLOYDIR}/settings.bin
}

addtask deploy after do_install before do_build

do_compile[noexec] = "1"
do_install[noexec] = "1"
do_populate_sysroot[noexec] = "1"

PROVIDES += "settings-21-ls101wx1"

PACKAGE_ARCH = "${MACHINE_ARCH}"
