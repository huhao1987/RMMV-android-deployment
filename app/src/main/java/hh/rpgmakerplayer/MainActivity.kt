package hh.rpgmakerplayer

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.angads25.filepicker.controller.DialogSelectionListener
import com.github.angads25.filepicker.model.DialogConfigs
import com.github.angads25.filepicker.model.DialogProperties
import com.github.angads25.filepicker.view.FilePickerDialog
import hh.rpgmakerplayer.SDCardsUtils.dialog
import hh.rpgmakerplayer.SDCardsUtils.initFolderSelection
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        webplayview.Playgame(getString(R.string.mv_project_index))
        initFolderSelection(this)
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            when(requestCode) {
                FilePickerDialog.EXTERNAL_READ_PERMISSION_GRANT -> {
                    if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if (dialog != null) {
                            //Show dialog if the read permission has been granted.
                            dialog!!.show();
                        }
                    } else {
                        //Permission has not been granted. Notify the user.
                        dialog!!.dismiss()
                    }
                }
            }
    }
}
