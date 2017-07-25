# Provides the DHCOM i.MX6 common settings

require conf/machine/include/imx-base.inc
require conf/machine/include/tune-cortexa9.inc

PREFERRED_PROVIDER_virtual/bootloader = "u-boot-imx6-dhcom"
PREFERRED_PROVIDER_u-boot = "u-boot-imx6-dhcom"
PREFERRED_VERSION_u-boot-imx6-dhcom = "v2015.10"
IMAGE_BOOTLOADER ?= "u-boot"
#do_rootfs[depends] += "bootscript:do_deploy"
do_rootfs[depends] += "splash:do_deploy"
do_rootfs[depends] += "settings-13-fg0700g3dssw:do_deploy"
BOOT_SCRIPTS += "uLinuxEnv.txt 800x480_done.bmp 800x480_error.bmp 800x480_progress.bmp splash.bmp settings.bin"

UBOOT_MAKE_TARGET = "u-boot.imx"
UBOOT_BINARY = "u-boot.imx"

# override boot partition size 50MB and volume-id
BOOT_SPACE = "51200"
BOOTDD_VOLUME_ID ?= "boot"

# Use linux kernel iMX6 dhcom
PREFERRED_PROVIDER_virtual/kernel = "linux-imx6-dhcom"
PREFERRED_VERSION_linux-imx6-dhcom = "4.1.15"
KERNEL_IMAGETYPE = "zImage"

SERIAL_CONSOLE = "115200 ttymxc0"

MACHINE_FIRMWARE_append_mx6 = " linux-firmware-ath6k"
MACHINE_FEATURES += " pci wifi bluetooth"

#MACHINE_EXTRA_RRECOMMENDS_append = " kernel-modules"

SOC_DEFAULT_IMAGE_FSTYPES = "tar.bz2 sdcard"
SDCARD_ROOTFS = "${DEPLOY_DIR_IMAGE}/${IMAGE_NAME}.rootfs.ext3"
