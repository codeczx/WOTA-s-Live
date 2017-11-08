package io.github.wotaslive.showlist

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.data.model.ShowInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Tony on 2017/10/22 13:43.
 * Class description:
 */
class ShowListPresenterImpl(context: Context, view: ShowListContract.ShowListView) : ShowListContract.ShowListPresenter {
    private val mContext = context

    private val mView = view
    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private var isLockRefresh: Boolean = false

    init {
        mView.setPresenter(this)
    }

    override fun unSubscribe() {
        if (!mCompositeDisposable.isDisposed) {
            mCompositeDisposable.dispose()
        }
    }

    override fun getShowList() {
        val disposable = AppRepository.getInstance().getOpenLiveInfo(0, 0, 0, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    isLockRefresh = false
                    mView.refreshUI()
                }
                .subscribe({ t ->
                    mView.updateShow(t.content.showList)
                }, { t ->
                    t.printStackTrace()
                })
        mCompositeDisposable.add(disposable)
    }

    override fun setClipboard(text: String) {
        val cmb = mContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData = ClipData.newPlainText(null, text)
        cmb.primaryClip = clipData
    }

    override fun onCoverClick(show: ShowInfo.ContentBean.ShowBean) {
    }
}
