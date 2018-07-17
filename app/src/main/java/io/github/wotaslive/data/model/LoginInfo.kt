package io.github.wotaslive.data.model

/**
 * Created by codeczx on 2017/11/7 0:19.
 * Class description:
 */
class LoginInfo {

    var status: Int = 0
    var message: String? = null
    var content: ContentBean? = null

    class ContentBean {

        var userInfo: UserInfoBean? = null
        var token: String? = null
        var isTodayPunchCard: Boolean = false
        var bindInfo: List<BindInfoBean>? = null
        var friends: List<Int>? = null
        var functionIds: List<Int>? = null

        class UserInfoBean {
            /**
             * userId : 597267
             * nickName : 15625053113
             * pocketId :
             * avatar : /mediasource/profile_icon.png
             * experience : 5
             * level : 1
             * gender : 0
             * punchCardDay : 0
             * role : 0
             */

            var userId: Int = 0
            var nickName: String? = null
            var pocketId: String? = null
            var avatar: String? = null
            var experience: Int = 0
            var level: Int = 0
            var gender: Int = 0
            var punchCardDay: Int = 0
            var role: Int = 0
        }

        class BindInfoBean {

            var type: Int = 0
            var thirdName: String? = null
        }
    }
}
