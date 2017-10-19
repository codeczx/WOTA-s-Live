package io.github.wotaslive.widget;

/**
 * Author codeczx
 * Created at 2017/8/29
 */

public interface MediaViewControl {
	void start();

	void pause();

	void startPolling();

	void cancelPolling();
}
