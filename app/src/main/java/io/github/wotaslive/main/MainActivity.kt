package io.github.wotaslive.main

import android.Manifest
import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.github.florent37.materialviewpager.header.HeaderDesign
import com.tbruyelle.rxpermissions2.RxPermissions
import io.github.wotaslive.R
import io.github.wotaslive.databinding.ActMainBinding
import io.github.wotaslive.login.LoginActivity
import io.github.wotaslive.utils.obtainViewModel
import io.github.wotaslive.utils.setupSnackbar


class MainActivity : AppCompatActivity() {
    private lateinit var viewDataBinding: ActMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.act_main)
        viewModel = obtainViewModel(MainViewModel::class.java).also {
            viewDataBinding.viewModel = it
            viewDataBinding.setLifecycleOwner(this)
        }
        val toolbar = viewDataBinding.materialViewPager.toolbar
        toolbar.let {
            setSupportActionBar(toolbar)
            supportActionBar?.let {
                it.setHomeButtonEnabled(false)
                it.setDisplayShowTitleEnabled(false)
                it.setDisplayShowHomeEnabled(false)
                it.setDisplayHomeAsUpEnabled(false)
            }
        }
        subscribeUIBinding()
        initView()
        checkPermissions()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_login -> {
                LoginActivity.startLoginActivity(this)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.run {
            val isLogin = viewModel.isLogin.value ?: false
            findItem(R.id.menu_login).isVisible = !isLogin
            with(findItem(R.id.menu_username)) {
                isVisible = isLogin
                if (isLogin)
                    title = viewModel.nickname
            }
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LoginActivity.requestCode && resultCode == Activity.RESULT_OK) {
            viewModel.loadInfo()
        }
    }

    private fun checkPermissions() {
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(Manifest.permission.READ_PHONE_STATE).subscribe {
            if (it)
                viewModel.start()
            else
                finish()
        }
    }

    private fun subscribeUIBinding() {
        viewModel.headerRefreshCommand.observe(this, Observer {
            with(viewDataBinding.materialViewPager) {
                setMaterialViewPagerListener(it)
                notifyHeaderChanged()
            }
        })
        viewModel.isLogin.observe(this, Observer {
            invalidateOptionsMenu()
        })
        viewDataBinding.root.setupSnackbar(this,
                viewModel.friendMessageCommand,
                Snackbar.LENGTH_LONG)
    }

    private fun initView() {
        val pagerAdapter = MainPagerAdapter(this@MainActivity, supportFragmentManager)
        with(viewDataBinding.materialViewPager) {
            viewPager.adapter = pagerAdapter
            setMaterialViewPagerListener {
                HeaderDesign.fromColorResAndDrawable(R.color.colorPrimaryDark, getDrawable(R.drawable.bg_default_header))
            }
            viewPager.offscreenPageLimit = pagerAdapter.count - 1
            pagerTitleStrip.setViewPager(viewPager)
        }
    }
}