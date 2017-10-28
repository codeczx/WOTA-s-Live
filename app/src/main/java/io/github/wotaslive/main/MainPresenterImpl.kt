package io.github.wotaslive.main

import android.graphics.Color
import com.github.florent37.materialviewpager.header.HeaderDesign
import io.github.wotaslive.data.AppRepository
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
    private val mHeaderImgArray = ArrayList<String>()
    private var maxImgSize = 5

    init {
        mView.setPresenter(this)
    }

    override fun subscribe() {
        val disposable = AppRepository.getInstance().recommendList
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t ->
                    if (t.content != null && t.content.size > maxImgSize) {
                        (0..maxImgSize).mapTo(mHeaderImgArray) { t.content[it].picPath }
                        mView.updateHeader(this)
                    }

                }, { t -> t.printStackTrace() })
        mCompositeDisposable.add(disposable)
    }

    override fun unSubscribe() {
        if (mCompositeDisposable.isDisposed) {
            mCompositeDisposable.dispose()
        }
    }

    override fun getHeaderDesign(page: Int): HeaderDesign {
        val rnd = Random()
        val color = Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        return HeaderDesign.fromColorAndUrl(color, AppRepository.IMG_BASE_URL + mHeaderImgArray[page % maxImgSize])
    }
}