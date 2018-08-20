package io.github.wotaslive.roomlist.all

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import io.github.wotaslive.data.AppDatabase
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.data.model.SyncInfo
import io.github.wotaslive.utils.RxJavaUtil
import io.reactivex.Flowable

class AllRoomListViewModel(application: Application, appRepository: AppRepository) : AndroidViewModel(application) {
    val groups = MutableLiveData<List<SyncInfo.Content.Group>>()
    val ids = MutableLiveData<List<List<Int>>>()
    val titles = MutableLiveData<List<String>>()

    private val appDatabase = AppDatabase.getInstance(application)

    fun start() {
        queryGroups()
        switchToGroup(0)
    }

    private fun queryGroups() {
        appDatabase
                .groupDao()
                .getAll()
                .compose(RxJavaUtil.flowableNetworkScheduler())
                .subscribe {
                    groups.value = it
                }
    }

    fun switchToGroup(groupId: Int) {
        val tempIds = ArrayList<List<Int>>()
        val tempTitles = ArrayList<String>()
        appDatabase.teamDao().getTeams(groupId)
                .flatMap { it ->
                    it.forEach {
                        tempTitles.add(it.team_name)
                    }
                    titles.value = tempTitles
                    Flowable.fromIterable(it)
                }
                .flatMap {
                    appDatabase.memberDao().getMemberByTeam(it.team_id)
                }
                .subscribe({ it ->
                    val list = ArrayList<Int>()
                    it.forEach {
                        list.add(it.member_id)
                    }
                    tempIds.add(list)
                }, {}, {
                    ids.value = tempIds
                })
    }
}