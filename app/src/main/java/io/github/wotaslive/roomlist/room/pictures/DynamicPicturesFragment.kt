package io.github.wotaslive.roomlist.room.pictures


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.SizeUtils
import io.github.wotaslive.R
import io.github.wotaslive.databinding.FragPicsBinding
import io.github.wotaslive.roomlist.room.RoomDetailActivity
import io.github.wotaslive.roomlist.room.RoomViewModel
import io.github.wotaslive.utils.checkUrl
import io.github.wotaslive.utils.obtainViewModel
import io.github.wotaslive.utils.setupActionBar
import io.github.wotaslive.widget.GridSpaceItemDecoration
import net.moyokoo.diooto.Diooto
import net.moyokoo.diooto.config.DiootoConfig


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
        viewDataBinding.lifecycleOwner = this
        setupActionBar(viewDataBinding.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
        viewModel.memberId = (activity as RoomDetailActivity).obtainViewModel(RoomViewModel::class.java).memberId
        viewModel.picsData.observe(this, Observer {
            if (viewModel.isLoadMore) {
                viewDataBinding.srlPics.finishLoadMore()
            } else {
                viewDataBinding.srlPics.finishRefresh()
            }
            adapter.setNewData(it)
        })
        initView()
        viewModel.load(false)
    }

    private fun initView() {
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
        adapter.setOnItemClickListener { _, _, position ->
            val urls = arrayOfNulls<String>(adapter.data.size)
            adapter.data.forEachIndexed { index, item ->
                urls[index] = checkUrl(item.filePath)
            }
            Diooto(context)
                    .urls(urls)
                    .type(DiootoConfig.PHOTO)
                    .position(position)
                    .views(viewDataBinding.rvPics, R.id.iv_image)
                    .loadPhotoBeforeShowBigImage { sketchImageView, pos ->
                        sketchImageView.displayImage(checkUrl(getString(R.string.resize_250) +
                                adapter.getItem(pos)!!.filePath))
                    }
                    .start()
        }
    }

    companion object {
        fun newInstance() = DynamicPicturesFragment()
    }
}
