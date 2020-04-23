package hh.rpgmakerplayer.webviewmodule

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.util.Base64
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebSettings
import android.webkit.WebView
import java.io.File
import java.nio.charset.Charset
import java.security.cert.CertPath

/**
 * Created by Hao which is based on felixjones` code
 * @author Hao
 */
class rpgPlayerView   : WebView {
    constructor(mcontext: Context) : super(mcontext) {

    }

    constructor(
        mcontext: Context,
        attrs: AttributeSet?
    ) : super(mcontext, attrs,mcontext.resources.getIdentifier("webViewStyle","attr","android")) {
    }

    constructor(
        mcontext: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(mcontext, attrs, defStyleAttr) {
    }
    private var rendervalue:String?=null
    private var fullscreen:Boolean=true
    private var gamename:String?=null
    fun setevaluateJavascript(value:String):rpgPlayerView{
        this.rendervalue=value
        return this
    }

    fun isfullscreen(fullscreen:Boolean=true):rpgPlayerView{
       this.fullscreen=fullscreen
        return this
    }
    fun setgamesavefolder(middlename:String?):rpgPlayerView{
        gamename=middlename;
        return this
    }
    fun build():rpgPlayerView{
        initUi()
        return this;
    }

    fun initUi() {
        settings?.also {
            it.allowContentAccess = true
            it.allowFileAccess = true
            it.setAppCacheEnabled(true)
            it.databaseEnabled = true
            it.domStorageEnabled = true
            it.loadsImagesAutomatically = true
            it.setSupportMultipleWindows(true)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                it.allowFileAccessFromFileURLs = true
                it.allowUniversalAccessFromFileURLs = true
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                it.mediaPlaybackRequiresUserGesture = false
            it.javaScriptCanOpenWindowsAutomatically = true
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2)
                it.setRenderPriority(WebSettings.RenderPriority.HIGH)
                it.javaScriptEnabled = true

        }
        webChromeClient = rpgChromeClient(context)
        webViewClient = rpgViewClient()
        initRendering()
        addjsinterface()
        initwebdetect(context)
        this.requestFocus(View.FOCUS_DOWN)

        if(fullscreen)
            UiUtils.setFullScreen(context)
    }

    private fun addjsinterface(){
        this.addJavascriptInterface(JSInterface(context,encrypt.Base64,gamename),"androidinterface")
    }
    private fun initRendering(){
        var renderdata=""
        if(rendervalue!=null)
            renderdata=rendervalue!!
        else
            renderdata=String(Base64.decode(context.getString(R.string.webview_detection_source), Base64.DEFAULT), Charset.forName("UTF-8")) + "boot" + "." + "prepare( webgl(), webaudio(), false )" + ";"
        this.post {
            Runnable {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT)
                    this.evaluateJavascript(renderdata,null)
                else this.loadUrl("javascript:"+renderdata)
            }
        }
    }

    private fun initwebdetect(context: Context){
        val code = String(
            Base64.decode(
                context.getString(R.string.webview_detection_source),
                Base64.DEFAULT
            ), Charset.forName("UTF-8")
        ) + "boot" + "." + "prepare( webgl(), webaudio(), false )" + ";"
        this.post(Runnable { this.evaluateJavascript(code,null) })
    }
    override fun overScrollBy(
        deltaX: Int,
        deltaY: Int,
        scrollX: Int,
        scrollY: Int,
        scrollRangeX: Int,
        scrollRangeY: Int,
        maxOverScrollX: Int,
        maxOverScrollY: Int,
        isTouchEvent: Boolean
    ): Boolean = false

    fun loadData(data:String){
        this.loadData(data,"text/html", "base64")
    }

    override fun evaluateJavascript(script: String?, resultCallback: ValueCallback<String>?) {
        super.evaluateJavascript(script, resultCallback)
    }

    fun Playgame(path: String,isurl:Boolean){
        if(!isurl) {
            var mURIBuilder = Uri.fromFile(File(path))
                .buildUpon()
            this.loadUrl(mURIBuilder.build().toString())
        }
        else
            this.loadUrl(path)
    }
}