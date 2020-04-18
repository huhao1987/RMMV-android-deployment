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
import com.github.angads25.filepicker.view.FilePickerDialog
import hh.rpgmakerplayer.SDCardsUtils.dialog
import hh.rpgmakerplayer.SDCardsUtils.initFolderSelection
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFolderSelection(this,object: SDCardsUtils.filecallback{
            override fun onPath(path: String) {
                webplayview.Playgame(path)

            }
        })
    }
}
