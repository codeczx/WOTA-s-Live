package io.github.wotaslive.photo

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableField
import android.os.Environment
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ToastUtils
import io.github.wotaslive.GlideApp
import io.github.wotaslive.utils.checkUrl
import io.reactivex.schedulers.Schedulers

class PhotoViewModel(application: Application) : AndroidViewModel(application) {
    val url = ObservableField<String>()

    fun saveToLocal(activity: Activity) {
        val url = url.get() ?: ""
        val worker = Schedulers.io().createWorker()
        worker.schedule {
            val file = GlideApp.with(activity)
                    .downloadOnly()
                    .load(checkUrl(url))
                    .submit()
                    .get()
            val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val fileName = path.absolutePath + "/" + url.substring(url.lastIndexOf("/") + 1)
            FileUtils.copyFile(file.absolutePath, fileName, null)
            ToastUtils.showShort("已保存到:$fileName")
        }

    }
}