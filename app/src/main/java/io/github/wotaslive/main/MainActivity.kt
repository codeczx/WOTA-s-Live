package io.github.wotaslive.main

import android.Manifest
import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.github.florent37.materialviewpager.header.HeaderDesign
import com.tbruyelle.rxpermissions2.RxPermissions
import io.github.wotaslive.R
import io.github.wotaslive.databinding.ActMainBinding
import io.github.wotaslive.utils.obtainViewModel
import io.github.wotaslive.utils.setupActionBar
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
        setupActionBar(viewDataBinding.materialViewPager.toolbar.id) {
            setDisplayShowHomeEnabled(false)
        }
        subscribeUIBinding()
        initView()
        checkPermissions()
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