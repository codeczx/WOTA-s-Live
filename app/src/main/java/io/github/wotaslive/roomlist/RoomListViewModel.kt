package io.github.wotaslive.roomlist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import io.github.wotaslive.R
import io.github.wotaslive.SingleLiveEvent
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.data.model.RoomInfo
import io.github.wotaslive.utils.RxJavaUtil
import io.reactivex.disposables.CompositeDisposable

class RoomListViewModel(application: Application, private val appRepository: AppRepository) :
        AndroidViewModel(application) {
    val roomListData = MutableLiveData<List<RoomInfo.ContentBean>>()
    val roomMessageCommand = SingleLiveEvent<Int>()
    private val compositeDisposable = CompositeDisposable()

    fun start() {
        compositeDisposable.add(appRepository.roomList
                .compose(RxJavaUtil.flowableNetworkScheduler())
                .subscribe({
                    if (it.status == 401) {
                        roomMessageCommand.value = R.string.auth_fail
                    } else {
                        roomListData.value = it.content?.sortedByDescending {
                            it.commentTimeMs
                        }
                    }
                }, Throwable::printStackTrace))
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}