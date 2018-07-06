package io.github.wotaslive.showlist

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
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
import io.github.wotaslive.widget.SpaceItemDecoration

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
            viewDataBinding.setLifecycleOwner(this@ShowListFragment)
        }
        viewModel.showListData.observe(this, Observer {
            adapter.addNewData(it)
            viewDataBinding.srlShow.finishRefresh()
        })
        setupAdapter()
    }

    private fun setupAdapter() {
        // recycler view
        viewDataBinding.rvShow.layoutManager = LinearLayoutManager(context)
        viewDataBinding.rvShow.addItemDecoration(MaterialViewPagerHeaderDecorator())
        viewDataBinding.rvShow.addItemDecoration(
                SpaceItemDecoration(
                        resources.getDimensionPixelOffset(R.dimen.cardMarginHorizontal),
                        resources.getDimensionPixelOffset(R.dimen.cardMarginVertical)
                )
        )
        viewDataBinding.rvShow.adapter = adapter

        // refresh layout
        viewDataBinding.srlShow.setEnableLoadMore(false)
        viewDataBinding.srlShow.setRefreshHeader(MaterialHeader(context))
        viewDataBinding.srlShow.setOnRefreshListener { viewModel.start() }
    }

    override fun onCoverClick(show: ShowInfo.ContentBean.ShowBean) {

    }

    companion object {
        fun newInstance() = ShowListFragment()
    }
}
