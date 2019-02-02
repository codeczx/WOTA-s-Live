package io.github.wotaslive.showlist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import io.github.wotaslive.R
import io.github.wotaslive.SingleLiveEvent
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.data.model.ShowInfo
import io.github.wotaslive.utils.RxJavaUtil
import io.reactivex.disposables.CompositeDisposable
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ShowListViewModel(application: Application, private val appRepository: AppRepository) : AndroidViewModel(application) {
    val showListData = MutableLiveData<List<ShowInfo.ContentBean.ShowBean>>()
    val errorCommand = SingleLiveEvent<Int>()
    private val compositeDisposable = CompositeDisposable()

    fun start() {
        val disposable = AppRepository.instance.getOpenLiveInfo(0, 0, 0, 0)
                .compose(RxJavaUtil.flowableNetworkScheduler())
                .subscribe({ t ->
                    showListData.value = t.content?.showList
                }, {
                    if (it is SocketTimeoutException || it is UnknownHostException)
                        errorCommand.value = R.string.timeout_exception
                    else
                        errorCommand.value = R.string.server_exception
                })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}