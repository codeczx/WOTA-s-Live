package io.github.wotaslive.data.model;

/**
 * Created by Tony on 2017/10/22 15:41.
 * Class description:
 */

public class LiveOneRequestBody {

	/**
	 * type : 0
	 * userId : 594709
	 * liveId : 59dda14b0cf2d32740bafa6d
	 */

	private int type;
	private int userId;
	private String liveId;

	public LiveOneRequestBody(int type, int userId, String liveId) {
		this.type = type;
		this.userId = userId;
		this.liveId = liveId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getLiveId() {
		return liveId;
	}

	public void setLiveId(String liveId) {
		this.liveId = liveId;
	}
}
