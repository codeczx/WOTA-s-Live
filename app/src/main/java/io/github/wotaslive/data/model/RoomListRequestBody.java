package io.github.wotaslive.data.model;

import java.util.List;

/**
 * Created by codeczx on 2017/11/2 21:50.
 * Class description:
 */
public class RoomListRequestBody {

	private List<Integer> friends;

	public List<Integer> getFriends() {
		return friends;
	}

	public RoomListRequestBody(List<Integer> friends) {
		this.friends = friends;
	}

	public void setFriends(List<Integer> friends) {
		this.friends = friends;
	}
}
