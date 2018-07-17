package io.github.wotaslive.showlist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.data.model.ShowInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ShowListViewModel(application: Application, private val appRepository: AppRepository) : AndroidViewModel(application) {
    val showListData = MutableLiveData<List<ShowInfo.ContentBean.ShowBean>>()
    private val compositeDisposable = CompositeDisposable()

    fun start() {
        val disposable = AppRepository.instance.getOpenLiveInfo(0, 0, 0, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t ->
                    showListData.value = t.content?.showList
                }, Throwable::printStackTrace)
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}