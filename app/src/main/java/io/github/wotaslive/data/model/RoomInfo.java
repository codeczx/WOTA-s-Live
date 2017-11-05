package io.github.wotaslive.data.model;

import java.util.List;

/**
 * Created by codeczx on 2017/11/2 21:51.
 * Class description:
 */
public class RoomInfo {

	private int status;
	private String message;
	private List<ContentBean> content;

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

	public List<ContentBean> getContent() {
		return content;
	}

	public void setContent(List<ContentBean> content) {
		this.content = content;
	}

	public static class ContentBean {
		/**
		 * roomId : 5770618
		 * roomType : 1
		 * chatType : 0
		 * roomName : 李清扬
		 * roomAvatar : /mediasource/room/14916912868567h66NnMvFu.jpg
		 * roomTopic :
		 * creatorId : 28
		 * creatorName : 李清扬
		 * creatorRole : 1
		 * comment : 缺席三周年公演好遗憾老是特殊公演的时候有事情委屈T^T
		 * commentTime : 3天前
		 * commentTimeMs : 1509278654683
		 * hot : 0
		 * bgPath :
		 * fontColor :
		 * maxJoinerNum : 200
		 * nowJoinerNum : 0
		 */

		private int roomId;
		private int roomType;
		private int chatType;
		private String roomName;
		private String roomAvatar;
		private String roomTopic;
		private int creatorId;
		private String creatorName;
		private int creatorRole;
		private String comment;
		private String commentTime;
		private long commentTimeMs;
		private int hot;
		private String bgPath;
		private String fontColor;
		private int maxJoinerNum;
		private int nowJoinerNum;

		public int getRoomId() {
			return roomId;
		}

		public void setRoomId(int roomId) {
			this.roomId = roomId;
		}

		public int getRoomType() {
			return roomType;
		}

		public void setRoomType(int roomType) {
			this.roomType = roomType;
		}

		public int getChatType() {
			return chatType;
		}

		public void setChatType(int chatType) {
			this.chatType = chatType;
		}

		public String getRoomName() {
			return roomName;
		}

		public void setRoomName(String roomName) {
			this.roomName = roomName;
		}

		public String getRoomAvatar() {
			return roomAvatar;
		}

		public void setRoomAvatar(String roomAvatar) {
			this.roomAvatar = roomAvatar;
		}

		public String getRoomTopic() {
			return roomTopic;
		}

		public void setRoomTopic(String roomTopic) {
			this.roomTopic = roomTopic;
		}

		public int getCreatorId() {
			return creatorId;
		}

		public void setCreatorId(int creatorId) {
			this.creatorId = creatorId;
		}

		public String getCreatorName() {
			return creatorName;
		}

		public void setCreatorName(String creatorName) {
			this.creatorName = creatorName;
		}

		public int getCreatorRole() {
			return creatorRole;
		}

		public void setCreatorRole(int creatorRole) {
			this.creatorRole = creatorRole;
		}

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
		}

		public String getCommentTime() {
			return commentTime;
		}

		public void setCommentTime(String commentTime) {
			this.commentTime = commentTime;
		}

		public long getCommentTimeMs() {
			return commentTimeMs;
		}

		public void setCommentTimeMs(long commentTimeMs) {
			this.commentTimeMs = commentTimeMs;
		}

		public int getHot() {
			return hot;
		}

		public void setHot(int hot) {
			this.hot = hot;
		}

		public String getBgPath() {
			return bgPath;
		}

		public void setBgPath(String bgPath) {
			this.bgPath = bgPath;
		}

		public String getFontColor() {
			return fontColor;
		}

		public void setFontColor(String fontColor) {
			this.fontColor = fontColor;
		}

		public int getMaxJoinerNum() {
			return maxJoinerNum;
		}

		public void setMaxJoinerNum(int maxJoinerNum) {
			this.maxJoinerNum = maxJoinerNum;
		}

		public int getNowJoinerNum() {
			return nowJoinerNum;
		}

		public void setNowJoinerNum(int nowJoinerNum) {
			this.nowJoinerNum = nowJoinerNum;
		}
	}
}
