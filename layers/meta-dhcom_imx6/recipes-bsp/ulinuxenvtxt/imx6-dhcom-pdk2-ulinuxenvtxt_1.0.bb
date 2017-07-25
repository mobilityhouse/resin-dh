LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://${S}COPYING;md5=751419260aa954499f7abaabaa882bbe"
COMPATIBLE_MACHINE = "(dhcom3bimx6d|dhcom3bimx6dl|dhcom3bimx6q|dhcom3bimx6s|dhcom4bimx6d|dhcom4bimx6dl|dhcom4bimx6q|dhcom4bimx6s)"
#DEPENDS = "u-boot-mkimage-native"
DESCRIPTION = "Install an uLinuxEnv.txt in the final .sdcard image"

S = "${WORKDIR}/"
SRC_URI = "file://uLinuxEnv.txt; \
           file://COPYING "

inherit deploy

#do_mkimage () {
#    }

#addtask mkimage after do_compile before do_install

do_deploy () {
    install -d ${DEPLOYDIR}
    rm -f ${DEPLOYDIR}/uLinuxEnv.txt
    install ${S}/uLinuxEnv.txt ${DEPLOYDIR}/uLinuxEnv.txt
}

addtask deploy after do_install before do_build

do_compile[noexec] = "1"
do_install[noexec] = "1"
do_populate_sysroot[noexec] = "1"

PROVIDES += "imx6-dhcom-pdk2-ulinuxenvtxt"

PACKAGE_ARCH = "${MACHINE_ARCH}"
