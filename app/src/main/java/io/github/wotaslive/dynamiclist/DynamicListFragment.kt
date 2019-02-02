package io.github.wotaslive.dynamiclist


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import io.github.wotaslive.BaseLazyFragment
import io.github.wotaslive.databinding.FragDynamicListBinding
import io.github.wotaslive.main.MainActivity
import io.github.wotaslive.utils.obtainViewModel
import io.github.wotaslive.utils.showSnackbar

class DynamicListFragment : BaseLazyFragment() {
    private lateinit var viewModel: DynamicListViewModel
    private lateinit var viewDataBinding: FragDynamicListBinding
    private val adapter = DynamicAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewDataBinding = FragDynamicListBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModel = (activity as MainActivity).obtainViewModel(DynamicListViewModel::class.java)
        viewDataBinding.setLifecycleOwner(this)
        subscribe()
        initView()
        super.onActivityCreated(savedInstanceState)
    }

    private fun subscribe() {
        with(viewModel) {
            data.observe(this@DynamicListFragment, Observer {
                if (isLoad) {
                    viewDataBinding.srlDynamic.finishLoadMore()
                    adapter.addData(it.orEmpty())
                } else {
                    viewDataBinding.srlDynamic.finishRefresh()
                    adapter.setNewData(it.orEmpty())
                }
            })
            members.observe(this@DynamicListFragment, Observer {
                adapter.updateMember(it)
            })
            errorCommand.observe(this@DynamicListFragment, Observer {
                it?.let {
                    viewDataBinding.rvDynamic.showSnackbar(getString(it), Snackbar.LENGTH_SHORT)
                    viewDataBinding.srlDynamic.finishRefresh()
                    viewDataBinding.srlDynamic.finishLoadMoreWithNoMoreData()
                }
            })
        }
    }

    override fun initData() {
        viewModel.load(false)
    }

    override fun scrollToTop() {
        viewDataBinding.rvDynamic.scrollToPosition(0)
    }

    private fun initView() {
        with(viewDataBinding.srlDynamic) {
            setEnableAutoLoadMore(true)
            setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    viewModel.load(true)
                }

                override fun onRefresh(refreshLayout: RefreshLayout) {
                    viewModel.load(false)
                }
            })
        }
        with(viewDataBinding.rvDynamic) {
            layoutManager = LinearLayoutManager(context)
            itemAnimator?.changeDuration = 0
            addItemDecoration(
                    io.github.wotaslive.widget.SpaceItemDecoration(
                            resources.getDimensionPixelOffset(io.github.wotaslive.R.dimen.margin_horizontal),
                            resources.getDimensionPixelOffset(io.github.wotaslive.R.dimen.margin_vertical)
                    )
            )
            adapter = this@DynamicListFragment.adapter
        }
    }

    companion object {
        fun newInstance() = DynamicListFragment()
    }
}
