package com.logigear.control.common;

public interface IVideo {

	void play();
	
	void pause();
	
	void mute();
	
	void unmute();
	
	double currentTime();
	
	double duration();
	
	void waitForVideoLoaded();
	
	void waitForVideoLoaded(int timeOutInSeconds);
	
	void waitForVideoPlayed();
	
	void waitForVideoPlayed(int timeOutInSeconds);
	
	void waitForVideoEnded(int timewait);
}
