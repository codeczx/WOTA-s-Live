package io.github.wotaslive.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object ChatDateTime {
    private const val DAY = 60 * 60 * 24 * 1000

    /**
     * 获取当天0点的时间点
     *
     * @return 时间点
     */
    private val startTimeOfDay: Long
        get() {
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.SECOND, 0)
            cal.set(Calendar.MINUTE, 0)
            cal.set(Calendar.MILLISECOND, 0)
            return cal.timeInMillis
        }

    /**
     * 获取日期的星期数
     *
     * @param dt 日期
     * @return 星期
     */
    private fun getWeekOfDate(dt: Date): String {
        val weekDays = arrayOf("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
        val cal = Calendar.getInstance()
        cal.time = dt
        var w = cal.get(Calendar.DAY_OF_WEEK) - 1
        if (w < 0)
            w = 0
        return weekDays[w]
    }

    /**
     * 获取显示的时间描述文字
     *
     * @param time 需要转换的时间
     * @return 时间描述
     */
    fun getNiceTime(time: Long): String {
        val pointText: String
        val date = Date(time)
        val df: DateFormat
        if (time < startTimeOfDay) {
            return when {
                time >= startTimeOfDay - DAY -> {
                    //昨天
                    df = SimpleDateFormat("HH:mm", Locale.US)
                    pointText = "昨天 " + df.format(date)
                    pointText
                }
                time >= startTimeOfDay - 6 * DAY -> {
                    //近7天
                    df = SimpleDateFormat("HH:mm", Locale.US)
                    getWeekOfDate(date) + " " + df.format(date)
                }
                else -> {
                    //7天以前
                    df = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US)
                    pointText = df.format(date)
                    pointText
                }
            }

        } else {
            //今天
            df = SimpleDateFormat("HH:mm", Locale.US)
            pointText = df.format(date)
            return pointText
        }
    }
}
