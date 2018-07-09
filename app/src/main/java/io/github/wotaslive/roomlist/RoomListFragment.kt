package io.github.wotaslive.roomlist

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.SPUtils
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator
import com.scwang.smartrefresh.header.MaterialHeader
import io.github.wotaslive.Constants
import io.github.wotaslive.R
import io.github.wotaslive.data.model.RoomInfo
import io.github.wotaslive.databinding.FragRoomListBinding
import io.github.wotaslive.login.LoginActivity
import io.github.wotaslive.main.MainActivity
import io.github.wotaslive.room.RoomDetailActivity
import io.github.wotaslive.utils.obtainViewModel
import io.github.wotaslive.widget.SpaceItemDecoration

class RoomListFragment : Fragment(), RoomListAdapter.Callback {
    private lateinit var viewDataBinding: FragRoomListBinding

    private lateinit var viewModel: RoomListViewModel
    private val adapter = RoomListAdapter(this)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragRoomListBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onResume() {
        super.onResume()
        val spUtils = SPUtils.getInstance(Constants.SP_NAME)
        val token = spUtils.getString(Constants.HEADER_KEY_TOKEN, "")
        if (TextUtils.isEmpty(token)) {
            activity?.let { LoginActivity.startLoginActivity(it) }
        } else {
            viewModel.start()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = (activity as MainActivity).obtainViewModel(RoomListViewModel::class.java).also {
            viewDataBinding.viewModel = it
            viewDataBinding.setLifecycleOwner(this@RoomListFragment)
        }
        viewModel.roomListData.observe(this, Observer {
            adapter.submitList(it)
            viewDataBinding.srlRoom.finishRefresh()
        })
        setupAdapter()
        setupRefresh()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LoginActivity.requestCode && resultCode == Activity.RESULT_OK) {
            viewModel.start()
        }
    }

    private fun setupAdapter() {
        with(viewDataBinding.rvRoom) {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(MaterialViewPagerHeaderDecorator())
            addItemDecoration(
                    SpaceItemDecoration(
                            resources.getDimensionPixelOffset(R.dimen.cardMarginHorizontal),
                            resources.getDimensionPixelOffset(R.dimen.cardMarginVertical)
                    )
            )
            adapter = this@RoomListFragment.adapter
        }
    }

    private fun setupRefresh() {
        with(viewDataBinding.srlRoom) {
            setEnableAutoLoadMore(false)
            setRefreshHeader(MaterialHeader(context))
            setOnRefreshListener {
                viewModel.start()
            }
        }
    }

    override fun onRoomClick(content: RoomInfo.ContentBean) {
        RoomDetailActivity.startRoomDetailActivity(context, content)
    }

    companion object {
        fun newInstance() = RoomListFragment()
    }
}