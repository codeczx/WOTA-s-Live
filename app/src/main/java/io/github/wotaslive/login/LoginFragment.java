package io.github.wotaslive.login;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.wotaslive.R;

public class LoginFragment extends DialogFragment implements LoginContract.LoginView {

	@BindView(R.id.et_username)
	EditText mEtUsername;
	@BindView(R.id.et_password)
	EditText mEtPassword;
	@BindView(R.id.button)
	Button mButton;
	Unbinder unbinder;

	private LoginContract.LoginPresenter mPresenter;

	public LoginFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_login, container, false);
		unbinder = ButterKnife.bind(this, view);
		return view;
	}

	@Override
	public void onResume() {
		int width = getResources().getDimensionPixelSize(R.dimen.popup_width);
		int height = getResources().getDimensionPixelSize(R.dimen.popup_height);
		Window window = getDialog().getWindow();
		if (window != null) {
			window.setLayout(width, height);
		}
		super.onResume();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		new LoginPresenterImpl(getContext(), this);
	}

	@OnClick(R.id.button)
	public void onViewClicked() {
		String username = mEtUsername.getText().toString();
		String password = mEtPassword.getText().toString();
		if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
			mPresenter.performLogin(username, password);
		}
	}

	@Override
	public void setPresenter(LoginContract.LoginPresenter presenter) {
		mPresenter = presenter;
	}

	@Override
	public void onStop() {
		super.onStop();
		mPresenter.unSubscribe();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		unbinder.unbind();
	}

}
