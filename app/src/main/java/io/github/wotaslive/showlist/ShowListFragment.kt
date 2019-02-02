package io.github.wotaslive.showlist

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.wotaslive.BaseLazyFragment
import io.github.wotaslive.R
import io.github.wotaslive.databinding.FragShowListBinding
import io.github.wotaslive.main.MainActivity
import io.github.wotaslive.utils.obtainViewModel
import io.github.wotaslive.utils.showSnackbar
import io.github.wotaslive.widget.SpaceItemDecoration

class ShowListFragment : BaseLazyFragment() {
    lateinit var viewModel: ShowListViewModel

    private lateinit var viewDataBinding: FragShowListBinding
    private val adapter = ShowListAdapter()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragShowListBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).obtainViewModel(ShowListViewModel::class.java).also {
            viewDataBinding.viewModel = it
            viewDataBinding.setLifecycleOwner(this@ShowListFragment)
        }
        subscribe()
        setupAdapter()
        setupRefresh()
        super.onActivityCreated(savedInstanceState)
    }

    private fun subscribe() {
        viewModel.showListData.observe(this, Observer {
            adapter.setNewData(it.orEmpty())
            viewDataBinding.srlShow.finishRefresh()
        })
        viewModel.errorCommand.observe(this, Observer {
            it?.let {
                viewDataBinding.rvShow.showSnackbar(getString(it), Snackbar.LENGTH_SHORT)
                viewDataBinding.srlShow.finishRefresh()
            }
        })
    }

    override fun initData() {
        viewModel.start()
    }

    override fun scrollToTop() {
        viewDataBinding.rvShow.scrollToPosition(0)
    }

    private fun setupAdapter() {
        with(viewDataBinding.rvShow) {
            layoutManager = LinearLayoutManager(context)
            itemAnimator?.changeDuration = 0
            addItemDecoration(
                    SpaceItemDecoration(
                            resources.getDimensionPixelOffset(R.dimen.margin_horizontal),
                            resources.getDimensionPixelOffset(R.dimen.margin_vertical)
                    )
            )
            adapter = this@ShowListFragment.adapter
        }
    }

    private fun setupRefresh() {
        with(viewDataBinding.srlShow) {
            setOnRefreshListener { viewModel.start() }
        }
    }

    companion object {
        fun newInstance() = ShowListFragment()
    }
}
