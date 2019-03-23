package io.github.wotaslive.roomlist.room

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.google.gson.JsonParser
import io.github.wotaslive.Constants
import io.github.wotaslive.R
import io.github.wotaslive.data.model.ExtInfo
import io.github.wotaslive.databinding.FragRoomDetailBinding
import io.github.wotaslive.roomlist.room.pictures.DynamicPicturesFragment
import io.github.wotaslive.utils.obtainViewModel
import io.github.wotaslive.utils.setupActionBar
import io.github.wotaslive.utils.showSnackbar
import net.moyokoo.diooto.Diooto
import net.moyokoo.diooto.config.DiootoConfig

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
            viewDataBinding.lifecycleOwner = this@RoomDetailFragment
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
        subscribe()
        initView()
        viewModel.load()
    }

    private fun subscribe() {
        with(viewModel) {
            roomDetailData.observe(this@RoomDetailFragment, Observer {
                adapter.addData(it.orEmpty())
                viewDataBinding.srlRoomDetail.finishRefresh()
            })
            roomBoardData.observe(this@RoomDetailFragment, Observer {
                if (viewModel.isLoadMore) {
                    boardAdapter.addData(it.orEmpty())
                    viewDataBinding.srlRoomBoard.finishLoadMore()
                } else {
                    boardAdapter.setNewData(it.orEmpty())
                    viewDataBinding.srlRoomBoard.finishRefresh()
                }
            })
            errorCommand.observe(this@RoomDetailFragment, Observer {
                it?.let {
                    viewDataBinding.srlRoomDetail.finishLoadMoreWithNoMoreData()
                    viewDataBinding.rvRoomDetail.showSnackbar(getString(it), Snackbar.LENGTH_SHORT)
                }
            })
            errorBoardCommand.observe(this@RoomDetailFragment, Observer {
                it?.let {
                    viewDataBinding.srlRoomBoard.finishRefresh()
                    viewDataBinding.srlRoomBoard.finishLoadMoreWithNoMoreData()
                    viewDataBinding.srlRoomBoard.showSnackbar(getString(it), Snackbar.LENGTH_SHORT)
                }
            })
        }
    }

    private fun initView() {
        with(viewDataBinding) {
            viewModel = this@RoomDetailFragment.viewModel
            lifecycleOwner = this@RoomDetailFragment
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

        adapter.setOnItemChildClickListener { _, view, position ->
            Diooto(context)
                    .urls(JsonParser().parse((adapter.getItem(position) as ExtInfo).bodys).asJsonObject["url"].asString)
                    .type(DiootoConfig.PHOTO)
                    .position(position)
                    .views(view)
                    .start()
        }
    }

    companion object {
        fun newInstance() = RoomDetailFragment()
    }
}
