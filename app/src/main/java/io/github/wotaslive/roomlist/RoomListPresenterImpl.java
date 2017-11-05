package io.github.wotaslive.roomlist;

import android.content.Context;

import io.github.wotaslive.data.AppRepository;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by codeczx on 2017/11/2 21:24.
 * Class description:
 */
public class RoomListPresenterImpl implements RoomListContract.RoomListPresenter {

	private Context mContext;
	private RoomListContract.RoomListView mView;
	private boolean isLockRefresh;
	private CompositeDisposable mCompositeDisposable;

	RoomListPresenterImpl(Context context, RoomListContract.RoomListView roomListView) {
		mContext = context;
		mView = roomListView;
		mView.setPresenter(this);
		mCompositeDisposable = new CompositeDisposable();
	}

	@Override
	public void getRoomList() {
		if(isLockRefresh){
			return;
		}
		Disposable disposable = AppRepository.getInstance().getRoomList()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.doFinally(() -> {
					isLockRefresh = false;
					mView.refreshUI();
				})
				.subscribe(roomInfo -> mView.updateRoom(roomInfo.getContent()),
						Throwable::printStackTrace);
		mCompositeDisposable.add(disposable);
	}

	@Override
	public void unSubscribe() {
		if (!mCompositeDisposable.isDisposed()) {
			mCompositeDisposable.isDisposed();
		}
	}
}
