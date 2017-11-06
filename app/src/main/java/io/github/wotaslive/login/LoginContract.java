package io.github.wotaslive.login;

import io.github.wotaslive.BasePresenter;
import io.github.wotaslive.BaseView;

/**
 * Created by codeczx on 2017/11/7 0:07.
 * Class description:
 */
public class LoginContract {
	interface LoginPresenter extends BasePresenter {

		void performLogin(String username, String password);
	}

	interface LoginView extends BaseView<LoginPresenter> {

		void dismiss();
	}
}
