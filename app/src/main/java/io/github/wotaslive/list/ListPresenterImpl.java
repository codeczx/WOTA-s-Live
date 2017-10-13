package io.github.wotaslive.list;

import android.content.Context;

import io.github.wotaslive.data.AppRepository;
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
				}, Throwable::printStackTrace, () -> mView.refreshUI());
	}
}
