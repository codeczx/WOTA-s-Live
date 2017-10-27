package io.github.wotaslive.data.model;

public class LiveRequestBody {
	
	/**
	 * lastTime : 0
	 * groupId : 0
	 * type : 0
	 * memberId : 0
	 * limit : 20
	 * giftUpdTime : 1498211389003
	 */
	
	private int lastTime;
	private int groupId;
	private int type;
	private int memberId;
	private int limit;
	private long giftUpdTime;
	
	public LiveRequestBody(int lastTime, int groupId, int type, int memberId, int limit, long giftUpdTime) {
		this.lastTime = lastTime;
		this.groupId = groupId;
		this.type = type;
		this.memberId = memberId;
		this.limit = limit;
		this.giftUpdTime = giftUpdTime;
	}
	
	public int getLastTime() {
		return lastTime;
	}
	
	public void setLastTime(int lastTime) {
		this.lastTime = lastTime;
	}
	
	public int getGroupId() {
		return groupId;
	}
	
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getMemberId() {
		return memberId;
	}
	
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	
	public int getLimit() {
		return limit;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	public long getGiftUpdTime() {
		return giftUpdTime;
	}
	
	public void setGiftUpdTime(long giftUpdTime) {
		this.giftUpdTime = giftUpdTime;
	}
}
