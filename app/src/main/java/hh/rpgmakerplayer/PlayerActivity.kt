package hh.rpgmakerplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.afollestad.materialdialogs.MaterialDialog
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        var path=intent.getStringExtra("gamepath")
        if(path.isNullOrEmpty())
            finish()
        path?.apply {
            if(!"".equals(path))
            {
                webplayview.build()
                webplayview.Playgame(path)
            }
        }
    }

    override fun onBackPressed() {
        MaterialDialog(this).show {
            title(R.string.quitplaytitle)
            message (R.string.quitplaycontent)
            positiveButton(R.string.exit){
                finish()
            }
            negativeButton(R.string.cancel){
                this.cancel()
            }
        }
    }
}
