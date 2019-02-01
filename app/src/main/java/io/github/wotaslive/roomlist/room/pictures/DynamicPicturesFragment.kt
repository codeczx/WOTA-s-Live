package io.github.wotaslive.roomlist.room.pictures


import android.arch.lifecycle.Observer
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.blankj.utilcode.util.SizeUtils
import com.previewlibrary.GPreviewBuilder
import io.github.wotaslive.R
import io.github.wotaslive.data.model.DynamicPictureInfo
import io.github.wotaslive.data.model.ImageInfo
import io.github.wotaslive.databinding.FragPicsBinding
import io.github.wotaslive.roomlist.room.RoomDetailActivity
import io.github.wotaslive.roomlist.room.RoomViewModel
import io.github.wotaslive.utils.checkUrl
import io.github.wotaslive.utils.obtainViewModel
import io.github.wotaslive.utils.setupActionBar
import io.github.wotaslive.widget.GridSpaceItemDecoration


class DynamicPicturesFragment : Fragment() {
    private lateinit var viewDataBinding: FragPicsBinding

    private lateinit var viewModel: DynamicPicturesViewModel
    private val adapter = DynamicPicturesAdapter()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewDataBinding = FragPicsBinding.inflate(inflater, container, false)
        return viewDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = (activity as RoomDetailActivity).obtainViewModel(DynamicPicturesViewModel::class.java)
        viewModel.memberId = (activity as RoomDetailActivity).obtainViewModel(RoomViewModel::class.java).memberId
        viewModel.picsData.observe(this, Observer {
            if (viewModel.isLoadMore) {
                viewDataBinding.srlPics.finishLoadMore()
            } else {
                viewDataBinding.srlPics.finishRefresh()
            }
            adapter.setNewData(it)
        })
        adapter.setOnItemClickListener { adapter, _, position ->
            val thumbViewInfoList = ArrayList<ImageInfo>()
            adapter.data.forEach {
                thumbViewInfoList.add(ImageInfo(checkUrl((it as DynamicPictureInfo.Content.Data).filePath), null, null))
            }
            val gridLayoutManager = viewDataBinding.rvPics.layoutManager as LinearLayoutManager
            val firstCompletelyVisiblePos = gridLayoutManager.findFirstVisibleItemPosition()
            for (i in firstCompletelyVisiblePos until thumbViewInfoList.size) {
                val itemView = gridLayoutManager.findViewByPosition(i)
                val bounds = Rect()
                if (itemView != null) {
                    val thumbView = itemView.findViewById(R.id.iv_image) as ImageView
                    thumbView.getGlobalVisibleRect(bounds)
                }
                thumbViewInfoList[i].rect = bounds
            }
            GPreviewBuilder.from(this)
                    .setData(thumbViewInfoList)
                    .setCurrentIndex(position)
                    .setSingleFling(true)
                    .setType(GPreviewBuilder.IndicatorType.Number)
                    .start()
        }
        viewDataBinding.setLifecycleOwner(this)
        setupActionBar(viewDataBinding.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        setupBinding()
        viewModel.load(false)
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
            layoutManager = GridLayoutManager(context, 3)
            adapter = this@DynamicPicturesFragment.adapter
            addItemDecoration(GridSpaceItemDecoration(3, SizeUtils.dp2px(5f), false))
        }
        with(activity as RoomDetailActivity) {
            setupActionBar(io.github.wotaslive.R.id.toolbar) {
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }
        }
    }

    companion object {
        fun newInstance() = DynamicPicturesFragment()
    }
}
