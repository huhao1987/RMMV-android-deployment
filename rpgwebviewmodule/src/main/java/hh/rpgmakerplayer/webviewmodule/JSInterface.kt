package hh.rpgmakerplayer.webviewmodule

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import java.io.*

/**
 * interface to comunicate with javascript
 */
class JSInterface(private val mContext: Context) : Any() {

    //在js中调用window.AndroidWebView.showInfoFromJs(name)，便会触发此方法。
    @JavascriptInterface
    fun showInfoFromJs(name: String?) {
        Log.d("savedata::", name)
    }

    @JavascriptInterface
    fun savedata(id: String, value: String) {
        createsavefile(id, value)
    }

    @JavascriptInterface
    fun loaddata(id: String): String? {
        return loadsavefile(id)
    }

    @JavascriptInterface
    fun backupdata(id: String) {
        val path = mContext.filesDir.absolutePath
        val savepath = File("$path/save")
        val savefile = File(savepath.absolutePath, "$id.rpgsave")
        val savebackpath = File("$path/savebackup")
        if (!savebackpath.isDirectory || !savebackpath.exists()) savebackpath.mkdirs()
        val backupfile =
            File(savebackpath.absolutePath, "$id.rpgsave.bak")
        savefile.renameTo(backupfile)
    }

    @JavascriptInterface
    fun loadbackupdata(id: String): String? {
        return loadbackupsavefile(id)
    }

    @JavascriptInterface
    fun backupexists(id: String): Boolean {
        val path = mContext.filesDir.absolutePath
        val savebackpath = File("$path/savebackup")
        if (!savebackpath.isDirectory || !savebackpath.exists()) return false
        val backupfile =
            File(savebackpath.absolutePath, "$id.rpgsave.bak")
        return if (backupfile.exists() || backupfile.length() > 0) {
            true
        } else false
    }

    @JavascriptInterface
    fun cleanbackup(id: String) {
        val path = mContext.filesDir.absolutePath
        val savebackpath = File("$path/savebackup")
        val backupfile =
            File(savebackpath.absolutePath, "$id.rpgsave.bak")
        if (backupfile.exists()) {
            backupfile.delete()
        }
    }

    @JavascriptInterface
    fun restorebackup(id: String) {
        val path = mContext.filesDir.absolutePath
        val savebackpath = File("$path/savebackup")
        val backupfile =
            File(savebackpath.absolutePath, "$id.rpgsave.bak")
        if (backupfile.exists()) {
            val savepath = File("$path/save")
            val savefile = File(savepath.absolutePath, "$id.rpgsave")
            backupfile.renameTo(savefile)
        }
    }

    @JavascriptInterface
    fun savefileexists(id: String): Boolean {
        val path = mContext.filesDir.absolutePath
        val savepath = File("$path/save")
        if (!savepath.isDirectory || !savepath.exists()) return false
        val savefile = File(savepath.absolutePath, "$id.rpgsave")
        return if (!savefile.exists() || savefile.length() == 0L) false else true
    }

    @JavascriptInterface
    fun removefile(id: String) {
        val path = mContext.filesDir.absolutePath
        val savepath = File("$path/save")
        val savefile = File(savepath.absolutePath, "$id.rpgsave")
        if (savefile.exists() && savefile.length() > 0) savefile.delete()
    }

    private fun createsavefile(id: String, value: String) {
        val path = mContext.filesDir.absolutePath
        val savepath = File("$path/save")
        if (!savepath.isDirectory || !savepath.exists()) savepath.mkdirs()
        val savefile = File(savepath.absolutePath, "$id.rpgsave")
        var outputStream: FileOutputStream? = null
        try {
            outputStream = FileOutputStream(savefile)
            outputStream.write(value.toByteArray())
            outputStream.close()
        } catch (e: FileNotFoundException) {
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun loadsavefile(id: String): String? {
        val path = mContext.filesDir.absolutePath
        val savepath = File("$path/save")
        if (!savepath.isDirectory || !savepath.exists()) return null
        val savefile = File(savepath.absolutePath, "$id.rpgsave")
        return if (!savefile.exists() || savefile.length() == 0L) null else try {
            val inputStream = FileInputStream(savefile)
            var stringBuilder: StringBuilder? = null
            val streamReader = InputStreamReader(inputStream)
            val reader = BufferedReader(streamReader)
            var line: String?
            stringBuilder = StringBuilder()
            while (reader.readLine().also { line = it } != null) {
                // stringBuilder.append(line);
                stringBuilder.append(line)
            }
            reader.close()
            inputStream.close()
            stringBuilder.toString()
        } catch (e: FileNotFoundException) {
            null
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun loadbackupsavefile(id: String): String? {
        val path = mContext.filesDir.absolutePath
        val savepath = File("$path/savebackup")
        if (!savepath.isDirectory || !savepath.exists()) return null
        val savefile = File(savepath.absolutePath, "$id.rpgsave.bak")
        return if (!savefile.exists() || savefile.length() == 0L) null else try {
            val inputStream = FileInputStream(savefile)
            var stringBuilder: StringBuilder? = null
            val streamReader = InputStreamReader(inputStream)
            val reader = BufferedReader(streamReader)
            var line: String?
            stringBuilder = StringBuilder()
            while (reader.readLine().also { line = it } != null) {
                // stringBuilder.append(line);
                stringBuilder.append(line)
            }
            reader.close()
            inputStream.close()
            stringBuilder.toString()
        } catch (e: FileNotFoundException) {
            null
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

}