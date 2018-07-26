package io.github.wotaslive.roomlist.room.viewmodel

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import io.github.wotaslive.data.model.ExtInfo
import io.github.wotaslive.utils.ChatDateTime

class ItemBoardViewModel(val extInfo: ExtInfo) : ViewModel() {
    val senderAvatar = ObservableField<String>(extInfo.senderAvatar)
    val text = ObservableField<String>(extInfo.text)
    val senderName = ObservableField<String>(extInfo.senderName)
    val msgTime = ObservableField<String>(ChatDateTime.getNiceTime(extInfo.msgTime))
    val senderLevel = ObservableField<String>(extInfo.senderLevel)
}