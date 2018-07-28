package io.github.wotaslive.data

import androidx.work.Worker
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import io.github.wotaslive.Constants
import io.github.wotaslive.data.model.SyncRequestBody
import io.reactivex.schedulers.Schedulers

class SyncWorker : Worker() {
    override fun doWork(): Result {
        AppRepository.instance
                .getSync()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .filter { it.status == 200 }
                .subscribe({
                    val spUtils = SPUtils.getInstance(Constants.SP_NAME)
                    val functionList = it.content.function
                    val groupList = it.content.group
                    val periodList = it.content.period
                    val urlList = it.content.url
                    val teamList = it.content.team
                    val memberList = it.content.memberInfo
                    val cacheSync = Gson().fromJson(spUtils.getString(Constants.SP_SYNC), SyncRequestBody::class.java)
                    val cache = SyncRequestBody(
                            if (functionList.isNotEmpty()) functionList[functionList.size - 1].utime else cacheSync.functionUtime,
                            if (groupList.isNotEmpty()) groupList[groupList.size - 1].utime else cacheSync.groupUtime,
                            if (periodList.isNotEmpty()) periodList[periodList.size - 1].utime else cacheSync.periodUtime,
                            if (urlList.isNotEmpty()) urlList[urlList.size - 1].utime else cacheSync.urlUtime,
                            if (teamList.isNotEmpty()) teamList[teamList.size - 1].utime else cacheSync.teamUtime,
                            if (memberList.isNotEmpty()) memberList[memberList.size - 1].utime else cacheSync.memberInfoUtime)

                    // cache this update time
                    spUtils.put(Constants.SP_SYNC, Gson().toJson(cache))
                    // update all data
                    AppDatabase.getInstance(applicationContext).let {
                        groupList.forEach { it1 ->
                            it.groupDao().addGroup(it1)
                        }
                        teamList.forEach { it1 ->
                            it.teamDao().addTeam(it1)
                        }
                        periodList.forEach { it1 ->
                            it.periodDao().addPeriod(it1)
                        }
                        memberList.forEach { it1 ->
                            it.memberDao().addMember(it1)
                        }
                    }
                }, Throwable::printStackTrace)
        return Result.SUCCESS
    }
}