# EUIProgressBar

This is an easy progressbar like HuaWei EUI progressbar, and a GifImageView with ProgressBar.

## Screenshots

<img src="/EUIProgressBar/screenshots/screenshot.gif" alt="screenshot_0" title="screenshot_0" />

## Gradle

```groovy
dependencies {
    compile 'com.github.whilu.EUIProgressBar:library:1.0.0'
}
```

Any problem, please send me an [email](mailto:lujunat1993@gmail.com).

## Usage

Add the namespace in the XML file:

```xml
xmlns:whilu="http://schemas.android.com/apk/res-auto"

```

Then, use it

1.GifImageView, use [Glide](https://github.com/bumptech/glide) loading and caching images.

```xml
<com.github.wihlu.library.GifImageView
    android:layout_width="match_parent"
    android:layout_height="200dp"
    android:background="@drawable/ic_launcher"
    whilu:strokecolor="#ff666666"
    whilu:fillcolor="#ff4387F0" />

```

* `whilu:strokecolor` (the ring color)
* `whilu:fillcolor` (the arc color)

2. CustomProgressBar

```xml
<com.github.wihlu.library.CustomProgressBar
   android:id="@+id/cusprogressbar"
   android:layout_width="100dp"
   android:layout_height="100dp"
   whilu:strokecolor="#ff666666"
   whilu:fillcolor="#ff4387F0" />

```

* `whilu:strokecolor` (the ring color)
* `whilu:fillcolor` (the arc color)

3. EUIProgressBar

```xml
<com.github.wihlu.library.EUIProgressBar
   android:id="@+id/progressbar"
   android:layout_width="100dp"
   android:layout_height="100dp"
   whilu:ringcolor="#ff666666"
   whilu:circlecolor="#ff3f51b5" />

```

* `whilu:ringcolor` (the ring color)
* `whilu:circlecolor` (the circle color)

## Proguard

Depending on your proguard config and usage, you may need to include the following lines in your proguard.cfg:
```
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}
```

## SAMPLE

[sample apk](/sample/sample-release.apk)

## About

If you have any problems, please [email me](mailto:lujunat1993@gmail.com).

### Thanks

* [Glide](https://github.com/bumptech/glide), An image loading and caching library for Android focused on smooth scrolling.


License
============

    Copyright 2015 whilu

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
