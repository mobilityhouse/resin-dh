# Copyright (C) 2015, 2016 O.S. Systems Software LTDA.
# Released under the MIT license (see COPYING.MIT for the terms)

SUMMARY = "FSL Community BSP i.MX Linux kernel with backported features and fixes"
DESCRIPTION = "Linux kernel based on NXP 4.1.15-1.2.0 GA release, used by FSL Community BSP in order to \
provide support for i.MX based platforms and include official Linux kernel stable updates, backported \
features and fixes coming from the vendors, kernel community or FSL Community itself."

include linux-fslc.inc

PV .= "+git${SRCPV}"

SRCBRANCH = "4.1-1.0.x-imx"
SRCREV = "2fa70a90615bbe3d56edc24b8950af0b801d7ad7"

COMPATIBLE_MACHINE = "(mx6|mx7)"
