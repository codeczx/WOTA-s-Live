package io.github.wotaslive.room;

import java.util.List;

import io.github.wotaslive.BasePresenter;
import io.github.wotaslive.BaseView;
import io.github.wotaslive.data.model.ExtInfo;
import io.github.wotaslive.data.model.RoomDetailInfo;

/**
 * Created by codeczx on 2017/11/9 0:18.
 * Class description:
 */
class RoomDetailContract {

	interface RoomDetailPresenter extends BasePresenter {

		void getRoomDetailInfo(int roomId, int i);
	}

	interface RoomDetailView extends BaseView<RoomDetailPresenter> {

		void updateData(List<ExtInfo> extInfoList, List<RoomDetailInfo.ContentBean.DataBean> content);

		void refreshUI();
	}
}
