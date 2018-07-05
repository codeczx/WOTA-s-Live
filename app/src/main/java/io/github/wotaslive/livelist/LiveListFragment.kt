package io.github.wotaslive.livelist

import android.arch.lifecycle.Observer
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.view.ContextThemeWrapper
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator
import com.scwang.smartrefresh.header.MaterialHeader
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import io.github.wotaslive.R
import io.github.wotaslive.data.model.LiveInfo
import io.github.wotaslive.databinding.FragLiveListBinding
import io.github.wotaslive.main.MainActivity
import io.github.wotaslive.player.PlayerActivity
import io.github.wotaslive.utils.obtainViewModel
import io.github.wotaslive.utils.setClipboard


class LiveListFragment : Fragment(), LiveListAdapter.CallBack {
    private lateinit var viewDataBinding: FragLiveListBinding

    lateinit var viewModel: LiveListViewModel
    private val adapter = LiveListAdapter(this)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragLiveListBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = (activity as MainActivity).obtainViewModel(LiveListViewModel::class.java).also {
            viewDataBinding.viewModel = it
        }
        viewModel.liveListData.observe(this, Observer {
            if (viewModel.isLoadMore) {
                adapter.addMoreData(it)
                viewDataBinding.srlLive.finishLoadMore()
            } else {
                adapter.addNewData(it)
                viewDataBinding.srlLive.finishRefresh()
            }
        })
        setupAdapter()
    }

    private fun setupAdapter() {
        // recycler view
        val verticalSpace = resources.getDimensionPixelOffset(R.dimen.cardMarginVertical)
        val horizontalSpace = resources.getDimensionPixelOffset(R.dimen.cardMarginHorizontal)
        viewDataBinding.rvLive.layoutManager = LinearLayoutManager(context)
        viewDataBinding.rvLive.addItemDecoration(MaterialViewPagerHeaderDecorator())
        viewDataBinding.rvLive.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect?.bottom = 0
                outRect?.left = horizontalSpace
                outRect?.right = horizontalSpace
                outRect?.top = if (parent?.getChildAdapterPosition(view) == 0) 0 else verticalSpace
            }
        })
        viewDataBinding.rvLive.adapter = adapter

        // refresh layout
        viewDataBinding.srlLive.setEnableAutoLoadMore(true)
        viewDataBinding.srlLive.setRefreshHeader(MaterialHeader(context))
        viewDataBinding.srlLive.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
            override fun onLoadMore(refreshLayout: RefreshLayout) {
                viewModel.loadLives(true)
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                viewModel.loadLives(false)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.start()
    }

    override fun onCoverClick(room: LiveInfo.ContentBean.RoomBean) {
        PlayerActivity.startPlayerActivity(context, room.streamPath, room.liveType == 1)
    }

    override fun onLongClick(room: LiveInfo.ContentBean.RoomBean, anchor: View):Boolean {
        val wrapper = ContextThemeWrapper(context, R.style.AppTheme_Menu)
        val popupMenu = PopupMenu(wrapper, anchor)
        popupMenu.inflate(R.menu.menu_list_more)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.List_copy_address -> {
                    context?.setClipboard(room.streamPath)
                    return@setOnMenuItemClickListener true
                }
            }
            false
        }
        popupMenu.show()
        return true
    }

    companion object {
        fun newInstance() = LiveListFragment()
    }
}