package io.github.wotaslive.widget

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableField
import android.databinding.ObservableInt

class MediaControlViewModel : ViewModel() {
    val max = ObservableInt()
    val progress = ObservableInt()
    val curTime = ObservableField<String>()
    val totalTime = ObservableField<String>()


    fun setCurTime(time: Long) {
        curTime.set(stringForTime(time))
    }

    fun setTotalTime(time: Long) {
        totalTime.set(stringForTime(time))
    }

    /**
     * Time Format
     *
     * @param timeMs
     * @return
     */
    private fun stringForTime(timeMs: Long): String {
        val totalSeconds = timeMs / 1000

        val seconds = totalSeconds % 60
        val minutes = totalSeconds / 60 % 60
        val hours = totalSeconds / 3600

        return if (hours > 0) {
            String.format("%d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format("%02d:%02d", minutes, seconds)
        }
    }
}