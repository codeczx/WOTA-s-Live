package io.github.wotaslive.roomlist.room

import android.Manifest
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.databinding.ObservableField
import android.os.Environment
import android.support.v4.app.FragmentActivity
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.Gson
import com.tbruyelle.rxpermissions2.RxPermissions
import io.github.wotaslive.GlideApp
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.data.model.ExtInfo
import io.github.wotaslive.utils.checkUrl
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RoomViewModel(application: Application, private val appRepository: AppRepository) :
        AndroidViewModel(application) {
    val url = ObservableField<String>()
    val imageUrl = ObservableField<String>()
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
                .filter {
                    it.status == 200
                }
                .flatMap {
                    this@RoomViewModel.lastTime = it.content?.lastTime ?: 0
                    return@flatMap Flowable.fromIterable(it.content?.data)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    list.addAll(0, roomDetailData.value.orEmpty())
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
        val list = ArrayList(roomBoardData.value.orEmpty())
        val disposable = appRepository.getRoomBoard(roomId, boardLastTime)
                .filter {
                    it.status == 200
                }
                .flatMap {
                    boardLastTime = it.content?.lastTime ?: 0
                    return@flatMap Flowable.fromIterable(it.content?.data)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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

    fun saveToLocal(activity: FragmentActivity) {
        val url = imageUrl.get() ?: ""
        checkPermission(activity).subscribe {
            if (it) {
                val worker = Schedulers.io().createWorker()
                worker.schedule {
                    val file = GlideApp.with(activity)
                            .downloadOnly()
                            .load(checkUrl(url))
                            .submit()
                            .get()
                    val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    val fileName = path.absolutePath + "/" + url.substring(url.lastIndexOf("/") + 1)
                    FileUtils.copyFile(file.absolutePath, fileName, null)
                    ToastUtils.showShort("已保存到:$fileName")
                }
            }
        }

    }

    private fun checkPermission(activity: FragmentActivity): Observable<Boolean> {
        val rxPermissions = RxPermissions(activity)
        return rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}