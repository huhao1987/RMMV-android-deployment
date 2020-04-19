package hh.rpgmakerplayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hh.rpgmakerplayer.SDCardsUtils.initFolderSelection
import hh.rpgmakerplayer.webviewmodule.UiUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFolderSelection(this,object: SDCardsUtils.filecallback{
            override fun onPath(path: String) {
                webplayview
                    .isfullscreen(false)
                    .build()
                webplayview.Playgame(path)

            }
        })
    }

}
