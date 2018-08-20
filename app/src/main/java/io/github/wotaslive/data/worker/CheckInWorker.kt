package io.github.wotaslive.data.worker

import androidx.work.Worker
import com.blankj.utilcode.util.ToastUtils
import io.github.wotaslive.data.AppRepository
import io.reactivex.schedulers.Schedulers

class CheckInWorker : Worker() {
    override fun doWork(): Result {
        AppRepository.instance.checkCardInfo()
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .filter {
                    it.status == 200
                }
                .filter {
                    !it.content.todayPunchCard
                }.flatMap {
                    AppRepository.instance.doCheckIn()
                }
                .filter {
                    it.status == 200
                }
                .subscribe({
                    ToastUtils.showShort("自动签到成功！累计签到${it.content.days}天")
                }, Throwable::printStackTrace)
        return Result.SUCCESS
    }
}