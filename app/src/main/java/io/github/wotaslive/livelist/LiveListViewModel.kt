package io.github.wotaslive.livelist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import io.github.wotaslive.R
import io.github.wotaslive.SingleLiveEvent
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.data.model.LiveInfo
import io.github.wotaslive.utils.RxJavaUtil
import io.reactivex.disposables.CompositeDisposable
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class LiveListViewModel(application: Application, private val appRepository: AppRepository) : AndroidViewModel(application) {
    val liveListData = MutableLiveData<List<LiveInfo.ContentBean.RoomBean>>()
    val errorCommand = SingleLiveEvent<Int>()
    var isLoadMore: Boolean = false
    private val compositeDisposable = CompositeDisposable()
    private var lastTime = 0L

    fun loadLives(isLoadMore: Boolean) {
        this.isLoadMore = isLoadMore
        if (!isLoadMore) {
            lastTime = 0
        }
        compositeDisposable.add(appRepository.getLiveInfo(lastTime)
                .compose(RxJavaUtil.flowableNetworkScheduler())
                .map {
                    val list = ArrayList<LiveInfo.ContentBean.RoomBean>()
                    list.addAll(it.content?.liveList.orEmpty())
                    list.addAll(it.content?.reviewList.orEmpty())
                    return@map list
                }
                .subscribe({
                    lastTime = it[it.size - 1].startTime
                    liveListData.value = it
                }, {
                    if (it is SocketTimeoutException || it is UnknownHostException)
                        errorCommand.value = R.string.timeout_exception
                    else
                        errorCommand.value = R.string.server_exception
                }))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}