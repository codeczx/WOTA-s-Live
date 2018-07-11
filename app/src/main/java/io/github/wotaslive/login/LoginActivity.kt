package io.github.wotaslive.login

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import io.github.wotaslive.R
import io.github.wotaslive.databinding.ActLoginBinding
import io.github.wotaslive.utils.obtainViewModel
import io.github.wotaslive.utils.setupActionBar
import io.github.wotaslive.utils.setupSnackbar

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginViewModel
    private lateinit var viewDataBinding: ActLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = obtainViewModel(LoginViewModel::class.java)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.act_login)
        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        with(viewDataBinding) {
            viewModel = this@LoginActivity.viewModel
            setLifecycleOwner(this@LoginActivity)
            fabLogin.setOnClickListener {
                this@LoginActivity.viewModel.login()
            }
            root.setupSnackbar(this@LoginActivity,
                    this@LoginActivity.viewModel.loginStatusCommand,
                    Snackbar.LENGTH_LONG)
        }
        viewModel.loginSuccessCommand.observe(this, Observer {
            setResult(Activity.RESULT_OK)
            finish()
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_CANCELED)
    }

    companion object {
        const val requestCode = 1
        fun startLoginActivity(activity: Activity) {
            val intent = Intent(activity, LoginActivity::class.java)
            activity.startActivityForResult(intent, requestCode)
        }
    }
}
