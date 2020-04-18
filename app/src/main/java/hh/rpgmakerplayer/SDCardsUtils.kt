package hh.rpgmakerplayer

import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Environment
import android.os.storage.StorageManager
import android.text.TextUtils
import com.github.angads25.filepicker.controller.DialogSelectionListener
import com.github.angads25.filepicker.model.DialogConfigs
import com.github.angads25.filepicker.model.DialogProperties
import com.github.angads25.filepicker.view.FilePickerDialog
import java.io.File

/**
 * Created by laole918 on 2016/4/23.
 */
object SDCardsUtils {
    private var sdcard0: String? = null
    private var sdcard1: String? = null
    private var initialized = false
    fun getSDCard0(context: Context): String? {
        initialize(context)
        return sdcard0
    }

    val sDCard0: String
        get() = Environment.getExternalStorageDirectory().absolutePath

    fun getSDCard1(context: Context): String? {
        initialize(context)
        return sdcard1
    }

    val sDCard0State: String
        get() = Environment.getExternalStorageState()

    fun getSDCard0State(context: Context): String {
        return getSDCardState(context, sdcard0)
    }

    fun getSDCard1State(context: Context): String {
        return getSDCardState(context, sdcard1)
    }

    @TargetApi(19)
    private fun getSDCardState(
        context: Context,
        sdcard: String?
    ): String {
        initialize(context)
        return if (!TextUtils.isEmpty(sdcard)) {
            val path = File(sdcard)
            if (Build.VERSION.SDK_INT >= 19) {
                Environment.getStorageState(path)
            } else {
                if (path.exists() && path.isDirectory && path.canRead()) {
                    if (path.canWrite()) {
                        Environment.MEDIA_MOUNTED
                    } else {
                        Environment.MEDIA_MOUNTED_READ_ONLY
                    }
                } else {
                    Environment.MEDIA_REMOVED
                }
            }
        } else {
            Environment.MEDIA_UNMOUNTED
        }
    }

    private fun initialize(context: Context) {
        if (!initialized) {
            synchronized(SDCardsUtils::class.java) {
                if (!initialized) {
                    val dirs = getExternalDirs(context)
                    if (dirs != null) {
                        val length = dirs.size
                        if (length > 0) {
                            sdcard0 = dirs[0]
                        }
                        if (length > 1) {
                            sdcard1 = dirs[1]
                        }
                    }
                    initialized = true
                }
            }
        }
    }

    private fun getExternalDirs(context: Context): Array<String?>? {
        val mContext = context.applicationContext
        val mStorageManager =
            mContext.getSystemService(Context.STORAGE_SERVICE) as StorageManager
        try {
            val storageVolumeClazz =
                Class.forName("android.os.storage.StorageVolume")
            val getVolumeList =
                mStorageManager.javaClass.getMethod("getVolumeList")
            val getPath = storageVolumeClazz.getMethod("getPath")
            val result = getVolumeList.invoke(mStorageManager)
            val length = java.lang.reflect.Array.getLength(result)
            val paths = arrayOfNulls<String>(length)
            for (i in 0 until length) {
                val storageVolumeElement = java.lang.reflect.Array.get(result, i)
                paths[i] = getPath.invoke(storageVolumeElement) as String
            }
            return paths
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
    var dialog:FilePickerDialog?=null

    fun initFolderSelection(context: Context,filecallback: filecallback){
        if(getSDCard1(context)!=null){
            var sddialog= AlertDialog.Builder(context)
            sddialog.setTitle(R.string.sdchoose)
            val items = arrayOf(context.getString(R.string.insd),context.getString(R.string.outsd))
            sddialog.setItems(items,object: DialogInterface.OnClickListener{
                override fun onClick(dialog1: DialogInterface?, which: Int) {
                    when(which)
                    {
                        0-> {
                            var folder = getSDCard0(context)
                            filedialog(context,folder,filecallback)
                            dialog!!.show()
                        }

                        1->            {
                            var folder=getSDCard1(context)
                            filedialog(context,folder,filecallback)
                            dialog!!.show()

                        }
                    }
                }

            }).show()
        }
        else {
            var folder = getSDCard0(context)
            filedialog(context,folder,filecallback)
            dialog!!.show()
        }
    }

    fun filedialog(context: Context,folder:String?,filecallback: filecallback){
        var properties = DialogProperties()
        properties.selection_mode = DialogConfigs.SINGLE_MODE
        properties.selection_type = DialogConfigs.FILE_SELECT
        properties.root = File(folder)
        properties.error_dir = File(DialogConfigs.DEFAULT_DIR)
        properties.offset = File(DialogConfigs.DEFAULT_DIR)
        var exten=ArrayList<String>()
        exten.add("html")
        properties.extensions=exten.toTypedArray()
        dialog = FilePickerDialog(context, properties)
        dialog!!.setTitle(context.getString(R.string.fileselmsg))
        dialog!!.setPositiveBtnName(context.getString(R.string.filesepo))
        dialog!!.setNegativeBtnName(context.getString(R.string.filesena))
        dialog!!.setDialogSelectionListener (object: DialogSelectionListener {
            override fun onSelectedFilePaths(files: Array<out String>?) {
                files?.apply {
                    filecallback.onPath(this.get(0))
                }

            }
        })
    }

    interface filecallback{
        fun onPath(path:String)
    }
}