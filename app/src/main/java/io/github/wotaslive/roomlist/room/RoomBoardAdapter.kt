package io.github.wotaslive.roomlist.room

import android.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import io.github.wotaslive.R
import io.github.wotaslive.data.model.ExtInfo
import io.github.wotaslive.databinding.ItemRoomBoardBinding
import io.github.wotaslive.roomlist.room.viewmodel.ItemBoardViewModel

class RoomBoardAdapter : BaseQuickAdapter<ExtInfo, BaseViewHolder>(R.layout.item_room_board) {
    override fun convert(helper: BaseViewHolder?, item: ExtInfo?) {
        helper?.itemView?.let {
            val binding: ItemRoomBoardBinding? = DataBindingUtil.bind(it)
            item?.let {
                binding?.viewModel = ItemBoardViewModel(item)
            }
        }
    }
}