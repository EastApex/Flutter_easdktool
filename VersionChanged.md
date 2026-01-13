Date:01.13.2026
Change:
    Android:
        1. Del invalid sms 
    iOS:
       No modification for now

=================================================================


Date:12.29.2025
Change:
    Android:
        1. Changed jieli lib 
    iOS:
       No modification for now

=================================================================


Date:12.12.2025
Change:
    Android:
        1. Changed jieli lib 
    iOS:
       No modification for now

=================================================================


Date:11.28.2025
Change:
    Android:
        1. Fixed the bug related to air humidity
        2. Modify the logic of the OTA online Watch Face. There is no need to delete the online  Watch Face to perform OTA.
    iOS:
        1. Fixed the bug related to air humidity
        2. Modify the logic of the OTA online  Watch Face. There is no need to delete the online  Watch Face to perform OTA.

=================================================================


Date:10.29.2025
Change:
    Android:
        1.Modifying the gradle
    iOS:
        No modification for now

=================================================================


Date:10.27.2025
Change:
    Android:
        1.add jieli 707 custom watch face.
    iOS:
        1.Fixed the issue where it sometimes failed to reconnect after pairing.

=================================================================


Date:10.21.2025
Change:
    Android:
        No modification for now
    iOS:
        1.ios add jieli 707 custom watch face
          Add func:
            getJieLiCustomWatchfacePreviewImage()
            otaJieLiCustomWatchface()
        2. See image: style.png

=================================================================



Date:10.20.2025
Change:
    Android:
        1. Add more log prints
        2. If the watch fails to connect successfully or is in OTA status, all instructions sent to the watch will become invalid.
    iOS:
        No modification for now

=================================================================



Date:10.14.2025
Change:
    Android:
        No modification for now
    iOS:
        1.Fix the issue where language Settings fail to work.

=================================================================


Date:10.09.2025
Change:
    Android:
        1.Solve the OTA completion issue.
    iOS:
        No modification for now

=================================================================

Date:10.01.2025
Change:
    Android:
        1.Solve the problem of dial encryption of jieli707
    iOS:
        1.Fixed the issue of OTA progress not returning.Please refer to the code to parse the progress data returned by the watch.
        
Notes:
    1.Air 5 Pro OTA firmware Description: 
        The jl707 OTA solution has been abandoned, and the same OTA solution as the Air 5 watch is adopted. For details, please refer To the Demo code: 1.To upgrade the firmware

    2.Air 5 Pro OTA Dial Description: 
        Use the new method OTA Dial. For details, please refer to the Demo code: 4.add 707watch face, 5.delete 707watch face, 6.get 707watch face
    
    3.Regarding the custom dial of Air 5 Pro: 
        The watch is not supported for the time being. Please do not use the currently provided method for customizing the dial.
