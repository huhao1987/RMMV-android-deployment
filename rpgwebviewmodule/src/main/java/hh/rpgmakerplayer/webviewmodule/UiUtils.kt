package hh.rpgmakerplayer.webviewmodule

import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import java.io.*
import java.lang.Exception
import java.nio.channels.FileChannel
import java.util.regex.Matcher
import java.util.regex.Pattern

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


        fun getRegex(soap: String?, rgex: String?): String? {
            if(soap!=null) {
                val pattern = Pattern.compile(rgex)
                val m: Matcher = pattern.matcher(soap)
                while (m.find()) {
                    return m.group(1)
                }
            }
            return null
        }


        fun copyassetfile(context: Context,assetsname:String,savepath:String,savename:String){
            var filename=savepath+"/$savename"
            var dir=File(savepath)
            if(!dir.exists())dir.mkdir()
            try{
                if(!File(filename).exists()){
                    var isstream=context.resources.assets.open(assetsname)
                    var fostream=FileOutputStream(filename)
                    var buffer=ByteArray(1024*1000)
                    var count=0
                    while ({count = isstream.read(buffer);count}() > 0) {
                        fostream.write(buffer,0,count)
                    }
                }
            }
            catch (e:Exception){

            }
        }

    }
}