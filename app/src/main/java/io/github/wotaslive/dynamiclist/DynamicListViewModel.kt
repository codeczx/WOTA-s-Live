package io.github.wotaslive.dynamiclist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.orhanobut.logger.Logger
import io.github.wotaslive.data.AppRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class DynamicListViewModel(application: Application, private val appRepository: AppRepository) : AndroidViewModel(application) {
    var isLoad = false
    private var lastTime = 0L
    private val compositeDisposable = CompositeDisposable()

    fun load(isLoad: Boolean) {
        if (!isLoad) lastTime = 0
        val disposable = appRepository.getDynamicList(lastTime)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Logger.d(it.content.data.size)
                }, Throwable::printStackTrace)
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}