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
		void refreshUI();

		void updateLive(List<LiveInfo.ContentBean.RoomBean> list);

		void updateReview(List<LiveInfo.ContentBean.RoomBean> list);

		void showMenu(LiveInfo.ContentBean.RoomBean room, View anchor);
	}

	interface LiveListPresenter extends BasePresenter, LiveListAdapter.Callbacks {
		void getMemberLive();

		void setClipboard(String text);
	}
}
