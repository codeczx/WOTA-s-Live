package io.github.wotaslive

import android.app.Application
import com.blankj.utilcode.util.Utils
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Utils.init(this)
        instance = this
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    companion object {
        lateinit var instance: App
    }
}
