package io.github.wotaslive.showlist

import android.arch.lifecycle.Observer
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator
import com.scwang.smartrefresh.header.MaterialHeader
import io.github.wotaslive.R
import io.github.wotaslive.data.model.ShowInfo
import io.github.wotaslive.databinding.FragShowListBinding
import io.github.wotaslive.main.MainActivity
import io.github.wotaslive.utils.obtainViewModel

class ShowListFragment : Fragment(), ShowListAdapter.Callback {
    lateinit var viewModel: ShowListViewModel

    private lateinit var viewDataBinding: FragShowListBinding
    private val adapter = ShowListAdapter(this)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragShowListBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.start()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = (activity as MainActivity).obtainViewModel(ShowListViewModel::class.java).also {
            viewDataBinding.viewModel = it
        }
        viewModel.showListData.observe(this, Observer {
            adapter.addNewData(it)
            viewDataBinding.srlShow.finishRefresh()
        })
        setupAdapter()
    }

    private fun setupAdapter() {
        // recycler view
        val verticalSpace = resources.getDimensionPixelOffset(R.dimen.cardMarginVertical)
        val horizontalSpace = resources.getDimensionPixelOffset(R.dimen.cardMarginHorizontal)
        viewDataBinding.rvShow.layoutManager = LinearLayoutManager(context)
        viewDataBinding.rvShow.addItemDecoration(MaterialViewPagerHeaderDecorator())
        viewDataBinding.rvShow.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect?.bottom = 0
                outRect?.left = horizontalSpace
                outRect?.right = horizontalSpace
                outRect?.top = if (parent?.getChildAdapterPosition(view) == 0) 0 else verticalSpace
            }
        })
        viewDataBinding.rvShow.adapter = adapter

        // refresh layout
        viewDataBinding.srlShow.setEnableLoadMore(false)
        viewDataBinding.srlShow.setRefreshHeader(MaterialHeader(context))
        viewDataBinding.srlShow.setOnRefreshListener { viewModel.start() }
    }

    override fun onCoverClick(show: ShowInfo.ContentBean.ShowBean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        fun newInstance() = ShowListFragment()
    }
}
