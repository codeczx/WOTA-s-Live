package io.github.wotaslive.livelist

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.view.ContextThemeWrapper
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import io.github.wotaslive.utils.showSnackbar
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
        subscribe()
        setupAdapter()
        setupRefresh()
        super.onActivityCreated(savedInstanceState)
    }

    private fun subscribe() {
        viewModel.liveListData.observe(this, Observer {
            if (viewModel.isLoadMore) {
                adapter.addData(it.orEmpty())
                viewDataBinding.srlLive.finishLoadMore()
            } else {
                adapter.setNewData(it.orEmpty())
                viewDataBinding.srlLive.finishRefresh()
            }
        })
        viewModel.errorCommand.observe(this, Observer {
            it?.let {
                viewDataBinding.rvLive.showSnackbar(getString(it), Snackbar.LENGTH_SHORT)
                viewDataBinding.srlLive.finishRefresh()
                viewDataBinding.srlLive.finishLoadMoreWithNoMoreData()
            }
        })
    }

    override fun initData() {
        viewModel.loadLives(false)
    }

    override fun scrollToTop() {
        viewDataBinding.rvLive.scrollToPosition(0)
    }

    private fun setupAdapter() {
        with(viewDataBinding.rvLive) {
            layoutManager = LinearLayoutManager(context)
            itemAnimator?.changeDuration = 0
            addItemDecoration(
                    SpaceItemDecoration(
                            resources.getDimensionPixelOffset(R.dimen.margin_horizontal),
                            resources.getDimensionPixelOffset(R.dimen.margin_vertical)
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
