package io.github.wotaslive.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class LiveInfo(var status: Int = 0,
                    var message: String? = null,
                    var content: ContentBean? = null) {

    data class ContentBean(var giftUpdTime: Long = 0,
                           @SerializedName("liveList")
                           var liveList: List<RoomBean>? = null,
                           @SerializedName("reviewList")
                           var reviewList: List<RoomBean>? = null,
                           var giftUpdUrl: List<*>? = null,
                           var hasReviewUids: List<Int>? = null) {

        /**
         * liveId : 59de13f10cf294bc616de87e
         * title : 吕梦莹的直播间
         * subTitle : 日常写作业🐷
         * picPath : /mediasource/live/15061690159861h5h22LAZ0.jpg
         * startTime : 1507726321749
         * memberId : 286982
         * liveType : 1
         * picLoopTime : 0
         * lrcPath : /mediasource/live/lrc/59de13f10cf294bc616de87e.lrc
         * streamPath : http://2519.liveplay.myqcloud.com/live/2519_3145421.flv
         * screenMode : 0
         */
        data class RoomBean(var liveId: String? = null,
                            var title: String? = null,
                            var subTitle: String? = null,
                            var picPath: String? = null,
                            var startTime: Long = 0,
                            var memberId: Int = 0,
                            var liveType: Int = 0,
                            var picLoopTime: Int = 0,
                            var lrcPath: String? = null,
                            var streamPath: String? = null,
                            var screenMode: Int = 0):Parcelable {
            constructor(parcel: Parcel) : this(
                    parcel.readString(),
                    parcel.readString(),
                    parcel.readString(),
                    parcel.readString(),
                    parcel.readLong(),
                    parcel.readInt(),
                    parcel.readInt(),
                    parcel.readInt(),
                    parcel.readString(),
                    parcel.readString(),
                    parcel.readInt()) {
            }

            override fun writeToParcel(parcel: Parcel, flags: Int) {
                parcel.writeString(liveId)
                parcel.writeString(title)
                parcel.writeString(subTitle)
                parcel.writeString(picPath)
                parcel.writeLong(startTime)
                parcel.writeInt(memberId)
                parcel.writeInt(liveType)
                parcel.writeInt(picLoopTime)
                parcel.writeString(lrcPath)
                parcel.writeString(streamPath)
                parcel.writeInt(screenMode)
            }

            override fun describeContents(): Int {
                return 0
            }

            companion object CREATOR : Parcelable.Creator<RoomBean> {
                override fun createFromParcel(parcel: Parcel): RoomBean {
                    return RoomBean(parcel)
                }

                override fun newArray(size: Int): Array<RoomBean?> {
                    return arrayOfNulls(size)
                }
            }
        }
    }
}
