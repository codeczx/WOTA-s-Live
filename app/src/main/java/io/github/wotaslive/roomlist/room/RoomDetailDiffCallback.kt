package io.github.wotaslive.roomlist.room

import android.support.v7.util.DiffUtil
import io.github.wotaslive.data.model.ExtInfo

class RoomDetailDiffCallback : DiffUtil.ItemCallback<Any>() {
    override fun areItemsTheSame(oldItem: Any?, newItem: Any?): Boolean {
        if (oldItem is Long && newItem is Long) {
            return oldItem == newItem
        } else if (oldItem is ExtInfo && newItem is ExtInfo) {
            return oldItem.sourceId == newItem.sourceId
        }
        return false
    }

    override fun areContentsTheSame(oldItem: Any?, newItem: Any?): Boolean {
        if (oldItem is Long && newItem is Long) {
            return oldItem == newItem
        } else if (oldItem is ExtInfo && newItem is ExtInfo) {
            return oldItem.senderAvatar == newItem.senderAvatar &&
                    oldItem.text == newItem.text &&
                    oldItem.messageObject == newItem.messageObject
        }
        return false
    }
}