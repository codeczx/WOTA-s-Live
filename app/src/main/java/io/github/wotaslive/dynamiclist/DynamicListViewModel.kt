package io.github.wotaslive.dynamiclist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import io.github.wotaslive.data.AppDatabase
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.data.model.DynamicInfo
import io.github.wotaslive.data.model.SyncInfo
import io.github.wotaslive.utils.RxJavaUtil
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable

class DynamicListViewModel(application: Application, private val appRepository: AppRepository) : AndroidViewModel(application) {
    val members = MutableLiveData<Map<Int, SyncInfo.Content.MemberInfo>>()
    val data = MutableLiveData<List<DynamicInfo.Content.Data>>()
    var isLoad = false
    private var lastTime = 0L
    private val compositeDisposable = CompositeDisposable()
    private val dao = AppDatabase.getInstance(getApplication()).memberDao()

    fun load(isLoad: Boolean) {
        if (!isLoad) lastTime = 0
        this.isLoad = isLoad
        val map = HashMap<Int, SyncInfo.Content.MemberInfo>(members.value.orEmpty())
        val list = ArrayList<DynamicInfo.Content.Data>()
        val disposable = appRepository.getDynamicList(lastTime)
                .flatMap {
                    with(it.content.data) {
                        if (size > 0) {
                            lastTime = get(size - 1).ctime
                        }
                        forEach { it1 ->
                            if (!map.containsKey(it1.memberId)) {
                                map[it1.memberId] = dao.getMember(it1.memberId)
                            }
                            list.add(it1)
                        }
                    }
                    Flowable.just(true)
                }
                .compose(RxJavaUtil.flowableNetworkScheduler())
                .subscribe({
                    members.value = map
                    data.value = list
                }, Throwable::printStackTrace)
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}