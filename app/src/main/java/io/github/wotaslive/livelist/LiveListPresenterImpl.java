package io.github.wotaslive.livelist;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;

import io.github.wotaslive.data.AppRepository;
import io.github.wotaslive.data.model.LiveInfo;
import io.github.wotaslive.player.PlayerActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * Author codeczx
 * Created at 2017/10/10
 */
public class LiveListPresenterImpl implements LiveListContract.LiveListPresenter {

	private CompositeDisposable mCompositeDisposable;
	private LiveListContract.LiveListView mView;
	private Context mContext;
	private boolean isLockRefresh;

	LiveListPresenterImpl(Context context, LiveListContract.LiveListView view) {
		mView = view;
		mView.setPresenter(this);
		mContext = context;
		mCompositeDisposable = new CompositeDisposable();
	}


	@Override
	public void getMemberLive() {
		if (isLockRefresh)
			return;
		isLockRefresh = true;
		Disposable disposable = AppRepository.getInstance().getLiveInfo()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.doFinally(() -> {
					isLockRefresh = false;
					mView.refreshUI();
				})
				.subscribe(liveInfo -> {
					mView.updateLive(liveInfo.getContent().getLiveList());
					mView.updateReview(liveInfo.getContent().getReviewList());
				}, Throwable::printStackTrace);
		mCompositeDisposable.add(disposable);
	}

	@Override
	public void setClipboard(String text) {
		ClipboardManager cmb = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
		if (cmb != null) {
			ClipData clipData = ClipData.newPlainText(null, text);
			cmb.setPrimaryClip(clipData);
		}
	}

	@Override
	public void onMoreClick(LiveInfo.ContentBean.RoomBean room, View anchor) {
		mView.showMenu(room, anchor);
	}

	@Override
	public void onCoverClick(LiveInfo.ContentBean.RoomBean room, boolean isLive) {
		PlayerActivity.startPlayerActivity(mContext, room.getStreamPath(), isLive);
	}

	@Override
	public void unSubscribe() {
		if (!mCompositeDisposable.isDisposed()) {
			mCompositeDisposable.dispose();
		}
	}
}
