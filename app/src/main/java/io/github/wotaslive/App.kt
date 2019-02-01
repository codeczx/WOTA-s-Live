package io.github.wotaslive

import android.app.Application
import cn.jzvd.Jzvd
import com.blankj.utilcode.util.Utils
import com.previewlibrary.ZoomMediaLoader
import io.github.wotaslive.widget.JZMediaIjkplayer

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        instance = this
        Jzvd.setMediaInterface(JZMediaIjkplayer())
        Jzvd.SAVE_PROGRESS = false
        ZoomMediaLoader.getInstance().init(ImageLoader())
    }

    companion object {
        lateinit var instance: App
    }
}
