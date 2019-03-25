package io.github.wotaslive

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.model.VideoOptionModel
import com.shuyu.gsyvideoplayer.player.IjkPlayerManager
import com.shuyu.gsyvideoplayer.player.PlayerFactory
import com.shuyu.gsyvideoplayer.utils.GSYVideoType
import com.simple.spiderman.SpiderMan
import tv.danmaku.ijk.media.player.IjkMediaPlayer

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        SpiderMan.init(this)
        Utils.init(this)
        instance = this
        PlayerFactory.setPlayManager(IjkPlayerManager::class.java)
        IjkPlayerManager.setLogLevel(IjkMediaPlayer.IJK_LOG_SILENT)
        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_FULL)

        val list = ArrayList<VideoOptionModel>()
        list.add(VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "dns_cache_clear", 1))
        list.add(VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "infbuf", 1))
        // 设置是否开启环路过滤: 0开启，画面质量高，解码开销大，48关闭，画面质量差点，解码开销小
        list.add(VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 0))
        list.add(VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "packet-buffering", 1))
        list.add(VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "max-buffer-size", 1024 * 10))
        list.add(VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 1))
        list.add(VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "analyzemaxduration", 1))
        list.add(VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "probesize", 1024 * 10))
        list.add(VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "flush_packets", 1))
        list.add(VideoOptionModel(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "packet-buffering", 0))
        GSYVideoManager.instance().optionModelList = list
    }

    companion object {
        lateinit var instance: App
    }
}
