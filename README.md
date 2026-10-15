**Note:** updated version of this library will be available [here](https://github.com/dmytrodanylyk/android-morphing-button).

[![](https://jitpack.io/v/dmytrodanylyk/circular-progress-button.svg)](https://jitpack.io/#dmytrodanylyk/circular-progress-button)
[![Android Arsenal](http://img.shields.io/badge/Android%20Arsenal-Circular%20Progress%20Button-brightgreen.png?style=flat)](http://android-arsenal.com/details/1/67)

### Description

Android Button which can morph to Circular Progress

![](screenshots/intro.gif)

### Wiki

- [Home]
- [Screenshots]
- [User Guide]

### Integration

The lib is available on Maven Central, you can find it with [Gradle, please]

```
allprojects {
   repositories {
      ...
      maven { url 'https://jitpack.io' }
   }
}
```

```
dependencies {
    implementation 'com.github.dmytrodanylyk:circular-progress-button:$latest'
}
```

### ProGuard

If you are using proguard with your project, add following code to your proguard config file

```
-keepclassmembers class com.dd.StrokeGradientDrawable {
    public void setStrokeColor(int);
}
```

### Contributions

If you want to contribute to this library make sure you send pull request to **dev** branch.

### Used By

List of applications on Play Store where this library is used, if you want to be added - ping me.

Icon | Application
------------ | -------------
<img src="http://goo.gl/WL5GME" width="48" height="48" /> | [Passei Direto: Provas & Aulas]
<img src="http://goo.gl/HIKRF9" width="48" height="48" /> | [Finger Gesture Launcher]

### Sample

<a href="https://play.google.com/store/apps/details?id=com.dd.sample.progressbutton">
  <img alt="Android app on Google Play"
       src="https://developer.android.com/images/brand/en_app_rgb_wo_45.png" />
</a>
<a href="https://play.google.com/store/apps/details?id=com.inappsquared.devappsdirect">
  <img alt="DevAppsDirect"
       src="http://www.inappsquared.com/img/icons/devappsdirect_icon.png" width="48" height="48" />
</a>

### License

```
The MIT License (MIT)

Copyright (c) 2014 Danylyk Dmytro

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

[Home]:https://github.com/dmytrodanylyk/circular-progress-button/wiki
[Screenshots]:https://github.com/dmytrodanylyk/circular-progress-button/wiki/Screenshots
[User Guide]:https://github.com/dmytrodanylyk/circular-progress-button/wiki/User-Guide
[Gradle, Please]:http://gradleplease.appspot.com/
[Passei Direto: Provas & Aulas]:https://play.google.com/store/apps/details?id=br.com.passeidireto
[Finger Gesture Launcher]:https://play.google.com/store/apps/details?id=com.carlosdelachica.finger

[![Analytics](https://ga-beacon.appspot.com/UA-44382495-3/circular-progress-button/readme)](https://github.com/igrigorik/ga-beacon)
