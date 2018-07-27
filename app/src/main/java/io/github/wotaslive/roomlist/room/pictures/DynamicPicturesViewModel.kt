package io.github.wotaslive.roomlist.room.pictures

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.data.model.DynamicPictureInfo
import io.github.wotaslive.utils.RxJavaUtil
import io.reactivex.disposables.CompositeDisposable

class DynamicPicturesViewModel(application: Application, private val appRepository: AppRepository) :
        AndroidViewModel(application) {
    var memberId: Int = 0
    var isLoadMore = false
    val picsData = MutableLiveData<List<DynamicPictureInfo.Content.Data>>()
    private var lastTime = 0L
    private val compositeDisposable = CompositeDisposable()

    fun load(load: Boolean) {
        isLoadMore = load
        if (!isLoadMore) lastTime = 0
        val list = ArrayList<DynamicPictureInfo.Content.Data>()
        val disposable = appRepository.getDynamicPictures(memberId, lastTime)
                .compose(RxJavaUtil.flowableNetworkScheduler())
                .doFinally {
                    if (isLoadMore)
                        list.addAll(0, picsData.value.orEmpty())
                    picsData.value = list
                }
                .subscribe({
                    if (it.status == 200) {
                        list.addAll(it.content.data)
                        if (list.size > 0) {
                            lastTime = list[list.size - 1].ctime
                        }
                    }
                }, Throwable::printStackTrace)
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}