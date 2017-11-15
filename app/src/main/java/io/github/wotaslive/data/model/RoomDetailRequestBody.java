package io.github.wotaslive.data.model;

/**
 * Created by codeczx on 2017/11/7 23:24.
 * Class description:
 */
public class RoomDetailRequestBody {

	/**
	 * roomId : 5777239
	 * chatType : 0
	 * lastTime : 0
	 * limit : 10
	 */

	private int roomId;
	private int chatType;
	private int lastTime;
	private int limit;

	public RoomDetailRequestBody(int roomId, int chatType, int lastTime, int limit) {
		this.roomId = roomId;
		this.chatType = chatType;
		this.lastTime = lastTime;
		this.limit = limit;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getChatType() {
		return chatType;
	}

	public void setChatType(int chatType) {
		this.chatType = chatType;
	}

	public int getLastTime() {
		return lastTime;
	}

	public void setLastTime(int lastTime) {
		this.lastTime = lastTime;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}
