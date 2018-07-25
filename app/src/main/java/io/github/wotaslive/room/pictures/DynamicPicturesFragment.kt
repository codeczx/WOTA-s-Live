package io.github.wotaslive.room.pictures


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.wotaslive.R
import io.github.wotaslive.databinding.FragPicsBinding
import io.github.wotaslive.room.PhotoFragment
import io.github.wotaslive.room.RoomDetailActivity
import io.github.wotaslive.room.RoomViewModel
import io.github.wotaslive.utils.obtainViewModel
import io.github.wotaslive.utils.setupActionBar


class DynamicPicturesFragment : Fragment(), DynamicPicturesAdapter.Callback {
    private lateinit var viewDataBinding: FragPicsBinding

    private lateinit var viewModel: DynamicPicturesViewModel
    private val adapter = DynamicPicturesAdapter(this)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewDataBinding = FragPicsBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = (activity as RoomDetailActivity).obtainViewModel(DynamicPicturesViewModel::class.java)
        viewModel.roomId = (activity as RoomDetailActivity).obtainViewModel(RoomViewModel::class.java).roomId
        viewModel.roomId = 6738
        viewModel.picsData.observe(this, Observer {
            if (viewModel.isLoadMore) {
                viewDataBinding.srlPics.finishLoadMore()
            } else {
                viewDataBinding.srlPics.finishRefresh()
            }
            adapter.submitList(it)
        })
        viewDataBinding.setLifecycleOwner(this)
        setupBinding()
    }

    private fun setupBinding() {
        with(viewDataBinding.srlPics) {
            setOnRefreshListener {
                viewModel.load(false)
            }
            setOnLoadMoreListener {
                viewModel.load(true)
            }
        }
        with(viewDataBinding.rvPics) {
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            adapter = this@DynamicPicturesFragment.adapter
        }
        with(activity as RoomDetailActivity) {
            setupActionBar(R.id.toolbar) {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.load(false)
    }

    override fun onImageClick(url: String) {
        (activity as RoomDetailActivity).obtainViewModel(RoomViewModel::class.java).imageUrl.set(url)
        fragmentManager?.run {
            beginTransaction()
                    .add(R.id.container, PhotoFragment.newInstance())
                    .hide(this@DynamicPicturesFragment)
                    .addToBackStack(this@DynamicPicturesFragment.javaClass.name)
                    .commit()
        }
    }

    companion object {
        fun newInstance() = DynamicPicturesFragment()
    }
}
