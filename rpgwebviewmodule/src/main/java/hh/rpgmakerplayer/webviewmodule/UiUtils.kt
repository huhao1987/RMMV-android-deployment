package hh.rpgmakerplayer.webviewmodule

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View

class UiUtils {
    companion object{
        fun setFullScreen(context: Context){
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.KITKAT)
                if(context is Activity)
                    context.window.decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or  View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }
}