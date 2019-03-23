package io.github.wotaslive.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import io.github.wotaslive.BaseLazyFragment
import io.github.wotaslive.R
import io.github.wotaslive.databinding.ActMainBinding
import io.github.wotaslive.login.LoginActivity
import io.github.wotaslive.utils.obtainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var viewDataBinding: ActMainBinding
    private lateinit var viewModel: MainViewModel
    private var firstTime = 0L
    private var lastTapId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.act_main)
        viewModel = obtainViewModel(MainViewModel::class.java).also {
            viewDataBinding.viewModel = it
            viewDataBinding.lifecycleOwner = this
        }
        initView()
        checkPermissions()
        viewModel.loadInfo()
        if (viewModel.isLogin.value != true) {
            LoginActivity.startLoginActivity(this)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadInfo()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LoginActivity.requestCode && resultCode == Activity.RESULT_OK) {
            viewModel.loadInfo()
        }
    }

    @SuppressLint("CheckResult")
    private fun checkPermissions() {
        val rxPermissions = RxPermissions(this)
        rxPermissions.request(Manifest.permission.READ_PHONE_STATE).subscribe {
            if (it)
                viewModel.start()
            else
                checkPermissions()
        }
    }

    private fun initView() {
        val pagerAdapter = MainPagerAdapter(this@MainActivity, supportFragmentManager)
        with(viewDataBinding) {
            viewPager.adapter = pagerAdapter
            viewPager.offscreenPageLimit = pagerAdapter.count - 1
            bottomNavi.setOnNavigationItemSelectedListener {
                val secondTime = System.currentTimeMillis()
                if (secondTime - firstTime < 500 && it.order == lastTapId) {
                    // double tap
                    (pagerAdapter.getItem(it.order) as BaseLazyFragment?)?.scrollToTop()
                }
                firstTime = secondTime
                lastTapId = it.order
                viewPager.currentItem = it.order
                return@setOnNavigationItemSelectedListener true
            }
            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(p0: Int) {

                }

                override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {

                }

                override fun onPageSelected(p0: Int) {
                    bottomNavi.menu.getItem(p0).isChecked = true
                }
            })
        }
    }
}