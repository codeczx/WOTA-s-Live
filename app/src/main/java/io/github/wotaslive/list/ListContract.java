package io.github.wotaslive.list;

import android.view.View;

import java.util.List;

import io.github.wotaslive.data.model.LiveInfo;

/**
 * Created by codeczx on 2017/10/10.
 */

class ListContract {

	interface MemberLiveView {
		void refreshUI();

		void updateLive(List<LiveInfo.ContentBean.RoomBean> list);

		void updateReview(List<LiveInfo.ContentBean.RoomBean> list);

		void showMenu(LiveInfo.ContentBean.RoomBean room, View anchor);
	}

	interface MemberLivePresenter extends ListAdapter.Callbacks {
		void getMemberLive();

		void setClipboard(String text);
	}
}
