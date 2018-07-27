package io.github.wotaslive.showlist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.data.model.ShowInfo
import io.github.wotaslive.utils.RxJavaUtil
import io.reactivex.disposables.CompositeDisposable

class ShowListViewModel(application: Application, private val appRepository: AppRepository) : AndroidViewModel(application) {
    val showListData = MutableLiveData<List<ShowInfo.ContentBean.ShowBean>>()
    private val compositeDisposable = CompositeDisposable()

    fun start() {
        val disposable = AppRepository.instance.getOpenLiveInfo(0, 0, 0, 0)
                .compose(RxJavaUtil.flowableNetworkScheduler())
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