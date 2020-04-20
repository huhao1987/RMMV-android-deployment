package hh.rpgmakerplayer.webviewmodule

import android.content.Context
import android.util.Base64
import android.util.Log
import android.webkit.JavascriptInterface
import java.io.*

/**
 * interface to comunicate with javascript
 */

/*
    enum class for encrypt
 */
enum class encrypt{
        None,
        Base64
}

class JSInterface(private var mContext: Context, var encryptmethod:encrypt=encrypt.None, var middlepath:String="") : Any() {
    private val TAG = "RPGsavestatus::"

    private fun getfilename(id: String): String {
        if (id.toInt() < 0)
            return "config"
        else if (id.toInt() == 0)
            return "global"
        else return "file$id"
    }


    private fun encryptsave(value:String):String{
        when(encryptmethod) {
            encrypt.None->return value
            encrypt.Base64->return Base64.encodeToString(value.toByteArray(),Base64.DEFAULT)
            else->return value
        }
    }
    private fun decryptsave(envalue:String):String{
        when(encryptmethod) {
            encrypt.None->return envalue
            encrypt.Base64->return String(Base64.decode(envalue,Base64.DEFAULT))
            else->return envalue
        }
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
        Log.d(TAG, "backup save on ${getfilename(id)}.rpgsave")
        var path = mContext.filesDir.absolutePath
        var savepath = File("$path/save")
        var savefile = File(savepath.absolutePath, "${getfilename(id)}.rpgsave")
        var savebackpath = File("$path/save")
        if (!savebackpath.isDirectory || !savebackpath.exists()) savebackpath.mkdirs()
        if (savefile.exists()) {
            var backupfile =
                File(savebackpath.absolutePath, "${getfilename(id)}.rpgsave.bak")
            savefile.renameTo(backupfile)
        }
    }

    @JavascriptInterface
    fun loadbackupdata(id: String): String? {
        return loadbackupsavefile(id)
    }

    @JavascriptInterface
    fun backupexists(id: String): Boolean {
//        Log.d(TAG, "backup save exist judgement on ${getfilename(id)}.rpgsave")
        var path = mContext.filesDir.absolutePath
        var savebackpath = File("$path/save")
        if (!savebackpath.isDirectory || !savebackpath.exists()) return false
        var backupfile =
            File(savebackpath.absolutePath, "${getfilename(id)}.rpgsave.bak")
        return if (backupfile.exists() || backupfile.length() > 0) {
            true
        } else false
    }

    @JavascriptInterface
    fun cleanbackup(id: String) {
        Log.d(TAG, "delete backup save on ${getfilename(id)}.rpgsave")
        var path = mContext.filesDir.absolutePath
        var savebackpath = File("$path/save")
        var backupfile =
            File(savebackpath.absolutePath, "${getfilename(id)}.rpgsave.bak")
        if (backupfile.exists()) {
            backupfile.delete()
        }
    }

    @JavascriptInterface
    fun restorebackup(id: String) {
        Log.d(TAG, "restore backup save on ${getfilename(id)}.rpgsave.bak")
        var path = mContext.filesDir.absolutePath
        var savebackpath = File("$path/save")
        var backupfile =
            File(savebackpath.absolutePath, "${getfilename(id)}.rpgsave.bak")
        if (backupfile.exists()) {
            var savepath = File("$path/save")
            var savefile = File(savepath.absolutePath, "${getfilename(id)}.rpgsave")
            backupfile.renameTo(savefile)
        }
    }

    @JavascriptInterface
    fun savefileexists(id: String): Boolean {
//        Log.d(TAG, "save file exist judgement on ${getfilename(id)}.rpgsave")
        var path = mContext.filesDir.absolutePath
        var savepath = File("$path/save")
        if (!savepath.isDirectory || !savepath.exists()) return false
        var savefile = File(savepath.absolutePath, "${getfilename(id)}.rpgsave")
        return if (!savefile.exists() || savefile.length() == 0L) false else true
    }

    @JavascriptInterface
    fun removefile(id: String) {
        Log.d(TAG, "remove save on ${getfilename(id)}.rpgsave")
        var path = mContext.filesDir.absolutePath
        var savepath = File("$path/save")
        var savefile = File(savepath.absolutePath, "${getfilename(id)}.rpgsave")
        if (savefile.exists() && savefile.length() > 0) savefile.delete()
    }

    private fun createsavefile(id: String, value: String) {
        Log.d(TAG, "create new save on ${getfilename(id)}.rpgsave")
        var path = mContext.filesDir.absolutePath
        var savepath = File(path+"/save")
        if (!savepath.isDirectory || !savepath.exists()) savepath.mkdirs()

        var savefile = File(savepath.absolutePath, "${getfilename(id)}.rpgsave")
        var outputStream: FileOutputStream? = null
        try {
            outputStream = FileOutputStream(savefile)
            outputStream.write(encryptsave(value).toByteArray())
            outputStream.close()
        } catch (e: FileNotFoundException) {
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun loadsavefile(id: String): String? {
        Log.d(TAG, "load save on ${getfilename(id)}.rpgsave")
        var path = mContext.filesDir.absolutePath
        var savepath = File("$path/save")
        if (!savepath.isDirectory || !savepath.exists()) return null
        var savefile = File(savepath.absolutePath, "${getfilename(id)}.rpgsave")
        return if (!savefile.exists() || savefile.length() == 0L) null else try {
            var inputStream = FileInputStream(savefile)
            var stringBuilder: StringBuilder? = null
            var streamReader = InputStreamReader(inputStream)
            var reader = BufferedReader(streamReader)
            var line: String?
            stringBuilder = StringBuilder()
            while (reader.readLine().also { line = it } != null) {
                // stringBuilder.append(line);
                stringBuilder.append(line)
            }
            reader.close()
            inputStream.close()
            decryptsave(stringBuilder.toString())
        } catch (e: FileNotFoundException) {
            null
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun loadbackupsavefile(id: String): String? {
        Log.d(TAG, "load backup save on ${getfilename(id)}.rpgsave.bak")
        var path = mContext.filesDir.absolutePath
        var savepath = File("$path/save")
        if (!savepath.isDirectory || !savepath.exists()) return null
        var savefile = File(savepath.absolutePath, "${getfilename(id)}.rpgsave.bak")
        return if (!savefile.exists() || savefile.length() == 0L) null else try {
            var inputStream = FileInputStream(savefile)
            var stringBuilder: StringBuilder? = null
            var streamReader = InputStreamReader(inputStream)
            var reader = BufferedReader(streamReader)
            var line: String?
            stringBuilder = StringBuilder()
            while (reader.readLine().also { line = it } != null) {
                // stringBuilder.append(line);
                stringBuilder.append(line)
            }
            reader.close()
            inputStream.close()
            decryptsave(stringBuilder.toString())
        } catch (e: FileNotFoundException) {
            null
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}