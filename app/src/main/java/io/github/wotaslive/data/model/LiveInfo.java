package io.github.wotaslive.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LiveInfo {
	
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
		
		private long giftUpdTime;
		@SerializedName("liveList")
		private List<RoomBean> liveList;
		@SerializedName("reviewList")
		private List<RoomBean> reviewList;
		private List<?> giftUpdUrl;
		private List<Integer> hasReviewUids;
		
		public long getGiftUpdTime() {
			return giftUpdTime;
		}
		
		public void setGiftUpdTime(long giftUpdTime) {
			this.giftUpdTime = giftUpdTime;
		}
		
		public List<RoomBean> getLiveList() {
			return liveList;
		}
		
		public void setLiveList(List<RoomBean> liveList) {
			this.liveList = liveList;
		}
		
		public List<RoomBean> getReviewList() {
			return reviewList;
		}
		
		public void setReviewList(List<RoomBean> reviewList) {
			this.reviewList = reviewList;
		}
		
		public List<?> getGiftUpdUrl() {
			return giftUpdUrl;
		}
		
		public void setGiftUpdUrl(List<?> giftUpdUrl) {
			this.giftUpdUrl = giftUpdUrl;
		}
		
		public List<Integer> getHasReviewUids() {
			return hasReviewUids;
		}
		
		public void setHasReviewUids(List<Integer> hasReviewUids) {
			this.hasReviewUids = hasReviewUids;
		}
		
		public static class RoomBean {
			/**
			 * liveId : 59de13f10cf294bc616de87e
			 * title : 吕梦莹的直播间
			 * subTitle : 日常写作业🐷
			 * picPath : /mediasource/live/15061690159861h5h22LAZ0.jpg
			 * startTime : 1507726321749
			 * memberId : 286982
			 * liveType : 1
			 * picLoopTime : 0
			 * lrcPath : /mediasource/live/lrc/59de13f10cf294bc616de87e.lrc
			 * streamPath : http://2519.liveplay.myqcloud.com/live/2519_3145421.flv
			 * screenMode : 0
			 */
			
			private String liveId;
			private String title;
			private String subTitle;
			private String picPath;
			private long startTime;
			private int memberId;
			private int liveType;
			private int picLoopTime;
			private String lrcPath;
			private String streamPath;
			private int screenMode;
			
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
			
			public long getStartTime() {
				return startTime;
			}
			
			public void setStartTime(long startTime) {
				this.startTime = startTime;
			}
			
			public int getMemberId() {
				return memberId;
			}
			
			public void setMemberId(int memberId) {
				this.memberId = memberId;
			}
			
			public int getLiveType() {
				return liveType;
			}
			
			public void setLiveType(int liveType) {
				this.liveType = liveType;
			}
			
			public int getPicLoopTime() {
				return picLoopTime;
			}
			
			public void setPicLoopTime(int picLoopTime) {
				this.picLoopTime = picLoopTime;
			}
			
			public String getLrcPath() {
				return lrcPath;
			}
			
			public void setLrcPath(String lrcPath) {
				this.lrcPath = lrcPath;
			}
			
			public String getStreamPath() {
				return streamPath;
			}
			
			public void setStreamPath(String streamPath) {
				this.streamPath = streamPath;
			}
			
			public int getScreenMode() {
				return screenMode;
			}
			
			public void setScreenMode(int screenMode) {
				this.screenMode = screenMode;
			}
		}
	}
}
