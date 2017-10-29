package io.github.wotaslive.main

import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.v7.graphics.Palette
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.github.florent37.materialviewpager.header.HeaderDesign
import io.github.wotaslive.GlideApp
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.data.model.RecommendInfo
import io.reactivex.Flowable
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
    private var maxImgSize = 10

    init {
        mView.setPresenter(this)
    }

    override fun loanHeader(context: Context) {
        val disposable = AppRepository.getInstance().recommendList
                .flatMap({ t: RecommendInfo -> Flowable.fromIterable(t.content) })
                .take(maxImgSize.toLong())
                .map { t -> AppRepository.IMG_BASE_URL + t.picPath }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t ->
                    GlideApp.with(context)
                            .asDrawable()
                            .load(t)
                            .into(object : SimpleTarget<Drawable>() {
                                override fun onResourceReady(resource: Drawable?, transition: Transition<in Drawable>?) {
                                    if (resource is BitmapDrawable) {
                                        Palette.from(resource.bitmap).generate { palette ->
                                            palette.lightVibrantSwatch?.rgb?.let {
                                                mHeaderArray.add(HeaderDesign.fromColorAndDrawable(it, resource))
                                                mView.updateHeader(this@MainPresenterImpl)
                                            }
                                        }
                                    }
                                }
                            })
                }, { t -> t.printStackTrace() })
        mCompositeDisposable.add(disposable)
    }

    override fun unSubscribe() {
        if (mCompositeDisposable.isDisposed) {
            mCompositeDisposable.dispose()
        }
    }

    override fun getHeaderDesign(page: Int): HeaderDesign {
        val random = Random()
        return mHeaderArray[random.nextInt(mHeaderArray.size)]
    }
}