package io.github.wotaslive.list;

import java.util.List;

/**
 * Created by codeczx on 2017/10/10.
 */

public class ListContract {

    interface MemberLiveView {
        void refreshUI(List<Object> list);
    }

    interface MemberLivePresenter {
        void getMemberLive();
    }
}
