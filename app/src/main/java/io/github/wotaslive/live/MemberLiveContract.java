package io.github.wotaslive.live;

import java.util.List;

/**
 * Created by codeczx on 2017/10/10.
 */

public class MemberLiveContract {

    interface MemberLiveView {
        void refreshUI(List<Object> list);
    }

    interface MemberLivePresenter {
        void getMemberLive();
    }
}
