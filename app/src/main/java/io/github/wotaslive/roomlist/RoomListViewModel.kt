package io.github.wotaslive.roomlist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableBoolean
import io.github.wotaslive.R
import io.github.wotaslive.SingleLiveEvent
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.data.model.RoomInfo
import io.github.wotaslive.utils.RxJavaUtil
import io.reactivex.disposables.CompositeDisposable
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class RoomListViewModel(application: Application, private val appRepository: AppRepository) :
        AndroidViewModel(application) {
    val roomListData = MutableLiveData<List<RoomInfo.ContentBean>>()
    val errorCommand = SingleLiveEvent<Int>()
    val memberIds = ArrayList<Int>()
    val isMain = ObservableBoolean()
    val roomMessageCommand = SingleLiveEvent<Int>()
    private val compositeDisposable = CompositeDisposable()

    fun start() {
        if (isMain.get()) {
            memberIds.clear()
            memberIds.addAll(AppRepository.friends)
        }
        compositeDisposable.add(appRepository.roomList(memberIds)
                .compose(RxJavaUtil.flowableNetworkScheduler())
                .subscribe({ it ->
                    if (it.status == 401) {
                        roomMessageCommand.value = R.string.auth_fail
                    } else {
                        roomListData.value = it.content?.sortedByDescending {
                            it.commentTimeMs
                        }
                    }
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