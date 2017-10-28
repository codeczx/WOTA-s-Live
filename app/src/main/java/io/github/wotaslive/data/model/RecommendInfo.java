package io.github.wotaslive.data.model;

import java.util.List;

/**
 * Created by Tony on 2017/10/29 1:38.
 * Class description:
 */

public class RecommendInfo {

	/**
	 * status : 200
	 * message : 请求成功
	 * content : [{"sourceId":"2135","sourceType":3,"sourceName":"视频","picPath":"/mediasource/video/1509084512611kK3b7Jq5Ob.jpg","memo":"《记忆中的你我》MV","groupId":10,"heat":3217},{"sourceId":"239","sourceType":4,"sourceName":"夜谈","picPath":"/mediasource/nightwords/15090039890052pHd3meIJ0.jpg","memo":"口口一的KOKORO SONG","groupId":10,"heat":1400}]
	 */

	private int status;
	private String message;
	private List<RecommendBean> content;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<RecommendBean> getContent() {
		return content;
	}

	public void setContent(List<RecommendBean> content) {
		this.content = content;
	}

	public static class RecommendBean {
		/**
		 * sourceId : 2135
		 * sourceType : 3
		 * sourceName : 视频
		 * picPath : /mediasource/video/1509084512611kK3b7Jq5Ob.jpg
		 * memo : 《记忆中的你我》MV
		 * groupId : 10
		 * heat : 3217
		 */

		private String sourceId;
		private int sourceType;
		private String sourceName;
		private String picPath;
		private String memo;
		private int groupId;
		private int heat;

		public String getSourceId() {
			return sourceId;
		}

		public void setSourceId(String sourceId) {
			this.sourceId = sourceId;
		}

		public int getSourceType() {
			return sourceType;
		}

		public void setSourceType(int sourceType) {
			this.sourceType = sourceType;
		}

		public String getSourceName() {
			return sourceName;
		}

		public void setSourceName(String sourceName) {
			this.sourceName = sourceName;
		}

		public String getPicPath() {
			return picPath;
		}

		public void setPicPath(String picPath) {
			this.picPath = picPath;
		}

		public String getMemo() {
			return memo;
		}

		public void setMemo(String memo) {
			this.memo = memo;
		}

		public int getGroupId() {
			return groupId;
		}

		public void setGroupId(int groupId) {
			this.groupId = groupId;
		}

		public int getHeat() {
			return heat;
		}

		public void setHeat(int heat) {
			this.heat = heat;
		}
	}
}
