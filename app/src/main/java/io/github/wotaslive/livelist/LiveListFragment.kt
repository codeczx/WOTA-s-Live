package io.github.wotaslive.livelist

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.view.ContextThemeWrapper
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import io.github.wotaslive.BaseLazyFragment
import io.github.wotaslive.R
import io.github.wotaslive.data.model.LiveInfo
import io.github.wotaslive.databinding.FragLiveListBinding
import io.github.wotaslive.main.MainActivity
import io.github.wotaslive.player.PlayerActivity
import io.github.wotaslive.utils.obtainViewModel
import io.github.wotaslive.utils.setClipboard
import io.github.wotaslive.widget.SpaceItemDecoration


class LiveListFragment : BaseLazyFragment() {
    lateinit var viewModel: LiveListViewModel
    private lateinit var viewDataBinding: FragLiveListBinding
    private val adapter = LiveListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragLiveListBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).obtainViewModel(LiveListViewModel::class.java).also {
            viewDataBinding.viewModel = it
            viewDataBinding.setLifecycleOwner(this@LiveListFragment)
        }
        viewModel.liveListData.observe(this, Observer {
            if (viewModel.isLoadMore) {
                adapter.addData(it.orEmpty())
                viewDataBinding.srlLive.finishLoadMore()
            } else {
                adapter.setNewData(it.orEmpty())
                viewDataBinding.srlLive.finishRefresh()
            }
        })
        setupAdapter()
        setupRefresh()
        super.onActivityCreated(savedInstanceState)
    }

    override fun initData() {
        viewModel.loadLives(false)
    }

    private fun setupAdapter() {
        with(viewDataBinding.rvLive) {
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
            itemAnimator?.changeDuration = 0
            addItemDecoration(MaterialViewPagerHeaderDecorator())
            addItemDecoration(
                    SpaceItemDecoration(
                            resources.getDimensionPixelOffset(R.dimen.cardMarginHorizontal),
                            resources.getDimensionPixelOffset(R.dimen.cardMarginVertical)
                    )
            )
            adapter = this@LiveListFragment.adapter
        }
        adapter.setOnItemClickListener { adapter, _, position ->
            PlayerActivity.startPlayerActivity(context, adapter.getItem(position) as LiveInfo.ContentBean.RoomBean)
        }
        adapter.setOnItemLongClickListener { adapter, view, position ->
            val room = adapter.getItem(position) as LiveInfo.ContentBean.RoomBean
            val wrapper = ContextThemeWrapper(context, R.style.AppTheme_Menu)
            val popupMenu = PopupMenu(wrapper, view)
            with(popupMenu) {
                inflate(R.menu.menu_list_more)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.List_copy_address -> {
                            room.streamPath?.let {
                                context?.setClipboard(it)
                            }
                            return@setOnMenuItemClickListener true
                        }
                    }
                    false
                }
                show()
            }
            return@setOnItemLongClickListener true
        }
    }

    private fun setupRefresh() {
        with(viewDataBinding.srlLive) {
            setEnableAutoLoadMore(true)
            setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    viewModel.loadLives(true)
                }

                override fun onRefresh(refreshLayout: RefreshLayout) {
                    viewModel.loadLives(false)
                }
            })
        }
    }

    companion object {
        fun newInstance() = LiveListFragment()
    }
}
