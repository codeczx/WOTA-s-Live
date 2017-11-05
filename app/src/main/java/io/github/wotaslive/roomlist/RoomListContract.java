package io.github.wotaslive.roomlist;

import java.util.List;

import io.github.wotaslive.BasePresenter;
import io.github.wotaslive.BaseView;
import io.github.wotaslive.data.model.RoomInfo;

/**
 * Created by codeczx on 2017/11/2 21:18.
 * Class description:
 */
public class RoomListContract {

	interface RoomListView extends BaseView<RoomListPresenter>{

		void updateRoom(List<RoomInfo.ContentBean> roomInfo);

		void refreshUI();
	}

	interface RoomListPresenter extends BasePresenter{

		void getRoomList();
	}
}
