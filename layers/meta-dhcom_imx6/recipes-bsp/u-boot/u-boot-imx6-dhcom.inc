# Copyright (C) 2012-2014 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

require recipes-bsp/u-boot/u-boot.inc

inherit fsl-u-boot-localversion

DESCRIPTION = "U-Boot based on mainline U-Boot used by DH electronics GmbH to support i.MX6 DHCOMs"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://Licenses/README;md5=0507cd7da8e7ad6d6701926ec9b84c95"
COMPATIBLE_MACHINE = "(mxs|mx3|mx5|mx6|vf60)"

DEPENDS_mxs += "elftosb-native openssl-native"

PROVIDES += "u-boot"

SRCBRANCH ??= "dev/2015.10_dhcom"

SRC_URI = "git://github.com/dh-electronics/u-boot-imx6qdl.git;protocol=https;branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

# FIXME: Allow linking of 'tools' binaries with native libraries
#        used for generating the boot logo and other tools used
#        during the build process.
EXTRA_OEMAKE += 'HOSTCC="${BUILD_CC} ${BUILD_CPPFLAGS}" \
                 HOSTLDFLAGS="${BUILD_LDFLAGS}" \
                 HOSTSTRIP=true'

PACKAGE_ARCH = "${MACHINE_ARCH}"
