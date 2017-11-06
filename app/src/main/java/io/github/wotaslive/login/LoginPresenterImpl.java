package io.github.wotaslive.login;

import android.content.Context;

import com.blankj.utilcode.util.SPUtils;

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
public class LoginPresenterImpl implements LoginContract.LoginPresenter {

	private Context mContext;
	private LoginContract.LoginView mView;
	private CompositeDisposable mCompositeDisposable;

	LoginPresenterImpl(Context context, LoginContract.LoginView view) {
		mContext = context;
		mView = view;
		mView.setPresenter(this);
		mCompositeDisposable = new CompositeDisposable();
	}

	@Override
	public void performLogin(String username, String password) {
		Disposable disposable = AppRepository.getInstance().login(username, password)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(loginInfo -> {
					SPUtils spUtils = SPUtils.getInstance(Constants.SP_NAME);
					spUtils.put(Constants.HEADER_KEY_TOKEN, loginInfo.getContent().getToken());
					EventBus.getDefault().post(new LoginEvent(loginInfo));
					mView.dismiss();
				}, Throwable::printStackTrace);
		mCompositeDisposable.add(disposable);
	}

	@Override
	public void unSubscribe() {
		if(!mCompositeDisposable.isDisposed()){
			mCompositeDisposable.dispose();
		}
	}
}
