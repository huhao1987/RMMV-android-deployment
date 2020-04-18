package hh.rpgmakerplayer.webviewmodule

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Message
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebView.WebViewTransport
import android.webkit.WebViewClient

/**
 * Created by Hao which is based on felixjones` code
 * @author Hao
 */
class rpgChromeClient(var context: Context) : WebChromeClient() {
    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        if ("Scripts may close only the windows that were opened by it." == consoleMessage!!.message())
            if (context is Activity)
                (context as Activity).finish()
        return super.onConsoleMessage(consoleMessage)
    }

    override fun onCreateWindow(view: WebView?, isDialog: Boolean, isUserGesture: Boolean,resultMsg: Message?): Boolean {
        val dumbWV = WebView(view!!.context)
        dumbWV.webViewClient = object : WebViewClient() {
            override fun onPageStarted(
                view: WebView,
                url: String,
                favicon: Bitmap
            ) {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                view.context.startActivity(browserIntent)
            }
        }
        (resultMsg!!.obj as WebViewTransport).webView = dumbWV
        resultMsg!!.sendToTarget()
        return true
    }
}