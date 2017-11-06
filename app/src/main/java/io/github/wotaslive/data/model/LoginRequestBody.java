package io.github.wotaslive.data.model;

/**
 * Created by codeczx on 2017/11/7 0:20.
 * Class description:
 */
public class LoginRequestBody {

	private int latitude;
	private int longitude;
	private String password;
	private String account;

	public LoginRequestBody(int latitude, int longitude, String password, String account) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.password = password;
		this.account = account;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}
