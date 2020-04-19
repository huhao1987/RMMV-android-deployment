# RMMV-android-deployment

<img src="https://img.shields.io/github/v/release/huhao1987/RMMV-android-deployment.svg">


 *本项目基于https://github.com/AltimitSystems/mv-android-client, 虽然修改重写并优化了很多东西,但是依然感谢Altimit Systems的代码!*

#### 本项目提供了一个可以帮助RMMV游戏作者制作_安卓_版本游戏的依赖库,并且使用方便快捷.
**本项目使用kotlin编写,所以代码比Java更易读和简便.**

## 如何使用
#### 基本步骤
1. 首先你要下载最新Android Studio, 目前最新版是3.6.2.
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
implementation 'com.github.huhao1987:RMMV-android-deployment:1.0'
```

5. 在项目的主activity的layout文件中加入下面的控件,默认主activity为MainActivity, 它的layout文件一般为activity_main.
```kotlin
 <hh.rpgmakerplayer.webviewmodule.rpgPlayerView
        android:id="@+id/rpgwebview"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
 ```
6. 在MainActivity的oncreate方法中加入下面几行
```kotlin
webplayview.build()
webplayview.Playgame(path)
```
7. 在手机上编译并且运行你的游戏.

## 高级操作 
除去基本步骤,你也可以使用一些其他属性
1) 全屏游戏或者不全屏
```kotlin
webplayview
 .isfullscreen(false/true)
 .build()
 ```
2) 使用你自己的evaluateJavascript运行游戏
 ```kotlin
webplayview
 .setevaluateJavascript(xxxxxx)
 .build()
 ```
3) 更多新功能....
比如,Android上本地存储模式基本上没用,新功能中会加入,尽情期待...


## Demo
内部代码中包含一个名为RPG maker mv player的app,这个app是一个Android上的通用rpg maker mv游戏运行器.可以玩手机存储内的游戏,不久会上线到google play.

