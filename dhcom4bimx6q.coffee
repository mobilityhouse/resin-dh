deviceTypesCommon = require '@resin.io/device-types/common'
{ networkOptions, commonImg, instructions } = deviceTypesCommon

module.exports =
  arch: 'armv7hf'
  state: 'experimental'
  yocto:
    machine: 'dhcom4bimx6q'
    image: 'resin-image'
    fstype: 'resinos-img'
    version: 'yocto-krogoth'
    deployArtifact: 'resin-image-dhcom4bimx6q.resinos-img'
    compressed: true

  options: [ networkOptions.group ]

  configuration:
      config:
            partition:
	            primary: 1
		          path: '/config.json'

  initialization: commonImg.initialization