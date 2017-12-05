package io.github.wotaslive.livelist;

import android.view.View;

import java.util.List;

import io.github.wotaslive.BasePresenter;
import io.github.wotaslive.BaseView;
import io.github.wotaslive.data.model.LiveInfo;

/**
 * Author codeczx
 * Created at 2017/10/10
 */

class LiveListContract {

	interface LiveListView extends BaseView<LiveListPresenter> {
		void refreshList(List<LiveInfo.ContentBean.RoomBean> liveList, List<LiveInfo.ContentBean.RoomBean> reviewList);

		void updateList(List<LiveInfo.ContentBean.RoomBean> liveList, List<LiveInfo.ContentBean.RoomBean> reviewList);

		void showMenu(LiveInfo.ContentBean.RoomBean room, View anchor);

		void stopRefreshing();
	}

	interface LiveListPresenter extends BasePresenter, LiveListAdapter.Callbacks {
		void getMemberLive();

		void loadMemberLive();

		void setClipboard(String text);
	}
}
