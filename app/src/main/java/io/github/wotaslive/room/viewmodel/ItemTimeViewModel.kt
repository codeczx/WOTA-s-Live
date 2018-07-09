package io.github.wotaslive.room.viewmodel

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import io.github.wotaslive.utils.ChatDateTime

class ItemTimeViewModel(obj: Any) : ViewModel() {
    private val timeMills: Long by lazy {
        obj as Long
    }
    val time = ObservableField<String>(ChatDateTime.getNiceTime(timeMills))
}