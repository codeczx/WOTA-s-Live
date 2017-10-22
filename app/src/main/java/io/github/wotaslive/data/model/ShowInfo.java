package io.github.wotaslive.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Tony on 2017/10/22 22:26.
 * Class description:
 */

public class ShowInfo {

	/**
	 * status : 200
	 * message : 请求成功
	 * content : {"liveList":[{"liveId":"59e6c4be0cf23fa04ee4c2fd","title":"《第48区》剧场公演","subTitle":"TEAM SII剧场公演","picPath":"/mediasource/live/1508295870745LpNjGPwh18.jpg","isOpen":false,"startTime":1508930100000,"count":{"praiseCount":711,"commentCount":54,"memberCommentCount":0,"shareCount":17,"quoteCount":0},"isLike":false,"groupId":10},{"liveId":"59e6c4df0cf23fa04ee4c2fe","title":"《以爱之名》剧场公演","subTitle":"TEAM NII剧场公演","picPath":"/mediasource/live/1508295903659T8a11zK5V9.jpg","isOpen":false,"startTime":1509016500000,"count":{"praiseCount":596,"commentCount":43,"memberCommentCount":0,"shareCount":6,"quoteCount":0},"isLike":false,"groupId":10}]}
	 */

	private int status;
	private String message;
	private ContentBean content;

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

	public ContentBean getContent() {
		return content;
	}

	public void setContent(ContentBean content) {
		this.content = content;
	}

	public static class ContentBean {
		@SerializedName("liveList")
		private List<ShowBean> showList;

		public List<ShowBean> getShowList() {
			return showList;
		}

		public void setShowList(List<ShowBean> showList) {
			this.showList = showList;
		}

		public static class ShowBean {
			/**
			 * liveId : 59e6c4be0cf23fa04ee4c2fd
			 * title : 《第48区》剧场公演
			 * subTitle : TEAM SII剧场公演
			 * picPath : /mediasource/live/1508295870745LpNjGPwh18.jpg
			 * isOpen : false
			 * startTime : 1508930100000
			 * count : {"praiseCount":711,"commentCount":54,"memberCommentCount":0,"shareCount":17,"quoteCount":0}
			 * isLike : false
			 * groupId : 10
			 */

			private String liveId;
			private String title;
			private String subTitle;
			private String picPath;
			private boolean isOpen;
			private long startTime;
			private CountBean count;
			private boolean isLike;
			private int groupId;

			public String getLiveId() {
				return liveId;
			}

			public void setLiveId(String liveId) {
				this.liveId = liveId;
			}

			public String getTitle() {
				return title;
			}

			public void setTitle(String title) {
				this.title = title;
			}

			public String getSubTitle() {
				return subTitle;
			}

			public void setSubTitle(String subTitle) {
				this.subTitle = subTitle;
			}

			public String getPicPath() {
				return picPath;
			}

			public void setPicPath(String picPath) {
				this.picPath = picPath;
			}

			public boolean isIsOpen() {
				return isOpen;
			}

			public void setIsOpen(boolean isOpen) {
				this.isOpen = isOpen;
			}

			public long getStartTime() {
				return startTime;
			}

			public void setStartTime(long startTime) {
				this.startTime = startTime;
			}

			public CountBean getCount() {
				return count;
			}

			public void setCount(CountBean count) {
				this.count = count;
			}

			public boolean isIsLike() {
				return isLike;
			}

			public void setIsLike(boolean isLike) {
				this.isLike = isLike;
			}

			public int getGroupId() {
				return groupId;
			}

			public void setGroupId(int groupId) {
				this.groupId = groupId;
			}

			public static class CountBean {
				/**
				 * praiseCount : 711
				 * commentCount : 54
				 * memberCommentCount : 0
				 * shareCount : 17
				 * quoteCount : 0
				 */

				private int praiseCount;
				private int commentCount;
				private int memberCommentCount;
				private int shareCount;
				private int quoteCount;

				public int getPraiseCount() {
					return praiseCount;
				}

				public void setPraiseCount(int praiseCount) {
					this.praiseCount = praiseCount;
				}

				public int getCommentCount() {
					return commentCount;
				}

				public void setCommentCount(int commentCount) {
					this.commentCount = commentCount;
				}

				public int getMemberCommentCount() {
					return memberCommentCount;
				}

				public void setMemberCommentCount(int memberCommentCount) {
					this.memberCommentCount = memberCommentCount;
				}

				public int getShareCount() {
					return shareCount;
				}

				public void setShareCount(int shareCount) {
					this.shareCount = shareCount;
				}

				public int getQuoteCount() {
					return quoteCount;
				}

				public void setQuoteCount(int quoteCount) {
					this.quoteCount = quoteCount;
				}
			}
		}
	}
}
