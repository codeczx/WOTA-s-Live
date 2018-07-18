package io.github.wotaslive.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v7.graphics.Palette
import android.text.TextUtils
import com.blankj.utilcode.util.SPUtils
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.github.florent37.materialviewpager.MaterialViewPager
import com.github.florent37.materialviewpager.header.HeaderDesign
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.github.wotaslive.Constants
import io.github.wotaslive.GlideApp
import io.github.wotaslive.R
import io.github.wotaslive.SingleLiveEvent
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.data.model.LoginInfo
import io.github.wotaslive.data.model.RecommendInfo
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
    private var maxImgSize = 3L
    private val spUtils = SPUtils.getInstance(Constants.SP_NAME)
    val headerRefreshCommand = SingleLiveEvent<MaterialViewPager.Listener>()
    val friendMessageCommand = SingleLiveEvent<Int>()
    var isLogin = MutableLiveData<Boolean>()
    lateinit var nickname: String

    fun start() {
        initHeader()
        refreshToken()
    }

    fun loadInfo() {
        nickname = spUtils.getString(Constants.SP_NICKNAME)
        isLogin.value = spUtils.getString(Constants.HEADER_KEY_TOKEN).isNotEmpty()
    }

    private fun refreshToken() {
        val username = spUtils.getString(Constants.SP_USERNAME)
        val password = spUtils.getString(Constants.SP_PASSWORD)
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) return
        val disposable = appRepository.login(username, password)
                .filter { it.status == 200 }
                .flatMap {
                    checkFriends(it.content)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    friendMessageCommand.value = R.string.friend_reload
                },
                        Throwable::printStackTrace)
        compositeDisposable.add(disposable)
    }

    private fun checkFriends(content: LoginInfo.ContentBean?): Flowable<Boolean> {
        val gson = Gson()
        return Flowable.create({
            with(content) {
                val oldFriends = spUtils.getString(io.github.wotaslive.Constants.SP_FRIENDS)
                val newFriends = gson.toJson(this?.friends)
                this?.token?.let { it1 -> spUtils.put(Constants.HEADER_KEY_TOKEN, it1) }
                spUtils.put(Constants.SP_FRIENDS, newFriends)
                if (android.text.TextUtils.isEmpty(oldFriends)) {
                    it.onNext(true)
                } else {
                    val listType = object : TypeToken<ArrayList<Int>>() {}.type
                    val newFriendlist: ArrayList<Int> = gson.fromJson(newFriends, listType)
                    val oldFriendList: ArrayList<Int> = gson.fromJson(oldFriends, listType)
                    it.onNext(!TextUtils.equals(newFriendlist.sort().toString(), oldFriendList.sort().toString()))
                }
            }
        }, BackpressureStrategy.BUFFER)
    }

    private fun initHeader() {
        val disposable = Flowable.mergeArray(appRepository.recommendList
                .delay(3, TimeUnit.SECONDS)
                .flatMap { t: RecommendInfo -> Flowable.fromIterable(t.content) }
                .take(maxImgSize)
                .flatMap { t -> Flowable.just(AppRepository.IMG_BASE_URL + t.picPath) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { t -> createBitmapFlowable(t, getApplication()) })
                .observeOn(Schedulers.io())
                .flatMap { t -> createPaletteFlowable(t, getApplication()) }
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    if (mHeaderArray.size > 0)
                        headerRefreshCommand.value = this@MainViewModel
                }
                .subscribe({ t ->
                    mHeaderArray.add(t)
                }, { t -> t.printStackTrace() })
        compositeDisposable.add(disposable)
    }

    private fun createBitmapFlowable(url: String, context: Context): Flowable<Bitmap> {
        return Flowable.create({ e: FlowableEmitter<Bitmap> ->
            GlideApp.with(context)
                    .asBitmap()
                    .override(480, 360)
                    .load(url)
                    .into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            resource.let {
                                e.onNext(resource)
                            }
                            e.onComplete()
                        }
                    })
        }, BackpressureStrategy.BUFFER)
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