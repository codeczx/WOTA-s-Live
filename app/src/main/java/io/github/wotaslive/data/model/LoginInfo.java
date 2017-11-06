package io.github.wotaslive.data.model;

import java.util.List;

/**
 * Created by codeczx on 2017/11/7 0:19.
 * Class description:
 */
public class LoginInfo {

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

		private UserInfoBean userInfo;
		private String token;
		private boolean todayPunchCard;
		private List<BindInfoBean> bindInfo;
		private List<Integer> friends;
		private List<Integer> functionIds;

		public UserInfoBean getUserInfo() {
			return userInfo;
		}

		public void setUserInfo(UserInfoBean userInfo) {
			this.userInfo = userInfo;
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public boolean isTodayPunchCard() {
			return todayPunchCard;
		}

		public void setTodayPunchCard(boolean todayPunchCard) {
			this.todayPunchCard = todayPunchCard;
		}

		public List<BindInfoBean> getBindInfo() {
			return bindInfo;
		}

		public void setBindInfo(List<BindInfoBean> bindInfo) {
			this.bindInfo = bindInfo;
		}

		public List<Integer> getFriends() {
			return friends;
		}

		public void setFriends(List<Integer> friends) {
			this.friends = friends;
		}

		public List<Integer> getFunctionIds() {
			return functionIds;
		}

		public void setFunctionIds(List<Integer> functionIds) {
			this.functionIds = functionIds;
		}

		public static class UserInfoBean {
			/**
			 * userId : 597267
			 * nickName : 15625053113
			 * pocketId :
			 * avatar : /mediasource/profile_icon.png
			 * experience : 5
			 * level : 1
			 * gender : 0
			 * punchCardDay : 0
			 * role : 0
			 */

			private int userId;
			private String nickName;
			private String pocketId;
			private String avatar;
			private int experience;
			private int level;
			private int gender;
			private int punchCardDay;
			private int role;

			public int getUserId() {
				return userId;
			}

			public void setUserId(int userId) {
				this.userId = userId;
			}

			public String getNickName() {
				return nickName;
			}

			public void setNickName(String nickName) {
				this.nickName = nickName;
			}

			public String getPocketId() {
				return pocketId;
			}

			public void setPocketId(String pocketId) {
				this.pocketId = pocketId;
			}

			public String getAvatar() {
				return avatar;
			}

			public void setAvatar(String avatar) {
				this.avatar = avatar;
			}

			public int getExperience() {
				return experience;
			}

			public void setExperience(int experience) {
				this.experience = experience;
			}

			public int getLevel() {
				return level;
			}

			public void setLevel(int level) {
				this.level = level;
			}

			public int getGender() {
				return gender;
			}

			public void setGender(int gender) {
				this.gender = gender;
			}

			public int getPunchCardDay() {
				return punchCardDay;
			}

			public void setPunchCardDay(int punchCardDay) {
				this.punchCardDay = punchCardDay;
			}

			public int getRole() {
				return role;
			}

			public void setRole(int role) {
				this.role = role;
			}
		}

		public static class BindInfoBean {

			private int type;
			private String thirdName;

			public int getType() {
				return type;
			}

			public void setType(int type) {
				this.type = type;
			}

			public String getThirdName() {
				return thirdName;
			}

			public void setThirdName(String thirdName) {
				this.thirdName = thirdName;
			}
		}
	}
}
