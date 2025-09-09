#
# To learn more about a Podspec see http://guides.cocoapods.org/syntax/podspec.html.
# Run `pod lib lint easdktool.podspec` to validate before publishing.
#
Pod::Spec.new do |s|
  s.name             = 'easdktool'
  s.version          = '1.1.21.2'
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
  s.libraries = 'c++' 
  
  s.dependency 'ZipArchive'
  s.dependency 'Protobuf'
  s.dependency 'YYKit'
  s.dependency 'BGFMDB'
  s.dependency 'SAMKeychain'
  s.dependency 'SVGKit'
  s.dependency 'zipzap'

  s.resource_bundles = {'EAWatchFace' => ['Lib/EAWatchFace.bundle/*']} #工程需要引入的bundle

  s.vendored_frameworks = [
    'Lib/EABluetooth.framework',
    'Lib/JLLogHelper.framework',
    'Lib/JL_OTALib.framework',
    'Lib/JL_HashPair.framework',
    'Lib/JJL_BLEKit.framework',
    'Lib/JL_AdvParse.framework',
    'Lib/JLDialUnit.framework'
  ]
  
  
  # Flutter.framework does not contain a i386 slice.
  s.pod_target_xcconfig = { 'DEFINES_MODULE' => 'YES', 'EXCLUDED_ARCHS[sdk=iphonesimulator*]' => 'i386' }
end
