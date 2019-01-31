package io.github.wotaslive.roomlist.room

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import com.google.gson.Gson
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.data.model.ExtInfo
import io.github.wotaslive.utils.RxJavaUtil
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable

class RoomViewModel(application: Application, private val appRepository: AppRepository) :
        AndroidViewModel(application) {
    val url = ObservableField<String>()
    val roomDetailData = MutableLiveData<List<Any>>()
    val roomBoardData = MutableLiveData<List<ExtInfo>>()
    var isLoadMore = false
    var roomId = 0
    var memberId = 0
    private var firstSend = 0L
    private val compositeDisposable = CompositeDisposable()
    private var lastTime = 0L
    private val defaultInterval = 1000 * 60 * 5L
    private val gson = Gson()
    private var boardLastTime = 0L

    fun load() {
        loadRoom()
        loadBoard(false)
    }

    fun loadRoom() {
        val list = ArrayList<Any>()
        val disposable = appRepository.getRoomDetailInfo(roomId, lastTime)
                .compose(RxJavaUtil.flowableNetworkScheduler())
                .filter {
                    it.status == 200
                }
                .flatMap {
                    this@RoomViewModel.lastTime = it.content?.lastTime ?: 0
                    return@flatMap Flowable.fromIterable(it.content?.data)
                }
                .doFinally {
                    roomDetailData.value = list
                }
                .subscribe({
                    if (firstSend - it.msgTime > defaultInterval) {
                        list.add(firstSend)
                    }
                    val obj = gson.fromJson(it.extInfo, ExtInfo::class.java)
                    obj.bodys = it.bodys
                    obj.msgTime = it.msgTime
                    list.add(obj)
                    firstSend = it.msgTime
                }, Throwable::printStackTrace)
        compositeDisposable.add(disposable)
    }

    fun loadBoard(isMore: Boolean) {
        isLoadMore = isMore
        if (!isLoadMore)
            boardLastTime = 0
        val list = ArrayList<ExtInfo>()
        val disposable = appRepository.getRoomBoard(roomId, boardLastTime)
                .compose(RxJavaUtil.flowableNetworkScheduler())
                .filter {
                    it.status == 200
                }
                .flatMap {
                    boardLastTime = it.content?.lastTime ?: 0
                    return@flatMap Flowable.fromIterable(it.content?.data)
                }
                .doFinally {
                    roomBoardData.value = list
                }
                .subscribe({
                    list.add(gson.fromJson(it.extInfo, ExtInfo::class.java).run {
                        this.msgTime = it.msgTime
                        this
                    })
                }, Throwable::printStackTrace)
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}