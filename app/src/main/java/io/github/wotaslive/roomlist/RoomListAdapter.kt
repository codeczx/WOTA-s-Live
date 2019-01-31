package io.github.wotaslive.roomlist

import android.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import io.github.wotaslive.R
import io.github.wotaslive.data.model.RoomInfo
import io.github.wotaslive.databinding.ItemRoomBinding

class RoomListAdapter : BaseQuickAdapter<RoomInfo.ContentBean, BaseViewHolder>(R.layout.item_room) {
    override fun convert(helper: BaseViewHolder?, item: RoomInfo.ContentBean?) {
        helper?.itemView?.let {
            val binding: ItemRoomBinding? = DataBindingUtil.bind(it)
            item?.let { it1 ->
                binding?.viewModel = RoomItemViewModel(it1)
            }
        }
    }
}