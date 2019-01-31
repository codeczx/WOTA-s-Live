package io.github.wotaslive.roomlist.room

import android.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.util.MultiTypeDelegate
import io.github.wotaslive.Constants
import io.github.wotaslive.R
import io.github.wotaslive.data.model.ExtInfo
import io.github.wotaslive.databinding.ItemRoomImageBinding
import io.github.wotaslive.databinding.ItemRoomLiveBinding
import io.github.wotaslive.databinding.ItemRoomTextBinding
import io.github.wotaslive.databinding.ItemRoomTimeBinding
import io.github.wotaslive.roomlist.room.viewmodel.ItemImageViewModel
import io.github.wotaslive.roomlist.room.viewmodel.ItemLiveViewModel
import io.github.wotaslive.roomlist.room.viewmodel.ItemTextViewModel
import io.github.wotaslive.roomlist.room.viewmodel.ItemTimeViewModel

class RoomDetailAdapter : BaseQuickAdapter<Any, BaseViewHolder>(null) {

    init {
        multiTypeDelegate = object : MultiTypeDelegate<Any>() {
            override fun getItemType(t: Any?): Int {
                if (t is Long) return R.layout.item_room_time
                return when ((t as ExtInfo).messageObject) {
                    Constants.MESSAGE_TYPE_TEXT,
                    Constants.MESSAGE_TYPE_FANPAI_TEXT ->
                        R.layout.item_room_text
                    Constants.MESSAGE_TYPE_IMAGE ->
                        R.layout.item_room_image
                    Constants.MESSAGE_TYPE_LIVE,
                    Constants.MESSAGE_TYPE_DIANTAI ->
                        R.layout.item_room_live
                    else ->
                        R.layout.item_room_text
                }
            }
        }
        multiTypeDelegate
                .registerItemType(R.layout.item_room_time, R.layout.item_room_time)
                .registerItemType(R.layout.item_room_text, R.layout.item_room_text)
                .registerItemType(R.layout.item_room_image, R.layout.item_room_image)
                .registerItemType(R.layout.item_room_live, R.layout.item_room_live)
    }

    override fun convert(helper: BaseViewHolder?, item: Any?) {
        helper?.itemView?.let {
            item?.let { it1 ->
                when (helper.itemViewType) {
                    R.layout.item_room_image -> {
                        val binding: ItemRoomImageBinding? = DataBindingUtil.bind(it)
                        binding?.viewModel = ItemImageViewModel(it1)
                        helper.addOnClickListener(R.id.iv_image)
                    }
                    R.layout.item_room_time -> {
                        val binding: ItemRoomTimeBinding? = DataBindingUtil.bind(it)
                        binding?.viewModel = ItemTimeViewModel(it1)
                    }
                    R.layout.item_room_live -> {
                        val binding: ItemRoomLiveBinding? = DataBindingUtil.bind(it)
                        binding?.viewModel = ItemLiveViewModel(it1)
                    }
//                    R.layout.item_room_text
                    // 把其他无法识别的都当初文字消息处理
                    else -> {
                        val binding: ItemRoomTextBinding? = DataBindingUtil.bind(it)
                        binding?.viewModel = ItemTextViewModel(it1)
                    }
                }
            }
        }
    }
}