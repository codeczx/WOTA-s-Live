package io.github.wotaslive.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v7.graphics.Palette
import androidx.work.*
import com.blankj.utilcode.util.SPUtils
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.github.florent37.materialviewpager.MaterialViewPager
import com.github.florent37.materialviewpager.header.HeaderDesign
import com.orhanobut.logger.Logger
import io.github.wotaslive.Constants
import io.github.wotaslive.GlideApp
import io.github.wotaslive.SingleLiveEvent
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.data.worker.CheckInWorker
import io.github.wotaslive.data.worker.RefreshWorker
import io.github.wotaslive.data.worker.SyncWorker
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainViewModel(application: Application, private val appRepository: AppRepository) : AndroidViewModel(application), MaterialViewPager.Listener {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val mHeaderArray = ArrayList<HeaderDesign>()
    private var maxImgSize = 5L
    private val spUtils = SPUtils.getInstance(Constants.SP_NAME)
    val headerRefreshCommand = SingleLiveEvent<MaterialViewPager.Listener>()
    var isLogin = MutableLiveData<Boolean>()
    lateinit var nickname: String

    fun start() {
        work()
        initHeader()
    }

    private fun work() {
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        val sync = PeriodicWorkRequest.Builder(SyncWorker::class.java, 1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()
        val checkIn = PeriodicWorkRequest.Builder(CheckInWorker::class.java, 1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()
        val refresh = OneTimeWorkRequest.Builder(RefreshWorker::class.java)
                .setConstraints(constraints)
                .build()
        WorkManager.getInstance().enqueue(sync)
        WorkManager.getInstance().enqueue(checkIn)
        WorkManager.getInstance().enqueue(refresh)
    }

    fun loadInfo() {
        nickname = spUtils.getString(Constants.SP_NICKNAME)
        isLogin.value = spUtils.getString(Constants.HEADER_KEY_TOKEN).isNotEmpty()
        Logger.d(spUtils.getString(Constants.HEADER_KEY_TOKEN))
    }

    private fun initHeader() {
        val disposable = Flowable.mergeArray(appRepository.recommendList
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .flatMap { Flowable.fromIterable(it.content) }
                .take(maxImgSize)
                .flatMap { Flowable.just(AppRepository.IMG_BASE_URL + "resize_800X800/" + it.picPath) }
                .flatMap { createBitmapFlowable(it, getApplication()) })
                .flatMap { createPaletteFlowable(it, getApplication()) }
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    if (mHeaderArray.size > 0)
                        headerRefreshCommand.value = this@MainViewModel
                }
                .subscribe({ t ->
                    mHeaderArray.add(t)
                }, Throwable::printStackTrace)
        compositeDisposable.add(disposable)
    }

    private fun createBitmapFlowable(url: String, context: Context): Flowable<Bitmap> {
        return Flowable.create({ e: FlowableEmitter<Bitmap> ->
            GlideApp.with(context)
                    .asBitmap()
                    .load(url)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            resource.let {
                                e.onNext(resource)
                            }
                            e.onComplete()
                        }
                    })
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(AndroidSchedulers.mainThread())
    }

    private fun createPaletteFlowable(bitmap: Bitmap, context: Context): Flowable<HeaderDesign> {
        return Flowable.create({ e: FlowableEmitter<HeaderDesign> ->
            val swatch = Palette.from(bitmap).generate().vibrantSwatch
            swatch?.let {
                e.onNext(HeaderDesign.fromColorAndDrawable(swatch.rgb, BitmapDrawable(context.resources, bitmap)))
            }
            e.onComplete()
        }, BackpressureStrategy.BUFFER)
    }

    override fun getHeaderDesign(page: Int): HeaderDesign {
        return if (page < mHeaderArray.size)
            mHeaderArray[page]
        else
            mHeaderArray[mHeaderArray.size - 1]
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}