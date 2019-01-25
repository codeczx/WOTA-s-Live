package io.github.wotaslive

import android.app.Application
import cn.jzvd.JZMediaInterface
import cn.jzvd.Jzvd
import com.blankj.utilcode.util.Utils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import io.github.wotaslive.widget.JZMediaIjkplayer

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        instance = this
        Logger.addLogAdapter(AndroidLogAdapter())
        Jzvd.setMediaInterface(JZMediaIjkplayer())
        Jzvd.SAVE_PROGRESS = false
    }

    companion object {
        lateinit var instance: App
    }
}
