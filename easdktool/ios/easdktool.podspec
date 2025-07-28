#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html.
# Run `pod lib lint easdktool.podspec` to validate before publishing.
#
Pod::Spec.new do |s|
  s.name             = 'easdktool'
  s.version          = '1.1.07.1'
  s.summary          = 'A new flutter plugin project.'
  s.description      = <<-DESC
A new flutter plugin project.
                       DESC
  s.homepage         = 'http://example.com'
  s.license          = { :file => '../LICENSE' }
  s.author           = { 'Your Company' => 'email@example.com' }
  s.source           = { :path => '.' }
  s.source_files = 'Classes/**/*'
  s.public_header_files = 'Classes/**/*.h'
  s.dependency 'Flutter'
  s.platform = :ios, '12.0'
  
  s.dependency 'ZipArchive'
  s.dependency 'Protobuf'
  s.dependency 'YYKit'
  s.dependency 'BGFMDB'
  s.dependency 'SAMKeychain'
  s.dependency 'SVGKit'
#  s.dependency "Realm"

  s.resource_bundles = {'EAWatchFace' => ['Lib/EAWatchFace.bundle/*']} #工程需要引入的bundle
  #s.ios.vendored_frameworks = 'Lib/EABluetooth.framework'
  #s.vendored_frameworks = 'EABluetooth.framework'
  s.vendored_frameworks = [
    'Lib/EABluetooth.framework',
    'Lib/JLLogHelper/JLLogHelper.framework',
    'Lib/JL_OTALib/JL_OTALib.framework',
    'Lib/JL_HashPair/JL_HashPair.framework',
    'Lib/JL_BLEKit/JL_BLEKit.framework',
    'Lib/JL_AdvParse/JL_AdvParse.framework'
  ]
  
  
  # Flutter.framework does not contain a i386 slice.
  s.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES', 'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'i386' }
end
