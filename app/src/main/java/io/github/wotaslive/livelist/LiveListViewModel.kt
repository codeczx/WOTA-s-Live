package io.github.wotaslive.livelist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.data.model.LiveInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LiveListViewModel(application: Application, private val appRepository: AppRepository) : AndroidViewModel(application) {
    val liveListData = MutableLiveData<List<LiveInfo.ContentBean.RoomBean>>()
    var isLoadMore: Boolean = false
    private val compositeDisposable = CompositeDisposable()
    private var lastTime = 0L

    fun start() {
        loadLives(false)
    }

    fun loadLives(isLoadMore: Boolean) {
        this.isLoadMore = isLoadMore
        if (!isLoadMore) {
            lastTime = 0
        }
        compositeDisposable.add(appRepository.getLiveInfo(lastTime)
                .map {
                    val list = ArrayList<LiveInfo.ContentBean.RoomBean>()
                    val value = liveListData.value
                    if (isLoadMore) {
                        value?.let {
                            list.addAll(it)
                        }
                    }
                    val live = it.content.liveList
                    val review = it.content.reviewList
                    live?.let {
                        it.forEach {
                            it.liveType = 1
                        }
                    }
                    review?.let {
                        it.forEach {
                            it.liveType = 2
                        }
                    }
                    list.addAll(live)
                    list.addAll(review)
                    return@map list
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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