package io.github.wotaslive.list;

import java.util.List;

import io.github.wotaslive.data.model.LiveInfo;

/**
 * Created by codeczx on 2017/10/10.
 */

public class ListContract {
	
	interface MemberLiveView {
		void refreshUI();
		
		void updateLive(List<LiveInfo.ContentBean.RoomBean> list);
		
		void updateReview(List<LiveInfo.ContentBean.RoomBean> list);
	}
	
	interface MemberLivePresenter {
		void getMemberLive();
	}
}
