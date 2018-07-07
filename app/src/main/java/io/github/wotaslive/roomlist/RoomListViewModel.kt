package io.github.wotaslive.roomlist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.data.model.RoomInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class RoomListViewModel(application: Application, private val appRepository: AppRepository) :
        AndroidViewModel(application) {
    val roomListData = MutableLiveData<List<RoomInfo.ContentBean>>()
    private val compositeDisposable = CompositeDisposable()

    fun start() {
        compositeDisposable.add(appRepository.roomList
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    it.content.sortWith(Comparator { o1, o2 -> (o2.commentTimeMs - o1.commentTimeMs).toInt() })
                    roomListData.value = it.content
                }, Throwable::printStackTrace))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}