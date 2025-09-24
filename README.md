# ProDVX Android Demo Application
A demo application that showcases the different uses of the APPC devices. Open source for every ProDVX Customer to see what is needed behind the scenes

## Features
- Adaptive Light technology that adapts the LED ring to the screen content. (S-Series only, API required)
- LED Control: Manually change the color the the Full Led Bar LED's using either SDK or ProDVX API
- S-Series Led Demo: Demo Application that showcases the individual led control (S-Series only,  API required)

_To Come_:
- NFC Demo: Test the full functionality of the integrated or external NFC capabilities
- Radar Demo: Test the functionalities of the Radar Motion Sensor
- Camera: Test some of the different ways to implement the camera on your APPC device
- Scanner: See what is possible with the barcode scanner

## Installation
- Download the App from the releases tab.

- Either:
  - Transfer to USB stick
  - Insert USB into APPC device
  - Install using USB
- Or
  - Install using ADB
 
## Usage
### Without API:
Currently only the LED Demo is available, with only two functions. Turning the full LED ring Red, or turning the full LED ring Green
- Open the app
- Tap "LED Demo"
- Use any function with "SDK:"

### With API
- Ensure that the ProDVX API is running.
- When the app first starts no token is provided.
- Tap "Set new API Token". You can set it in two ways
  - Manual entry:
    - Enter the token or paste from another application
  - Configuration File
    - Create a "configuration.json" file  (example below)
    - Place it on the device
    - When tapping above button, tap "Pick a file"
    - Pick the configuration file
- All other functionality becomes available now.

## Issues
Please input any issue to the issue tracker
