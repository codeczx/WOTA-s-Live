package io.github.wotaslive.login

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableField
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.SPUtils
import com.google.gson.Gson
import io.github.wotaslive.Constants
import io.github.wotaslive.R
import io.github.wotaslive.SingleLiveEvent
import io.github.wotaslive.data.AppRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginViewModel(application: Application, private val appRepository: AppRepository) : AndroidViewModel(application) {
    val username = ObservableField<String>()
    val password = ObservableField<String>()
    val loginStatusCommand = SingleLiveEvent<Int>()
    val loginSuccessCommand = SingleLiveEvent<Void>()
    private val compositeDisposable = CompositeDisposable()

    /**
     * 1001002 账号不存在
     * 1001003 密码错误
     * 400 password不能为空
     * 200 success
     */
    fun login() {
        val un = username.get() ?: ""
        val pw = password.get() ?: ""
        if (!RegexUtils.isMobileSimple(un)) {
            loginStatusCommand.value = R.string.username_format_error
            return
        }
        if (pw.length < 6) {
            loginStatusCommand.value = R.string.password_format_error
            return
        }
        appRepository.login(un, pw)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    when (it.status) {
                        200 -> {
                            val spUtils = SPUtils.getInstance(Constants.SP_NAME)
                            it.content?.token?.let {
                                spUtils.put(Constants.HEADER_KEY_TOKEN, it)
                            }
                            spUtils.put(Constants.SP_FRIENDS, Gson().toJson(it.content?.friends))
                            spUtils.put(Constants.SP_USERNAME, un)
                            spUtils.put(Constants.SP_PASSWORD, pw)
                            it.content?.userInfo?.let {
                                spUtils.put(Constants.SP_NICKNAME, it.nickName.orEmpty())
                                spUtils.put(Constants.SP_USER_ID, it.userId)
                            }
                            loginSuccessCommand.call()
                        }
                        400 ->
                            loginStatusCommand.value = R.string.password_is_null
                        1001003 ->
                            loginStatusCommand.value = R.string.password_wrong
                        1001002 ->
                            loginStatusCommand.value = R.string.user_not_exist
                    }
                },
                        Throwable::printStackTrace)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}