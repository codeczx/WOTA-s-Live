package io.github.wotaslive.roomlist.room.pictures


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import io.github.wotaslive.R
import io.github.wotaslive.data.model.DynamicPictureInfo
import io.github.wotaslive.databinding.FragPicsBinding
import io.github.wotaslive.roomlist.room.RoomDetailActivity
import io.github.wotaslive.roomlist.room.RoomViewModel
import io.github.wotaslive.utils.checkUrl
import io.github.wotaslive.utils.obtainViewModel
import io.github.wotaslive.utils.setupActionBar
import net.moyokoo.diooto.Diooto
import net.moyokoo.diooto.config.DiootoConfig
import net.moyokoo.diooto.interfaces.CircleIndexIndicator
import net.moyokoo.diooto.interfaces.DefaultPercentProgress
import java.util.*


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
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
            val urls = arrayOfNulls<String>(adapter.data.size)
            adapter.data.forEachIndexed { index, any ->
                urls[index] = checkUrl((any as DynamicPictureInfo.Content.Data).filePath)
            }
            Diooto(context)
                    .urls(Arrays.copyOfRange(urls, position, adapter.data.size))
                    .type(DiootoConfig.PHOTO)
                    .fullscreen(false)
                    .position(0)
                    .views(viewDataBinding.rvPics, R.id.iv_image)
                    .setIndicator(CircleIndexIndicator())
                    .setProgress(DefaultPercentProgress())
                    .start()
        }
        viewDataBinding.setLifecycleOwner(this)
        setupActionBar(viewDataBinding.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
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
            layoutManager = GridLayoutManager(context, 3)
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

    companion object {
        fun newInstance() = DynamicPicturesFragment()
    }
}
