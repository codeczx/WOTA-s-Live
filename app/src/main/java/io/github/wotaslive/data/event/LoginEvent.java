package io.github.wotaslive.data.event;

/**
 * Created by codeczx on 2017/11/7 0:32.
 * Class description:
 */

import io.github.wotaslive.data.model.LoginInfo;
public class LoginEvent {

	private LoginInfo mLoginInfo;

	public LoginEvent(LoginInfo loginInfo) {
		mLoginInfo = loginInfo;
	}

	public LoginInfo getLoginInfo() {
		return mLoginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		mLoginInfo = loginInfo;
	}
}
