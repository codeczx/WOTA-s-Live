package io.github.wotaslive.data.model;

import java.util.List;

/**
 * Created by codeczx on 2017/11/2 21:49.
 * Class description:
 */
public class RoomListDiffRequestBody {

	/**
	 * clientTime : 1509288860624
	 * roomIds : [5782033,5776908,5774517,5776968,5778528,9011537,5776909,5770619,5780790,5771863,5783159,5774519,5779211,5770618,5773753,5773766,5777239,5778526,5776912]
	 */

	private long clientTime;
	private List<Integer> roomIds;

	public long getClientTime() {
		return clientTime;
	}

	public void setClientTime(long clientTime) {
		this.clientTime = clientTime;
	}

	public List<Integer> getRoomIds() {
		return roomIds;
	}

	public void setRoomIds(List<Integer> roomIds) {
		this.roomIds = roomIds;
	}
}
