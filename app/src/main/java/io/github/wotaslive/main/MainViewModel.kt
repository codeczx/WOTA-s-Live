package io.github.wotaslive.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.blankj.utilcode.util.SPUtils
import com.orhanobut.logger.Logger
import io.github.wotaslive.Constants
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.data.worker.SyncWorker
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class MainViewModel(application: Application, private val appRepository: AppRepository) : AndroidViewModel(application) {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val spUtils = SPUtils.getInstance(Constants.SP_NAME)
    var isLogin = MutableLiveData<Boolean>()
    lateinit var nickname: String

    fun start() {
        work()
    }

    private fun work() {
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        val sync = PeriodicWorkRequest.Builder(SyncWorker::class.java, 1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()
//        val checkIn = OneTimeWorkRequest.Builder(CheckInWorker::class.java)
//                .setConstraints(constraints)
//                .build()
//        val refresh = OneTimeWorkRequest.Builder(RefreshWorker::class.java)
//                .setConstraints(constraints)
//                .build()
        WorkManager.getInstance().enqueue(sync)
//        WorkManager.getInstance()
//                .beginWith(refresh)
//                .then(checkIn)
//                .enqueue()
    }

    fun loadInfo() {
        nickname = spUtils.getString(Constants.SP_NICKNAME)
        isLogin.value = spUtils.getString(Constants.HEADER_KEY_TOKEN).isNotEmpty()
        Logger.d(spUtils.getString(Constants.HEADER_KEY_TOKEN))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}