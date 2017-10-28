package io.github.wotaslive.data.model;

/**
 * Created by Tony on 2017/10/29 0:16.
 * Class description:
 */

public class ShowRequestBody {

	/**
	 * isReview : 1
	 * groupId : 0
	 * userId : 594709
	 * lastGroupId : 0
	 * lastTime : 0
	 * type : 0
	 * limit : 20
	 * giftUpdTime : 1498211389003
	 */

	private int isReview;
	private int groupId;
	private int userId;
	private int lastGroupId;
	private long lastTime;
	private int type;
	private int limit;
	private long giftUpdTime;

	public ShowRequestBody(int isReview, int groupId, int lastGroupId, long lastTime, int limit, long giftUpdTime) {
		this.isReview = isReview;
		this.groupId = groupId;
		this.lastGroupId = lastGroupId;
		this.lastTime = lastTime;
		this.limit = limit;
		this.giftUpdTime = giftUpdTime;
	}

	public int getIsReview() {
		return isReview;
	}

	public void setIsReview(int isReview) {
		this.isReview = isReview;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getLastGroupId() {
		return lastGroupId;
	}

	public void setLastGroupId(int lastGroupId) {
		this.lastGroupId = lastGroupId;
	}

	public long getLastTime() {
		return lastTime;
	}

	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
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
