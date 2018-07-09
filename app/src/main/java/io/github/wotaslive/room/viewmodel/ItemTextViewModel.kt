package io.github.wotaslive.room.viewmodel

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import io.github.wotaslive.Constants
import io.github.wotaslive.data.model.ExtInfo

class ItemTextViewModel(obj: Any) : ViewModel() {
    private val extInfo: ExtInfo by lazy {
        obj as ExtInfo
    }
    val avatar = ObservableField<String>(extInfo.senderAvatar)
    val senderName = ObservableField<String>(extInfo.senderName)
    val isFanpai = ObservableBoolean(extInfo.messageObject == Constants.MESSAGE_TYPE_FANPAI_TEXT)
    val isFromCreator = ObservableBoolean(extInfo.role == 0)
    val text = ObservableField<String>(extInfo.text)
    val juju = ObservableField<String>(extInfo.faipaiContent)
}