package io.github.wotaslive.roomlist;

import android.content.Context;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.FileOutputStream;
import java.io.IOException;

import io.github.wotaslive.Constants;
import io.github.wotaslive.data.AppRepository;
import io.github.wotaslive.data.event.LoginEvent;
import io.github.wotaslive.data.model.LoginInfo;
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
		if (isLockRefresh) {
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

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onLoginEvent(LoginEvent loginEvent) {
		try {
			saveLoginInfo(loginEvent.getLoginInfo());
		} catch (IOException e) {
			e.printStackTrace();
		}
		mView.showRoomList();
	}

	private void saveLoginInfo(LoginInfo loginInfo) throws IOException {
		Gson gson = new Gson();
		String login = gson.toJson(loginInfo);
		FileOutputStream fos = null;
		try {
			fos = mContext.openFileOutput(Constants.CACHE_FRIENDS, Context.MODE_PRIVATE);
			fos.write(login.getBytes());
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}

	@Override
	public void subscribe() {
		EventBus.getDefault().register(this);
	}

	@Override
	public void unSubscribe() {
		EventBus.getDefault().unregister(this);
		if (!mCompositeDisposable.isDisposed()) {
			mCompositeDisposable.isDisposed();
		}
	}
}
