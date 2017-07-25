# resin-dh

Build this repository

Run the build script: ./resin-yocto-scripts/build/barys

You can also run barys with the -h switch to inspect the available options

Custom build using this repository

Run the build script in dry run mode to setup an empty build directory ./resin-yocto-scripts/build/barys --remove-build --dry-run

Edit the local.conf in the build/conf directory

Prepare build's shell environment source layers/poky/oe-init-build-env

Run bitbake (see message outputted when you sourced above for examples)
