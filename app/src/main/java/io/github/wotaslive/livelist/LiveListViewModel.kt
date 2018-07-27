package io.github.wotaslive.livelist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.data.model.LiveInfo
import io.github.wotaslive.utils.RxJavaUtil
import io.reactivex.disposables.CompositeDisposable

class LiveListViewModel(application: Application, private val appRepository: AppRepository) : AndroidViewModel(application) {
    val liveListData = MutableLiveData<List<LiveInfo.ContentBean.RoomBean>>()
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
                    if (isLoadMore) {
                        list.addAll(liveListData.value.orEmpty())
                    }
                    val live = it.content?.liveList
                    val review = it.content?.reviewList
                    live?.let {
                        it.forEach {
                            it.liveType = 1
                        }
                        list.addAll(it)
                    }
                    review?.let {
                        it.forEach {
                            it.liveType = 2
                        }
                        list.addAll(it)
                    }
                    return@map list
                }
                .subscribe({
                    lastTime = it[it.size - 1].startTime
                    liveListData.value = it
                }, Throwable::printStackTrace))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}