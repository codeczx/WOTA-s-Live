package io.github.wotaslive.player

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import cn.jzvd.Jzvd
import io.github.wotaslive.Constants
import io.github.wotaslive.GlideApp
import io.github.wotaslive.R
import io.github.wotaslive.data.model.LiveInfo
import io.github.wotaslive.databinding.ActPlayerBinding
import io.github.wotaslive.utils.checkUrl

class PlayerActivity : AppCompatActivity() {

    private lateinit var viewDataBinding: ActPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.act_player)
        val room = intent.getParcelableExtra<LiveInfo.ContentBean.RoomBean>(Constants.ROOM)
        with(viewDataBinding.player) {
            progressBar.visibility = if (room.liveType == 1) View.INVISIBLE else View.VISIBLE
            fullscreenButton.visibility = View.GONE
            setUp(room.streamPath, room.title, cn.jzvd.Jzvd.SCREEN_WINDOW_NORMAL)
            room.picPath?.let {
                val imgArr = it.split(",")
                if (!imgArr.isEmpty()) {
                    GlideApp.with(this@PlayerActivity)
                            .load(checkUrl(imgArr[0]))
                            .dontAnimate()
                            .into(thumbImageView)
                }
            }
            startVideo()
        }


    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) return
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

    companion object {

        fun startPlayerActivity(context: Context?, room: LiveInfo.ContentBean.RoomBean) {
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra(Constants.ROOM, room)
            context?.startActivity(intent)
        }
    }
}
