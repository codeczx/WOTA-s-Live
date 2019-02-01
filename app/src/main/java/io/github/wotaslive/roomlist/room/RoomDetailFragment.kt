package io.github.wotaslive.roomlist.room

import android.arch.lifecycle.Observer
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.*
import android.widget.ImageView
import com.google.gson.JsonParser
import com.previewlibrary.GPreviewBuilder
import io.github.wotaslive.Constants
import io.github.wotaslive.R
import io.github.wotaslive.data.model.ExtInfo
import io.github.wotaslive.data.model.ImageInfo
import io.github.wotaslive.databinding.FragRoomDetailBinding
import io.github.wotaslive.roomlist.room.pictures.DynamicPicturesFragment
import io.github.wotaslive.utils.obtainViewModel
import io.github.wotaslive.utils.setupActionBar

class RoomDetailFragment : Fragment() {
    private lateinit var viewModel: RoomViewModel
    private lateinit var viewDataBinding: FragRoomDetailBinding
    private val adapter = RoomDetailAdapter()
    private val boardAdapter = RoomBoardAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewDataBinding = FragRoomDetailBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return viewDataBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_room, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.menu_pics) {
            fragmentManager?.run {
                beginTransaction()
                        .add(R.id.container, DynamicPicturesFragment.newInstance())
                        .hide(this@RoomDetailFragment)
                        .addToBackStack(this@RoomDetailFragment.javaClass.name)
                        .commit()
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = (activity as RoomDetailActivity).obtainViewModel(RoomViewModel::class.java).also {
            viewDataBinding.viewModel = it
            viewDataBinding.setLifecycleOwner(this@RoomDetailFragment)
        }
        (activity as RoomDetailActivity).let {
            val extras = it.intent.extras
            extras?.let {
                viewModel.roomId = extras.getInt(Constants.ROOM_ID)
                viewModel.memberId = extras.getInt(Constants.MEMBER_ID)
                viewModel.url.set(extras.getString(Constants.ROOM_BG_PATH))
                this.setupActionBar(viewDataBinding.toolbar) {
                    title = "${extras.getString(Constants.ROOM_CREATOR)}:${extras.getString(Constants.ROOM_NAME)}"
                    setDisplayHomeAsUpEnabled(true)
                    setDisplayShowHomeEnabled(true)
                }
            }
        }
        viewModel.roomDetailData.observe(this, Observer {
            adapter.addData(it.orEmpty())
            viewDataBinding.srlRoomDetail.finishRefresh()
        })
        viewModel.roomBoardData.observe(this, Observer {
            if (viewModel.isLoadMore) {
                boardAdapter.addData(it.orEmpty())
                viewDataBinding.srlRoomBoard.finishLoadMore()
            } else {
                boardAdapter.setNewData(it.orEmpty())
                viewDataBinding.srlRoomBoard.finishRefresh()
            }
        })
        setupBinding()
        adapter.setOnItemChildClickListener { adapter, _, position ->
            val info = adapter.getItem(position) as ExtInfo
            var idx = -1
            val layoutManager = viewDataBinding.rvRoomDetail.layoutManager as LinearLayoutManager
            val list = ArrayList<ImageInfo>()
            adapter.data.forEachIndexed { index, item ->
                if (item is ExtInfo && Constants.MESSAGE_TYPE_IMAGE == item.messageObject) {
                    if (TextUtils.equals(item.bodys, info.bodys)) idx = list.size
                    val rect = Rect()
                    val itemView = layoutManager.findViewByPosition(index)
                    itemView?.let {
                        val imageView = itemView.findViewById(R.id.iv_image) as ImageView
                        imageView.getGlobalVisibleRect(rect)
                    }
                    list.add(0, ImageInfo(JsonParser().parse(item.bodys).asJsonObject["url"].asString, null, rect))
                }
            }
            GPreviewBuilder.from(this)
                    .setData(list)
                    .setCurrentIndex(list.size - 1 - idx)
                    .setSingleFling(true)
                    .setType(GPreviewBuilder.IndicatorType.Number)
                    .start()
        }
        viewModel.load()
    }

    private fun setupBinding() {
        with(viewDataBinding) {
            viewModel = this@RoomDetailFragment.viewModel
            setLifecycleOwner(this@RoomDetailFragment)
        }
        with(viewDataBinding.srlRoomDetail) {
            setOnRefreshListener {
                viewModel.loadRoom()
            }
        }
        with(viewDataBinding.rvRoomDetail) {
            layoutManager = android.support.v7.widget.LinearLayoutManager(context, android.support.v7.widget.LinearLayoutManager.VERTICAL, true)
            adapter = this@RoomDetailFragment.adapter
        }
        with(viewDataBinding.srlRoomBoard) {
            setOnRefreshListener {
                viewModel.loadBoard(false)
            }
            setOnLoadMoreListener {
                viewModel.loadBoard(true)
            }
        }
        with(viewDataBinding.rvRoomBoard) {
            layoutManager = android.support.v7.widget.LinearLayoutManager(context)
            adapter = boardAdapter
        }
    }

    companion object {
        fun newInstance() = RoomDetailFragment()
    }
}
