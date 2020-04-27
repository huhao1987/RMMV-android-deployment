package hh.rpgmakerplayer.webviewmodule

import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager

class NavigationBarUtil {
    /**
     * Hide navigation bar for full screen
     * @param window
     */
    companion object{
        fun hideNavigationBar(window: Window) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            window.decorView
                .setOnSystemUiVisibilityChangeListener {
                    var uiOptions =
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or  //布局位于状态栏下方
                                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or  //全屏
                                View.SYSTEM_UI_FLAG_FULLSCREEN or  //隐藏导航栏
                                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    uiOptions = if (Build.VERSION.SDK_INT >= 19) {
                        uiOptions or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    } else {
                        uiOptions or View.SYSTEM_UI_FLAG_LOW_PROFILE
                    }
                    window.decorView.systemUiVisibility = uiOptions
                }
        }


        fun focusNotAle(window: Window) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            )
        }


        fun clearFocusNotAle(window: Window) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        }
    }
}