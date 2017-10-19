package io.github.wotaslive.widget;

/**
 * Created by codeczx on 2017/8/29.
 */

public interface MediaPlayerControl {

	void changeOrientation();

	boolean isPlaying();

	void start();

	void pause();

	void stop();

	void release();

	void restart();

	boolean isLive();

	long getDuration();

	long getCurrentPosition();

	void seekTo(long progress);

	void close();
}
