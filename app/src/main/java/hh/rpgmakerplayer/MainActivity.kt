package hh.rpgmakerplayer

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.angads25.filepicker.view.FilePickerDialog
import hh.rpgmakerplayer.SDCardsUtils.initFolderSelection
import hh.rpgmakerplayer.webviewmodule.UiUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playwithfolder()
    }

    private fun playwithfolder(){
        initFolderSelection(this,object: SDCardsUtils.filecallback{
            override fun onPath(path: String) {
               startActivity(Intent(this@MainActivity,PlayerActivity::class.java).also {
                   it.putExtra("gamepath",path)
               })
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            FilePickerDialog.EXTERNAL_READ_PERMISSION_GRANT -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    playwithfolder()
                }
            }
        }
    }
    override fun onResume() {
        super.onResume()
        UiUtils.setFullScreen(this)
    }
}
