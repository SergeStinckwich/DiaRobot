DiaRobot
========

Follow [install](http://docs.rosjava.googlecode.com/hg/rosjava_core/html/installing.html)
and [build](http://docs.rosjava.googlecode.com/hg/rosjava_core/html/building.html) instructions.
Make sure to use OpenJDK, see [issue 116](http://code.google.com/p/rosjava/issues/detail?id=116).
Tested with Revision: [e417224a6150](http://code.google.com/p/rosjava/source/detail?r=e417224a6150445f2c62bca188dc6c47e3a44918)
(02-05-2012)

    roscd rosjava_core
    git clone git://github.com/SergeStinckwich/DiaRobot.git

edit `settings.gradle`, add `DiaRobot` in `include`

    roscd DiaRobot

    ../gradlew installApp

    roscore

    cd morse
    morse edit beego.py

    ./build/install/DiaRobot/bin/DiaRobot diarobot.Wander base_scan:=/beego/scan cmd_vel:=/beego/velocity
