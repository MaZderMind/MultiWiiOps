MultiWiiOps
===========

A new approach to a cross-platform MultiWii GUI

## Mission Goals
**Uniformity** - No not-OS-Widgets beside OS-Widgets

**UI-Partition** - Separate Cockpit-View for in-the-air monitoring with big, readable gauges ans numbers

**Threading** - IO & Communication should be threaded in the background, so GUI manipulation is always possible and not obstructed by bad connectivity (iw Bluetooth)

**Feedback** - The UI should give feedback for each action (calibrate, upload settings) and show the status of each these operations (initiated, in progess, completed, failed)

**None-Crashy** - The Application must survive any conditions that may happen in real live (unplugging usb cables, invalid data from the FC, breaking bluetooth connectivity) and must always offer a way to continue (resettingretry, reset data, reconnect, close connection, ...)

**Environment-Aware** - The Application must be aware of its boundaries and that their conditions may change (new serial ports entering the systems, existing ports getting unavailable, the computer getting suspended, ...) and must react on these events (update UI, close communication sockets, reset state)

**Help** - All Control-Widgets should be supplied with explanation of their meaning and their possible states. These texts should be visible by default when interacting with the UI bud must be hidable.

**Cross-Platform** - The Application must be operable and tested in the following OS-Environments:Linux64, Linux32, Win64, Win32, OSX-Intel

## Motivation
I'm good at coding and I think I can build a GUI for the wonderful MultiWii Project that fulfils the above requirements.
I'm aware of [MultiWiiConf](https://code.google.com/p/multiwii/) and [MW-Wingui](https://code.google.com/p/mw-wingui/).


## Documentation
I'm not someone who doesn't like to write documentation - I just don't like to write it twice. So once the code runs smoothly and is cleaned up, I'll update and rewrite the documentation. If you're stuck while playing around in this early stage, please just drop me a mail and I'll help you out.

## Contact
If you have any questions just ask at github@mazdermind.de.

## Licence
This Project is licensed under the FreeBSD-License
