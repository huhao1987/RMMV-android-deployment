package hh.rpgmakerplayer

import android.view.View
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.row_gameinfo.view.*

class gamelistadpter(lists: ArrayList<gamelistbean>) : BaseQuickAdapter<gamelistbean, gamelistadpter.ViewHolder>(R.layout.row_gameinfo, lists) {

    override fun convert(holder: ViewHolder, item: gamelistbean) {
        GlideApp.with(context)
            .load(item.icon)
            .error(R.mipmap.ic_launcher)
            .fitCenter()
            .into(holder.gameimage)
        holder.gametitle.text=item.name
    }
    class ViewHolder(var view: View) : BaseViewHolder(view) {
        var gametitle = view.gametitle
        var gameimage = view.gameimage
    }
}