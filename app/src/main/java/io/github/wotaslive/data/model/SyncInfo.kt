package io.github.wotaslive.data.model


data class SyncInfo(
        val status: Int, // 200
        val message: String, // success
        val content: Content
) {

    data class Content(
            val url: List<Url>,
            val group: List<Group>,
            val team: List<Team>,
            val period: List<Period>,
            val function: List<Function>,
            val memberInfo: List<MemberInfo>,
            val partTime: List<Any>,
            val memberProperty: List<Any>,
            val propertyMember: List<Any>,
            val needUpdateMusic: Boolean, // false
            val needUpdateMusicAlbum: Boolean, // false
            val needUpdateVideo: Boolean, // false
            val needUpdateVideoType: Boolean, // false
            val needUpdateTalk: Boolean, // false
            val needUpateMusicAlbum: Boolean, // false
            val needUpateMusic: Boolean // false
    ) {

        data class Team(
                val team_id: Int, // 1403
                val group_id: Int, // 14
                val team_name: String, // 预备生
                val full_logo: String, // /mediasource/teamLogo/fulllogo/snh48_team_yubeisheng.png
                val logo1: String, // /mediasource/teamLogo/fulllogo/snh48_team_yubeisheng.png
                val logo2: String, // /mediasource/teamLogo/fulllogo/snh48_team_yubeisheng.png
                val avatar_background: String, // /mediasource/teamLogo/fulllogo/snh48_team_yubeisheng.png
                val color: String, // a7b0ba
                val status: Int, // 1
                val ctime: String, // 2018-04-09 17:43:00
                val utime: String // 2018-04-09 17:43:00
        )


        data class MemberInfo(
                val member_id: Int, // 844552
                val ctime: String, // 2018-07-24 14:42:09
                val utime: String, // 2018-07-24 14:46:00
                val real_name: String, // SHY48-星梦剧院
                val pinyin: String, // shy48xmjy
                val nick_name: String, // SHY48星梦剧院
                val team: Int, // 0
                val period: Int, // 0
                val avatar: String, // /mediasource/avatar/1532414520064RJW8z5Tv7T.jpg
                val jtime: String, // 2018-07-24 00:00:00
                val wb_uid: String, // 6427120034
                val wb_name: String, // SHY48星梦剧院
                val city: Int, // 13
                val height: Int, // 0
                val blood_type: String,
                val birthday: String,
                val constellation: String,
                val star_region: String,
                val birthplace: String,
                val specialty: String,
                val hobbies: String,
                val full_photo_1: String,
                val full_photo_2: String,
                val full_photo_3: String,
                val full_photo_4: String,
                val status: Int, // 1
                val first_team: Int, // 0
                val sid: Int, // 0
                val sno: Int // 0
        )


        data class Group(
                val group_id: Int, // 14
                val group_name: String, // CKG48
                val ctime: String, // 2017-10-17 17:27:00
                val utime: String, // 2017-10-17 17:27:00
                val info: String // CKG48
        )


        data class Url(
                val url_key: String, // UNBOUNDSTATE
                val url_name: String, // 帐号绑定解除说明
                val url_info: String, // https://h5.48.cn/2018apppage/nutie/rule.html
                val status: Int, // 2
                val ctime: String, // 2018-05-18 12:37:37
                val utime: String // 2018-05-18 12:37:41
        )


        data class Period(
                val period_id: Int, // 1402
                val group_id: Int, // 14
                val period_name: String, // CKG48 二期生
                val ctime: String, // 2018-04-09 18:02:00
                val utime: String // 2018-04-09 18:02:00
        )


        data class Function(
                val func_id: Int, // 3004
                val func_type_id: Int, // 3
                val func_type_name: String, // 活动任务
                val func_type_order: Int, // 3
                val func_key: String, // ACTIVE
                val func_name: String, // 活动
                val func_pic_path: String, // /mediasource/function/icon_fenlei_activity@3x.png
                val trans_type: Int, // 0
                val trans_path: String, // activity/list
                val role: Int, // 0
                val status: Int, // 1
                val ctime: String, // 2017-07-04 10:12:24
                val utime: String // 2018-04-28 10:57:35
        )
    }
}