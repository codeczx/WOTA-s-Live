package io.github.wotaslive.livelist

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.view.ContextThemeWrapper
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import io.github.wotaslive.R
import io.github.wotaslive.data.model.LiveInfo
import io.github.wotaslive.databinding.FragLiveListBinding
import io.github.wotaslive.main.MainActivity
import io.github.wotaslive.player.PlayerActivity
import io.github.wotaslive.utils.obtainViewModel
import io.github.wotaslive.utils.setClipboard
import io.github.wotaslive.widget.SpaceItemDecoration


class LiveListFragment : Fragment(), LiveListAdapter.CallBack {
    lateinit var viewModel: LiveListViewModel
    private lateinit var viewDataBinding: FragLiveListBinding
    private val adapter = LiveListAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragLiveListBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.start()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = (activity as MainActivity).obtainViewModel(LiveListViewModel::class.java).also {
            viewDataBinding.viewModel = it
            viewDataBinding.setLifecycleOwner(this@LiveListFragment)
        }
        viewModel.liveListData.observe(this, Observer {
            adapter.submitList(it)
            if (viewModel.isLoadMore) {
                viewDataBinding.srlLive.finishLoadMore()
            } else {
                viewDataBinding.srlLive.finishRefresh()
            }
        })
        setupAdapter()
        setupRefresh()
    }

    private fun setupAdapter() {
        with(viewDataBinding.rvLive) {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(MaterialViewPagerHeaderDecorator())
            addItemDecoration(
                    SpaceItemDecoration(
                            resources.getDimensionPixelOffset(R.dimen.cardMarginHorizontal),
                            resources.getDimensionPixelOffset(R.dimen.cardMarginVertical)
                    )
            )
            adapter = this@LiveListFragment.adapter
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

    override fun onCoverClick(room: LiveInfo.ContentBean.RoomBean) {
        PlayerActivity.startPlayerActivity(context, room.streamPath, room.liveType == 1)
    }

    override fun onLongClick(room: LiveInfo.ContentBean.RoomBean, anchor: View): Boolean {
        val wrapper = ContextThemeWrapper(context, R.style.AppTheme_Menu)
        val popupMenu = PopupMenu(wrapper, anchor)
        with(popupMenu) {
            inflate(R.menu.menu_list_more)
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.List_copy_address -> {
                        context?.setClipboard(room.streamPath)
                        return@setOnMenuItemClickListener true
                    }
                }
                false
            }
            show()
        }
        return true
    }

    companion object {
        fun newInstance() = LiveListFragment()
    }
}
