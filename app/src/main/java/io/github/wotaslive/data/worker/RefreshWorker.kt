package io.github.wotaslive.data.worker

import android.content.Context
import android.text.TextUtils
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import io.github.wotaslive.Constants
import io.github.wotaslive.data.AppRepository
import io.reactivex.schedulers.Schedulers

class RefreshWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {
    private val spUtils = SPUtils.getInstance(Constants.SP_NAME)

    override fun doWork(): Result {
        val username = spUtils.getString(Constants.SP_USERNAME)
        val password = spUtils.getString(Constants.SP_PASSWORD)
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) return Result.failure()
        AppRepository.instance.login(username, password)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .filter { it.status == 200 }
                .subscribe({ it ->
                    spUtils.put(Constants.SP_NICKNAME, it.content?.userInfo?.nickName.orEmpty())
                    with(it.content) {
                        this?.token?.let { it1 -> spUtils.put(io.github.wotaslive.Constants.HEADER_KEY_TOKEN, it1) }
                        this?.friends?.let {
                            AppRepository.friends.clear()
                            AppRepository.friends.addAll(ArrayList(it))
                            spUtils.put(io.github.wotaslive.Constants.SP_FRIENDS, Gson().toJson(it))
                        }
                    }
                },
                        Throwable::printStackTrace)
        return Result.success()
    }
}