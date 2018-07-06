package io.github.wotaslive.login;

import android.content.Context;

import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import io.github.wotaslive.Constants;
import io.github.wotaslive.data.AppRepository;
import io.github.wotaslive.data.event.LoginEvent;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by codeczx on 2017/11/7 0:09.
 * Class description:
 */
public class LoginPresenterImpl {

	private Context mContext;
	private CompositeDisposable mCompositeDisposable;

	LoginPresenterImpl(Context context) {
		mContext = context;
		mCompositeDisposable = new CompositeDisposable();
	}

	public void performLogin(String username, String password) {
		Disposable disposable = AppRepository.getInstance().login(username, password)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(loginInfo -> {
					SPUtils spUtils = SPUtils.getInstance(Constants.SP_NAME);
					spUtils.put(Constants.HEADER_KEY_TOKEN, loginInfo.getContent().getToken());
					spUtils.put(Constants.SP_FRIENDS, new Gson().toJson(loginInfo.getContent().getFriends()));
					EventBus.getDefault().post(new LoginEvent());
				}, Throwable::printStackTrace);
		mCompositeDisposable.add(disposable);
	}

	public void unSubscribe() {
		if (!mCompositeDisposable.isDisposed()) {
			mCompositeDisposable.dispose();
		}
	}
}
