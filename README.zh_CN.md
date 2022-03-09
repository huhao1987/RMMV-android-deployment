# RMMV-android-deployment

<img src="https://img.shields.io/github/v/release/huhao1987/RMMV-android-deployment.svg">


 *本项目基于https://github.com/AltimitSystems/mv-android-client, 虽然修改重写并优化了很多东西,但是依然感谢Altimit Systems的代码!*

#### 本项目提供了一个可以帮助RMMV游戏作者制作_安卓_版本游戏的依赖库,并且使用方便快捷.
**本项目使用kotlin编写,所以代码比Java更易读和简便.**

## 更新日志 ##
**添加存档类型支持**

**添加Android本地存档支持（基于我编写的插件）**

**添加自定义存档目录**

## 如何使用
#### 基本步骤
1. 首先你要下载最新Android Studio, 目前最新版是3.6.3.
2. 创建一个新项目或者打开一个已有的项目.
3. 在项目环境刷新完毕后,选择"build.gradle(Project:xxxx)" 在allproects里加入 "maven { url 'https://jitpack.io' }"
```kotlin
allprojects {
    repositories {
        google()
        jcenter()
         maven { url 'https://jitpack.io' }

    }
}
```

4. 在 "build.gradle(Module:app)"中加入下面一行
```kotlin
implementation 'com.github.huhao1987:RMMV-android-deployment:1.0.9'
```
5. 选择 "manifests", 在"activity"标签内添加
```kotlin
       android:configChanges="orientation|screenSize"
       android:screenOrientation="sensorLandscape"
```
此步骤可以保证游戏运行于横屏

* 如果你的游戏需要网络支持

5.1 请添加 
```kotlin
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```
5.2 在"application"标签内添加 
```kotlin
        android:networkSecurityConfig="@xml/network_security_config"
```
5.3 右键点击 "app"->"New"->"Android Resource Directory", 在Resource type里选择"xml".
    右键点击 "xml"->"New"->"XML Resource file",命名为"network_security_config",然后复制以下代码
```kotlin
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <base-config cleartextTrafficPermitted="true" />
</network-security-config>
```
5.1 和 5.2设置可以让游戏有网络连接权限.5.2能保证连接到"http"和"https"都不被阻断

6. 在项目的主activity的layout文件中加入下面的控件,默认主activity为MainActivity, 它的layout文件一般为activity_main.
```kotlin
 <hh.rpgmakerplayer.webviewmodule.rpgPlayerView
        android:id="@+id/rpgwebview"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
 ```
  
7. 如果你的项目里没有assets目录,右键点击 "app"-"New"->"Folder"->"Assets Folder",然后把你游戏的www文件夹复制进去.

8. 在MainActivity的oncreate方法中加入下面几行
* kotlin:
```kotlin
rpgwebview.build()
rpgwebview.Playgame("//android_asset/www/index.html")
```
* Java:
```Java
rpgPlayerView rpgwebview=findViewById(R.id.rpgwebview)
rpgwebview.build()
rpgwebview.Playgame("//android_asset/www/index.html")
```
9. 在手机上编译并且运行你的游戏.

## 高级操作 
除去基本步骤,你也可以使用一些其他属性
1) 全屏游戏或者不全屏
```kotlin
rpgwebview
 .isfullscreen(false/true)
 .build()
 ```
2) 使用你自己的evaluateJavascript运行游戏
 ```kotlin
rpgwebview
 .setevaluateJavascript(xxxxxx)
 .build()
 ```
 
 3) **Android 存档支持**

   由于Android系统的限制，RMMV无法将存档保存在Android本地，我写了个插件来支持。
   用法很简单，首先使用本项目来创建你的Android游戏项目，从 [这里](https://github.com/huhao1987/RMMV-Plugins)下载hh_Android_save.js，然后在你的RMMV项目中开启这个插件。
   
   **记住修改之后更新你Android项目中的assets文件夹**
   
   之后当你测试和玩游戏的时候，游戏存档会被存储在app的内部存储中，如果你root过，可以在这个文件夹下找到游戏存档。
 
4） **自定义存档设置**

添加自定义存档设置，本功能允许你把存档保存在设置的目录里，但是你必须自己获取android的写文件权限。
     
 ```kotlin
rpgwebview.setupcustompath("your path")
rpgwebview.build()
   ```
5) 更多新功能...敬请期待


