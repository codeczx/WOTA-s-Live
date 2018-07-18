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
    private val textContent: String by lazy {
        when {
            Constants.MESSAGE_TYPE_IDOL_FLIP == extInfo.messageObject -> return@lazy extInfo.bodys
                    ?: ""
            Constants.MESSAGE_TYPE_FANPAI_TEXT == extInfo.messageObject -> return@lazy extInfo.messageText
                    ?: ""
            else -> return@lazy extInfo.text ?: ""
        }
    }
    val avatar = ObservableField<String>(extInfo.senderAvatar)
    val senderName = ObservableField<String>(extInfo.senderName)
    val senderLevel = ObservableField<String>(extInfo.senderLevel)
    val isFanpai = ObservableBoolean(extInfo.messageObject == Constants.MESSAGE_TYPE_FANPAI_TEXT)
    val isFromCreator = ObservableBoolean(extInfo.role == 2)
    val text = ObservableField<String>(textContent)
    val juju = ObservableField<String>(extInfo.faipaiContent)
}