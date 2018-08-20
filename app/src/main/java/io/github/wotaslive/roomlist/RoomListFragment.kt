package io.github.wotaslive.roomlist

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator
import io.github.wotaslive.App
import io.github.wotaslive.BaseLazyFragment
import io.github.wotaslive.R
import io.github.wotaslive.data.AppRepository
import io.github.wotaslive.data.model.RoomInfo
import io.github.wotaslive.databinding.FragRoomListBinding
import io.github.wotaslive.login.LoginActivity
import io.github.wotaslive.main.MainActivity
import io.github.wotaslive.roomlist.all.AllRoomListActivity
import io.github.wotaslive.roomlist.room.RoomDetailActivity
import io.github.wotaslive.widget.SpaceItemDecoration

class RoomListFragment : BaseLazyFragment(), RoomListAdapter.Callback {
    private lateinit var viewDataBinding: FragRoomListBinding
    val viewModel = RoomListViewModel(App.instance, AppRepository.instance)
    private val adapter = RoomListAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragRoomListBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onResume() {
        super.onResume()
        lazyInitData()
    }

    override fun initData() {
        viewModel.start()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewDataBinding.viewModel = viewModel
        viewDataBinding.eventHandler = this@RoomListFragment
        viewDataBinding.setLifecycleOwner(this@RoomListFragment)

        viewModel.isMain.set(activity is MainActivity)

        viewModel.roomListData.observe(this, Observer {
            adapter.submitList(it)
            viewDataBinding.srlRoom.finishRefresh()
        })
        viewModel.roomMessageCommand.observe(this, Observer { it ->
            with(viewDataBinding.srlRoom) {
                finishRefresh()
                it?.let { it1 ->
                    Snackbar.make(this, it1, Snackbar.LENGTH_LONG)
                            .setAction(R.string.login) { _ ->
                                LoginActivity.startLoginActivity(activity as MainActivity)
                            }
                            .show()
                }
            }
        })
        setupAdapter()
        setupRefresh()
        super.onActivityCreated(savedInstanceState)
    }

    private fun setupAdapter() {
        with(viewDataBinding.rvRoom) {
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
            itemAnimator.changeDuration = 0
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

    fun enterAll() {
        activity?.let {
            AllRoomListActivity.startAllRoomListActivity(it)
        }
    }

    companion object {
        fun newInstance() = RoomListFragment()
        fun newInstance(memberIds: List<Int>) = RoomListFragment().also {
            it.viewModel.memberIds.addAll(memberIds)
        }
    }
}