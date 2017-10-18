package io.github.wotaslive.list;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;

import io.github.wotaslive.data.AppRepository;
import io.github.wotaslive.data.model.LiveInfo;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by codeczx on 2017/10/10.
 */

public class ListPresenterImpl implements ListContract.MemberLivePresenter {

	private Context mContext;
	private ListContract.MemberLiveView mView;

	ListPresenterImpl(Context context, ListContract.MemberLiveView view) {
		mContext = context;
		mView = view;
	}


	@Override
	public void getMemberLive() {
		AppRepository.getInstance().getLiveInfo()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(liveInfo -> {
					mView.updateLive(liveInfo.getContent().getLiveList());
					mView.updateReview(liveInfo.getContent().getReviewList());
					mView.refreshUI();
				}, error -> {
					error.printStackTrace();
					mView.refreshUI();
				}, () -> mView.refreshUI());
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
	public void onCoverClick(LiveInfo.ContentBean.RoomBean room) {
		//TODO enter live room
	}
}
