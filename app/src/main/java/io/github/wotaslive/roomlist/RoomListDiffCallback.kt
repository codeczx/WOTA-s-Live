package io.github.wotaslive.roomlist

import android.support.v7.util.DiffUtil
import io.github.wotaslive.data.model.RoomInfo

class RoomListDiffCallback : DiffUtil.ItemCallback<RoomInfo.ContentBean>() {
    override fun areItemsTheSame(oldItem: RoomInfo.ContentBean?, newItem: RoomInfo.ContentBean?): Boolean {
        return oldItem?.roomId == newItem?.roomId
    }

    override fun areContentsTheSame(oldItem: RoomInfo.ContentBean?, newItem: RoomInfo.ContentBean?): Boolean {
        return oldItem?.roomName == newItem?.roomName &&
                oldItem?.commentTimeMs == newItem?.commentTimeMs &&
                oldItem?.roomAvatar == newItem?.roomAvatar
    }
}