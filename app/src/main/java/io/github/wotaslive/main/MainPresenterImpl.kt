package io.github.wotaslive.main

import android.Manifest
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v7.graphics.Palette
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.github.florent37.materialviewpager.header.HeaderDesign
import com.tbruyelle.rxpermissions2.RxPermissions
import io.github.wotaslive.GlideApp
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.data.model.RecommendInfo
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * Created by Tony on 2017/10/29 1:20.
 * Class description:
 */
class MainPresenterImpl(view: MainContract.MainView) : MainContract.MainPresenter {
    private var mView: MainContract.MainView = view
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private val mHeaderArray = ArrayList<HeaderDesign>()
    private var maxImgSize = 5

    init {
        mView.setPresenter(this)
    }

    override fun loanHeader(context: Context) {
        val rxPermissions = RxPermissions(context as Activity)
        rxPermissions.request(Manifest.permission.READ_PHONE_STATE).subscribe {
            if (it)
                initHeader(context)
            else
                context.finish()
        }

    }

    private fun initHeader(context: Context) {
        val disposable = Flowable.mergeArray(AppRepository.getInstance().recommendList
                .flatMap { t: RecommendInfo -> Flowable.fromIterable(t.content) }
                .take(maxImgSize.toLong())
                .flatMap { t -> Flowable.just(AppRepository.IMG_BASE_URL + t.picPath) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap { t -> createBitmapFlowable(t, context) })
                .observeOn(Schedulers.io())
                .flatMap { t -> createPaletteFlowable(t, context) }
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    if (mHeaderArray.size > 0)
                        mView.updateHeader(this@MainPresenterImpl)
                }
                .subscribe({ t ->
                    mHeaderArray.add(t)
                }, { t -> t.printStackTrace() })
        mCompositeDisposable.add(disposable)
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

    override fun unSubscribe() {
        if (mCompositeDisposable.isDisposed) {
            mCompositeDisposable.dispose()
        }
    }

    override fun getHeaderDesign(page: Int): HeaderDesign {
        return if (page < mHeaderArray.size)
            mHeaderArray[page]
        else
            mHeaderArray[mHeaderArray.size - 1]
    }
}