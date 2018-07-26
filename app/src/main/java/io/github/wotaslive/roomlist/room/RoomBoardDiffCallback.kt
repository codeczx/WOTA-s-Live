package io.github.wotaslive.roomlist.room

import android.support.v7.util.DiffUtil
import io.github.wotaslive.data.model.ExtInfo

class RoomBoardDiffCallback : DiffUtil.ItemCallback<ExtInfo>() {
    override fun areItemsTheSame(oldItem: ExtInfo?, newItem: ExtInfo?): Boolean {
        return oldItem?.msgTime == newItem?.msgTime
    }

    override fun areContentsTheSame(oldItem: ExtInfo?, newItem: ExtInfo?): Boolean {
        return oldItem?.text == newItem?.text &&
                oldItem?.msgTime == newItem?.msgTime &&
                oldItem?.senderAvatar == newItem?.senderAvatar &&
                oldItem?.senderName == newItem?.senderName
    }
}