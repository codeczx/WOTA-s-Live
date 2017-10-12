package io.github.wotaslive.list;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.github.wotaslive.Constants;
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

	public ListPresenterImpl(Context context, ListContract.MemberLiveView view) {
		mContext = context;
		mView = view;
	}


	@Override
	public void getMemberLive() {
		AppRepository.getInstance().getLiveInfo()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(liveInfo -> {
					List<Object> list = new ArrayList<>();
					LiveInfo.ContentBean contentBean = liveInfo.getContent();
					list.add(Constants.MEMBER_LIVE_TYPE_LIVE);
					list.addAll(contentBean.getLiveList());
					if (contentBean.getLiveList().size() % 2 > 0) {
						list.add(null);
					}
					list.add(Constants.MEMBER_LIVE_TYPE_REVIEW);
					list.addAll(contentBean.getReviewList());
					mView.refreshUI(list);
				}, Throwable::printStackTrace);
	}
}
