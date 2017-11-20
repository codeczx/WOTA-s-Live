package io.github.wotaslive.room;


import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.github.wotaslive.data.AppRepository;
import io.github.wotaslive.data.model.ExtInfo;
import io.github.wotaslive.data.model.RoomDetailInfo;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by codeczx on 2017/11/9 0:22.
 * Class description:
 */
public class RoomDetailPresenterImpl implements RoomDetailContract.RoomDetailPresenter {

	private Context mContext;
	private RoomDetailContract.RoomDetailView mView;
	private boolean isLockRefresh;
	private CompositeDisposable mCompositeDisposable;
	private long mLastTime;

	RoomDetailPresenterImpl(Context context, RoomDetailContract.RoomDetailView view) {
		mContext = context;
		mView = view;
		mView.setPresenter(this);
		mCompositeDisposable = new CompositeDisposable();
	}

	@Override
	public void unSubscribe() {
		if (!mCompositeDisposable.isDisposed()) {
			mCompositeDisposable.dispose();
		}
	}

	@Override
	public void getRoomDetailInfo(int roomId) {
		if (isLockRefresh) {
			return;
		}
		isLockRefresh = true;
		Disposable disposable = AppRepository.getInstance().getRoomDetailInfo(roomId, mLastTime)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.doFinally(() -> {
					isLockRefresh = false;
					mView.refreshUI();
				})
				.subscribe(roomDetailInfo -> {
					List<ExtInfo> extInfoList = new ArrayList<>();
					for (RoomDetailInfo.ContentBean.DataBean dataBean : roomDetailInfo.getContent().getData()) {
						ExtInfo extInfo = new Gson().fromJson(dataBean.getExtInfo(), ExtInfo.class);
						extInfoList.add(extInfo);
					}
					mLastTime = roomDetailInfo.getContent().getLastTime();
					mView.updateData(extInfoList, roomDetailInfo.getContent().getData());
				}, Throwable::printStackTrace);
		mCompositeDisposable.add(disposable);
	}
}
