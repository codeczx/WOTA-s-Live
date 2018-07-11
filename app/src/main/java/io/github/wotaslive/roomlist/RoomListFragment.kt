package io.github.wotaslive.roomlist

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator
import io.github.wotaslive.R
import io.github.wotaslive.data.model.RoomInfo
import io.github.wotaslive.databinding.FragRoomListBinding
import io.github.wotaslive.login.LoginActivity
import io.github.wotaslive.main.MainActivity
import io.github.wotaslive.main.MainViewModel
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
        viewModel.roomMessageCommand.observe(this, Observer {
            with(viewDataBinding.srlRoom) {
                finishRefresh()
                it?.let {
                    Snackbar.make(this, it, Snackbar.LENGTH_LONG)
                            .setAction(R.string.login) {
                                LoginActivity.startLoginActivity(activity as MainActivity)
                            }
                            .show()
                }
            }
        })
        (activity as MainActivity).obtainViewModel(MainViewModel::class.java)
                .friendsReloadCommand.observe(this, Observer {
            viewModel.start()
        })
        setupAdapter()
        setupRefresh()
        viewModel.start()
    }

    private fun setupAdapter() {
        with(viewDataBinding.rvRoom) {
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
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
            setOnRefreshListener {
                viewModel.start()
            }
        }
    }

    override fun onRoomClick(content: RoomInfo.ContentBean) {
        context?.let {
            RoomDetailActivity.startRoomDetailActivity(it, content)
        }
    }

    companion object {
        fun newInstance() = RoomListFragment()
    }
}