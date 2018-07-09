package io.github.wotaslive.room

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import com.google.gson.Gson
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.data.model.ExtInfo
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RoomViewModel(application: Application, private val appRepository: AppRepository) :
        AndroidViewModel(application) {
    val url = ObservableField<String>()
    val roomDetailData = MutableLiveData<List<Any>>()
    var roomId = 0
    private var firstSend = 0L
    private val compositeDisposable = CompositeDisposable()
    private var lastTime = 0L
    private val defaultInterval = 1000 * 60 * 5L
    private val gson = Gson()

    fun load() {
        val list = ArrayList<Any>()
        val disposable = appRepository.getRoomDetailInfo(roomId, lastTime)
                .flatMap {
                    with(it.content) {
                        this@RoomViewModel.lastTime = lastTime
                        return@flatMap Flowable.fromIterable(data)
                    }
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    roomDetailData.value = list
                }
                .subscribe {
                    if (firstSend - it.msgTime > defaultInterval) {
                        list.add(0, firstSend)
                    }
                    list.add(0, gson.fromJson(it.extInfo, ExtInfo::class.java))
                    firstSend = it.msgTime
                }
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}