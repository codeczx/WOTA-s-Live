package io.github.wotaslive.dynamiclist


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import io.github.wotaslive.BaseLazyFragment
import io.github.wotaslive.databinding.FragDynamicListBinding
import io.github.wotaslive.main.MainActivity
import io.github.wotaslive.roomlist.room.pictures.DynamicPicturesAdapter
import io.github.wotaslive.utils.obtainViewModel
import io.github.wotaslive.widget.PhotoWindow

class DynamicListFragment : BaseLazyFragment(), DynamicPicturesAdapter.Callback {
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
        with(viewModel) {
            data.observe(this@DynamicListFragment, Observer {
                if (isLoad)
                    viewDataBinding.srlDynamic.finishLoadMore()
                else
                    viewDataBinding.srlDynamic.finishRefresh()
                adapter.submitList(it)
            })
            members.observe(this@DynamicListFragment, Observer {
                adapter.updateMember(it)
            })
        }
        subscribeUi()
        super.onActivityCreated(savedInstanceState)
    }

    override fun initData() {
        viewModel.load(false)
    }

    private fun subscribeUi() {
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
            isNestedScrollingEnabled = false
            itemAnimator.changeDuration = 0
            addItemDecoration(com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator())
            addItemDecoration(
                    io.github.wotaslive.widget.SpaceItemDecoration(
                            resources.getDimensionPixelOffset(io.github.wotaslive.R.dimen.cardMarginHorizontal),
                            resources.getDimensionPixelOffset(io.github.wotaslive.R.dimen.cardMarginVertical)
                    )
            )
            adapter = this@DynamicListFragment.adapter
        }
    }

    override fun onImageClick(view: View, url: String) {
        activity?.let {
            PhotoWindow(it, url).show(view)
        }
    }

    companion object {
        fun newInstance() = DynamicListFragment()
    }
}
